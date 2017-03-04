package se.mdh.idt.fbdtool.structures;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class Variable extends FBDElement {
  private String value;

  public Variable() {

  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
