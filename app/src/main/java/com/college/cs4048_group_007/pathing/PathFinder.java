package com.college.cs4048_group_007.pathing;


import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class PathFinder {
    private Map<String, Double> distances;
    private Map<String, String> previousNodes;

    public PathFinder(PathGraph graph, String startNode) {
        distances = new HashMap<>();
        previousNodes = new HashMap<>();

        // Initialize distances
        for (String node : graph.adjacencyList.keySet()) {
            distances.put(node, Double.POSITIVE_INFINITY);
            previousNodes.put(node, null);
        }
        distances.put(startNode, 0.0);

        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));
        pq.add(new NodeDistance(startNode, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            String currentNode = current.node;
            double currentDistance = current.distance;

            if (currentDistance > distances.get(currentNode)) continue;

            for (PathGraph.Edge edge : graph.getNeighbors(currentNode)) {
                String neighbor = edge.to;
                double newDistance = currentDistance + edge.weight;

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, currentNode);
                    pq.add(new NodeDistance(neighbor, newDistance));
                }
            }
        }
    }

    public List<String> getShortestPath(String from, String to) {
        List<String> path = new ArrayList<>();
        String currentNode = to;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = previousNodes.get(currentNode);
        }

        Collections.reverse(path);
        return path;
    }

    static class NodeDistance {
        String node;
        double distance;

        public NodeDistance(String node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}

