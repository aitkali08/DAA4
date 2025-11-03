package graph.model;

import graph.model.Edge;

import java.util.*;

public class Vertex {
    private final String id;
    private final int duration;
    private final List<Edge> incomingEdges;
    private final List<Edge> outgoingEdges;

    public Vertex(String id, int duration) {
        this.id = id;
        this.duration = duration;
        this.incomingEdges = new ArrayList<>();
        this.outgoingEdges = new ArrayList<>();
    }

    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
    }

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public String getId() { return id; }
    public int getDuration() { return duration; }
    public List<Edge> getIncomingEdges() { return incomingEdges; }
    public List<Edge> getOutgoingEdges() { return outgoingEdges; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}