package rs.dnikolskaia.metrics.popularity;

import rs.dnikolskaia.metrics.popularity.FeaturesPopularityMetric;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.ArrayList;
import java.util.List;

public class ParentFunctionPopularityMetric extends FeaturesPopularityMetric {

    public ParentFunctionPopularityMetric(List<Snippet> snippets) {
        super(snippets);
    }

    @Override
    List<Usage.Method> getMethods(Snippet snippet) {
        List<Usage.Method> methods = new ArrayList<>();
        var parentFunction = snippet.getUsage().featuresPack().categoryFeatures().parentFunction();
        if (parentFunction != null)
            methods.add(parentFunction.fact().method());
        return methods;
    }
}
