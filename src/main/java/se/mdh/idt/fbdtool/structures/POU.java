package se.mdh.idt.fbdtool.structures;

import java.util.ArrayList;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class POU extends FBDElement{
  public ArrayList<Variable> getVariables() {
    return variables;
  }

  public void setVariables(ArrayList<Variable> variables) {
    this.variables = variables;
  }

  public ArrayList<Block> getBlocks() {
    return blocks;
  }

  public void setBlocks(ArrayList<Block> blocks) {
    this.blocks = blocks;
  }

  private ArrayList<Variable> variables;
  private ArrayList<Block> blocks;

  public POU() {
    this.variables = new ArrayList<>();
    this.blocks = new ArrayList<>();
  }
}
