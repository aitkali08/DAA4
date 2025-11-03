package graph.model;

import java.util.*;

public class Graph {
    private final List<graph.model.Vertex> vertices;
    private final List<Edge> edges;
    private final Map<String, Vertex> vertexMap;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.vertexMap = new HashMap<>();
    }

    public void addVertex(String id, int duration) {
        graph.model.Vertex vertex = new graph.model.Vertex(id, duration);
        vertices.add(vertex);
        vertexMap.put(id, vertex);
    }

    public void addEdge(String from, String to, int weight) {
        graph.model.Vertex source = vertexMap.get(from);
        graph.model.Vertex destination = vertexMap.get(to);
        if (source != null && destination != null) {
            Edge edge = new Edge(source, destination, weight);
            edges.add(edge);
            source.addOutgoingEdge(edge);
            destination.addIncomingEdge(edge);
        }
    }

    public graph.model.Vertex getVertex(String id) {
        return vertexMap.get(id);
    }

    public List<graph.model.Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }


}