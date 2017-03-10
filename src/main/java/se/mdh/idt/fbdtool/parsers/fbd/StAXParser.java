package se.mdh.idt.fbdtool.parsers.fbd;

import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;
import se.mdh.idt.fbdtool.structures.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.List;

/**
 * Created by ado_4 on 3/10/2017.
 */
public class StAXParser implements FBDParser {

  private void parse(String name) throws XMLStreamException {
    InputStream xml = getClass().getResourceAsStream(name);
    XMLInputFactory2 inputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
    inputFactory.configureForSpeed();
    XMLStreamReader2 streamReader = (XMLStreamReader2) inputFactory.createXMLStreamReader(xml);
    while (streamReader.hasNext()) {
      int eventType = streamReader.nextTag();
      switch (eventType) {
        case XMLEvent.START_ELEMENT:
          break;
        default:
          break;
      }
    }
  }

  @Override
  public Project extractFBDProject() {
    return null;
  }

  @Override
  public List<POU> extractProjectPOUs() {
    return null;
  }

  @Override
  public List<DataType> extractProjectDataTypes() {
    return null;
  }

  @Override
  public List<Variable> extractPOUVariables(String POU) {
    return null;
  }

  @Override
  public List<Variable> extractProjectVariables() {
    return null;
  }

  @Override
  public List<Block> extractPOUBlocks(String POU) {
    return null;
  }

  @Override
  public List<Block> extractProjectBlocks() {
    return null;
  }
}
