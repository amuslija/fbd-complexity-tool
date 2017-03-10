package se.mdh.idt.fbdtool.metrics;

import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;

import java.util.HashMap;

/**
 * Created by ado_4 on 3/5/2017.
 */
public interface ComplexityMetric {
  HashMap<String, Double> measureProjectComplexity(Project project);

  HashMap<String, Double> measurePOUComplexity(POU pou);
}
