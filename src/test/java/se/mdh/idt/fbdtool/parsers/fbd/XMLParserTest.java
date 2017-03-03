package se.mdh.idt.fbdtool.parsers.fbd;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.structures.ASTNode;

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
  public void addVariablesToTree() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    XMLParser xmlParser = new XMLParser(url, "config.properties");
    ASTNode node = xmlParser.searchForVariables();
    assertThat(node.treeSize(), is(18));
  }

  @Test
  public void addDataTypesToTree() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    XMLParser xmlParser = new XMLParser(url, "config.properties");
    ASTNode node = xmlParser.searchForDataTypes();
    assertThat(node.treeSize(), is(1));
  }
}