public class Q2_LexicographicallySmallestEquivalentString {
    private int[] parent;
    
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        parent = new int[26];
        for (int i = 0; i < 26; i++) {
            parent[i] = i;
        }
        
        // Union characters based on equivalence
        for (int i = 0; i < s1.length(); i++) {
            union(s1.charAt(i) - 'a', s2.charAt(i) - 'a');
        }
        
        StringBuilder result = new StringBuilder();
        for (char c : baseStr.toCharArray()) {
            result.append((char) ('a' + find(c - 'a')));
        }
        
        return result.toString();
    }
    
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX != rootY) {
            // Always make the lexicographically smaller character the root
            if (rootX < rootY) {
                parent[rootY] = rootX;
            } else {
                parent[rootX] = rootY;
            }
        }
    }
    
    public static void main(String[] args) {
        Q2_LexicographicallySmallestEquivalentString solver = 
            new Q2_LexicographicallySmallestEquivalentString();
        
        // Test case 1
        String A1 = "parker", B1 = "morris", S1 = "parser";
        System.out.println("Result 1: " + solver.smallestEquivalentString(A1, B1, S1));
        
        // Test case 2
        String A2 = "hello", B2 = "world", S2 = "hold";
        System.out.println("Result 2: " + solver.smallestEquivalentString(A2, B2, S2));
    }
}
