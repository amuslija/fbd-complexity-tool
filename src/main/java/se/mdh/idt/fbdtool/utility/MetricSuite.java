package se.mdh.idt.fbdtool.utility;

import org.dom4j.DocumentException;
import se.mdh.idt.fbdtool.metrics.*;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.parsers.fbd.FBDParser;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


/**
 * Created by ado_4 on 3/10/2017.
 */
public class MetricSuite implements Runnable {

  HashMap<String, Double> results;
  List<ComplexityMetric> metricList;
  private FBDParser parser;
  private Properties config;
  private String filePath;
  private String name;
  private String type;
  private boolean validated = false;

  public MetricSuite(Properties config, String filePath, String name) {
    this.init(config, filePath, name);
  }

  public MetricSuite(Properties config, String filePath, String name, String xsdPath) {
    this.init(config, filePath, name);
    this.validated = XMLProjectValidator.validateProjectFile(filePath, xsdPath);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public HashMap<String, Double> getResults() {
    return results;
  }

  private List<ComplexityMetric> defaultMetrics() {
    ArrayList<ComplexityMetric> metricList = new ArrayList<>();
    metricList.add(new CCMetric());
    metricList.add(new NOEMetric());
    metricList.add(new HalsteadMetric());
    metricList.add(new IFCMetric());
    return metricList;
  }

  private boolean configureSuite(Properties config, String folderPath) {
    try {
      parser = new DOM4JParser(folderPath, config);
    } catch (DocumentException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private HashMap<String, Double> projectComplexity() {
    Project project = this.parser.extractFBDProject();
    this.results = new HashMap<>();
    for (ComplexityMetric metric : this.metricList) {
      this.results.putAll(metric.measureProjectComplexity(project));
    }
    return this.results;
  }

  private void init(Properties config, String filePath, String name) {
    this.config = config;
    this.filePath = filePath;
    this.metricList = this.defaultMetrics();
    this.name = name;
    this.type = "project";
  }

  private HashMap<String, Double> pouComplexity() {
    return null;
  }


  public HashMap<String, Double> measureComplexity(String type) {
    if (type.equals("project")) {
      return this.projectComplexity();
    }

    if (type.equals("pou")) {
      return this.pouComplexity();
    }

    return null;
  }

  @Override
  public void run() {
    this.configureSuite(this.config, this.filePath);
    if (!validated) {
      System.out.println(this.getName() + " " + "does not pass XSD schema validation");
    } else {
      this.measureComplexity(this.type);
    }
  }
}
