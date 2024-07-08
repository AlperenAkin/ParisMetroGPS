import java.util.List;

class DFS {
    private final Graph graph;
    private boolean[] visited;

    DFS(Graph graph) {
        this.graph = graph;
        visited = new boolean[graph.getVerticesCount()];
    }

    void depthFirstSearch(int startVertex) {
        System.out.print("Stations belonging to the same line as Station " + startVertex + " are: ");
        visited = new boolean[graph.getVerticesCount()];
        dfsUtil(startVertex);
    }

    private void dfsUtil(int vertex) {

        //recursive dfsMethod
        visited[vertex] = true;
        System.out.print(vertex + " ");

        List<Edge> edges = graph.getAdjacencyList().get(vertex);
        for (Edge edge : edges) {
            if (edge.weight >= 0) {
                int destination = edge.destination;
                if (!visited[destination]) {
                    dfsUtil(destination);
                }
            }
        }
    }
}
