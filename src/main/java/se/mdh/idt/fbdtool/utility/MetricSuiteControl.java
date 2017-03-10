package se.mdh.idt.fbdtool.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ado_4 on 3/10/2017.
 */
public class MetricSuiteControl {
  public static void filterPLCProjects(String folderPath) {
    File dir = new File(folderPath);
    List<File> fileList = Arrays.asList(dir.listFiles());
    fileList = fileList.stream().filter(f -> f.isFile() && f.getName().contains(".plc")).collect(Collectors.toList());
  }


}
