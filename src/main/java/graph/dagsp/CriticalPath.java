package graph.dagsp;

import graph.model.Edge;
import graph.model.Graph;
import graph.model.Vertex;
import graph.topo.KahnTopologicalSort;
import java.util.*;

public class CriticalPath {

    private final Graph graph;
    private final Map<Vertex, Double> earliestStart = new HashMap<>();
    private final Map<Vertex, Double> latestStart = new HashMap<>();
    private List<Vertex> topoOrder;
    private List<Vertex> criticalPath = new ArrayList<>();
    private int totalDuration = 0;

    public CriticalPath(Graph graph) {
        this.graph = graph;
        compute();
        finalizeCritical();
    }

    private void compute() {
        KahnTopologicalSort topo = new KahnTopologicalSort();
        topoOrder = topo.topologicalSort(graph);

        for (Vertex v : graph.getVertices()) {
            earliestStart.put(v, 0.0);
        }
        for (Vertex u : topoOrder) {
            for (Edge e : u.getOutgoingEdges()) {
                Vertex v = e.getDestination();
                double newStart = earliestStart.get(u) + e.getWeight();
                if (newStart > earliestStart.get(v)) {
                    earliestStart.put(v, newStart);
                }
            }
        }

        double maxTime = earliestStart.values().stream().max(Double::compare).orElse(0.0);
        for (Vertex v : graph.getVertices()) {
            latestStart.put(v, maxTime);
        }

        ListIterator<Vertex> it = topoOrder.listIterator(topoOrder.size());
        while (it.hasPrevious()) {
            Vertex u = it.previous();
            for (Edge e : u.getOutgoingEdges()) {
                Vertex v = e.getDestination();
                double newStart = latestStart.get(v) - e.getWeight();
                if (newStart < latestStart.get(u)) {
                    latestStart.put(u, newStart);
                }
            }
        }
    }

    private void finalizeCritical() {
        List<Vertex> cp = new ArrayList<>();
        for (Vertex v : topoOrder) {
            double slack = latestStart.get(v) - earliestStart.get(v);
            if (Math.abs(slack) < 1e-9) {
                cp.add(v);
            }
        }
        this.criticalPath = cp;
        this.totalDuration = earliestStart.values().stream()
                .max(Double::compare)
                .orElse(0.0)
                .intValue();
    }

    public List<Vertex> findCriticalPath() {
        return new ArrayList<>(criticalPath);
    }

    public String getPathAsString() {
        if (criticalPath == null || criticalPath.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < criticalPath.size(); i++) {
            sb.append(criticalPath.get(i).getId());
            if (i < criticalPath.size() - 1) sb.append(" -> ");
        }
        sb.append("]");
        return sb.toString();
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public Map<Vertex, Double> getEarliestStart() { return earliestStart; }
    public Map<Vertex, Double> getLatestStart() { return latestStart; }
    public List<Vertex> getTopoOrder() { return topoOrder; }
}
