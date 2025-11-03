package graph;

import graph.model.Graph;
import graph.topo.KahnTopologicalSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TopologicalSortTest {

    @Test
    void testTopologicalSortOnDAG() {
        Graph graph = new Graph();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);
        graph.addVertex("C", 3);
        graph.addVertex("D", 4);

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 1);
        graph.addEdge("B", "D", 1);
        graph.addEdge("C", "D", 1);

        KahnTopologicalSort topoSort = new KahnTopologicalSort();
        List<graph.model.Vertex> result = topoSort.topologicalSort(graph);

        assertEquals(4, result.size());

        int indexA = findIndex(result, "A");
        int indexB = findIndex(result, "B");
        int indexC = findIndex(result, "C");
        int indexD = findIndex(result, "D");

        assertTrue(indexA < indexB);
        assertTrue(indexA < indexC);
        assertTrue(indexB < indexD);
        assertTrue(indexC < indexD);
    }

    @Test
    void testTopologicalSortThrowsOnCycle() {
        Graph graph = new Graph();

        graph.addVertex("A", 1);
        graph.addVertex("B", 2);

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "A", 1);

        KahnTopologicalSort topoSort = new KahnTopologicalSort();

        assertThrows(IllegalArgumentException.class, () -> {
            topoSort.topologicalSort(graph);
        });
    }

    private int findIndex(List<graph.model.Vertex> vertices, String id) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}