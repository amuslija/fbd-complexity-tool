package se.mdh.idt.fbdtool.metrics;

import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class CCMetricTest {

  DOM4JParser xmlParser;
  CCMetric metric;

  @Before
  public void loadConfiguration() throws DocumentException {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    xmlParser = new DOM4JParser(url, "config.properties");
    metric = new CCMetric();
  }


  @Test
  public void measureProjectComplexity() {
    Project project = xmlParser.extractFBDProject();
    String[] keywords = {"Norm"};
    int[] weights = {1};
    metric.addNewKeywords(keywords, weights);
    HashMap<String, Double> results = metric.measureProjectComplexity(project);

    assertThat(results.get("CyclomaticNumber:Volume"), is(5.0));
    assertThat(results.get("CyclomaticNumber:Norm"), is(2.0));
  }

  @Test
  public void measurePOUComplexity() {
    List<POU> pous = xmlParser.extractProjectPOUs();
    HashMap<String, Double> results = metric.measurePOUComplexity(pous.get(1));
    assertThat(results.get("CyclomaticNumber:Norm"), is(2.0));
  }

}