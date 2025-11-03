package graph.topo;

import graph.model.Graph;
import graph.model.Vertex;
import graph.metrics.Metrics;

import java.util.*;

public class KahnTopologicalSort {
    private Metrics metrics;

    public KahnTopologicalSort() {
        this.metrics = new Metrics();
    }

    public List<Vertex> topologicalSort(Graph graph) {
        metrics.startTimer();
        metrics.reset();

        Map<Vertex, Integer> inDegree = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();
        List<Vertex> result = new ArrayList<>();

        for (Vertex vertex : graph.getVertices()) {
            inDegree.put(vertex, vertex.getIncomingEdges().size());
            if (vertex.getIncomingEdges().isEmpty()) {
                queue.offer(vertex);
                metrics.incrementQueueOperations();
            }
        }

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            metrics.incrementQueueOperations();
            result.add(current);

            for (graph.model.Edge edge : current.getOutgoingEdges()) {
                Vertex neighbor = edge.getDestination();
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                metrics.incrementEdgeRelaxations();

                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                    metrics.incrementQueueOperations();
                }
            }
        }

        metrics.stopTimer();

        if (result.size() != graph.getVertexCount()) {
            throw new IllegalArgumentException("Graph has cycles, topological sort not possible");
        }

        return result;
    }

    public Metrics getMetrics() {
        return metrics;
    }

}