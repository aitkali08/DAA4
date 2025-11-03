package graph.model;

public class Edge {
    private final graph.model.Vertex source;
    private final graph.model.Vertex destination;
    private final int weight;

    public Edge(graph.model.Vertex source, graph.model.Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public graph.model.Vertex getSource() { return source; }
    public graph.model.Vertex getDestination() { return destination; }
    public int getWeight() { return weight; }
}