import java.util.*;

public class Q1a_SubarraysWithKDifferent {
    public static int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }
    
    private static int atMostK(int[] nums, int k) {
        if (k == 0) return 0;
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, result = 0;
        
        for (int right = 0; right < nums.length; right++) {
            count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);
            
            while (count.size() > k) {
                count.put(nums[left], count.get(nums[left]) - 1);
                if (count.get(nums[left]) == 0) {
                    count.remove(nums[left]);
                }
                left++;
            }
            result += right - left + 1;
        }
        return result;
    }
    
    public static void main(String[] args) {
        int[] A = {1, 2, 1, 2, 3};
        int K = 2;
        System.out.println("Result: " + subarraysWithKDistinct(A, K)); // Output: 7
    }
}
