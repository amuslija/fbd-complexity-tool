![Tiqva logo](http://i.imgur.com/EfBRP9E.png)

# Tiqva - FBD complexity measurement tool

FBD Complexity Tool is a Java program used for measuring code complexity metrics of IEC 61131-3 Function Block Diagram programs. The tool supports FBD programs saved in the PLC Open TC6  XML schema.

## Requirements
  - Java 1.8 Run Time Environment
  - Maven (for building the project)
## Setup
To build the project, run the following Maven  command:
   `$ mvn clean package`

This will pull all the dependencies from the Maven repository, build the project and create an executable `.jar` in the `\target` directory


## Usage


The tool requires three parameters to successfully perform the complexity metrics measurements:
  - Path to the folder with FBD XML files
  - Path to the configuration file
  - Output file path

Using the provided example PLC project:
```
java -jar ./target/fbd-complexity-tool.jar -c ./examples/config.properties -f ./examples -o results.csv
```
The measurement results will be stored in a CSV file (`results.csv` in the example above).

## Notes

The FBD Complexity Measurement Tool can measure different type of code complexities:
  - Number of Elements
  - Cyclomatic Complexity Number
  - Halstead Metrics
  - Information Flow Metric

The listed complexity metrics are defined for general-purpose programming languages (such as C or Java). Mapping of these complexity metrics (Number of Elements metric relates to the Lines of Code metric for general purpose programming languages) to the FBD syntax has been done and documented in a research paper (link).
