package se.mdh.idt.fbdtool.parsers.fbd;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import se.mdh.idt.fbdtool.structures.ASTNode;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParser implements FBDParser {
  private String filePath;
  private String properties;
  private Document doc;
  private Properties config;

  public XMLParser(String filePath, String properties) {
    this.filePath = filePath;
    this.properties = properties;
  }

  private Document loadXMLDocument() throws DocumentException {
    SAXReader reader = new SAXReader();
    File file = new File(this.filePath);
    return reader.read(file);
  }

  private Properties loadProperties() {
    Properties config = new Properties();
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.properties);

    try {
      config.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return config;
  }

  private String getXPath(String element) {
    return "//*[local-name()='" + element + "']";
  }

  private ASTNode extractVariable(Element e, String type) {
    ASTNode node = new ASTNode();
    node.setType(type);
    node.setName(e.attributeValue("name"));
    node.setKey(((Element)e.element("type").elements().get(0)).getName());
    return node;
  }

  private ASTNode addFBDVariables()  {
    ASTNode variables = new ASTNode();
    variables.setType("variables");
    String[] types = config.getProperty("variables.types").split(",");
    String variableTag = config.getProperty("variables.variable");
    for(int i = 0; i < types.length; i++) {
      List elements = doc.selectNodes(getXPath(types[i]) + getXPath(variableTag));
      for(Object o : elements) {
        variables.getChildNodes().add(extractVariable((Element) o, types[i]));
      }
    }
    return variables;
  }

  @Override
  public ASTNode generateAbstractSyntaxTree()  {
    ASTNode node = null;

    try {
      this.doc = this.loadXMLDocument();
      this.config = this.loadProperties();
      node = this.addFBDVariables();
    } catch (DocumentException e) {
      e.printStackTrace();
    }

    return node;
  }
}
