import java.util.*;

class IndexPairsOfString {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
    
    private TrieNode root;
    
    public IndexPairsOfString() {
        root = new TrieNode();
    }
    
    // Insert word into trie
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }
    
    // Find all index pairs where words from dictionary appear in text
    public int[][] indexPairs(String text, String[] words) {
        // Build trie with all words
        for (String word : words) {
            insert(word);
        }
        
        List<int[]> result = new ArrayList<>();
        
        // For each starting position in text
        for (int i = 0; i < text.length(); i++) {
            TrieNode current = root;
            
            // Try to match words starting from position i
            for (int j = i; j < text.length(); j++) {
                char ch = text.charAt(j);
                
                if (!current.children.containsKey(ch)) {
                    break; // No more matches possible from this position
                }
                
                current = current.children.get(ch);
                
                // If we found a complete word, add the index pair
                if (current.isEndOfWord) {
                    result.add(new int[]{i, j});
                }
            }
        }
        
        // Convert to array and sort by start index, then by end index
        int[][] pairs = result.toArray(new int[result.size()][]);
        Arrays.sort(pairs, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0]; // Sort by start index
            }
            return a[1] - b[1]; // Then by end index
        });
        
        return pairs;
    }
    
    // Alternative method that returns the actual words found with their positions
    public List<String> findWordsWithPositions(String text, String[] words) {
        for (String word : words) {
            insert(word);
        }
        
        List<String> result = new ArrayList<>();
        
        for (int i = 0; i < text.length(); i++) {
            TrieNode current = root;
            StringBuilder currentWord = new StringBuilder();
            
            for (int j = i; j < text.length(); j++) {
                char ch = text.charAt(j);
                
                if (!current.children.containsKey(ch)) {
                    break;
                }
                
                current = current.children.get(ch);
                currentWord.append(ch);
                
                if (current.isEndOfWord) {
                    result.add("Word: " + currentWord.toString() + 
                              " at position [" + i + ", " + j + "]");
                }
            }
        }
        
        return result;
    }
    
    // Find overlapping occurrences
    public List<int[]> findOverlappingOccurrences(String text, String[] words) {
        for (String word : words) {
            insert(word);
        }
        
        List<int[]> result = new ArrayList<>();
        
        for (int i = 0; i < text.length(); i++) {
            TrieNode current = root;
            
            for (int j = i; j < text.length(); j++) {
                char ch = text.charAt(j);
                
                if (!current.children.containsKey(ch)) {
                    break;
                }
                
                current = current.children.get(ch);
                
                if (current.isEndOfWord) {
                    result.add(new int[]{i, j});
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IndexPairsOfString solution = new IndexPairsOfString();
        
        System.out.println("Enter text:");
        String text = scanner.nextLine();
        
        System.out.println("Enter number of words:");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        String[] words = new String[n];
        System.out.println("Enter words:");
        for (int i = 0; i < n; i++) {
            words[i] = scanner.nextLine();
        }
        
        System.out.println("\nChoose operation:");
        System.out.println("1. Find index pairs");
        System.out.println("2. Find words with positions");
        System.out.println("3. Find overlapping occurrences");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                int[][] pairs = solution.indexPairs(text, words);
                System.out.println("Index pairs:");
                for (int[] pair : pairs) {
                    System.out.println("[" + pair[0] + ", " + pair[1] + "]");
                }
                break;
            case 2:
                solution = new IndexPairsOfString(); // Reset trie
                List<String> wordsWithPos = solution.findWordsWithPositions(text, words);
                System.out.println("Words with positions:");
                for (String result : wordsWithPos) {
                    System.out.println(result);
                }
                break;
            case 3:
                solution = new IndexPairsOfString(); // Reset trie
                List<int[]> overlapping = solution.findOverlappingOccurrences(text, words);
                System.out.println("Overlapping occurrences:");
                for (int[] pair : overlapping) {
                    System.out.println("[" + pair[0] + ", " + pair[1] + "] = \"" + 
                                     text.substring(pair[0], pair[1] + 1) + "\"");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        scanner.close();
    }
}
