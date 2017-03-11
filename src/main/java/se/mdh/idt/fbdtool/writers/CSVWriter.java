package se.mdh.idt.fbdtool.writers;

import se.mdh.idt.fbdtool.structures.POU;
import se.mdh.idt.fbdtool.structures.Project;
import se.mdh.idt.fbdtool.utility.MetricSuite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ado_4 on 3/11/2017.
 */
public class CSVWriter implements ComplexityWriter {
  private BufferedWriter writer;
  private List<String> headerList;
  public static final String DELIMITER = ",";
  public static final String NEWLINE = "\n";

  public CSVWriter(String outputFile, List<String> header) {
    try {
      this.writer = new BufferedWriter(new FileWriter(outputFile));
      StringBuilder headerRow = new StringBuilder();
      this.headerList = header;
      for (String s : header){
        headerRow.append(s + DELIMITER);

      }
      headerRow.deleteCharAt(headerRow.length() - 1);
      headerRow.append(NEWLINE);
      this.writer.write(headerRow.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean write(Project p, boolean close) {
    return false;
  }

  @Override
  public boolean write(POU pou, boolean close) {
    return false;
  }

  @Override
  public boolean write(MetricSuite suite, boolean close) {
    StringBuilder row = new StringBuilder();
    row.append(suite.getName() + DELIMITER);

    HashMap<String, Double> results = suite.getResults();
    for (String s : this.headerList) {
      row.append(results.get(s) + DELIMITER);
    }
    row.deleteCharAt(row.length() - 1);
    row.append(NEWLINE);
    boolean success = false;
    try {
      this.writer.write(row.toString());
      if (close) {
        writer.flush();
        writer.close();
      }
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  @Override
  public boolean close() {
    boolean success = false;
    try {
      writer.flush();
      writer.close();
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return  success;
  }
}
