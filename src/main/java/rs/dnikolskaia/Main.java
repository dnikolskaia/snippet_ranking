package rs.dnikolskaia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.metrics.*;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.SnippetStorage;
import rs.dnikolskaia.parser.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> jsonArray = new ArrayList<>();
        try {
            jsonArray = objectMapper.readValue(new File("/home/kerenskiy/Downloads/result-5.json"),
                    new TypeReference<>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FileDescription> fileDescriptions = JsonParser.parse(jsonArray, FileDescription.class);
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
            String fullMethodName = entry.getKey();
            try {
                FileWriter fileWriter = new FileWriter("/home/kerenskiy/Downloads/resultdir/" + fullMethodName);
                String separator = "\n====================\n";
                List<Snippet> snippets = entry.getValue();
                List<String> snippetsText = snippets.stream().map(Snippet::getText).collect(Collectors.toList());
                fileWriter.write(String.join(separator,snippetsText));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}