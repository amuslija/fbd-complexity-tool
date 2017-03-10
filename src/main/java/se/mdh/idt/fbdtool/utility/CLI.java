package se.mdh.idt.fbdtool.utility;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ado_4 on 3/9/2017.
 */
public class CLI {
  public static Options generateCLIOptions() {
    Options options = new Options();
    List<Option> optionList = new ArrayList<Option>();
    optionList.add(new Option("f", "folder", true, "Folder that contains .plc projects."));
    optionList.add(new Option("m", "metrics", true, "List of complexity metrics that will be calculated."));
    optionList.add(new Option("c", "config", true, "Java configuration property file."));
    optionList.add(new Option("o", "output", true, "Output file path"));

    for (Option o : optionList) {
      o.setRequired(true);
      options.addOption(o);
      if (o.getOpt().equals("m")) {
        o.setArgs(Option.UNLIMITED_VALUES);
      }
    }

    return options;
  }

  public static CommandLine parseArguments(String[] args, Options options) {
    CommandLineParser parser = new BasicParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("Following options are required:", options);

      System.exit(1);
      return null;
    }
    return cmd;
  }
}
