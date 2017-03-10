package se.mdh.idt.fbdtool.metrics;

import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ado_4 on 3/6/2017.
 */
public class IFCMetricTest {
  DOM4JParser xmlParser;
  IFCMetric metric;

  @Before
  public void setUp() throws Exception {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    xmlParser = new DOM4JParser(url, "config.properties");
    metric = new IFCMetric();
  }

  @Test
  public void measureProjectComplexity() throws Exception {
    Project project = xmlParser.extractFBDProject();
    HashMap<String, Double> results = metric.measureProjectComplexity(project);
    assertThat(results.get("InformationFlow:Volume"), is(20400.0));
    assertThat(results.get("InformationFlow:Norm"), is(243.0));
  }

  @Test
  public void measurePOUComplexity() throws Exception {
    POU pou = xmlParser.extractProjectPOUs().get(0);
    HashMap<String, Double> results = metric.measurePOUComplexity(pou);
    assertThat(results.get("InformationFlow:Volume"), is(20400.0));
  }


}