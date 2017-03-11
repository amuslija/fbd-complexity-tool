package se.mdh.idt.fbdtool.utility;

import se.mdh.idt.fbdtool.writers.CSVWriter;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by ado_4 on 3/10/2017.
 */
public class SuiteManager {

  private static List<File> fbdProjects;
  private static List<MetricSuite> results;
  private static String defaultConfig = "config.properties";

  public static void filterPLCProjects(String folderPath) {
    File dir = new File(folderPath);
    List<File> fileList = Arrays.asList(dir.listFiles());
    fbdProjects = fileList.stream().filter(f -> f.isFile() && f.getName().contains(".xml")).collect(Collectors.toList());
  }

  public static void measurePLCMetrics(String config) throws IOException, TimeoutException {
    if (fbdProjects.size() == 0) {
      throw new NoSuchFileException("No file to be analyzed");
    }
    Properties props = prepareSuite(config);
    ExecutorService service = Executors.newFixedThreadPool(fbdProjects.size());
    boolean finished = false;
    results = new ArrayList<>();
    for (File f : fbdProjects) {
      MetricSuite suite = new MetricSuite(props, f.getPath(), f.getName());
      results.add(suite);
      service.execute(suite);
    }

    service.shutdown();
    try {
      finished = service.awaitTermination(1, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (!finished) {
      throw new TimeoutException();
    }
  }

  private static Properties prepareSuite(String configPath) throws IOException {
    InputStream inputStream;
    if (!(new File(configPath).exists())) {
      System.out.println("Provided properties file does not exist. Using builtin config.properties.");
      inputStream = SuiteManager.class.getClassLoader().getResourceAsStream(defaultConfig);
    } else {
      inputStream = new FileInputStream(configPath);
    }
    Properties props = new Properties();
    props.load(inputStream);



    return props;
  }

  public static void saveMeasurementResults(String output) throws Exception {
    List<String> headerRow = new ArrayList<>();
    headerRow.add("Name");
    if (results.size() == 0) {
      throw new Exception("No results found");
    }
    headerRow.addAll(results.get(0).getResults().keySet());

    CSVWriter writer = new CSVWriter(output, headerRow);
    for (MetricSuite suite : results) {
      writer.write(suite, false);
    }

    writer.close();
  }
}
