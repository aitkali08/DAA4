package graph.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ResultLogger {

    private static final String OUTPUT_FILE = "output.txt";

    public static void log(String title, String message) {
        try (FileWriter writer = new FileWriter(OUTPUT_FILE, true)) {
            writer.write("===== " + title + " =====\n");
            writer.write("Time: " + LocalDateTime.now() + "\n");
            writer.write(message + "\n\n");
        } catch (IOException e) {
            System.err.println("⚠️ Error writing to output.txt: " + e.getMessage());
        }
    }
}
