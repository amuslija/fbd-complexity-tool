package se.mdh.idt.fbdtool;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import se.mdh.idt.fbdtool.metrics.CCMetricTest;
import se.mdh.idt.fbdtool.metrics.HalsteadMetricTest;
import se.mdh.idt.fbdtool.metrics.IFCMetricTest;
import se.mdh.idt.fbdtool.metrics.NOEMetricTest;

/**
 * Created by ado_4 on 3/9/2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CCMetricTest.class, HalsteadMetricTest.class, IFCMetricTest.class, NOEMetricTest.class})
public final class PackageMetricsSuite {
}
