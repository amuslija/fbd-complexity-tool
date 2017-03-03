package se.mdh.idt.fbdtool.parsers.fbd;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import se.mdh.idt.fbdtool.structures.ASTNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParser implements FBDParser {
  private Document doc;
  private Properties config;
  private HashMap<String, List<ASTNode>> fbdElements;

  public XMLParser(String file, String properties) throws DocumentException {
    this.loadConfiguration(file, properties);
    this.fbdElements = new HashMap<String, List<ASTNode>>();
    fbdElements.put("datatypes", new ArrayList<ASTNode>());
    fbdElements.put("variables", new ArrayList<ASTNode>());
    fbdElements.put("blocks", new ArrayList<ASTNode>());
    // fbdElements.put("datatypes", new ArrayList<ASTNode>());
  }

  private Document loadXMLDocument(String filePath) throws DocumentException {
    SAXReader reader = new SAXReader();
    File file = new File(filePath);
    return reader.read(file);
  }

  private Properties loadProperties(String properties) {
    Properties config = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(properties);

    try {
      config.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return config;
  }

  private void loadConfiguration(String file, String properties) throws DocumentException {
    this.doc = this.loadXMLDocument(file);
    this.config = this.loadProperties(properties);
  }

  private ASTNode extractVariable(Element e, String type) {
    ASTNode node = new ASTNode();
    node.setType(type);
    node.setName(e.attributeValue("name"));
    Element key = ((Element) e.element("type").elements().get(0));

    if (key.getName() == "derived") {
      node.setKey(key.attributeValue("name"));
    } else {
      node.setKey(key.getName());
    }

    if (e.element("initialValue") != null) {
      Element value = (Element) e.element("initialValue").elements().get(0);
      node.setValue(value.attributeValue("value"));
    }

    return node;
  }

  private ASTNode extractDataType(Element e) {
    ASTNode node = new ASTNode();
    node.setType("dataType");
    node.setName(e.attributeValue("name"));
    Element key = ((Element) e.element("baseType").elements().get(0));
    node.setKey(key.getName());

    if (e.element("initialValue") != null) {
      Element value = (Element) e.element("initialValue").elements().get(0);
      node.setValue(value.attributeValue("value"));
    }

    return node;
  }

  private void search(Element e, ASTNode ast) {
    for (int i = 0; i < e.nodeCount(); i++) {

      Node n = e.node(i);
      if (n instanceof Element) {
        Element el = (Element) n;
        Element parent = el.getParent();

        if (el.getName().equals(this.config.getProperty("variable"))) {
          if (parent.getName().contains(this.config.getProperty("variables.suffix"))) {
            fbdElements.get("variables").add(extractVariable(el, el.getParent().getName()));
          }
        }

        if (el.getName().equals(this.config.getProperty("datatype"))) {
          fbdElements.get("datatypes").add(extractDataType(el));
        }

        search(el, ast);
      }
    }
  }


  public ASTNode searchForVariables() {
    ASTNode node = new ASTNode();
    search(doc.getRootElement(), node);
    node.getChildNodes().addAll(fbdElements.get("variables"));
    return node;
  }

  public ASTNode searchForDataTypes() {
    ASTNode node = new ASTNode();
    search(doc.getRootElement(), node);
    node.getChildNodes().addAll(fbdElements.get("datatypes"));
    return node;
  }


  @Override
  public ASTNode generateAbstractSyntaxTree() {
    ASTNode node = null;
    return node;
  }
}
