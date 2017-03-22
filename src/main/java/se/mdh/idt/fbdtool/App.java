package se.mdh.idt.fbdtool;

import org.apache.commons.cli.CommandLine;
import se.mdh.idt.fbdtool.utility.CLI;
import se.mdh.idt.fbdtool.utility.SuiteManager;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class App {

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    CommandLine cli = CLI.parseArguments(args, CLI.generateCLIOptions());

    try {
      SuiteManager.filterPLCProjects(cli.getOptionValue("f"));
      SuiteManager.measurePLCMetrics(cli.getOptionValue("c"), cli.getOptionValue("v"));
      SuiteManager.saveMeasurementResults(cli.getOptionValue("o"));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException t) {
      t.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime - startTime);
  }
}
