import java.util.*;

public class Q1g_ArticulationPoints {
    private int time;
    private List<Integer> articulationPoints;
    
    public List<Integer> findArticulationPoints(int V, List<List<Integer>> adj) {
        boolean[] visited = new boolean[V];
        int[] disc = new int[V];
        int[] low = new int[V];
        int[] parent = new int[V];
        boolean[] ap = new boolean[V];
        
        Arrays.fill(parent, -1);
        time = 0;
        articulationPoints = new ArrayList<>();
        
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, visited, disc, low, parent, ap, adj);
            }
        }
        
        for (int i = 0; i < V; i++) {
            if (ap[i]) {
                articulationPoints.add(i);
            }
        }
        
        return articulationPoints;
    }
    
    private void dfs(int u, boolean[] visited, int[] disc, int[] low, 
                     int[] parent, boolean[] ap, List<List<Integer>> adj) {
        int children = 0;
        visited[u] = true;
        disc[u] = low[u] = ++time;
        
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                children++;
                parent[v] = u;
                dfs(v, visited, disc, low, parent, ap, adj);
                
                low[u] = Math.min(low[u], low[v]);
                
                if (parent[u] == -1 && children > 1) {
                    ap[u] = true;
                }
                
                if (parent[u] != -1 && low[v] >= disc[u]) {
                    ap[u] = true;
                }
            } else if (v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    public static void main(String[] args) {
        int V = 7;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
        
        // Adding edges
        adj.get(0).add(1);
        adj.get(1).add(0);
        adj.get(0).add(2);
        adj.get(2).add(0);
        adj.get(1).add(2);
        adj.get(2).add(1);
        adj.get(1).add(3);
        adj.get(3).add(1);
        adj.get(3).add(4);
        adj.get(4).add(3);
        
        Q1g_ArticulationPoints ap = new Q1g_ArticulationPoints();
        List<Integer> result = ap.findArticulationPoints(V, adj);
        System.out.println("Articulation points: " + result);
    }
}
