package se.mdh.idt.fbdtool.parsers.fbd;

import se.mdh.idt.fbdtool.structures.*;

import java.util.List;

/**
 * Created by ado_4 on 3/2/2017.
 */
public interface FBDParser {
  public Project extractFBDProject();
  public List<POU> extractProjectPOUs();
  public List<DataType> extractProjectDataTypes();
  public List<Variable> extractPOUVariables(String POU);
  public List<Variable> extractProjectVariables();
  public List<Block> extractPOUBlocks(String POU);
  public List<Block> extractProjectBlocks();

}
