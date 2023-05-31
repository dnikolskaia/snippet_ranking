package rs.dnikolskaia.metrics;

import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;
import rs.dnikolskaia.model.Usage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.*;

class ChainFunctionsPopularityMetricTest {
    @Test
    public void testScore(){
        var method1 = createMethod("method1");
        var method2 = createMethod("method2");
        var method3 = createMethod("method3");

        var chain1 = createChain(List.of(method1));
        var chain2 = createChain(List.of(method1, method2));
        var chain3 = createChain(List.of(method1, method3, method1));

        Snippet snippet1 = createTestSnippet(chain1);
        Snippet snippet2 = createTestSnippet(chain2);
        Snippet snippet3 = createTestSnippet(chain3);
        Snippet snippet4 = createTestSnippet((Usage.Chain) null);

        Metric metric = new ChainFunctionsPopularityMetric(List.of(snippet1, snippet2, snippet3, snippet4));

        // snippet1 chain: (method1), method1 appears in 3 out of 4 snippets
        assertEquals(0.75, metric.score(snippet1));

        // snippet2 chain (method1, method2), method1 appears in 3 out of 4 snippets, method2 in 1 out of 4
        // normalized score (0.75 + 0.25) / 2 = 0.5
        assertEquals(0.5, metric.score(snippet2));

        // snippet4 has no chain as 1 out of 4 snippets
        assertEquals(0.25, metric.score(snippet4));
    }
}