package graph.metrics;

public class Metrics {
    private int dfsVisits;
    private int edgeRelaxations;
    private int queueOperations;
    private long startTime;
    private long endTime;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void incrementDFSVisits() { dfsVisits++; }
    public void incrementEdgeRelaxations() { edgeRelaxations++; }
    public void incrementQueueOperations() { queueOperations++; }

    public int getDfsVisits() { return dfsVisits; }
    public int getEdgeRelaxations() { return edgeRelaxations; }
    public int getQueueOperations() { return queueOperations; }

    public void reset() {
        dfsVisits = 0;
        edgeRelaxations = 0;
        queueOperations = 0;
        startTime = 0;
        endTime = 0;
    }
}