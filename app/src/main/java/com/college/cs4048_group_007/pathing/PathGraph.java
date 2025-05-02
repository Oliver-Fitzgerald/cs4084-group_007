package com.college.cs4048_group_007.pathing;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class PathGraph {

    public Map<String, List<Edge>> adjacencyList;

    public PathGraph(Context context, int jsonResourceId) {
        this.adjacencyList = new HashMap<>();
        loadGraphFromJson(context, jsonResourceId);
    }

    private void loadGraphFromJson(Context context, int jsonResourceId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(jsonResourceId);
            String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();

            Gson gson = new Gson();
            AdjacencyListData adjacencyListData = new AdjacencyListData();
            try {
                adjacencyListData = gson.fromJson(jsonString, AdjacencyListData.class);
            } catch (Error e){
                Log.e("PATHING", e.getMessage());
            }


            for (Map.Entry<String, List<EdgeData>> entry : adjacencyListData.adjacency_list.entrySet()) {
                String fromNode = entry.getKey();
                for (EdgeData edgeData : entry.getValue()) {
                    addEdge(fromNode, edgeData.to, edgeData.weight, edgeData.image);
                }
            }
        } catch (Exception e) {
            Log.e("PATHING", e.toString());
        }
    }

    private void addEdge(String from, String to, double weight, String imagePath) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());

        adjacencyList.get(from).add(new Edge(to, weight, imagePath));
        adjacencyList.get(to).add(new Edge(from, weight, imagePath)); // For undirected graphs
    }

    public List<Edge> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public PathFinder createPathFinder(String startNode) {
        return new PathFinder(this, startNode);
    }

    static class AdjacencyListData {
        Map<String, List<EdgeData>> adjacency_list;
    }

    static class EdgeData {
        String to;
        double weight;
        String image;
    }

    static class Edge {
        String to;
        double weight;
        String imagePath;

        public Edge(String to, double weight, String imagePath) {
            this.to = to;
            this.weight = weight;
            this.imagePath = imagePath;
        }
    }
}
