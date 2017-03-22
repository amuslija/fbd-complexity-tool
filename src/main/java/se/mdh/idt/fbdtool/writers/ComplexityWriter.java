package se.mdh.idt.fbdtool.writers;

import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;
import se.mdh.idt.fbdtool.utility.MetricSuite;

/**
 * Created by ado_4 on 3/11/2017.
 */
public interface ComplexityWriter {
  boolean write(Project p, boolean close);

  boolean write(POU pou, boolean close);

  boolean write(MetricSuite suite, boolean close);

  boolean close();
}
