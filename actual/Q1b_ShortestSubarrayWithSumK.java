public class Q1b_ShortestSubarrayWithSumK {
    public static int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int[] deque = new int[n + 1];
        int front = 0, rear = 0;
        int minLen = Integer.MAX_VALUE;
        
        for (int i = 0; i <= n; i++) {
            while (front < rear && prefixSum[i] - prefixSum[deque[front]] >= k) {
                minLen = Math.min(minLen, i - deque[front++]);
            }
            
            while (front < rear && prefixSum[i] <= prefixSum[deque[rear - 1]]) {
                rear--;
            }
            
            deque[rear++] = i;
        }
        
        return minLen == Integer.MAX_VALUE ? -1 : minLen;
    }
    
    public static void main(String[] args) {
        int[] A1 = {1};
        int K1 = 1;
        System.out.println("Result 1: " + shortestSubarray(A1, K1)); // Output: 1
        
        int[] A2 = {1, 2};
        int K2 = 4;
        System.out.println("Result 2: " + shortestSubarray(A2, K2)); // Output: -1
    }
}
