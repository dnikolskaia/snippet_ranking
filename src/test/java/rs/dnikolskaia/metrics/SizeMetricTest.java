package rs.dnikolskaia.metrics;


import org.junit.jupiter.api.Test;
import rs.dnikolskaia.model.Snippet;

import static org.junit.jupiter.api.Assertions.*;
import static rs.dnikolskaia.metrics.TestDataUtil.createTestSnippet;

import java.util.ArrayList;
import java.util.List;

class SizeMetricTest {

    @Test
    public void testEmptyList() {
        List<Snippet> snippets = new ArrayList<>();
        SizeMetric sizeMetric = new SizeMetric(snippets);

        Snippet testSnippet = createTestSnippet();
        testSnippet.setText("some text");

        // testSnippet has 1 code line, metric code line range is 0-0
        assertThrows(IllegalArgumentException.class, () -> sizeMetric.score(testSnippet));
    }

    @Test
    public void testSizeOutOfBounds() {
        Snippet snippet1 = createTestSnippet();
        snippet1.setText("first line \n second line \n third line");
        Snippet snippet2 = createTestSnippet();
        snippet2.setText("first line \n second line");
        Snippet snippet3 = createTestSnippet();
        snippet3.setText("first line");
        Snippet snippet4 = createTestSnippet();
        snippet4.setText("first line \n second line \n third line \n fourth line");


        List<Snippet> snippets = List.of(snippet1, snippet2);
        SizeMetric sizeMetric = new SizeMetric(snippets);

        // snippet3 has 1 code lines, metric code line range is 2-3
        assertThrows(IllegalArgumentException.class, () -> sizeMetric.score(snippet3));

        // snippet4 has 4 code lines, metric code line range is 2-3
        assertThrows(IllegalArgumentException.class, () -> sizeMetric.score(snippet3));
    }

    @Test
    public void testSameLinesCount() {
        Snippet snippet1 = createTestSnippet();
        snippet1.setText("first line \n second line");
        Snippet snippet2 = createTestSnippet();
        snippet2.setText("first line \n second line");

        List<Snippet> snippets = List.of(snippet1, snippet2);
        SizeMetric sizeMetric = new SizeMetric(snippets);

        // all snippets have the same size in code lines
        assertEquals(1.0, sizeMetric.score(snippet1));
    }

    @Test
    public void testDifferentLineCounts() {
        Snippet snippet1 = createTestSnippet();
        snippet1.setText("first line \n second line \n third line \n fourth line \n fifth line");
        Snippet snippet2 = createTestSnippet();
        snippet2.setText("first line \n second line");
        Snippet snippet3 = createTestSnippet();
        snippet3.setText("first line");

        List<Snippet> snippets = List.of(snippet1, snippet2, snippet3);
        SizeMetric sizeMetric = new SizeMetric(snippets);

        assertEquals(0.0, sizeMetric.score(snippet1));
        assertEquals(0.75, sizeMetric.score(snippet2));
        assertEquals(1.0, sizeMetric.score(snippet3));
    }
}