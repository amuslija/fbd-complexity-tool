package se.mdh.idt.fbdtool.metrics;

import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class NOEMetric implements ComplexityMetric {

  static public final String[] keys = {"DataTypes", "Variables", "Blocks", "Connections"};
  public NOEMetric() {}

  private HashMap<String, Double> addKeysToMap() {
    HashMap<String, Double> map = new HashMap<>();
    map.put("DataTypes", 0.0);
    map.put("Variables", 0.0);
    map.put("Blocks", 0.0);
    map.put("Connections", 0.0);
    return map;
  }

  @Override
  public HashMap<String, Double> measureProjectComplexity(Project project) {
    HashMap<String, Double> metric = this.addKeysToMap();
    metric.put("DataTypes", (double) project.getDataTypes().size());
    for (POU pou : project.getPOUs()) {
      metric.put("Variables", metric.get("Variables") + pou.getVariables().size());
      metric.put("Blocks", metric.get("Blocks") + pou.getBlocks().size());
      metric.put("Connections", metric.get("Connections") + pou.getConnections().size());
    }
    return metric;
  }

  @Override
  public HashMap<String, Double> measurePOUComplexity(POU pou) {
    HashMap<String, Double> metric = this.addKeysToMap();
    metric.put("Variables", metric.get("Variables") + pou.getVariables().size());
    metric.put("Blocks", metric.get("Blocks") + pou.getBlocks().size());
    metric.put("Connections", metric.get("Connections") + pou.getConnections().size());
    return metric;
  }
}
