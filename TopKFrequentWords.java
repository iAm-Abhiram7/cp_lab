import java.util.*;

class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        // Count frequency of each word
        Map<String, Integer> count = new HashMap<>();
        for (String word : words) {
            count.put(word, count.getOrDefault(word, 0) + 1);
        }
        
        // Use a min-heap to keep track of top k frequent words
        PriorityQueue<String> heap = new PriorityQueue<>((w1, w2) -> {
            int freq1 = count.get(w1);
            int freq2 = count.get(w2);
            
            if (freq1 != freq2) {
                return freq1 - freq2; // Lower frequency first (min-heap)
            } else {
                return w2.compareTo(w1); // Lexicographically larger first (min-heap)
            }
        });
        
        for (String word : count.keySet()) {
            heap.offer(word);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        
        // Extract words from heap and reverse to get correct order
        List<String> result = new ArrayList<>();
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }
        
        Collections.reverse(result);
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String input = scanner.nextLine();
        String[] words = input.split(",");
        
        int k = scanner.nextInt();
        
        TopKFrequentWords solution = new TopKFrequentWords();
        List<String> result = solution.topKFrequent(words, k);
        
        System.out.println(result);
        
        scanner.close();
    }
}
