package se.mdh.idt.fbdtool.utility;

import se.mdh.idt.fbdtool.writers.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by ado_4 on 3/10/2017.
 */
public class SuiteManager {

  private static List<File> fbdProjects;
  private static List<MetricSuite> results;
  private static String defaultConfig = "config.properties";
  private static int threadNumber = 100;
  private static String type;

  public static void filterPLCProjects(String folderPath) {
    File dir = new File(folderPath);
    List<File> fileList = Arrays.asList(dir.listFiles());
    fbdProjects = fileList.stream().filter(f -> f.isFile() && f.getName().contains(".xml")).collect(Collectors.toList());
    System.out.println("Number of FBD projects: " + fbdProjects.size());
  }

  public static void measurePLCMetrics(String config, String xsdValidation) throws IOException, TimeoutException {
    if (fbdProjects.size() == 0) {
      throw new NoSuchFileException("No file to be analyzed");
    }
    Properties props = prepareSuite(config);
    List<String> filter = new ArrayList<>();
    if (props.getProperty("filter") != null) {
      filter = Arrays.asList(props.getProperty("filter").split(","));
    }
    type = props.getProperty("complexity.type");
    ExecutorService service = Executors.newFixedThreadPool(threadNumber);
    boolean finished = false;
    results = new ArrayList<>();
    for (File f : fbdProjects) {
      MetricSuite suite = new MetricSuite(props, f.getPath(), f.getName(), xsdValidation, type);
      if (!filter.contains(suite.getName())) {
        results.add(suite);
        service.execute(suite);
      }
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
    if (type.equals("pou")) {
      headerRow.addAll(results.get(0).getPouResults().get(0).keySet());
    } else {
      headerRow.addAll(results.get(0).getResults().keySet());
    }

    CSVWriter writer = new CSVWriter(output, headerRow);
    for (MetricSuite suite : results) {
      writer.write(suite, type, false);
    }

    writer.close();
  }
}
