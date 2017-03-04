package se.mdh.idt.fbdtool.structures;

/**
 * Created by ado_4 on 3/4/2017.
 */
public class FBDElement {
  private String name;
  private String element;
  private String type;

  public FBDElement() {
  }

  public String getElement() {
    return element;
  }

  public void setElement(String element) {
    this.element = element;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
