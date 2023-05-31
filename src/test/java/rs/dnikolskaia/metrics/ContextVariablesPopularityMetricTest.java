package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.createTestSnippetWithVariables;
import static rs.dnikolskaia.metrics.TestDataUtil.createVariable;

class ContextVariablesPopularityMetricTest {
    @Test
    public void testScore() {
        var variable1 = createVariable("type1");
        var variable2 = createVariable("type2");
        var variable3 = createVariable("type3");

        Snippet snippet1 = createTestSnippetWithVariables(List.of(variable1, variable2));
        Snippet snippet2 = createTestSnippetWithVariables(List.of(variable1, variable1, variable3));

        Metric metric = new ContextVariablesPopularityMetric(List.of(snippet1, snippet2));

        // type1 appears in 2 out of 2 snippets, type2 in 1 out of 2 snippets
        // (1.0 + 0.5) / 2 = 0.75
        assertEquals(0.75, metric.score(snippet1));

        // type1 appears in 2 out of 2 snippets, type3 in 1 out of 2 snippets
        assertEquals(0.75, metric.score(snippet2));
    }

}