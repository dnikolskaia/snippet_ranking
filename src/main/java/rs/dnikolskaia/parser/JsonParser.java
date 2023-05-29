package rs.dnikolskaia.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class JsonParser {

    public static List<String> removeEscapeSymbols(ArrayList<String> jsonArray) {
        return jsonArray.stream()
                .map(element -> element.replace("\\\\", "\\"))
                .map(element -> element.replace("\\\"", "\""))
                .collect(Collectors.toList());
    }

    public static List<String> addEscapeSymbols(ArrayList<String> jsonArray) {
        return jsonArray.stream()
                .map(element -> element.replace("\\", "\\\\"))
                .map(element -> element.replace("\"", "\\\""))
                .collect(Collectors.toList());
    }

    public static List<String> getFilteredJsonArray(ArrayList<String> jsonArray, String substring) {
        return jsonArray.stream()
                .filter(element -> element.contains(substring)).limit(1)
                .collect(Collectors.toList());
    }

    public static <T> List<T> parse(List<String> jsonArray, Class<T> targetType) {
        List<List<T>> parsedNestedArray = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        for (String json : jsonArray) {
            try {
                List<T> parsedObject = objectMapper
                        .readValue(json, typeFactory.constructCollectionType(List.class, targetType));
                parsedNestedArray.add(parsedObject);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error parsing JSON: " + json, e);
            }
        }

        return parsedNestedArray.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
