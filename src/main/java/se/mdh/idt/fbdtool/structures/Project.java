package se.mdh.idt.fbdtool.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class Project {
  private String title;
  private List<DataType> dataTypes;
  private List<POU> pous;

  public Project() {
    this.dataTypes = new ArrayList<>();
    this.pous = new ArrayList<>();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<DataType> getDataTypes() {
    return dataTypes;
  }

  public List<POU> getPOUs() {
    return pous;
  }

  public List<Variable> getAllVariables() {
    ArrayList<Variable> variables = new ArrayList<>();

    for(POU pou : this.pous) {
      variables.addAll(pou.getVariables());
    }

    return variables;
  }

  public List<Block> getAllBlocks() {
    ArrayList<Block> blocks = new ArrayList<>();

    for(POU pou : this.pous) {
      blocks.addAll(pou.getBlocks());
    }

    return blocks;
  }


}
