package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.*;

public class ContextVariablesPopularityMetric implements Metric{
    private final int snippetsAmount;
    private final int emptyCount;
    private final Map<String, Integer> usageCount;

    public ContextVariablesPopularityMetric(List<Snippet> snippets) {
        snippetsAmount = snippets.size();
        usageCount = new HashMap<>();

        int counter = 0;
        for (Snippet snippet: snippets) {
            var variables = snippet.getUsage().context().variables();
            if (variables.isEmpty())
                counter += 1;
            Set<String> uniqueClassNames = new HashSet<>();
            variables.forEach(variable -> uniqueClassNames.add(getFullClassName(variable)));
            uniqueClassNames.forEach(className ->
                usageCount.compute(className, (func, count) -> count == null ? 1 : count + 1));
            }
        emptyCount = counter;
    }

    private static String getFullClassName(Usage.Variable variable) {
        return variable.type().packazh() + '.' + variable.type().name();
    }

    @Override
    public double score(Snippet snippet) {
        var variables = snippet.getUsage().context().variables();
        if (variables.isEmpty())
            return emptyCount * 1.0 / snippetsAmount;

        double score = 0;
        for (var variable : variables) {
            String fullClassName = getFullClassName(variable);
            score += usageCount.get(fullClassName) * 1.0 / snippetsAmount;
        }
        return score / variables.size();
    }
}
