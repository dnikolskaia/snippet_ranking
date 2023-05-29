package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainFunctionsPopularityMetric extends PopularityMetric {

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
