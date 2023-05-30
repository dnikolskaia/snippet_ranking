package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;

/**
 * The Metric interface represents a metric for evaluating code snippets.
 * Implementations of this interface provide a score for a code snippet.
 */
public interface Metric {
    double score(Snippet snippet);
}
