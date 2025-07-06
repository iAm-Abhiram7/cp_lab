import java.util.*;

class TopKFrequentWordsAdvanced {
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
    
    public TopKFrequentWordsAdvanced() {
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
    
    // Helper class to store word and its frequency
    class WordFrequency {
        String word;
        int frequency;
        
        public WordFrequency(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
        
        @Override
        public String toString() {
            return word + "(" + frequency + ")";
        }
    }
    
    // Main method: Top K Frequent Words using Priority Queue with Trie
    public List<String> topKFrequent(String[] words, int k) {
        // Build trie with word frequencies
        for (String word : words) {
            insert(word);
        }
        
        // Use min-heap to maintain top k elements during traversal
        PriorityQueue<WordFrequency> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.frequency != b.frequency) {
                return a.frequency - b.frequency; // Lower frequency first (min-heap)
            } else {
                return b.word.compareTo(a.word); // Lexicographically larger first (min-heap)
            }
        });
        
        // Traverse trie and directly maintain top k in heap
        dfsWithHeap(root, minHeap, k);
        
        // Extract words from heap and reverse order
        List<String> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().word);
        }
        
        Collections.reverse(result);
        return result;
    }
    
    // DFS traversal that maintains priority queue of top k elements
    private void dfsWithHeap(TrieNode node, PriorityQueue<WordFrequency> heap, int k) {
        if (node.isEndOfWord) {
            WordFrequency wf = new WordFrequency(node.word, node.frequency);
            heap.offer(wf);
            
            // Keep only top k elements
            if (heap.size() > k) {
                heap.poll();
            }
        }
        
        // Continue DFS traversal
        for (TrieNode child : node.children.values()) {
            dfsWithHeap(child, heap, k);
        }
    }
    
    // Display trie structure with frequencies
    public void displayTrieWithFrequencies() {
        System.out.println("Trie Structure with Frequencies:");
        displayTrieHelper(root, "");
    }
    
    private void displayTrieHelper(TrieNode node, String prefix) {
        if (node.isEndOfWord) {
            System.out.println(prefix + " -> frequency: " + node.frequency);
        }
        
        List<Character> sortedKeys = new ArrayList<>(node.children.keySet());
        Collections.sort(sortedKeys);
        
        for (Character key : sortedKeys) {
            displayTrieHelper(node.children.get(key), prefix + key);
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TopKFrequentWordsAdvanced solution = new TopKFrequentWordsAdvanced();
        
        System.out.println("Enter words (comma-separated):");
        String input = scanner.nextLine();
        String[] words = input.split(",");
        
        // Trim whitespace from words
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        
        System.out.println("Enter k:");
        int k = scanner.nextInt();
        
        System.out.println("\nChoose operation:");
        System.out.println("1. Top K frequent words using Trie + Priority Queue");
        System.out.println("2. Display trie structure");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                List<String> result = solution.topKFrequent(words, k);
                System.out.println("Top " + k + " frequent words: " + result);
                break;
            case 2:
                solution = new TopKFrequentWordsAdvanced(); // Reset and rebuild
                for (String word : words) {
                    solution.insert(word);
                }
                solution.displayTrieWithFrequencies();
                break;
            default:
                System.out.println("Invalid choice");
        }
        
        scanner.close();
    }
}
