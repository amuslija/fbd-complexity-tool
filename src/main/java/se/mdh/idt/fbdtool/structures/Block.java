package se.mdh.idt.fbdtool.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class Block extends FBDElement {
  private int id;
  private List<Integer> references;
  private List<String> pins;

  public Block() {
    this.references = new ArrayList<>();
    this.pins = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Integer> getReferences() {
    return references;
  }

  public List<String> getPins() {
    return pins;
  }
}
