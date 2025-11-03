package graph;

import graph.model.Graph;
import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class SCCTest {

    @Test
    void testSCCOnCyclicGraph() {
        Graph graph = new Graph();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);
        graph.addEdge("C", "A", 1);
        graph.addEdge("C", "D", 1);

        TarjanSCC tarjan = new TarjanSCC();
        List<List<graph.model.Vertex>> sccs = tarjan.findSCCs(graph);

        assertEquals(2, sccs.size());

        boolean foundCycle = false;
        for (List<graph.model.Vertex> component : sccs) {
            if (component.size() == 3) {
                foundCycle = true;
                assertEquals(3, component.size());
            }
        }
        assertTrue(foundCycle);
    }

    @Test
    void testSCCOnDAG() {
        Graph graph = new Graph();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 1);

        TarjanSCC tarjan = new TarjanSCC();
        List<List<graph.model.Vertex>> sccs = tarjan.findSCCs(graph);

        assertEquals(3, sccs.size());
        for (List<graph.model.Vertex> component : sccs) {
            assertEquals(1, component.size());
        }
    }
}