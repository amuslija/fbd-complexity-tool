package se.mdh.idt.fbdtool.parsers.fbd;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.structures.Block;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Variable;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParserTest {
  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void extractVariablesFromXML() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    XMLParser xmlParser = new XMLParser(url, "config.properties");
    List<Variable> variables = xmlParser.extractProjectVariables();
    assertThat(variables.size(), is(18));
  }

  @Test
  public void extractBlocksFromXML() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    XMLParser xmlParser = new XMLParser(url, "config.properties");
    List<Block> blocks = xmlParser.extractProjectBlocks();
    assertThat(blocks.size(), is(29));
  }

  @Test
  public void checkExtractedBlocksForConnections() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    XMLParser xmlParser = new XMLParser(url, "config.properties");
    List<POU> pous = xmlParser.extractProjectPOUs();
    assertThat(pous.get(0).getConnections().size(), is(20));
    assertThat(pous.get(1).getConnections().size(), is(9));

  }

}
