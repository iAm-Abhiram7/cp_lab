import java.util.*;

class BridgesInGraph {
    private int V;
    private List<List<Integer>> adj;
    private int time;
    private int[] disc, low, parent;
    private List<int[]> bridges;
    
    public BridgesInGraph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
    
    public List<int[]> findBridges() {
        time = 0;
        disc = new int[V];
        low = new int[V];
        parent = new int[V];
        Arrays.fill(parent, -1);
        bridges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (disc[i] == 0) {
                dfs(i);
            }
        }
        return bridges;
    }
    
    private void dfs(int u) {
        disc[u] = low[u] = ++time;
        for (int v : adj.get(u)) {
            if (disc[v] == 0) {
                parent[v] = u;
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) {
                    bridges.add(new int[]{u, v});
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of vertices:");
        int V = scanner.nextInt();
        System.out.println("Enter number of edges:");
        int E = scanner.nextInt();
        BridgesInGraph graph = new BridgesInGraph(V);
        System.out.println("Enter edges:");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }
        List<int[]> bridges = graph.findBridges();
        System.out.println("Bridges in the graph:");
        for (int[] bridge : bridges) {
            System.out.println(bridge[0] + " " + bridge[1]);
        }
        scanner.close();
    }
}
