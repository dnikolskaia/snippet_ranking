package rs.dnikolskaia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.SnippetStorage;
import rs.dnikolskaia.model.Usage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<List<FileDescription>> nestedFileDescriptions = objectMapper.readValue(
                    new File("/home/kerenskiy/Downloads/output10.json"),
                    new TypeReference<>() {
                    }
            );

            List<FileDescription> fileDescriptions = nestedFileDescriptions.stream()
                .flatMap(List::stream).toList();

            SnippetStorage snippetStorage = new SnippetStorage();

            for (FileDescription fd : fileDescriptions) {
                fd.usages().forEach(usage -> snippetStorage.addSnippet(new Snippet(usage, fd.text())));
            }

            System.out.println(snippetStorage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}