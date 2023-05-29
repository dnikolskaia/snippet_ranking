package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.ArrayList;
import java.util.List;

public class PreviousCallsFeaturesPopularityMetric extends FeaturesPopularityMetric {

    public PreviousCallsFeaturesPopularityMetric(List<Snippet> snippets) {
        super(snippets);
    }

    @Override
    List<Usage.Method> getMethods(Snippet snippet) {
        List<Usage.Method> methods = new ArrayList<>();
        var previousCalls = snippet.getUsage().featuresPack().previousCalls();
        if (previousCalls != null)
            methods.addAll(previousCalls.fact().methods());
        return methods;
    }
}
