package rs.dnikolskaia;

import rs.dnikolskaia.io.FileUtil;
import rs.dnikolskaia.io.OptionsUtil;
import rs.dnikolskaia.metrics.*;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.SnippetStorage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        OptionsUtil optionsUtil = new OptionsUtil(args);
        String artifactPath = optionsUtil.getArtifactPath();
        String resultFolderPath = optionsUtil.getResultFolderPath();

        List<FileDescription> fileDescriptions = FileUtil
            .readFileDescriptions(artifactPath);

        SnippetStorage snippetStorage = new SnippetStorage();

        fileDescriptions
            .forEach(fd -> {
                fd.usages().forEach(usage ->
                    snippetStorage.addSnippet(new Snippet(usage, fd.text())));
            });

        snippetStorage.forEach(entry -> {
            ArrayList<Snippet> snippets = entry.getValue();
            CombinedMetric metric = new CombinedMetric(snippets);
            snippets.sort(Comparator.comparingDouble(metric::score).reversed());
        });


        snippetStorage.forEach(entry -> {
            List<Snippet> snippets = entry.getValue();
            FileUtil.printSnippets(snippets, resultFolderPath);
        });
    }
}