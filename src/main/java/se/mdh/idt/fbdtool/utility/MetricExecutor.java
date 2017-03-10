package se.mdh.idt.fbdtool.utility;

import se.mdh.idt.fbdtool.metrics.ComplexityMetric;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

/**
 * Created by ado_4 on 3/10/2017.
 */
public class MetricExecutor implements Runnable {
  private ComplexityMetric metric;
  private Project project;
  private HashMap<String, Double> results;

  MetricExecutor(ComplexityMetric metric, Project project) {
    this.metric = metric;
    this.project = project;
  }

  public HashMap<String, Double> getResults() {
    return this.results;
  }

  @Override
  public void run() {
    this.results = this.metric.measureProjectComplexity(this.project);
  }
}
