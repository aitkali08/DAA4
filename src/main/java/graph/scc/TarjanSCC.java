package graph.scc;

import graph.model.Graph;
import graph.model.Vertex;
import graph.metrics.Metrics;

import java.util.*;

public class TarjanSCC {
    private int index;
    private Stack<Vertex> stack;
    private Map<Vertex, Integer> indices;
    private Map<Vertex, Integer> lowLinks;
    private Set<Vertex> onStack;
    private List<List<Vertex>> components;
    private Metrics metrics;

    public TarjanSCC() {
        this.metrics = new Metrics();
    }

    public List<List<Vertex>> findSCCs(Graph graph) {
        metrics.startTimer();
        metrics.reset();

        index = 0;
        stack = new Stack<>();
        indices = new HashMap<>();
        lowLinks = new HashMap<>();
        onStack = new HashSet<>();
        components = new ArrayList<>();

        for (Vertex vertex : graph.getVertices()) {
            if (!indices.containsKey(vertex)) {
                strongConnect(vertex);
            }
        }

        metrics.stopTimer();
        return components;
    }

    private void strongConnect(Vertex vertex) {
        metrics.incrementDFSVisits();

        indices.put(vertex, index);
        lowLinks.put(vertex, index);
        index++;
        stack.push(vertex);
        onStack.add(vertex);

        for (graph.model.Edge edge : vertex.getOutgoingEdges()) {
            Vertex neighbor = edge.getDestination();
            metrics.incrementEdgeRelaxations();

            if (!indices.containsKey(neighbor)) {
                strongConnect(neighbor);
                lowLinks.put(vertex, Math.min(lowLinks.get(vertex), lowLinks.get(neighbor)));
            } else if (onStack.contains(neighbor)) {
                lowLinks.put(vertex, Math.min(lowLinks.get(vertex), indices.get(neighbor)));
            }
        }

        if (lowLinks.get(vertex).equals(indices.get(vertex))) {
            List<Vertex> component = new ArrayList<>();
            Vertex popped;
            do {
                popped = stack.pop();
                onStack.remove(popped);
                component.add(popped);
            } while (!popped.equals(vertex));
            components.add(component);
        }
    }

    public Graph buildCondensationGraph(Graph originalGraph, List<List<Vertex>> sccs) {
        Graph condensationGraph = new Graph();
        Map<Vertex, Integer> vertexToComponent = new HashMap<>();

        for (int i = 0; i < sccs.size(); i++) {
            List<Vertex> component = sccs.get(i);
            int totalDuration = component.stream().mapToInt(Vertex::getDuration).sum();
            condensationGraph.addVertex("C" + i, totalDuration);

            for (Vertex vertex : component) {
                vertexToComponent.put(vertex, i);
            }
        }

        Set<String> addedEdges = new HashSet<>();

        for (List<Vertex> component : sccs) {
            for (Vertex vertex : component) {
                for (graph.model.Edge edge : vertex.getOutgoingEdges()) {
                    Vertex dest = edge.getDestination();
                    int srcComp = vertexToComponent.get(vertex);
                    int destComp = vertexToComponent.get(dest);

                    if (srcComp != destComp) {
                        String edgeKey = srcComp + "->" + destComp;
                        if (!addedEdges.contains(edgeKey)) {
                            condensationGraph.addEdge("C" + srcComp, "C" + destComp, 0);
                            addedEdges.add(edgeKey);
                        }
                    }
                }
            }
        }

        return condensationGraph;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}