package dev.phoenixtype.db2topostgres.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentChangeEvent {
    private int id;
    private String name;

    @JsonProperty("Value")
    private void unpackValueField(JsonNode valueNode) {
        id = valueNode.get("id").asInt();
        name = valueNode.get("name").get("string").asText();
    }
}
