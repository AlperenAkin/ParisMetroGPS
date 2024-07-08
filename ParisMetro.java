import java.io.*;
import java.util.*;


class ParisMetro {
    private final int stations;
    private final List<List<Edge>> adjacencyList;

    ParisMetro(Graph graph) {
        this.stations = graph.getVerticesCount();
        this.adjacencyList = graph.getAdjacencyList();
    }


    public void shortestPath(int source, int destination) {
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        int[] time = new int[stations];
        Arrays.fill(time, Integer.MAX_VALUE);
        boolean[] visited = new boolean[stations];
        int[] previous = new int[stations];

        time[source] = 0;
        minHeap.offer(new Node(source, 0));

        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            int currVertex = node.station;

            if (!visited[currVertex]) {
                visited[currVertex] = true;

                List<Edge> edges = adjacencyList.get(currVertex);
                for (Edge edge : edges) {
                    int nextVertex = edge.destination;
                    int newDist = time[currVertex] + edge.weight;

                    if (!visited[nextVertex] && newDist < time[nextVertex]) {
                        time[nextVertex] = newDist;
                        previous[nextVertex] = currVertex;
                        minHeap.offer(new Node(nextVertex, newDist));
                    }
                }
            }
        }

        printPath(source, destination, time, previous);
    }
    

    private void printPath(int source, int destination, int[] time, int[] previous) {
        System.out.println("Shortest Path from Station " + source + " to Station " + destination + ": ");
        if (time[destination] == Integer.MAX_VALUE) {
            System.out.println("No path exists.");
            return;
        }

        LinkedList<Integer> path = new LinkedList<>();
        int current = destination;
        while (current != source) {
            path.addFirst(current);
            current = previous[current];
        }
        path.addFirst(source);

        System.out.print("Path: ");
        for (int vertex : path) {
            System.out.print(vertex + " ");
        }
        System.out.println("\nTotal time: " + time[destination]);
    }

    

    private static Graph readMetro(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            int numStations = scanner.nextInt();
            int numLines = scanner.nextInt();
            scanner.nextLine();

            Graph metro = new Graph(numStations);

            Map<String, Integer> stationMap = new HashMap<>();

            for (int i = 0; i < numStations; i++) {
                String[] station = scanner.nextLine().split(" ");
                int stationNumber = Integer.parseInt(station[0]);
                stationMap.put(station[1], stationNumber);
            }

            scanner.nextLine(); // Skip $

            for (int i = 0; i < numLines; i++) {
                String[] lineData = scanner.nextLine().split(" ");
                int v1 = Integer.parseInt(lineData[0]);
                int v2 = Integer.parseInt(lineData[1]);
                int weight = Integer.parseInt(lineData[2]);
                metro.addEdge(v1, v2, weight);
            }
            scanner.close();

            return metro;
 
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return null;

    }

    public void shortestPathWithDisabledLine(int source, int destination, int disabledStation) {
        
        int[] previous = new int[stations];
        boolean[] visited = new boolean[stations];
        int[] time = new int[stations];
        Arrays.fill(time, Integer.MAX_VALUE);
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));

        time[source] = 0;
        minHeap.offer(new Node(source, 0));

        while (!minHeap.isEmpty()) {
            Node node = minHeap.poll();
            int currVertex = node.station;

            if (!visited[currVertex]) {
                visited[currVertex] = true;

                List<Edge> lines = adjacencyList.get(currVertex);
                for (Edge edge : lines) {
                    if ( edge.destination == disabledStation|| edge.source == disabledStation) {
                        continue; 
                        // ignore disabled station
                    }

                    int nextVertex = edge.destination;
                    int newDist = time[currVertex] + edge.weight;

                    if (!visited[nextVertex] && newDist < time[nextVertex]) {
                        time[nextVertex] = newDist;
                        previous[nextVertex] = currVertex;
                        minHeap.offer(new Node(nextVertex, newDist));
                    }
                }
            }
        }
        
        System.out.println("WHEN STATION " + disabledStation + " IS DISABLED.....");
        printPath(source, destination, time, previous);

    }

    public static void main(String[] args) {
        
        int N1 = Integer.parseInt(args[0]);
        
        Graph metro = readMetro("metro.txt");
        
        System.out.println();

        ParisMetro dijkstra = new ParisMetro(metro);

        // COMMENT OUT LINE BELOW TO SEE PRINTED ADJACENCY LIST OF GRAPH
        //metro.printGraph();

        DFS dfs = new DFS(metro);
        

        if (args.length == 1){
            
            System.out.println("\nTest -----------------------------------");
            dfs.depthFirstSearch(N1);
            System.out.println("\nEnd of Test -----------------------------\n");

        } else if (args.length == 2) {

            int N2 = Integer.parseInt(args[1]);
            System.out.println("\nTest -----------------------------------");
            dijkstra.shortestPath(N1, N2);
            System.out.println("End of Test -----------------------------\n");

        } else if (args.length == 3) {

            System.out.println("\nTest -----------------------------------");
            int N2 = Integer.parseInt(args[1]);
            int N3 = Integer.parseInt(args[2]);
            dijkstra.shortestPathWithDisabledLine(N1, N2, N3);
            System.out.println("End of Test -----------------------------\n");

        } else {

            System.out.println("\nInvalid number of arguments.\n");

        }

        
        /* 
        
        CODE TO RUN DIRECTLY WITHOUT COMMAND LINE. JUST CALLING METHODS. INCLUDES THE ADJACENCY LIST OF THE GRAPH CREATED FROM metro.txt

        Graph metro = readMetro("metro.txt");
        metro.printGraph();   

        DFS dfs = new DFS(metro);
        dfs.depthFirstSearch(21);

        ParisMetro dijkstra = new ParisMetro(metro);
        dijkstra.shortestPath(21, 186);
        
        dijkstra.shortestPathWithDisabledLine(21, 186, 19);
        
        */
        
        
        
    }
}