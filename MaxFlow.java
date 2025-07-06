import java.util.*;

class MaxFlow {
    static final int INF = Integer.MAX_VALUE;
    private int V;
    private int[][] capacity;
    private List<List<Integer>> adj;
    
    public MaxFlow(int V) {
        this.V = V;
        capacity = new int[V][V];
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int u, int v, int cap) {
        capacity[u][v] = cap;
        adj.get(u).add(v);
        adj.get(v).add(u); // Add reverse edge for residual graph
    }
    
    // Edmonds-Karp (BFS based Ford-Fulkerson)
    public int edmondsKarp(int s, int t) {
        int flow = 0;
        int[] parent = new int[V];
        while (bfs(s, t, parent)) {
            int pathFlow = INF;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v]);
            }
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                capacity[u][v] -= pathFlow;
                capacity[v][u] += pathFlow;
            }
            flow += pathFlow;
        }
        return flow;
    }
    
    private boolean bfs(int s, int t, int[] parent) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        visited[s] = true;
        parent[s] = -1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                if (!visited[v] && capacity[u][v] > 0) {
                    queue.offer(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == t) return true;
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of vertices:");
        int V = scanner.nextInt();
        System.out.println("Enter number of edges:");
        int E = scanner.nextInt();
        MaxFlow graph = new MaxFlow(V);
        System.out.println("Enter edges (u v capacity):");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int cap = scanner.nextInt();
            graph.addEdge(u, v, cap);
        }
        System.out.println("Enter source and sink:");
        int s = scanner.nextInt();
        int t = scanner.nextInt();
        int maxFlow = graph.edmondsKarp(s, t);
        System.out.println("Maximum flow: " + maxFlow);
        scanner.close();
    }
}
