# Snippet Ranking Project

This project contains implementation of a snippet ranking algorithm that evaluates the informativeness of code snippets extracted from the IntelliJ Community project.

## Options

The program takes the following command line options:

- `-a,--artifact_path <artifact_path>` : Path to the JSON artifact file.
- `-r,--result_folder_path <result_folder_path>` : Path to the result folder.

These options specify the location of the JSON artifact file and the destination folder for the generated result files.

## Result Structure

The generated result files will be organized in the following folder structure:

```
result_folder
└── package_name
    └── class_name
        ├── method_1.txt
        ├── method_2.txt
        └── ...

```


Each method will have a separate file containing the snippets ranked from the most informative to the least informative.

## Execution Instructions
### Running tests
To run tests use the following command in the root folder of the project:
```bash
mvn test
```

### Executing the program

To process the artifact file, which is about 5GB in size, you may need to increase the heap memory by setting an environment variable.
```bash
export MAVEN_OPTS="-Xmx15g"
```
To execute the program, use the following command in the root folder of the project:
```bash
mvn exec:java -Dexec.args="-a=/path_to_artifact/artifact.json -r=/path_to_result_folder"
```     
## Approach explanation
### Informativeness of Code Snippets
An informative snippet should provide a developer with information on how to use a method. To achieve this goal, an informative snippet should have the following characteristics:

* **Popular**: It should demonstrate a common use of the method.
* **Concise**: It should provide just enough information to understand how to use the method, without being overloaded with unnecessary details. 
* **Clear**: It should be easy to understand and interpret. 

### Metrics
In line with the definition of an informative snippet, I developed metrics that could evaluate the above characteristics.

* **Size Metric** - evaluates the conciseness of a snippet. Smaller snippets will score higher than larger snippets among all of snippets of a given method.


* **Param Interpretation Metric** - evaluates how easy it is to understand the use of a method in a snippet by analysing the method parameters. For example, if the parameter is an initialised variable or literal, I consider it easier to interpret than if the parameter is a call of another function, an expression or an uninitialised variable.


* **Popularity Metrics** - if a method is used in a frequent context among other snippets, it increases the illustrativity of a snippet. Approaching this abstractly, I would cluster snippets into groups, where within each group the snippets are similar in the way they use the method. Then I would assign a score based on the group the snippet belongs to - the larger the group, the higher the snippet's score. I provided several metrics to rank a snippet based on how popular its features are among other snippets, such as parent function, chain functions, previous calls, and types of context variables.
 

* **Combined Metric** - the final score is calculated as a combination of the above metrics. Some metrics may be more important than others. Although the influence of the metric weight on the ranking result is a separate complex task, I assume that popularity is the most significant factor, so its weight is slightly higher.