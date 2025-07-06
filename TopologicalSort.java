import java.util.*;

class TopologicalSort {
    private int V;
    private List<List<Integer>> adj;
    
    public TopologicalSort(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int u, int v) {
        adj.get(u).add(v);
    }
    
    // DFS based topological sort
    public List<Integer> topologicalSortDFS() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                dfs(i, visited, stack);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    
    private void dfs(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        
        for (int u : adj.get(v)) {
            if (!visited[u]) {
                dfs(u, visited, stack);
            }
        }
        
        stack.push(v);
    }
    
    // Kahn's algorithm (BFS based topological sort)
    public List<Integer> topologicalSortBFS() {
        int[] indegree = new int[V];
        
        // Calculate indegree for each vertex
        for (int i = 0; i < V; i++) {
            for (int j : adj.get(i)) {
                indegree[j]++;
            }
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            result.add(u);
            
            for (int v : adj.get(u)) {
                indegree[v]--;
                if (indegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        
        return result.size() == V ? result : new ArrayList<>(); // Return empty if cycle exists
    }
    
    // Check if graph has cycle (useful for course schedule)
    public boolean hasCycle() {
        return topologicalSortBFS().size() != V;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter number of vertices:");
        int V = scanner.nextInt();
        System.out.println("Enter number of edges:");
        int E = scanner.nextInt();
        
        TopologicalSort graph = new TopologicalSort(V);
        
        System.out.println("Enter edges (u -> v):");
        for (int i = 0; i < E; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.addEdge(u, v);
        }
        
        System.out.println("Topological Sort (DFS): " + graph.topologicalSortDFS());
        System.out.println("Topological Sort (BFS): " + graph.topologicalSortBFS());
        System.out.println("Has cycle: " + graph.hasCycle());
        
        scanner.close();
    }
}
