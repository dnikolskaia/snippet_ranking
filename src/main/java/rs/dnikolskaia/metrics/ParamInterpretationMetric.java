package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.List;


// Metric to score parameters of the method. Scores how easy to interpret parameters.
// TODO: store param type scores as constants
// TODO: for call get recursion score?
public class ParamInterpretationMetric implements Metric{
    @Override
    public double score(Snippet snippet) {
        List<Usage.Parameter> params = snippet.getUsage().context().parameters();
        double score = 0.0;

        if (params.isEmpty())
            return 1.0;

        for (Usage.Parameter param : params) {
            if (param instanceof Usage.LiteralParameter)
                score += 1.0;
            if (param instanceof Usage.NewParameter)
                score += 0.8;

            if (param instanceof Usage.ExpressionParameter expressionParameter) {
                String expressionType = expressionParameter.parameter().type().name();
                // TODO: Add numeric type score as ~0.5, I don't know yet how they described
                if (expressionType.equals("String"))
                    score += 0.5;
            }
            if (param instanceof Usage.VariableParameter variableParameter) {
                boolean initialized = variableParameter.parameter().variable().initialized();
                if (initialized)
                    score += 1.0;
            }
        }
        return score / params.size();
    }
}
