import java.util.*;

class ConnectedComponents {
    private int V;
    private List<List<Integer>> adj;
    private boolean[] visited;
    
    public ConnectedComponents(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        visited = new boolean[V];
    }
    
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
    
    public int countComponents() {
        Arrays.fill(visited, false);
        int count = 0;
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i);
                count++;
            }
        }
        return count;
    }
    
    private void dfs(int u) {
        visited[u] = true;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                dfs(v);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of vertices:");
        int V = scanner.nextInt();
        System.out.println("Enter number of edges:");
        int E = scanner.nextInt();
        ConnectedComponents graph = new ConnectedComponents(V);
        System.out.println("Enter edges:");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }
        int result = graph.countComponents();
        System.out.println("Number of connected components: " + result);
        scanner.close();
    }
}
