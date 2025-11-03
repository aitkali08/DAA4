package graph.utils;

import graph.model.Graph;
import java.util.Random;

public class DataGenerator {
    private static final Random random = new Random();

    public static Graph generateSmallGraph(int variant) {
        Graph graph = new Graph();
        switch (variant) {
            case 1 -> generateSimpleDAG(graph, 8);
            case 2 -> generateGraphWithCycle(graph, 6);
            case 3 -> generateMixedGraph(graph, 10);
        }
        return graph;
    }

    private static void generateMixedGraph(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(5) + 1);
        }
        for (int i = 0; i < nodes; i++) {
            for (int j = i + 1; j < nodes; j++) {
                if (random.nextDouble() < 0.4) {
                    graph.addEdge("V" + i, "V" + j, random.nextInt(10) + 1);
                }
                if (random.nextDouble() < 0.15 && i > 0) {
                    graph.addEdge("V" + j, "V" + i, random.nextInt(10) + 1);
                }
            }
        }
    }

    public static Graph generateMediumGraph(int variant) {
        Graph graph = new Graph();
        int nodes = 15 + random.nextInt(6);
        switch (variant) {
            case 1 -> generateSparseGraph(graph, nodes, nodes * 2);
            case 2 -> generateDenseGraph(graph, nodes);
            case 3 -> generateMultipleSCCs(graph, nodes);
        }
        return graph;
    }

    private static void generateDenseGraph(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(10) + 1);
        }
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                if (i != j && random.nextDouble() < 0.6) {
                    graph.addEdge("V" + i, "V" + j, random.nextInt(20) + 1);
                }
            }
        }
    }

    public static Graph generateLargeGraph(int variant) {
        Graph graph = new Graph();
        int nodes = 30 + random.nextInt(21);
        switch (variant) {
            case 1 -> generateSparseGraph(graph, nodes, (int) (nodes * 1.5));
            case 2 -> generateDenseGraph(graph, nodes);
            case 3 -> generateComplexMixedGraph(graph, nodes);
        }
        return graph;
    }

    private static void generateComplexMixedGraph(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(8) + 1);
        }
        int blockSize = 5 + random.nextInt(3);
        for (int i = 0; i < nodes; i++) {
            for (int j = i + 1; j < Math.min(nodes, i + blockSize); j++) {
                if (random.nextDouble() < 0.5) {
                    graph.addEdge("V" + i, "V" + j, random.nextInt(15) + 1);
                }
            }
            if (random.nextDouble() < 0.2 && i > 1) {
                int back = i - random.nextInt(Math.min(3, i));
                if (back >= 0) graph.addEdge("V" + i, "V" + back, random.nextInt(15) + 1);
            }
        }
    }

    private static void generateSimpleDAG(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(5) + 1);
        }
        for (int i = 0; i < nodes; i++) {
            for (int j = i + 1; j < Math.min(nodes, i + 4); j++) {
                if (random.nextDouble() < 0.6) {
                    graph.addEdge("V" + i, "V" + j, random.nextInt(10) + 1);
                }
            }
        }
    }

    private static void generateGraphWithCycle(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(5) + 1);
        }
        for (int i = 0; i < nodes - 1; i++) {
            graph.addEdge("V" + i, "V" + (i + 1), random.nextInt(10) + 1);
        }
        graph.addEdge("V" + (nodes - 1), "V" + 0, random.nextInt(10) + 1);
    }

    private static void generateSparseGraph(Graph graph, int nodes, int edges) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(10) + 1);
        }
        for (int i = 0; i < edges; i++) {
            int from = random.nextInt(nodes);
            int to = random.nextInt(nodes);
            if (from != to) {
                graph.addEdge("V" + from, "V" + to, random.nextInt(20) + 1);
            }
        }
    }

    private static void generateMultipleSCCs(Graph graph, int nodes) {
        for (int i = 0; i < nodes; i++) {
            graph.addVertex("V" + i, random.nextInt(8) + 1);
        }
        int sccSize = 3;
        for (int i = 0; i < nodes; i += sccSize) {
            for (int j = i; j < Math.min(i + sccSize, nodes); j++) {
                for (int k = i; k < Math.min(i + sccSize, nodes); k++) {
                    if (j != k && random.nextDouble() < 0.7) {
                        graph.addEdge("V" + j, "V" + k, random.nextInt(15) + 1);
                    }
                }
            }
        }
        for (int i = 0; i < nodes - sccSize; i += sccSize) {
            graph.addEdge("V" + (i + sccSize - 1), "V" + (i + sccSize), random.nextInt(10) + 1);
        }
    }
}
