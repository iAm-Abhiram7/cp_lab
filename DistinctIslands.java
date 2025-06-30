import java.util.*;

class DistinctIslands {
    private int n, m;
    private boolean[][] visited;
    private int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int numDistinctIslands(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        visited = new boolean[n][m];
        Set<String> distinctShapes = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    List<String> shape = new ArrayList<>();
                    dfs(grid, i, j, i, j, shape);
                    distinctShapes.add(String.join(",", shape));
                }
            }
        }
        
        return distinctShapes.size();
    }
    
    private void dfs(int[][] grid, int i, int j, int baseI, int baseJ, List<String> shape) {
        if (i < 0 || i >= n || j < 0 || j >= m || grid[i][j] == 0 || visited[i][j]) {
            return;
        }
        
        visited[i][j] = true;
        // Store relative position from the starting point of the island
        shape.add((i - baseI) + ":" + (j - baseJ));
        
        for (int[] dir : directions) {
            dfs(grid, i + dir[0], j + dir[1], baseI, baseJ, shape);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        
        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        
        DistinctIslands solution = new DistinctIslands();
        System.out.println(solution.numDistinctIslands(grid));
        
        scanner.close();
    }
}
