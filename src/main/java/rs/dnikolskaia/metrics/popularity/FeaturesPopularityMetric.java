package rs.dnikolskaia.metrics.popularity;

import rs.dnikolskaia.metrics.Metric;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.*;

/**
 * The FeaturesPopularityMetric is an abstract class that represents metrics for evaluating the popularity
 * of code snippets based on their feature usages among other snippets.
 * Subclasses of this abstract class provide specific implementations of feature-based metrics.
 */
public abstract class FeaturesPopularityMetric implements Metric {
    private final int snippetsAmount;
    private final int emptyCount;
    private final Map<String, Integer> usagesCount;

    public FeaturesPopularityMetric(List<Snippet> snippets) {
        snippetsAmount = snippets.size();
        usagesCount = new HashMap<>();
        int counter = 0;
        for (Snippet snippet : snippets) {
            List<Usage.Method> methods = getMethods(snippet);
            if (methods.isEmpty()) {
                counter++;
            } else {
                Set<String> uniqueFunctions = new HashSet<>();
                methods.forEach(method -> uniqueFunctions.add(getFullFunctionName(method)));
                uniqueFunctions.forEach(functionName ->
                    usagesCount.compute(functionName, (func, count) -> count == null ? 1 : count + 1));
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
        Set<String> uniqueFunctions = new HashSet<>();
        methods.forEach(method -> uniqueFunctions.add(getFullFunctionName(method)));
        for (var func : uniqueFunctions) {
            int chainFunctionUsagesCount = usagesCount.getOrDefault(func,0);
            score += chainFunctionUsagesCount * 1.0 / snippetsAmount;
        }
        return score / uniqueFunctions.size();
    }

    private static String getFullFunctionName(Usage.Method method) {
        String functionName = method.signature().name();
        String fullClassName = method.address().clazz().packazh()
                + '.' + method.address().clazz().name();
        return fullClassName + '.' + functionName;
    }

    abstract List<Usage.Method> getMethods(Snippet snippet);

}
