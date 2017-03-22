package se.mdh.idt.fbdtool.utility;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by ado_4 on 3/22/2017.
 */
public class XMLProjectValidator {

  public static Boolean validateProjectFile(String projectFilePath, String schemaPath) {
    if (projectFilePath == null || schemaPath == null) {
      return true;
    }

    File schemaFile = new File(schemaPath);
    Source project = new StreamSource(new File(projectFilePath));

    SchemaFactory schemaFactory = SchemaFactory
            .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      Schema schema = schemaFactory.newSchema(schemaFile);
      Validator validator = schema.newValidator();
      validator.validate(project);
    } catch (SAXException sax) {
      sax.printStackTrace();
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }
}
