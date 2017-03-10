package se.mdh.idt.fbdtool.metrics;

import org.junit.Before;
import org.junit.Test;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

/**
 * Created by ado_4 on 3/7/2017.
 */
public class HalsteadMetricTest {
  DOM4JParser xmlParser;
  HalsteadMetric metric;

  @Before
  public void setUp() throws Exception {
    String url = getClass().getResource("/test1/plc.xml").getPath();
    xmlParser = new DOM4JParser(url, "config.properties");
    metric = new HalsteadMetric();
  }

  @Test
  public void measureProjectComplexity() throws Exception {
    Project project = xmlParser.extractFBDProject();
    HashMap<String, Double> results = metric.measureProjectComplexity(project);

    assertThat(results.get("ProgramVocabulary"), is(closeTo(78.0, 0.01)));
    assertThat(results.get("ProgramLength"), is(closeTo(129.0, 0.01)));
    assertThat(results.get("CalculatedProgramLength"), is(closeTo(423.31, 0.01)));
    assertThat(results.get("Volume"), is(closeTo(810.81, 0.01)));
    assertThat(results.get("Difficulty"), is(closeTo(14.73, 0.01)));
    assertThat(results.get("Effort"), is(closeTo(11945.07, 0.01)));
    assertThat(results.get("Time"), is(closeTo(663.61, 0.01)));
    assertThat(results.get("DeliveredBugs"), is(closeTo(0.174, 0.01)));
  }

  @Test
  public void measurePOUComplexity() throws Exception {
    Project project = xmlParser.extractFBDProject();
    HashMap<String, Double> results = metric.measurePOUComplexity(project.getPOUs().get(1));
    assertThat(results.get("ProgramVocabulary"), is(closeTo(30.0, 0.01)));
    assertThat(results.get("ProgramLength"), is(closeTo(44.0, 0.01)));
    assertThat(results.get("CalculatedProgramLength"), is(closeTo(118.76, 0.01)));
    assertThat(results.get("Volume"), is(closeTo(215.90, 0.01)));
    assertThat(results.get("Difficulty"), is(closeTo(7.526, 0.01)));
    assertThat(results.get("Effort"), is(closeTo(1624.95, 0.01)));
    assertThat(results.get("Time"), is(closeTo(90.27, 0.01)));
    assertThat(results.get("DeliveredBugs"), is(closeTo(0.046, 0.01)));
  }
}