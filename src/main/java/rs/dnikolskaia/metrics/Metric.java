package rs.dnikolskaia.metrics;

import rs.dnikolskaia.model.Snippet;

public interface Metric {
    double score(Snippet snippet);
}
