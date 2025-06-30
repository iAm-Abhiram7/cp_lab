import java.util.*;

class MinimumSemesters {
    public int minimumSemesters(int n, int[][] dependencies, int k) {
        // Build adjacency list and calculate in-degrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[n + 1];
        
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] dep : dependencies) {
            int prereq = dep[0];
            int course = dep[1];
            graph.get(prereq).add(course);
            indegree[course]++;
        }
        
        // Initialize queue with courses having no prerequisites
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
            int size = Math.min(queue.size(), k); // Can take at most k courses per semester
            
            for (int i = 0; i < size; i++) {
                int course = queue.poll();
                completed++;
                
                // Update prerequisites for dependent courses
                for (int dependent : graph.get(course)) {
                    indegree[dependent]--;
                    if (indegree[dependent] == 0) {
                        queue.offer(dependent);
                    }
                }
            }
        }
        
        return completed == n ? semesters : -1; // Return -1 if not all courses can be completed
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of Dependencies (n): ");
        int n = scanner.nextInt();
        
        System.out.print("Enter number of prerequisite relations: ");
        int numDeps = scanner.nextInt();
        
        int[][] dependencies = new int[numDeps][2];
        System.out.println("Enter prerequisite pairs (a b) :");
        for (int i = 0; i < numDeps; i++) {
            dependencies[i][0] = scanner.nextInt();
            dependencies[i][1] = scanner.nextInt();
        }
        
        System.out.print("Enter maximum number of Dependencies per semester (k): ");
        int k = scanner.nextInt();
        
        MinimumSemesters solution = new MinimumSemesters();
        int result = solution.minimumSemesters(n, dependencies, k);
        
        System.out.println("Minimum number of semesters: " + result);
        
        scanner.close();
    }
}
