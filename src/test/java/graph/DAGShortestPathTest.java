package graph;

import graph.model.Graph;
import graph.dagsp.DAGShortestPath;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class DAGShortestPathTest {

    @Test
    void testShortestPath() {
        Graph graph = new Graph();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "C", 3);
        graph.addEdge("B", "D", 2);
        graph.addEdge("C", "D", 7);

        DAGShortestPath shortestPath = new DAGShortestPath();
        graph.model.Vertex source = graph.getVertex("A");
        Map<graph.model.Vertex, Integer> distances = shortestPath.shortestPaths(graph, source);

        assertEquals(0, distances.get(graph.getVertex("A")));
        assertEquals(5, distances.get(graph.getVertex("B")));
        assertEquals(3, distances.get(graph.getVertex("C")));
        assertEquals(7, distances.get(graph.getVertex("D")));
    }

    @Test
    void testCriticalPath() {
        Graph graph = new Graph();

        graph.addVertex("A", 5);
        graph.addVertex("B", 3);
        graph.addVertex("C", 2);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 2);
        graph.addEdge("B", "D", 3);
        graph.addEdge("C", "D", 1);

        DAGShortestPath shortestPath = new DAGShortestPath();
        DAGShortestPath.CriticalPathResult result = shortestPath.findCriticalPath(graph);

        assertTrue(result.getTotalDuration() > 0);
        assertFalse(result.getPath().isEmpty());
    }
}