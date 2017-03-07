package se.mdh.idt.fbdtool.metrics;

import se.mdh.idt.fbdtool.structures.Block;
import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class CCMetric implements ComplexityMetric {

  static public final String[] keywords = {"AND","OR","XOR","SEL","MAX","MIN","LIMIT","MUX","GT","GE","EQ","LT","LE","NE"};
  private HashMap<String, Integer> weights;
  private String metricTitle = "CyclomaticNumber";

  public CCMetric() {
    weights = new HashMap<>();
    for (String key : keywords) {
      weights.put(key, 1);
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
      metric.putAll(this.measurePOUComplexity(pou));
    }
    return metric;
  }

  @Override
  public HashMap<String, Double> measurePOUComplexity(POU pou) {
    HashMap<String, Double> metric = new HashMap<>();
    String metricName = metricTitle + ":" + pou.getName();
    List<Block> blocks = pou.getBlocks();

    Stream<Block> decisionBlocksStream = blocks.stream().filter(b -> (weights.get(b.getName()) != null) && (b.getElement().equals("block")));
    List<Integer> decisionBlocks = decisionBlocksStream.map(b -> weights.get(b.getName())).collect(Collectors.toList());
    int cNumber = decisionBlocks.stream().mapToInt(Integer::intValue).sum() + 1;
    metric.put(metricName, (double) cNumber);
    return metric;
  }
}
