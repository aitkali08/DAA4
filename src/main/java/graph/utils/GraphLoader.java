package graph.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.model.Graph;

import java.io.File;
import java.io.IOException;

public class GraphLoader {

    public static Graph loadFromJson(String filePath) {
        Graph graph = new Graph();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(new File(filePath));

            // 1. Добавляем вершины
            for (JsonNode v : root.get("vertices")) {
                String id = v.isTextual() ? v.asText() : v.get("id").asText();
                int duration = v.has("duration") ? v.get("duration").asInt() : 1;
                graph.addVertex(id, duration);
            }

            // 2. Добавляем рёбра
            for (JsonNode e : root.get("edges")) {
                String from = e.get("from").asText();
                String to = e.get("to").asText();
                int weight = e.has("weight") ? e.get("weight").asInt() : 1;
                graph.addEdge(from, to, weight);
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении JSON: " + filePath, e);
        }

        return graph;
    }
}
