package rs.dnikolskaia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SnippetStorage implements Iterable<Map.Entry<String, ArrayList<Snippet>>> {
    private final HashMap<String, ArrayList<Snippet>> snippetMap;

    public SnippetStorage() {
        this.snippetMap = new HashMap<>();
    }

    public void addSnippet(Snippet snippet) {
        String methodName = snippet.getFullMethodName();
        ArrayList<Snippet> snippets = snippetMap.getOrDefault(methodName, new ArrayList<>());
        snippets.add(snippet);
        snippetMap.put(methodName, snippets);
    }

    public ArrayList<Snippet> getSnippets(String methodName) {
        return snippetMap.getOrDefault(methodName, new ArrayList<>());
    }

    @Override
    public Iterator<Map.Entry<String, ArrayList<Snippet>>> iterator() {
        return snippetMap.entrySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        snippetMap.forEach((key, value) -> {
            sb.append("Usages of ").append(key).append(":\n");
            value.forEach(snippet -> sb.append(snippet.toString()).append("\n\n"));
        });

        return sb.toString();
    }
}
