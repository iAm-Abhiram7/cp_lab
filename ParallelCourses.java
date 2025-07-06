import java.util.*;

class ParallelCourses {
    
    // Problem: Parallel Courses I
    // Given n courses labeled from 1 to n and prerequisites,
    // find minimum number of semesters to complete all courses
    public int minimumSemesters(int n, int[][] prerequisites) {
        // Build graph and calculate indegrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int prev = prereq[0];
            int next = prereq[1];
            graph.get(prev).add(next);
            indegree[next]++;
        }
        
        // BFS (Kahn's algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int semesters = 0;
        int studiedCourses = 0;
        
        while (!queue.isEmpty()) {
            semesters++;
            int size = queue.size();
            
            // Process all courses that can be taken in current semester
            for (int i = 0; i < size; i++) {
                int course = queue.poll();
                studiedCourses++;
                
                // Update prerequisites for next courses
                for (int nextCourse : graph.get(course)) {
                    indegree[nextCourse]--;
                    if (indegree[nextCourse] == 0) {
                        queue.offer(nextCourse);
                    }
                }
            }
        }
        
        return studiedCourses == n ? semesters : -1;
    }
    
    // Problem: Parallel Courses II
    // With constraint k: maximum courses per semester
    public int minNumberOfSemesters(int n, int[][] dependencies, int k) {
        // Build graph and calculate indegrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] dep : dependencies) {
            int prev = dep[0];
            int next = dep[1];
            graph.get(prev).add(next);
            indegree[next]++;
        }
        
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int semesters = 0;
        int completed = 0;
        
        while (!queue.isEmpty()) {
            semesters++;
            int coursesToTake = Math.min(queue.size(), k);
            
            // Take up to k courses this semester
            for (int i = 0; i < coursesToTake; i++) {
                int course = queue.poll();
                completed++;
                
                // Update prerequisites
                for (int nextCourse : graph.get(course)) {
                    indegree[nextCourse]--;
                    if (indegree[nextCourse] == 0) {
                        queue.offer(nextCourse);
                    }
                }
            }
        }
        
        return completed == n ? semesters : -1;
    }
    
    // Find all possible topological orderings
    public void findAllTopologicalSorts(int n, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[0]).add(prereq[1]);
            indegree[prereq[1]]++;
        }
        
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[n + 1];
        findAllTopoSorts(graph, indegree, visited, result, n);
    }
    
    private void findAllTopoSorts(List<List<Integer>> graph, int[] indegree, 
                                  boolean[] visited, List<Integer> result, int n) {
        boolean flag = false;
        
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0 && !visited[i]) {
                // Include this vertex in result
                visited[i] = true;
                result.add(i);
                
                // Reduce indegree of adjacent vertices
                for (int adj : graph.get(i)) {
                    indegree[adj]--;
                }
                
                findAllTopoSorts(graph, indegree, visited, result, n);
                
                // Backtrack
                visited[i] = false;
                result.remove(result.size() - 1);
                for (int adj : graph.get(i)) {
                    indegree[adj]++;
                }
                
                flag = true;
            }
        }
        
        // Print current result if we've included all vertices
        if (!flag && result.size() == n) {
            System.out.println(result);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ParallelCourses solution = new ParallelCourses();
        
        System.out.println("Choose problem:");
        System.out.println("1. Parallel Courses I (minimum semesters)");
        System.out.println("2. Parallel Courses II (with constraint k)");
        System.out.println("3. All possible course orderings");
        
        int choice = scanner.nextInt();
        
        System.out.println("Enter number of courses:");
        int n = scanner.nextInt();
        System.out.println("Enter number of prerequisites:");
        int p = scanner.nextInt();
        
        int[][] prerequisites = new int[p][2];
        System.out.println("Enter prerequisites (course1 course2):");
        for (int i = 0; i < p; i++) {
            prerequisites[i][0] = scanner.nextInt();
            prerequisites[i][1] = scanner.nextInt();
        }
        
        switch (choice) {
            case 1:
                int result1 = solution.minimumSemesters(n, prerequisites);
                System.out.println("Minimum semesters: " + result1);
                break;
            case 2:
                System.out.println("Enter maximum courses per semester (k):");
                int k = scanner.nextInt();
                int result2 = solution.minNumberOfSemesters(n, prerequisites, k);
                System.out.println("Minimum semesters with constraint: " + result2);
                break;
            case 3:
                System.out.println("All possible course orderings:");
                solution.findAllTopologicalSorts(n, prerequisites);
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        scanner.close();
    }
}
