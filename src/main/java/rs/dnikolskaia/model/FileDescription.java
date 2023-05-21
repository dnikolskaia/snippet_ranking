package rs.dnikolskaia.model;

import java.util.List;

public record FileDescription(
        String text,
        List<Usage> usages
) {
}
