package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.scorers.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.*;

class ParamInterpretationMetricTest {
    @Test
    public void testSingleParameter() {
        Metric metric = new ParamInterpretationMetric();
        Snippet snippet = createTestSnippet(List.of(createLiteralParameter()));

        assertEquals(LiteralScorer.LITERAL_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createVariableParameter(true)));
        assertEquals(VariableScorer.INITIALIZED_VARIABLE_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createVariableParameter(false)));
        assertEquals(VariableScorer.UNINITIALIZED_VARIABLE_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createExpressionParameter("some type")));
        assertEquals(ExpressionScorer.OTHER_EXPRESSION_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createExpressionParameter("String")));
        assertEquals(ExpressionScorer.STRING_EXPRESSION_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createNewParameter()));
        assertEquals(NewScorer.NEW_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createCallParameter()));
        assertEquals(CallScorer.CALL_PARAMETER_SCORE, metric.score(snippet));

    }

    @Test
    public void testMultipleParameters() {
        Metric metric = new ParamInterpretationMetric();
        var literalParameter = createLiteralParameter();
        var varInitParameter = createVariableParameter(true);
        var callParameter = createCallParameter();

        var parameters = List.of(literalParameter, varInitParameter, callParameter);
        Snippet snippet = createTestSnippet(parameters);

        double actualScore = metric.score(snippet);
        double expectedScore = (LiteralScorer.LITERAL_PARAMETER_SCORE
            + VariableScorer.INITIALIZED_VARIABLE_PARAMETER_SCORE
            + CallScorer.CALL_PARAMETER_SCORE) / parameters.size();

        assertEquals(expectedScore, actualScore, 0.0001);
    }
}