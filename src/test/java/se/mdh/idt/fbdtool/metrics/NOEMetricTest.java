package se.mdh.idt.fbdtool.metrics;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class NOEMetricTest {
  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void measureComplexityOfProject() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    DOM4JParser xmlParser = new DOM4JParser(url, "config.properties");
    Project project = xmlParser.extractFBDProject();
    NOEMetric metric = new NOEMetric();
    HashMap<String, Double> results = metric.measureProjectComplexity(project);
    assertThat(results.get("Variables"), is(18.0));
    assertThat(results.get("DataTypes"), is(1.0));
    assertThat(results.get("Blocks"), is(30.0));
    assertThat(results.get("Connections"), is(30.0));
  }

  @Test
  public void measurecomplexityOfPOU() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    DOM4JParser xmlParser = new DOM4JParser(url, "config.properties");
    POU pou = xmlParser.extractProjectPOUs().get(0);
    NOEMetric metric = new NOEMetric();
    HashMap<String, Double> results = metric.measurePOUComplexity(pou);
    assertThat(results.get("Variables"), is(12.0));
    assertThat(results.get("Blocks"), is(19.0));
    assertThat(results.get("Connections"), is(20.0));
  }

}