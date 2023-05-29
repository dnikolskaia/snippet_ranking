package rs.dnikolskaia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FileDescription(
        String text,
        List<Usage> usages
) {
}
