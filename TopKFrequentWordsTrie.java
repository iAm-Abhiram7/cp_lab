import java.util.*;

class TopKFrequentWordsTrie {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        int frequency;
        String word;
        
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
            frequency = 0;
            word = "";
        }
    }
    
    private TrieNode root;
    
    public TopKFrequentWordsTrie() {
        root = new TrieNode();
    }
    
    // Insert word into trie and update frequency
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
        current.frequency++;
        current.word = word;
    }
    
    // Get all words with their frequencies using DFS
    public List<WordFrequency> getAllWords() {
        List<WordFrequency> words = new ArrayList<>();
        dfs(root, words);
        return words;
    }
    
    private void dfs(TrieNode node, List<WordFrequency> words) {
        if (node.isEndOfWord) {
            words.add(new WordFrequency(node.word, node.frequency));
        }
        
        for (TrieNode child : node.children.values()) {
            dfs(child, words);
        }
    }
    
    // Helper class to store word and its frequency
    class WordFrequency {
        String word;
        int frequency;
        
        public WordFrequency(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }
    
    public List<String> topKFrequent(String[] words, int k) {
        // Build trie with word frequencies
        for (String word : words) {
            insert(word);
        }
        
        // Get all words from trie
        List<WordFrequency> allWords = getAllWords();
        
        // Sort by frequency (descending) and then lexicographically (ascending)
        allWords.sort((a, b) -> {
            if (a.frequency != b.frequency) {
                return b.frequency - a.frequency; // Higher frequency first
            } else {
                return a.word.compareTo(b.word); // Lexicographically smaller first
            }
        });
        
        // Extract top k words
        List<String> result = new ArrayList<>();
        for (int i = 0; i < Math.min(k, allWords.size()); i++) {
            result.add(allWords.get(i).word);
        }
        
        return result;
    }
        
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine();
        String[] words = input.split(",");
        
        int k = scanner.nextInt();
        
        TopKFrequentWordsTrie solution = new TopKFrequentWordsTrie();
        
        // Using sorting approach
        List<String> result1 = solution.topKFrequent(words, k);
        System.out.println("Using Trie + Sort: " + result1);
        
        // Reset trie for second approach
        solution = new TopKFrequentWordsTrie();        
        // Display final result (matches expected output format)
        System.out.println(result1);
        
        scanner.close();
    }
}
