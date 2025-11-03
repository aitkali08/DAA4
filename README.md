# Smart City Scheduling System

A comprehensive implementation of graph algorithms for smart city service scheduling, featuring Strongly Connected Components, Topological Sorting, and Shortest Path algorithms in Directed Acyclic Graphs.

## Table of Contents
- [Overview](#overview)
- [Algorithms Implemented](#algorithms-implemented)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Dataset Information](#dataset-information)
- [Performance Analysis](#performance-analysis)
- [Results](#results)
- [Technical Details](#technical-details)
- [Testing](#testing)
- [GitHub Repository Structure](#github-repository-structure)
- [Conclusion & Recommendations](#conclusion--recommendations)

## Overview

This project implements graph algorithms to solve scheduling problems in smart city environments. It handles dependencies between city-service tasks (street cleaning, repairs, maintenance) using SCC detection for cyclic dependencies and topological sorting with shortest path algorithms for optimal scheduling.

## Algorithms Implemented

### 1. Strongly Connected Components (SCC)
- **Algorithm**: Tarjan's Algorithm
- **Time Complexity**: O(V + E)
- **Space Complexity**: O(V)
- **Features**:
    - Detect all SCCs in directed graphs
    - Build condensation graph (DAG of components)
    - Handle cyclic dependencies in city service tasks

### 2. Topological Sorting
- **Algorithm**: Kahn's Algorithm
- **Time Complexity**: O(V + E)
- **Space Complexity**: O(V)
- **Features**:
    - Compute valid execution order for acyclic dependencies
    - Detect cycles in task dependencies
    - Provide optimal scheduling order

### 3. Shortest Paths in DAG
- **Algorithm**: Dynamic Programming over topological order
- **Time Complexity**: O(V + E)
- **Space Complexity**: O(V)
- **Features**:
    - Single-source shortest paths
    - Longest path computation (critical path)
    - Path reconstruction
    - Edge weight model (chosen over node durations for flexibility)

## Project Structure
```
DAA4/
├── data/
│ ├── small/
│ │ └── small1.json
│ ├── medium/
│ │ └── medium1.json
│ └── large/
│ └── large1.json
├── src/
│ └── main/
│ └── java/
│ └── graph/
│ ├── dagsp/
│ │ ├── CriticalPath.java
│ │ └── DAGShortestPath.java
│ ├── metrics/
│ │ └── Metrics.java
│ ├── model/
│ │ ├── Edge.java
│ │ ├── Graph.java
│ │ └── Vertex.java
│ ├── scc/
│ │ └── TarjanSCC.java
│ ├── topo/
│ │ └── KahnTopologicalSort.java
│ ├── utils/
│ │ ├── DataGenerator.java
│ │ ├── GraphLoader.java
│ │ └── ResultLogger.java
│ └── Main.java
├── src/
│ └── test/
│ └── java/
│ └── graph/
│ ├── DAGShortestPathTest.java
│ ├── SCCTest.java
│ └── TopologicalSortTest.java
├── pom.xml
└── README.md
```

## Installation & Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Git

### Build Instructions

```bash

# Clone the repository
git clone https://github.com/aitkali08/DAA4.git
cd DAA4
```

# Build the project
```
mvn clean compile
```

# Run tests
```
mvn test
```

# Package the application
```
mvn package
```


Usage
Running the Main Application
```bash

mvn exec:java -Dexec.mainClass="graph.Main"
```
Generating Datasets (Example)
```java
// Example code to generate datasets
Graph smallGraph = DataGenerator.generateSmallGraph(1);
Graph mediumGraph = DataGenerator.generateMediumGraph(2);
Graph largeGraph = DataGenerator.generateLargeGraph(3);
```
Example Algorithm Usage
```java
Graph graph = DataGenerator.generateMediumGraph(1);

// SCC detection
TarjanSCC tarjan = new TarjanSCC();
List<List<Vertex>> sccs = tarjan.findSCCs(graph);

// Condensation graph
Graph condensationGraph = tarjan.buildCondensationGraph(graph, sccs);

// Topological sort
KahnTopologicalSort topoSort = new KahnTopologicalSort();
List<Vertex> topologicalOrder = topoSort.topologicalSort(condensationGraph);

// Shortest path
DAGShortestPath shortestPath = new DAGShortestPath();
Vertex source = graph.getVertices().get(0);
Map<Vertex, Integer> distances = shortestPath.shortestPaths(graph, source);

// Critical path
DAGShortestPath.CriticalPathResult criticalPath = shortestPath.findCriticalPath(graph);
```

Dataset Information

Small Graphs (6-10 nodes)
```
small_1.json: Simple DAG, 8 nodes, 12 edges

small_2.json: Single cycle, 6 nodes, 7 edges

small_3.json: Mixed structure, 10 nodes, 15 edges
```

Medium Graphs (10-20 nodes)
```
medium_1.json: Sparse graph, 15 nodes, 22 edges

medium_2.json: Dense graph, 18 nodes, 45 edges

medium_3.json: Multiple SCCs, 16 nodes, 28 edges
```
Large Graphs (20-50 nodes)
```
large_1.json: Sparse performance test, 35 nodes, 52 edges

large_2.json: Dense performance test, 45 nodes, 120 edges

large_3.json: Complex mixed, 40 nodes, 75 edges
```

Performance Analysis
Metrics Collected

Execution Time: System.nanoTime()

DFS Visits: vertex visits in DFS

Edge Relaxations: edge operations

Queue Operations: enqueue/dequeue counts for BFS-based algorithms

Algorithm Performance
```
Algorithm	Best Case	Worst Case	Average Case
Tarjan's SCC	O(V+E)	O(V+E)	O(V+E)
Kahn's Topo Sort	O(V+E)	O(V+E)	O(V+E)
DAG Shortest Path	O(V+E)	O(V+E)	O(V+E)
```

Results
Performance Summary Example
```
Graph	Nodes	Edges	SCC Time (ms)	Topo Time (ms)	SP Time (ms)	SCC Count
Small 1	8	12	0.12	0.08	0.15	3
Small 2	6	7	0.10	0.07	0.12	1
Small 3	10	15	0.15	0.10	0.18	4

(other graphs follow same format)
```

Technical Details
Graph Representation: adjacency list

Vertex Properties: ID, duration, incoming/outgoing edges

Edge Properties: source, destination, weight

Weight Model: edge weights chosen for flexibility

Memory Management: efficient data structures, minimal object creation

Testing
Test Coverage: unit tests for all algorithms, edge cases, performance validation

Running Tests:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=SCCTest

# Detailed output
mvn test -Dmaven.test.failure.ignore=false

```
GitHub Repository Structure
Branches: main (production), develop (development), feature/*, hotfix/*

Commit Conventions: feat, fix, docs, test, refactor

Conclusion & Recommendations
Use SCC compression to handle circular dependencies

Apply topological ordering for optimal sequencing

Utilize critical path analysis for bottleneck detection

Consider graph density when optimizing

Future Enhancements

Parallel algorithms

Real-time graph updates

Integration with scheduling systems

Visualization components
