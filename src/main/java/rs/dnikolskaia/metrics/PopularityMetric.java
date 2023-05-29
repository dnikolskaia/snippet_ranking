package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PopularityMetric implements Metric {
    private final int snippetsAmount;
    private final int emptyCount;
    private final Map<String, Integer> usagesCount;

    public PopularityMetric(List<Snippet> snippets) {
        snippetsAmount = snippets.size();
        usagesCount = new HashMap<>();
        int counter = 0;
        for (Snippet snippet : snippets) {
            List<Usage.Method> methods = getMethods(snippet);
            if (methods.isEmpty()) {
                counter++;
            } else {
                for (var method : methods) {
                    String fullFunctionName = getFullFunctionName(method);
                    usagesCount.compute(fullFunctionName, (func, count) -> count == null ? 1 : count + 1);
                }
            }
        }
        emptyCount = counter;
    }

    @Override
    public double score(Snippet snippet) {
        List<Usage.Method> methods = getMethods(snippet);
        if (methods.isEmpty())
            return emptyCount * 1.0 / snippetsAmount;

        double score = 0;
        for (var method : methods) {
            int chainFunctionUsagesCount = usagesCount.getOrDefault(getFullFunctionName(method),0);
            score += chainFunctionUsagesCount * 1.0 / snippetsAmount;
        }
        return score / methods.size();
    }

    private static String getFullFunctionName(Usage.Method method) {
        String functionName = method.signature().name();
        String fullClassName = method.address().clazz().packazh()
                + '.' + method.address().clazz().name();
        return fullClassName + '.' + functionName;
    }

    abstract List<Usage.Method> getMethods(Snippet snippet);

}
