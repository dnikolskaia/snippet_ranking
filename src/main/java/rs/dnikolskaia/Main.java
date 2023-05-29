package rs.dnikolskaia;

import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.metrics.*;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.SnippetStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        List<String> jsonArray = new ArrayList<>();
//        try {
//            jsonArray = objectMapper.readValue(new File("/Users/kerenskiy/Downloads/result-5.json"),
//                    new TypeReference<>() {});
//            List<FileDescription> fd = JsonParser.parse(jsonArray, FileDescription.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//


//        jsonArray = JsonParser.removeEscapeSymbols((ArrayList<String>) jsonArray);
//        List<String> filteredJsonArray = JsonParser.getFilteredJsonArray((ArrayList<String>) jsonArray, "runWriteAction(()");
//
//        try {
//            String json = objectMapper.writeValueAsString(filteredJsonArray);
//
//            FileWriter fileWriter = new FileWriter("/Users/kerenskiy/Downloads/lambda-output.json");
//            fileWriter.write(json);
//            fileWriter.close();
//        } catch (Exception ignored) {
//
//        }

//        List<FileDescription> fileDescriptions = nestedFileDescriptions.stream()
//                .flatMap(List::stream).toList();
//
//        SnippetStorage snippetStorage = new SnippetStorage();
//
//        fileDescriptions.forEach(fd ->
//                fd.usages().forEach(usage -> snippetStorage.addSnippet(new Snippet(usage, fd.text())))
//        );
//
//        snippetStorage.forEach(entry -> {
//            ArrayList<Snippet> snippets = entry.getValue();
//            SizeMetric sizeMetric = new SizeMetric(snippets);
//            snippets.sort(Comparator.comparingDouble(sizeMetric::getScore).reversed());
//        });
//
//        snippetStorage.forEach(entry -> {
//            ArrayList<Snippet> snippets = entry.getValue();
//            snippets.sort(Comparator.comparingDouble(ParamMetric::getScore).reversed());
//        });
//
//        System.out.println(snippetStorage);

        FileDescription fileDescription = objectMapper
                .readValue(new File("/Users/kerenskiy/Downloads/good.json"), FileDescription.class);

        SnippetStorage snippetStorage = new SnippetStorage();

        fileDescription.usages()
                .forEach(usage -> snippetStorage
                        .addSnippet(new Snippet(usage, fileDescription.text())));

        snippetStorage.forEach(entry -> {
            ArrayList<Snippet> snippets = entry.getValue();
            PopularityMetric metric = new ParentFunctionPopularityMetric(snippets);
            ParamInterpretationMetric paramInterpretationMetric = new ParamInterpretationMetric();
            SizeMetric sizeMetric = new SizeMetric(snippets);
            Metric contextMetric = new ContextVariablesPopularityMetric(snippets);
            snippets.forEach(snippet -> System.out.println(contextMetric.score(snippet)));
            snippets.sort(Comparator.comparingDouble(metric::score).reversed());
        });

//        System.out.println(snippetStorage);

    }
}