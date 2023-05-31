package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.List;


/**
 * The ParamInterpretationMetric class evaluates the quality of code snippets
 * based on the ease of interpreting and understanding method parameters.
 */
public class ParamInterpretationMetric implements Metric{
    public static final double LITERAL_PARAMETER_SCORE = 1.0;
    public static final double NEW_PARAMETER_SCORE = 0.5;
    public static final double STRING_EXPRESSION_PARAMETER_SCORE = 0.5;

    public static final double OTHER_EXPRESSION_PARAMETER_SCORE = 0.0;
    public static final double INITIALIZED_VARIABLE_PARAMETER_SCORE = 1.0;
    public static final double UNINITIALIZED_VARIABLE_PARAMETER_SCORE = 0.0;

    public static final double CALL_PARAMETER_SCORE = 0.0;
    @Override
    public double score(Snippet snippet) {
        List<Usage.Parameter> params = snippet.getUsage().context().parameters();
        double score = 0.0;

        if (params.isEmpty())
            return 1.0;

        for (Usage.Parameter param : params) {
            if (param instanceof Usage.LiteralParameter)
                score += LITERAL_PARAMETER_SCORE;
            if (param instanceof Usage.NewParameter)
                score += NEW_PARAMETER_SCORE;

            if (param instanceof Usage.ExpressionParameter expressionParameter) {
                String expressionType = expressionParameter.parameter().type().name();
                if (expressionType.equals("String"))
                    score += STRING_EXPRESSION_PARAMETER_SCORE;
                else
                    score += OTHER_EXPRESSION_PARAMETER_SCORE;
            }
            if (param instanceof Usage.VariableParameter variableParameter) {
                boolean initialized = variableParameter.parameter().variable().initialized();
                if (initialized)
                    score += INITIALIZED_VARIABLE_PARAMETER_SCORE;
                else
                    score += UNINITIALIZED_VARIABLE_PARAMETER_SCORE;
            }
            if (param instanceof  Usage.CallParameter callParameter) {
                score += CALL_PARAMETER_SCORE;
            }
        }
        return score / params.size();
    }
}
