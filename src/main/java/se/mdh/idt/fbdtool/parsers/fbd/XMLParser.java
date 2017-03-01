package se.mdh.idt.fbdtool.parsers.fbd;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
/**
 * Created by ado_4 on 2/23/2017.
 */
public class XMLParser {
    private String filePath = null;

    public XMLParser(String filePath) throws InvalidArgumentException {
        if (filePath == null) {
            throw new InvalidArgumentException(new String[]{"Provided file path is null."});
        }
        this.filePath = filePath;
    }

    private Document getDocumentFromURL() throws DocumentException {
        SAXReader reader = new SAXReader();
        File file = new File(this.filePath);
        return reader.read(file);
    }

    public void printContent() {
        try {
            Document document = getDocumentFromURL();
            Element root = document.getRootElement();
            visitNode(root);
        } catch (DocumentException e) {
            e.printStackTrace();
            return;
        }
    }

    public void visitNode(Iterator i) {
        while (i.hasNext()) {
            Node node = (Node) i.next();
            if(node instanceof Element) {

                System.out.println(node.getName() + " attrs: " + ((Element) node).attribute("localId").toString());
                visitNode(((Element) node).nodeIterator());
            }
        }
    }

    public void listAllElements() {
        try {
            Document document = getDocumentFromURL();
            Element root = document.getRootElement();
            visitNode(root.nodeIterator());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void visitNode(Element element) {
        for(int i = 0; i < element.nodeCount(); i++) {
            Node node = element.node(i);
            if(node instanceof Element) {
                Element e = (Element) node;
                if(e.attribute("localId") != null) {
                    System.out.println(e.getName() + " " + e.attribute("localId"));
                }
                visitNode(e);
            }
        }
    }


}
