package se.mdh.idt.fbdtool.parsers.fbd;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import se.mdh.idt.fbdtool.structures.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class DOM4JParser implements FBDParser {
  private Document doc;
  private HashMap<String, String> tags;

  public DOM4JParser(String file, String properties) throws DocumentException {
    this.loadConfiguration(file, properties);
  }

  private Document loadXMLDocument(String filePath) throws DocumentException {
    SAXReader reader = new SAXReader();
    File file = new File(filePath);
    return reader.read(file);
  }

  private Properties loadProperties(String properties) {
    Properties config = new Properties();


    try {
      InputStream inputStream = new FileInputStream(properties);
      config.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return config;
  }

  public void extractConnections(Project project) {
    for (POU pou : project.getPOUs()) {
      for (Block block : pou.getBlocks()) {
        for (Integer ref : block.getReferences()) {
          int[] conn = new int[2];
          conn[0] = ref;
          conn[1] = block.getId();
          pou.getConnections().add(conn);
        }
      }
    }
  }

  private void loadConfiguration(String file, String properties) throws DocumentException {
    this.doc = this.loadXMLDocument(file);
    Properties props = this.loadProperties(properties);

    // Load properties into a String
    this.tags = new HashMap<>();
    this.tags.putAll(props.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
  }

  private Variable extractVariable(Element e, String type) {
    Variable var = new Variable();
    var.setElement(type);
    var.setName(e.attributeValue(this.tags.get("variable.name")));
    Element key = ((Element) e.element(this.tags.get("variable.type")).elements().get(0));

    if (key.getName().equals(this.tags.get("variable.derived"))) {
      var.setType(key.attributeValue("name"));
    } else {
      var.setType(key.getName());
    }

    if (e.element(this.tags.get("variable.value")) != null) {
      Element value = (Element) e.element(this.tags.get("variable.value")).elements().get(0);
      var.setValue(value.attributeValue(this.tags.get("variable.value.attribute")));
    }

    return var;
  }

  private DataType extractDataType(Element e) {
    DataType dataType = new DataType();
    dataType.setElement(this.tags.get("datatype"));
    dataType.setName(e.attributeValue(this.tags.get("datatype.name")));
    Element type = ((Element) e.element(this.tags.get("datatype.base")).elements().get(0));
    dataType.setType(type.getName());

    if (e.element(this.tags.get("datatype.value")) != null) {
      Element value = (Element) e.element(this.tags.get("datatype.value")).elements().get(0);
      dataType.setValue(value.attributeValue(this.tags.get("datatype.value.attribute")));
    }

    return dataType;
  }


  private Block extractBlock(Element e) {
    Block block = new Block();
    block.setElement(e.getName());

    if (e.element(this.tags.get("block.variable")) != null) {
      block.setType("block");
      block.setName(e.element(this.tags.get("block.variable")).getText());
    } else {
      block.setName(e.attributeValue(this.tags.get("block.name")));
      block.setType(block.getName());
    }

    block.setId(Integer.parseInt(e.attributeValue(this.tags.get("block.id"))));

    if (e.getName().equals(this.tags.get("block.output"))) {
      String ref = e.element(this.tags.get("block.conn.point")).element(this.tags.get("block.conn")).attributeValue(this.tags.get("block.ref"));
      block.getReferences().add(Integer.parseInt(ref));
    } else {
      extractBlockVariables(e, block);
    }
    return block;
  }

  private void extractBlockVariables(Element e, Block block) {
    String[] variables = this.tags.get("block.variable.types").toString().split(",");
    for (String varType : variables) {
      Element ins = e.element(varType);
      if (ins != null) {
        for (int i = 0; i < ins.elements().size(); i++) {
          Element vars = (Element) ins.elements().get(i);
          block.getPins().add(varType + " " + vars.getName());
          if (varType.equals(variables[0])) {
            String ref = vars.element(this.tags.get("block.conn.point")).element(this.tags.get("block.conn")).attributeValue(this.tags.get("block.ref"));
            block.getReferences().add(Integer.parseInt(ref));
          }
        }
      }
    }
  }

  private POU extractPOU(Element e) {
    POU pou = new POU();
    pou.setName(e.attributeValue("name"));
    pou.setElement("POU");
    pou.setType(e.attributeValue("pouType"));
    return pou;
  }

  private POU getLastPOU(Project project) {
    if (project.getPOUs().size() != 0) {
      List<POU> pous = project.getPOUs();
      return pous.get(pous.size() - 1);
    }

    return null;
  }

  private void parseXML(Element e, Project project) {
    for (int i = 0; i < e.nodeCount(); i++) {
      Node n = e.node(i);
      if (n instanceof Element) {
        Element el = (Element) n;
        Element parent = el.getParent();
        POU pou = this.getLastPOU(project);

        if (el.getName().equals(this.tags.get("datatype"))) {
          project.getDataTypes().add(extractDataType(el));
        }

        if (el.getName().equals(this.tags.get("pou"))) {
          project.getPOUs().add(extractPOU(el));
        }


        if (el.getName().equals(this.tags.get("variable"))) {
          if (this.tags.get("variables.types").contains(parent.getName())) {
            pou.getVariables().add(extractVariable(el, el.getParent().getName()));
          }
        }

        if (this.tags.get("block.types").contains(el.getName())) {
          pou.getBlocks().add(extractBlock(el));
        }

        parseXML(el, project);
      }
    }
  }

  @Override
  public Project extractFBDProject() {
    Project project = new Project();
    Element root = this.doc.getRootElement();
    parseXML(root, project);
    extractConnections(project);
    return project;
  }

  @Override
  public List<POU> extractProjectPOUs() {
    Project project = this.extractFBDProject();
    return project.getPOUs();
  }

  @Override
  public List<DataType> extractProjectDataTypes() {
    Project project = this.extractFBDProject();
    return project.getDataTypes();
  }

  @Override
  public List<Variable> extractPOUVariables(String POU) {
    List<POU> pous = this.extractProjectPOUs();

    for (POU pou : pous) {
      if (pou.getName().equals(POU)) {
        return pou.getVariables();
      }
    }

    return null;
  }

  @Override
  public List<Variable> extractProjectVariables() {
    List<POU> pous = this.extractProjectPOUs();
    List<Variable> variables = new ArrayList<>();
    for (POU pou : pous) {
      variables.addAll(pou.getVariables());
    }
    if (variables.size() != 0) {
      return variables;
    }
    return null;
  }

  @Override
  public List<Block> extractPOUBlocks(String POU) {
    List<POU> pous = this.extractProjectPOUs();

    for (POU pou : pous) {
      if (pou.getName().equals(POU)) {
        return pou.getBlocks();
      }
    }

    return null;
  }

  @Override
  public List<Block> extractProjectBlocks() {
    List<POU> pous = this.extractProjectPOUs();
    List<Block> blocks = new ArrayList<>();
    for (POU pou : pous) {
      blocks.addAll(pou.getBlocks());
    }
    if (blocks.size() != 0) {
      return blocks;
    }
    return null;
  }
}
