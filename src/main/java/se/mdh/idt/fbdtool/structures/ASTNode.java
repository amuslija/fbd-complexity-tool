package se.mdh.idt.fbdtool.structures;

import java.util.ArrayList;

/**
 * Created by ado_4 on 3/1/2017.
 */
/* Abstract Syntax Tree Implementation
   This data structure will be used for storing the FBD source code that is usually written in XML
 */
public class ASTNode {

  private String type = "";
  private String name = "";
  private String key = "";
  private String value = "";
  private ArrayList<ASTNode> nodes;

  public ASTNode() {
    nodes = new ArrayList<ASTNode>();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int treeSize() {
    int size = 0;
    if (nodes.size() == 0) {
      return 1;
    }

    for (ASTNode node : nodes) {
      size += node.treeSize();
    }

    return size;
  }

  public ArrayList<ASTNode> getChildNodes() {
    return nodes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
