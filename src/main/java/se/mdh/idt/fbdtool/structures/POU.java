package se.mdh.idt.fbdtool.structures;

import java.util.ArrayList;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class POU extends FBDElement {
  private ArrayList<Variable> variables;
  private ArrayList<Block> blocks;
  private ArrayList<int[]> connections;

  public POU() {
    this.variables = new ArrayList<>();
    this.blocks = new ArrayList<>();
    this.connections = new ArrayList<>();
  }

  public ArrayList<int[]> getConnections() {
    return connections;
  }

  public void setConnections(ArrayList<int[]> connections) {
    this.connections = connections;
  }

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
}
