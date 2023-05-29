package rs.dnikolskaia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FileDescription(
    String text,
    List<Usage> usages
) {
    @JsonCreator
    public FileDescription(
        String text,
        List<Usage> usages
    ) {
        this.text = text;
        this.usages = usages != null ? usages : new ArrayList<>();
    }
}