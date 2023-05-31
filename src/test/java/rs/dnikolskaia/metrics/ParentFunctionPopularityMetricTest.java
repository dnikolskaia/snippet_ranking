package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.*;

class ParentFunctionPopularityMetricTest {
    @Test
    public void testScore() {
        var parentFunction1 = createParentFunction(createMethod("method1"));
        var parentFunction2 = createParentFunction(createMethod("method2"));
        var parentFunction3 = createParentFunction(createMethod("method3"));

        Snippet snippet1 = createTestSnippet(parentFunction1);
        Snippet snippet2 = createTestSnippet(parentFunction1); // repeat parentFunction1
        Snippet snippet3 = createTestSnippet(parentFunction2);
        Snippet snippet4 = createTestSnippet(parentFunction3);
        Snippet snippet5 = createTestSnippet((Usage.ParentFunction) null); // no parent function

        var snippets = List.of(snippet1, snippet2, snippet3, snippet4, snippet5);

        Metric metric = new ParentFunctionPopularityMetric(snippets);

        // method1 appears in 2 out of 5 snippets
        assertEquals(0.4, metric.score(snippet1));

        // method2 appears in 1 out of 5 snippets
        assertEquals(0.2, metric.score(snippet3));

        // 1 out 5 snippets without parent function
        assertEquals(0.2, metric.score(snippet5));
    }
}