package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.*;

class ParamInterpretationMetricTest {
    @Test
    public void testSingleParameter() {
        Metric metric = new ParamInterpretationMetric();
        Snippet snippet = createTestSnippet(List.of(createLiteralParameter()));

        assertEquals(ParamInterpretationMetric.LITERAL_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createVariableParameter(true)));
        assertEquals(ParamInterpretationMetric.INITIALIZED_VARIABLE_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createVariableParameter(false)));
        assertEquals(ParamInterpretationMetric.UNINITIALIZED_VARIABLE_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createExpressionParameter("some type")));
        assertEquals(ParamInterpretationMetric.OTHER_EXPRESSION_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createExpressionParameter("String")));
        assertEquals(ParamInterpretationMetric.STRING_EXPRESSION_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createNewParameter()));
        assertEquals(ParamInterpretationMetric.NEW_PARAMETER_SCORE, metric.score(snippet));

        snippet = createTestSnippet(List.of(createCallParameter()));
        assertEquals(ParamInterpretationMetric.CALL_PARAMETER_SCORE, metric.score(snippet));

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
        double expectedScore = (ParamInterpretationMetric.LITERAL_PARAMETER_SCORE
            + ParamInterpretationMetric.INITIALIZED_VARIABLE_PARAMETER_SCORE
            + ParamInterpretationMetric.CALL_PARAMETER_SCORE) / parameters.size();

        assertEquals(expectedScore, actualScore, 0.0001);
    }
}