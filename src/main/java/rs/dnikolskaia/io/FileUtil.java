package rs.dnikolskaia.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.parser.JsonParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {
    public static List<FileDescription> readFileDescriptions (String artifactPath) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> jsonArray = new ArrayList<>();
        try {
            File file = new File(artifactPath);
            jsonArray = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JsonParser.parse(jsonArray, FileDescription.class);
    }

    public static void printSnippets(List<Snippet> snippets, String path) {
        if (snippets.isEmpty()) {
            return;
        }

        Snippet snippet = snippets.get(0);
        String directoryPath = path + '/' + snippet.getPackageName() + '/' + snippet.getClassName();
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                try {
                    throw new IOException("Failed to create directory: " + directoryPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        String separator = "\n====================\n";
        String snippetsText = snippets.stream()
            .map(Snippet::getText)
            .collect(Collectors.joining(separator));

        File file = new File(directory, snippet.getFunctionName());
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(snippetsText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
