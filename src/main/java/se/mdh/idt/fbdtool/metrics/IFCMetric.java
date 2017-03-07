package se.mdh.idt.fbdtool.metrics;

import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;
import se.mdh.idt.fbdtool.structures.Variable;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ado_4 on 3/5/2017.
 */
public class IFCMetric implements ComplexityMetric {
  private String metricTitle = "InformationFlow";

  public IFCMetric() {
  }

  private int[] filterIOVariables(List<Variable> variables) {
    int[] results = new int[3];
    for (Variable var : variables) {
      if(var.getElement().equals("inputVars")) {
        results[0]++;
      }
      else if(var.getElement().equals("outputVars")) {
        results[1]++;
      }
      else if(var.getElement().equals("inOutVars")) {
        results[2]++;
      }
    }

    return results;
  }

  private Double calculateInformationFlow(POU pou) {
    int results[] = this.filterIOVariables(pou.getVariables());
    if(pou.getType().equals("function")) {
      results[1]++;
    }
    int pouSize = pou.getBlocks().size() + pou.getConnections().size() + pou.getVariables().size();
    results[0] += results[2];
    results[1] += results[2];
    return pouSize * Math.pow((double) (results[0] * results[1]), 2);
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
    double result = calculateInformationFlow(pou);
    String metricName = this.metricTitle + ":" + pou.getName();
    metric.put(metricName, result);
    return metric;
  }
}