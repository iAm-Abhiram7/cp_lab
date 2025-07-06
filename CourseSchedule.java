import java.util.*;

class CourseSchedule {
    
    // Problem: Course Schedule I
    // Check if it's possible to finish all courses given prerequisites
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build graph and calculate indegrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            indegree[course]++;
        }
        
        // BFS (Kahn's algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int processedCourses = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            processedCourses++;
            
            for (int nextCourse : graph.get(course)) {
                indegree[nextCourse]--;
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        
        return processedCourses == numCourses;
    }
    
    // Problem: Course Schedule II
    // Return the ordering of courses to take to finish all courses
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // Build graph and calculate indegrees
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            graph.get(prerequisite).add(course);
            indegree[course]++;
        }
        
        // BFS (Kahn's algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int[] order = new int[numCourses];
        int index = 0;
        
        while (!queue.isEmpty()) {
            int course = queue.poll();
            order[index++] = course;
            
            for (int nextCourse : graph.get(course)) {
                indegree[nextCourse]--;
                if (indegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        
        return index == numCourses ? order : new int[0];
    }
    
    // DFS approach for cycle detection
    public boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
        }
        
        int[] visited = new int[numCourses]; // 0: unvisited, 1: visiting, 2: visited
        
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                if (hasCycleDFS(graph, i, visited)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean hasCycleDFS(List<List<Integer>> graph, int node, int[] visited) {
        if (visited[node] == 1) return true;  // Back edge found (cycle)
        if (visited[node] == 2) return false; // Already processed
        
        visited[node] = 1; // Mark as visiting
        
        for (int neighbor : graph.get(node)) {
            if (hasCycleDFS(graph, neighbor, visited)) {
                return true;
            }
        }
        
        visited[node] = 2; // Mark as visited
        return false;
    }
    
    // Find all possible course schedules
    public List<List<Integer>> findAllSchedules(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] indegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] prereq : prerequisites) {
            graph.get(prereq[1]).add(prereq[0]);
            indegree[prereq[0]]++;
        }
        
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentSchedule = new ArrayList<>();
        boolean[] taken = new boolean[numCourses];
        
        findAllSchedulesHelper(graph, indegree, taken, currentSchedule, result, numCourses);
        return result;
    }
    
    private void findAllSchedulesHelper(List<List<Integer>> graph, int[] indegree, 
                                       boolean[] taken, List<Integer> currentSchedule, 
                                       List<List<Integer>> result, int numCourses) {
        if (currentSchedule.size() == numCourses) {
            result.add(new ArrayList<>(currentSchedule));
            return;
        }
        
        for (int i = 0; i < numCourses; i++) {
            if (!taken[i] && indegree[i] == 0) {
                taken[i] = true;
                currentSchedule.add(i);
                
                // Reduce indegree of dependent courses
                for (int dependent : graph.get(i)) {
                    indegree[dependent]--;
                }
                
                findAllSchedulesHelper(graph, indegree, taken, currentSchedule, result, numCourses);
                
                // Backtrack
                taken[i] = false;
                currentSchedule.remove(currentSchedule.size() - 1);
                for (int dependent : graph.get(i)) {
                    indegree[dependent]++;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseSchedule solution = new CourseSchedule();
        
        System.out.println("Choose problem:");
        System.out.println("1. Course Schedule I (can finish all courses?)");
        System.out.println("2. Course Schedule II (find one valid order)");
        System.out.println("3. Find all possible schedules");
        
        int choice = scanner.nextInt();
        
        System.out.println("Enter number of courses:");
        int numCourses = scanner.nextInt();
        System.out.println("Enter number of prerequisites:");
        int p = scanner.nextInt();
        
        int[][] prerequisites = new int[p][2];
        System.out.println("Enter prerequisites (course prerequisite):");
        for (int i = 0; i < p; i++) {
            prerequisites[i][0] = scanner.nextInt();
            prerequisites[i][1] = scanner.nextInt();
        }
        
        switch (choice) {
            case 1:
                boolean canFinish = solution.canFinish(numCourses, prerequisites);
                System.out.println("Can finish all courses: " + canFinish);
                break;
            case 2:
                int[] order = solution.findOrder(numCourses, prerequisites);
                if (order.length == 0) {
                    System.out.println("No valid order exists");
                } else {
                    System.out.println("Course order: " + Arrays.toString(order));
                }
                break;
            case 3:
                List<List<Integer>> allSchedules = solution.findAllSchedules(numCourses, prerequisites);
                System.out.println("All possible schedules:");
                for (List<Integer> schedule : allSchedules) {
                    System.out.println(schedule);
                }
                if (allSchedules.isEmpty()) {
                    System.out.println("No valid schedule exists");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        scanner.close();
    }
}
