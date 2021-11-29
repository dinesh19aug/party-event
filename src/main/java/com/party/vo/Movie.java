package com.party.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neo4j.driver.types.Node;

@Data
@AllArgsConstructor
public class Movie {
    public String title;
    public static Movie from(Node node) {
        return new Movie(node.get("title").asString());
    }

}
