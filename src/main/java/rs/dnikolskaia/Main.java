package rs.dnikolskaia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.metrics.ParamMetric;
import rs.dnikolskaia.metrics.SizeMetric;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.SnippetStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<FileDescription>> nestedFileDescriptions = new ArrayList<>();
        try {
            nestedFileDescriptions = objectMapper.readValue(
                    new File("/Users/kerenskiy/Downloads/output10.json"),
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FileDescription> fileDescriptions = nestedFileDescriptions.stream()
                .flatMap(List::stream).toList();

        SnippetStorage snippetStorage = new SnippetStorage();

        fileDescriptions.forEach(fd ->
                fd.usages().forEach(usage -> snippetStorage.addSnippet(new Snippet(usage, fd.text())))
        );


        snippetStorage.forEach(entry -> {
            ArrayList<Snippet> snippets = entry.getValue();
            SizeMetric sizeMetric = new SizeMetric(snippets);
            snippets.sort(Comparator.comparingDouble(sizeMetric::getScore).reversed());
        });

        snippetStorage.forEach(entry -> {
            ArrayList<Snippet> snippets = entry.getValue();
            snippets.sort(Comparator.comparingDouble(ParamMetric::getScore).reversed());
        });

        System.out.println(snippetStorage);
    }
}