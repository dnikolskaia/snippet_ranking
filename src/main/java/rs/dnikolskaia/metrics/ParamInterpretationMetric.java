package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;
import rs.dnikolskaia.model.scorers.ParameterScorer;

import java.util.List;


/**
 * The ParamInterpretationMetric class evaluates the quality of code snippets
 * based on the ease of interpreting and understanding method parameters.
 */
public class ParamInterpretationMetric implements Metric {
    @Override
    public double score(Snippet snippet) {
        List<Usage.Parameter> params = snippet.getUsage().context().parameters();
        double score = 0.0;

        if (params.isEmpty())
            return 1.0;

        List<ParameterScorer> parameterScorers = params.stream().map(Usage.Parameter::toScorer).toList();
        for (var paramScorer : parameterScorers)
            score += paramScorer.getScore();

        return score / params.size();
    }
}
