import java.util.*;

public class Q1i_IndexPairs {
    public static int[][] indexPairs(String text, String[] words) {
        List<int[]> result = new ArrayList<>();
        
        for (String word : words) {
            int index = 0;
            while ((index = text.indexOf(word, index)) != -1) {
                result.add(new int[]{index, index + word.length() - 1});
                index++;
            }
        }
        
        result.sort((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        
        return result.toArray(new int[result.size()][]);
    }
    
    public static void main(String[] args) {
        String text = "thestoryofleetcodeandme";
        String[] words = {"story", "fleet", "leetcode"};
        
        int[][] result = indexPairs(text, words);
        
        System.out.println("Index pairs:");
        for (int[] pair : result) {
            System.out.println("[" + pair[0] + ", " + pair[1] + "]");
        }
    }
}
