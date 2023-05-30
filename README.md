# Snippet Ranking Project

This project implements a snippet ranking algorithm that evaluates the informativeness of code snippets extracted from the IntelliJ Community project.

## Table of Contents

- [Overview](#overview)
- [Options](#options)
- [Result Structure](#result-structure)

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
