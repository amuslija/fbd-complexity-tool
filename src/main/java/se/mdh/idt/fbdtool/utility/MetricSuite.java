package se.mdh.idt.fbdtool.utility;

import org.dom4j.DocumentException;
import se.mdh.idt.fbdtool.metrics.*;
import se.mdh.idt.fbdtool.parsers.fbd.DOM4JParser;
import se.mdh.idt.fbdtool.parsers.fbd.FBDParser;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.*;


/**
 * Created by ado_4 on 3/10/2017.
 */
public class MetricSuite implements Runnable {

  HashMap<String, Double> results;
  List<HashMap<String, Double> > pouResults;
  List<ComplexityMetric> metricList;

  private FBDParser parser;
  private Properties config;
  private String filePath;
  private String name;
  private String type;
  private boolean validated = false;

  public MetricSuite(Properties config, String filePath, String name, String type) {
    this.init(config, filePath, name, type);
  }

  public MetricSuite(Properties config, String filePath, String name, String xsdPath, String type) {
    this.init(config, filePath, name, type);
    this.validated = XMLProjectValidator.validateProjectFile(filePath, xsdPath);
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public HashMap<String, Double> getResults() {
    return this.results;
  }

  public List<HashMap<String, Double> > getPouResults() {
    return this.pouResults;
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

  private void init(Properties config, String filePath, String name, String type) {
    this.config = config;
    this.filePath = filePath;
    this.name = name.split(".xml")[0];
    if (type.equals("pou")) {
      this.type = "pou";
    } else {
      this.type = "project";
    }
    initializeMetrics(config);

  }

  private void initializeMetrics(Properties config) {
    this.metricList = new ArrayList<>();
    List<String> metrics = Arrays.asList(config.getProperty("complexity.metrics").split(","));

    for (String metric : metrics) {
      switch (metric) {
        case "noe":
          this.metricList.add(new NOEMetric());
          break;
        case "hc":
          this.metricList.add(new HalsteadMetric());
          break;
        case "ifc":
          this.metricList.add(new IFCMetric());
          break;
        case "cc":
          CCMetric ccMetric = new CCMetric();
          if (config.getProperty("cyclomatic.keywords") != null && config.getProperty("cyclomatic.weights") != null) {
            String[] keywords = config.getProperty("cyclomatic.keywords").split(",");
            int[] weights = Arrays.stream(config.getProperty("cyclomatic.weights").split(","))
                    .mapToInt(x -> Integer.parseInt(x))
                    .toArray();
            ccMetric.addNewKeywords(keywords, weights);
          }
          this.metricList.add(ccMetric);
          break;
        default:
          throw new IllegalArgumentException();
      }
    }
  }

  private List<HashMap<String, Double> > pouComplexity() {
    Project project = this.parser.extractFBDProject();
    this.pouResults = new ArrayList<>();
    StringBuilder completeName = new StringBuilder();

    for (POU pou : project.getPOUs()) {
      completeName.append(pou.getName() + ",");
      HashMap<String, Double> results = new HashMap<>();
      for (ComplexityMetric metric : this.metricList) {
        results.putAll(metric.measurePOUComplexity(pou));
      }
      this.pouResults.add(results);
    }

    HashMap<String, Double> projectResults = new HashMap<>();
    for (ComplexityMetric metric : this.metricList) {
      projectResults.putAll(metric.measureProjectComplexity(project));
    }
    pouResults.add(projectResults);
    completeName.append(project.getTitle());
    this.name = completeName.toString();
    return this.pouResults;
  }


  public void measureComplexity(String type) {
    if (type.equals("project")) {
      this.projectComplexity();
    }

    if (type.equals("pou")) {
      this.pouComplexity();
    }
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
