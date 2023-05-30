# Snippet Ranking Project

This project implements a snippet ranking algorithm that evaluates the informativeness of code snippets extracted from the IntelliJ Community project.

## Table of Contents

- [Overview](#overview)
- [Options](#options)
- [Result Structure](#result-structure)
- [Approach explaination](#approach-explanation)

## Overview

/TODO: add an overview

## Options

The program takes the following command line options:

- `-a,--artifact_path <artifact_path>`: Path to the JSON artifact file.
- `-r,--result_folder_path <result_folder_path>`: Path to the result folder.

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

## Approach explanation
### Informativeness of Code Snippets
An informative snippet should provide a developer information on how to use a method. To accomplish this goal, an effective snippet should have the following characteristics:

* **Illustrative**: It should demonstrate a common usage of the method.
* **Concise**: It should provide just enough information to understand method usage, without overloading with unnecessary details. 
* **Clear**: It should be easy to understand and interpret. 

### Metrics
Accordingly with informative snippet definition, I attempted to develop metrics that could evaluate the characteristics above.

* **Size Metric** - evaluates the conciseness of a snippet. Smaller snippets are scored higher than larger snippets among all of snippets of a given method.

* **Param Interpretation Metric** - evaluates how easy the usage of a method can be understood in a snippet by analyzing method parameters. For example, if parameter is an initialized variable or literal, I consider it easier to interpret than if parameter is a call of another function, an expression or uninitialized variable. 

 * **Populaarity Metrics** - if a method is used in a frequent among other snippets context, it increases the inllustrativity of a snippet. Approaching this abstractly, I would cluster snippets into groups, where within each group snippets are similar in a way of utilizing the method. Then, I would assign a score based on the group the snippet belongs to - larger the group, higher the snippet score. <!--I've come across research papers describing fingerprinting techniques to evaluate similarity of snippets, however, given a two-week time frame, I decided to make a simpler approach to identify popular usage contexts.  --> I provided several metrics to rank a snippet based on how popular among other snippets its features, such as parent function, chain functions, previous calls and types of context variables.
 
 * **Combined Metric** - the final score is calculated as a combination of metrics above. Some metrics could be more important than others. Though the influence of the metrics weight to the ranking outcome is a separate complex task, I assume that popularity is the most important factor, so its weight is slightly bigger. 
