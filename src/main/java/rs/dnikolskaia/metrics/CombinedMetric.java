package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;

import java.util.ArrayList;
import java.util.List;

/**
 * The CombinedMetric class combines multiple metrics to evaluate the quality of code snippets.
 * It calculates a score by considering the size of the snippet, the ease of interpreting method parameters,
 * and the popularity of method usage among snippets.
 */
public class CombinedMetric implements Metric {
    private final SizeMetric sizeMetric;
    private final ParamInterpretationMetric paramInterpretationMetric;
    private final List<Metric> popularityMetrics;

    public CombinedMetric(List<Snippet> snippets) {
        sizeMetric = new SizeMetric(snippets);
        paramInterpretationMetric = new ParamInterpretationMetric();
        popularityMetrics = createPopularityMetrics(snippets);
    }
    @Override
    public double score(Snippet snippet) {
        double popularityScore = calculatePopularityScore(snippet);
        double paramInterpretationScore = paramInterpretationMetric.score(snippet);
        double sizeScore = sizeMetric.score(snippet);

        return popularityScore + paramInterpretationScore + sizeScore;
    }

    private List<Metric> createPopularityMetrics(List<Snippet> snippets) {
        List<Metric> popularityMetrics = new ArrayList<>();
        popularityMetrics.add(new ChainFunctionsFeaturesPopularityMetric(snippets));
        popularityMetrics.add(new ContextVariablesPopularityMetric(snippets));
        popularityMetrics.add(new ParentFunctionFeaturesPopularityMetric(snippets));
        popularityMetrics.add(new PreviousCallsFeaturesPopularityMetric(snippets));
        return popularityMetrics;
    }

    private double calculatePopularityScore(Snippet snippet) {
        double score = 0;
        for (Metric metric : popularityMetrics) {
            score += metric.score(snippet);
        }
        return score / popularityMetrics.size();
    }
}
