package rs.dnikolskaia.metrics.popularity;

import rs.dnikolskaia.metrics.popularity.FeaturesPopularityMetric;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.ArrayList;
import java.util.List;

public class ChainFunctionsPopularityMetric extends FeaturesPopularityMetric {

    public ChainFunctionsPopularityMetric(List<Snippet> snippets) {
        super(snippets);
    }

    @Override
    List<Usage.Method> getMethods(Snippet snippet) {
        List<Usage.Method> methods = new ArrayList<>();
        var chain = snippet.getUsage().featuresPack().categoryFeatures().chain();
        if (chain != null)
            methods.addAll(chain.fact().methods());
        return methods;
    }

}
