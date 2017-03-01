package se.mdh.idt.fbdtool.parsers.fbd;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void printContent() throws Exception {
        String url = getClass().getResource("/test1/plc.xml").getPath();
        XMLParser xmlParser = new XMLParser(url);
        xmlParser.printContent();
    }
}