package com.college.cs4048_group_007.pathing;


import android.util.Log;

import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class PathFinder {
    private Map<String, Double> distances;
    private Map<String, String> previousNodes;
    private PathGraph graph;

    public PathFinder(PathGraph graph, String startNode) {
        this.graph = graph;
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
        Log.i("PATHFINDER", "FROM: " + from + ", TO: " + to);

        List<String> imagePaths = new ArrayList<>();
        String currentNode = to;


        while (currentNode != null) {
            String previousNode = previousNodes.get(currentNode);
            if (previousNode == null) {
                break;
            }


            List<PathGraph.Edge> neighbors = graph.getNeighbors(previousNode);
            for (PathGraph.Edge edge : neighbors) {
                if (edge.to.equals(currentNode)) {
                    imagePaths.add(edge.imagePath); // Add the image path to the list
                    break;
                }
            }

            currentNode = previousNode; // Move to the previous node
        }

        Collections.reverse(imagePaths); // Reverse to get the correct order
        return imagePaths; // Return the list of image paths
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

