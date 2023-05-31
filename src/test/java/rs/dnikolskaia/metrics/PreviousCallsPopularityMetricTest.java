package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.metrics.popularity.PreviousCallsPopularityMetric;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.*;
import static rs.dnikolskaia.metrics.TestDataUtil.createTestSnippet;

class PreviousCallsPopularityMetricTest {
    @Test
    public void testScore(){
        var method1 = createMethod("method1");
        var method2 = createMethod("method2");
        var method3 = createMethod("method3");

        var previousCalls1 = createPreviousCalls(List.of(method1));
        var previousCalls2 = createPreviousCalls(List.of(method1, method2));
        var previousCalls3 = createPreviousCalls(List.of(method1, method3, method1));

        Snippet snippet1 = createTestSnippet(previousCalls1);
        Snippet snippet2 = createTestSnippet(previousCalls2);
        Snippet snippet3 = createTestSnippet(previousCalls3);
        Snippet snippet4 = createTestSnippet((Usage.PreviousCalls) null);

        Metric metric = new PreviousCallsPopularityMetric(List.of(snippet1, snippet2, snippet3, snippet4));

        // snippet1 previous calls: (method1), method1 appears in 3 out of 4 snippets
        assertEquals(0.75, metric.score(snippet1));

        // snippet2 previous calls (method1, method2), method1 appears in 3 out of 4 snippets, method2 in 1 out of 4
        // normalized score (0.75 + 0.25) / 2 = 0.5
        assertEquals(0.5, metric.score(snippet2));

        // snippet4 has no previous calls as 1 out of 4 snippets
        assertEquals(0.25, metric.score(snippet4));
    }

}