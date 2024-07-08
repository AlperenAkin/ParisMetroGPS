import java.util.*;

public class Graph {
    private final int vertices;
    private final List<List<Edge>> adjacencyList;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyList.get(source).add(edge);
    }


    List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    int getVerticesCount() {
        return vertices;
    }

    public void printGraph() {
        System.out.println("Metro Station Adjacency List: ");
        for (int i = 0; i < vertices; i++) {
            List<Edge> edges = adjacencyList.get(i);
            for (Edge edge : edges) {
                System.out.println("Station " + edge.source + " to Station " + edge.destination + ", Time: " + edge.weight);
            }
        }
    }
    

    
}

   
