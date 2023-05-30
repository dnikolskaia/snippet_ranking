package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;

import java.util.List;


/**
 * The SizeMetric class evaluates code snippet sizes based on the number of code lines.
 * It calculates the score for a given snippet based on its code line count in relation
 * to the maximum and minimum code line counts among a list of snippets.
 */

public class SizeMetric implements Metric {
    private int maxCodeLines;
    private int minCodeLines;

    public SizeMetric(List<Snippet> snippets) {
        maxCodeLines = 0;
        minCodeLines = Integer.MAX_VALUE;
        if (!snippets.isEmpty()) {
            snippets.forEach(snippet -> {
                maxCodeLines = Math.max(maxCodeLines, snippet.getCodeLineCount());
                minCodeLines = Math.min(minCodeLines, snippet.getCodeLineCount());
            });
        }
    }

    @Override
    public double score(Snippet snippet) {
        if (maxCodeLines == minCodeLines) {
            return 1.0; // All snippets have the same size
        }
        return 1.0 - (snippet.getCodeLineCount() - minCodeLines) * 1.0 / maxCodeLines;
    }

}
