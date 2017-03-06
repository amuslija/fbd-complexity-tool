package se.mdh.idt.fbdtool.parsers.fbd;

import se.mdh.idt.fbdtool.structures.*;

import java.util.List;

/**
 * Created by ado_4 on 3/2/2017.
 */
public interface FBDParser {
  Project extractFBDProject();

  List<POU> extractProjectPOUs();

  List<DataType> extractProjectDataTypes();

  List<Variable> extractPOUVariables(String POU);

  List<Variable> extractProjectVariables();

  List<Block> extractPOUBlocks(String POU);

  List<Block> extractProjectBlocks();

}
