package graph;

import graph.model.Graph;
import graph.utils.GraphLoader;
import graph.utils.ResultLogger;
import graph.dagsp.CriticalPath;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String[] categories = {"data/small", "data/medium", "data/large"};

        for (String category : categories) {
            File folder = new File(category);
            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println("⚠️ Folder not found: " + category);
                continue;
            }

            File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
            if (files == null || files.length == 0) {
                System.out.println("⚠️ No JSON files in: " + category);
                continue;
            }

            for (File file : files) {
                processGraphFile(file);
            }
        }

        System.out.println("✅ Processing complete. Results saved in output.txt");
    }

    private static void processGraphFile(File file) {
        try {
            Graph graph = GraphLoader.loadFromJson(file.getPath());
            long start = System.nanoTime();

            CriticalPath cp = new CriticalPath(graph);
            cp.findCriticalPath();

            long end = System.nanoTime();
            double ms = (end - start) / 1_000_000.0;

            String result = String.format(
                    "Graph file: %s%nVertices: %d%nEdges: %d%nExecution time: %.3f ms%nCritical Path: %s%nTotal Duration: %d",
                    file.getName(),
                    graph.getVertexCount(),
                    graph.getEdgeCount(),
                    ms,
                    cp.getPathAsString(),
                    cp.getTotalDuration()
            );

            System.out.println(result);
            ResultLogger.log("Processed " + file.getName(), result);

        } catch (Exception e) {
            e.printStackTrace();
            ResultLogger.log("Error processing " + file.getName(), e.getMessage());
        }
    }
}
