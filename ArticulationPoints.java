import java.util.*;

class ArticulationPoints {
    private int V;
    private List<List<Integer>> adj;
    private boolean[] visited;
    private int[] disc, low, parent;
    private boolean[] ap;
    private int time;
    
    public ArticulationPoints(int V) {
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
    
    public List<Integer> findArticulationPoints() {
        visited = new boolean[V];
        disc = new int[V];
        low = new int[V];
        parent = new int[V];
        ap = new boolean[V];
        time = 0;
        
        Arrays.fill(parent, -1);
        
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (ap[i]) {
                result.add(i);
            }
        }
        
        return result;
    }
    
    private void dfs(int u) {
        int children = 0;
        visited[u] = true;
        disc[u] = low[u] = ++time;
        
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfs(v);
                
                low[u] = Math.min(low[u], low[v]);
                
                // Root is an articulation point if it has more than one child
                if (parent[u] == -1 && children > 1) {
                    ap[u] = true;
                }
                
                // Non-root is an articulation point if removing it disconnects the graph
                if (parent[u] != -1 && low[v] >= disc[u]) {
                    ap[u] = true;
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("enter number of servers");
        int V = scanner.nextInt();
        
        System.out.println("enter number of connections");
        int E = scanner.nextInt();
        
        ArticulationPoints graph = new ArticulationPoints(V);
        
        System.out.println("enter edges");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }
        
        List<Integer> articulationPoints = graph.findArticulationPoints();
        
        System.out.println("Articulation points in the graph");
        for (int i = 0; i < articulationPoints.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(articulationPoints.get(i));
        }
        System.out.println();
        
        scanner.close();
    }
}
