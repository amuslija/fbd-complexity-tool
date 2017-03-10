package se.mdh.idt.fbdtool.utility;

import org.dom4j.DocumentException;
import se.mdh.idt.fbdtool.metrics.*;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.parsers.fbd.FBDParser;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ado_4 on 3/10/2017.
 */
public class MetricSuite implements Runnable {
  HashMap<String, Double> results;
  List<ComplexityMetric> metricList;
  private FBDParser parser;
  private String configPath;
  private String filePath;

  public MetricSuite(String configPath, String filePath) {
    this.configPath = configPath;
    this.filePath = filePath;
    this.metricList = defaultMetrics();
  }

  private List<ComplexityMetric> defaultMetrics() {
    ArrayList<ComplexityMetric> metricList = new ArrayList<>();
    metricList.add(new CCMetric());
    metricList.add(new NOEMetric());
    metricList.add(new HalsteadMetric());
    metricList.add(new IFCMetric());
    return metricList;
  }

  public HashMap<String, Double> lastMeasurementResults() {
    return this.results;
  }

  private boolean configureSuite(String configPath, String folderPath) {
    try {
      parser = new DOM4JParser(folderPath, configPath);
    } catch (DocumentException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private HashMap<String, Double> sequential() {
    Project project = parser.extractFBDProject();
    this.results = new HashMap<>();

    for (ComplexityMetric metric : metricList) {
      this.results.putAll(metric.measureProjectComplexity(project));
    }

    System.out.println(java.lang.Thread.activeCount());
    return this.results;
  }


  public HashMap<String, Double> measureComplexity() {
    return this.sequential();
  }

  @Override
  public void run() {
    this.configureSuite(this.configPath, this.filePath);
    this.measureComplexity();
  }
}
