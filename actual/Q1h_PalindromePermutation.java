import java.util.*;

public class Q1h_PalindromePermutation {
    public static boolean canPermutePalindrome(String s) {
        Set<Character> oddCount = new HashSet<>();
        
        for (char c : s.toCharArray()) {
            if (oddCount.contains(c)) {
                oddCount.remove(c);
            } else {
                oddCount.add(c);
            }
        }
        
        return oddCount.size() <= 1;
    }
    
    public static void main(String[] args) {
        String[] testCases = {"aab", "code", "carerac", "racecar"};
        
        for (String test : testCases) {
            System.out.println("\"" + test + "\" can form palindrome: " + 
                             canPermutePalindrome(test));
        }
    }
}
