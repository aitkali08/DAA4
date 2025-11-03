package graph.dagsp;

import graph.model.Graph;
import graph.model.Vertex;
import graph.metrics.Metrics;
import graph.topo.KahnTopologicalSort;

import java.util.*;

public class DAGShortestPath {
    private Metrics metrics;

    public DAGShortestPath() {
        this.metrics = new Metrics();
    }

    public Map<Vertex, Integer> shortestPaths(Graph graph, Vertex source) {
        metrics.startTimer();
        metrics.reset();

        KahnTopologicalSort topoSort = new KahnTopologicalSort();
        List<Vertex> topologicalOrder = topoSort.topologicalSort(graph);

        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(source, 0);

        for (Vertex vertex : topologicalOrder) {
            metrics.incrementDFSVisits();

            if (distances.get(vertex) != Integer.MAX_VALUE) {
                for (graph.model.Edge edge : vertex.getOutgoingEdges()) {
                    metrics.incrementEdgeRelaxations();

                    Vertex neighbor = edge.getDestination();
                    int newDist = distances.get(vertex) + edge.getWeight();

                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        predecessors.put(neighbor, vertex);
                    }
                }
            }
        }

        metrics.stopTimer();
        return distances;
    }

    public Map<Vertex, Integer> longestPaths(Graph graph, Vertex source) {
        Graph negatedGraph = createNegatedGraph(graph);
        Map<Vertex, Integer> negatedDistances = shortestPaths(negatedGraph, source);

        Map<Vertex, Integer> longestPaths = new HashMap<>();
        for (Map.Entry<Vertex, Integer> entry : negatedDistances.entrySet()) {
            longestPaths.put(entry.getKey(), -entry.getValue());
        }

        return longestPaths;
    }

    private Graph createNegatedGraph(Graph original) {
        Graph negatedGraph = new Graph();

        for (Vertex vertex : original.getVertices()) {
            negatedGraph.addVertex(vertex.getId(), vertex.getDuration());
        }

        for (graph.model.Edge edge : original.getEdges()) {
            negatedGraph.addEdge(edge.getSource().getId(),
                    edge.getDestination().getId(),
                    -edge.getWeight());
        }

        return negatedGraph;
    }

    public CriticalPathResult findCriticalPath(Graph graph) {
        Map<Vertex, Integer> earliestStart = new HashMap<>();
        Map<Vertex, Integer> latestStart = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        KahnTopologicalSort topoSort = new KahnTopologicalSort();
        List<Vertex> topologicalOrder = topoSort.topologicalSort(graph);

        for (Vertex vertex : graph.getVertices()) {
            earliestStart.put(vertex, 0);
        }

        for (Vertex vertex : topologicalOrder) {
            for (graph.model.Edge edge : vertex.getOutgoingEdges()) {
                Vertex neighbor = edge.getDestination();
                int newTime = earliestStart.get(vertex) + vertex.getDuration() + edge.getWeight();
                if (newTime > earliestStart.get(neighbor)) {
                    earliestStart.put(neighbor, newTime);
                    predecessors.put(neighbor, vertex);
                }
            }
        }

        int maxTime = earliestStart.values().stream().max(Integer::compareTo).get();

        for (Vertex vertex : graph.getVertices()) {
            latestStart.put(vertex, maxTime);
        }

        List<Vertex> reverseOrder = new ArrayList<>(topologicalOrder);
        Collections.reverse(reverseOrder);

        for (Vertex vertex : reverseOrder) {
            for (graph.model.Edge edge : vertex.getOutgoingEdges()) {
                Vertex neighbor = edge.getDestination();
                latestStart.put(vertex, Math.min(latestStart.get(vertex),
                        latestStart.get(neighbor) - vertex.getDuration() - edge.getWeight()));
            }
        }

        Vertex criticalEnd = topologicalOrder.get(topologicalOrder.size() - 1);
        List<Vertex> criticalPath = new ArrayList<>();
        Vertex current = criticalEnd;

        while (current != null) {
            criticalPath.add(0, current);
            current = predecessors.get(current);
        }

        return new CriticalPathResult(criticalPath, maxTime);
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public static class CriticalPathResult {
        private final List<Vertex> path;
        private final int totalDuration;

        public CriticalPathResult(List<Vertex> path, int totalDuration) {
            this.path = path;
            this.totalDuration = totalDuration;
        }

        public List<Vertex> getPath() { return path; }
        public int getTotalDuration() { return totalDuration; }
    }
}