package se.mdh.idt.fbdtool.metrics;

import se.mdh.idt.fbdtool.structures.Block;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class CCMetric implements ComplexityMetric {

  static public final String[] functions = {"AND", "OR", "XOR", "SEL", "MAX", "MIN", "LIMIT", "MUX", "GT", "GE", "EQ", "LT", "LE", "NE", "RS", "SR", "F_TRIG", "R_TRIG"};
  static public final String[] functionBlocks = {"CTU", "CTD", "CTUD", "TOF", "TON", "TP"};
  static public final Integer[] functionBlockWeights = {3, 3, 6, 5, 5, 5};
  private HashMap<String, Integer> weights;
  private String metricTitle = "CyclomaticNumber";

  public CCMetric() {
    weights = new HashMap<>();
    for (String key : functions) {
      weights.put(key, 1);
    }

    for (int i = 0; i < functionBlocks.length; i++) {
      weights.put(functionBlocks[i], functionBlockWeights[i]);
    }
  }

  public void addNewKeywords(String[] keywords, int[] weights) {
    if (keywords.length != weights.length) {
      return;
    }

    for (int i = 0; i < keywords.length; i++) {
      this.weights.put(keywords[i], weights[i]);
    }
  }

  @Override
  public HashMap<String, Double> measureProjectComplexity(Project project) {
    HashMap<String, Double> metric = new HashMap<>();
    for (POU pou : project.getPOUs()) {
      this.analyzePOUComplexity(pou, metric);
    }
    return metric;
  }

  @Override
  public HashMap<String, Double> measurePOUComplexity(POU pou) {
    HashMap<String, Double> metric = new HashMap<>();
    this.analyzePOUComplexity(pou, metric);
    return metric;
  }

  void analyzePOUComplexity(POU pou, HashMap<String, Double> metric) {
    int cComplexity = 0;
    for (Block b : pou.getBlocks()) {
      if (b.getElement().equals("block") && this.weights.get(b.getName()) != null) {
        cComplexity += this.weights.get(b.getName());
      }
    }

    if (metric.containsKey(this.metricTitle) && metric.get(this.metricTitle) < ((double) cComplexity + 1)) {
      metric.put(this.metricTitle, (double) cComplexity + 1);
    } else if (!metric.containsKey(this.metricTitle)) {
      metric.put(this.metricTitle, (double) cComplexity + 1);
    }
  }
}
