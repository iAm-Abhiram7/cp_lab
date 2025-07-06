import java.util.*;

class LongestWordWithAllPrefixes {
    class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        String word;
        
        public TrieNode() {
            children = new TrieNode[26]; // For lowercase letters a-z
            isEndOfWord = false;
            word = "";
        }
    }
    
    private TrieNode root;
    
    public LongestWordWithAllPrefixes() {
        root = new TrieNode();
    }
    
    // Insert word into trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
        current.word = word;
    }
    
    // Optimal DFS approach: Only traverse paths where all prefixes exist
    public String longestWord(String[] words) {
        // Insert all words into trie
        for (String word : words) {
            insert(word);
        }
        
        // Start DFS from root - only follow paths with complete words
        return dfsOptimal(root);
    }
    
    // DFS that only follows valid prefix paths
    private String dfsOptimal(TrieNode node) {
        String result = node.word;
        
        // Process children in order (a-z) for lexicographic preference
        for (int i = 0; i < 26; i++) {
            TrieNode child = node.children[i];
            
            if (child != null && child.isEndOfWord) {
                // Only continue if this forms a complete word (valid prefix)
                String childResult = dfsOptimal(child);
                
                // Update result if we found a longer word, or same length but lexicographically smaller
                if (childResult.length() > result.length() || 
                    (childResult.length() == result.length() && 
                     childResult.compareTo(result) < 0)) {
                    result = childResult;
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LongestWordWithAllPrefixes solution = new LongestWordWithAllPrefixes();
        
        System.out.println("Enter number of words:");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String[] words = new String[n];
        System.out.println("Enter words:");
        for (int i = 0; i < n; i++) {
            words[i] = scanner.nextLine();
        }
        
        System.out.println("\nChoose operation:");
        System.out.println("1. Find longest word with all prefixes (Optimal DFS)");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                String longest = solution.longestWord(words);
                System.out.println("Longest word with all prefixes: " + 
                                 (longest.isEmpty() ? "None" : longest));
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        scanner.close();
    }
}
