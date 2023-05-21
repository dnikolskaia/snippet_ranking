package rs.dnikolskaia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.dnikolskaia.model.FileDescription;
import rs.dnikolskaia.model.Usage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<List<FileDescription>> nestedFileDescriptions = objectMapper.readValue(
                    new File("/Users/kerenskiy/Downloads/output10.json"),
                    new TypeReference<>() {
                    }
            );

            List<FileDescription> fileDescriptions = new ArrayList<>();
            for (List<FileDescription> sublist : nestedFileDescriptions) {
                if (!sublist.isEmpty()) {
                    fileDescriptions.addAll(sublist);
                }
            }

            for (FileDescription fd : fileDescriptions) {
                for (Usage usage : fd.usages()) {
                    System.out.println("Class: " + usage.method().address().path()
                            + " Method: " + usage.method().signature().name());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}