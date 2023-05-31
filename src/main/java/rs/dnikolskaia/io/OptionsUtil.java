package rs.dnikolskaia.io;

import org.apache.commons.cli.*;

public class OptionsUtil {
    private final String[] args;
    private final Options options;
    private final CommandLineParser parser;
    private final HelpFormatter helper;

    private final String USAGE_MSG = "ranks snippets of method usages " +
        "provided in json artifact and stores one file per method to result folder";

    public OptionsUtil(String[] args) {
        this.args = args;
        options = createOptions();
        parser = new DefaultParser();
        helper = new HelpFormatter();
    }

    private Options createOptions() {
        Options options = new Options();
        Option artifactPath = Option.builder("a").longOpt("artifact_path")
            .argName("artifact_path")
            .hasArg()
            .required(true)
            .desc("Path to json artifact").build();

        Option resultFolderPath = Option.builder("r").longOpt("result_folder_path")
            .argName("result folder path")
            .hasArg()
            .required(true)
            .desc("Path to result folder").build();

        options.addOption(artifactPath);
        options.addOption(resultFolderPath);
        return options;
    }

    public String getArtifactPath() {
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("a"))
                return cmd.getOptionValue("artifact_path");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp(USAGE_MSG, options);
            System.exit(0);
        }
        return null;
    }

    public String getResultFolderPath() {
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("r"))
                return cmd.getOptionValue("result_folder_path");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp(USAGE_MSG, options);
            System.exit(0);
        }
        return null;
    }

}
