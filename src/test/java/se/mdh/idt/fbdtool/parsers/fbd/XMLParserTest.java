package se.mdh.idt.fbdtool.parsers.fbd;

import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.structures.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void checkParsedVariablesSize() throws Exception {
      String url = getClass().getResource("/test1/plc.xml").getPath();
      XMLParser xmlParser = new XMLParser(url, "config.properties");
      ASTNode node = xmlParser.generateAbstractSyntaxTree();
      assertThat(node.getTreeSize(), is(19));
    }

    @Test
    public void checkParsedVariableTypes() {
      String url = getClass().getResource("/test1/plc.xml").getPath();
      XMLParser xmlParser = new XMLParser(url, "config.properties");
      ASTNode node = xmlParser.generateAbstractSyntaxTree();
      assertThat(node.getChildNodes().get(0).getKey(), is("SINT"));
      assertThat(node.getChildNodes().get(17).getKey(), is("REAL"));
    }
}