package rs.dnikolskaia.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SnippetStorage {
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<Snippet>> entry : snippetMap.entrySet()) {
            sb.append("Usages of ").append(entry.getKey()).append(":\n");
            entry.getValue().forEach(snippet -> sb.append(snippet.getText()).append("\n\n"));
        }

        return sb.toString();
    }
}
