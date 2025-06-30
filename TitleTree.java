import java.util.*;

class TitleTree {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        
        public TrieNode() {
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
    
    private TrieNode root;
    
    public TitleTree() {
        root = new TrieNode();
    }
    
    // Insert a title into the catalog
    public void insert(String title) {
        TrieNode current = root;
        for (char ch : title.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }
    
    // Search if a full title exists in the catalog
    public boolean search(String title) {
        TrieNode current = root;
        for (char ch : title.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return current.isEndOfWord;
    }
    
    // Check if any book title starts with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            if (!current.children.containsKey(ch)) {
                return false;
            }
            current = current.children.get(ch);
        }
        return true;
    }
    
    // Delete a title from the catalog
    public boolean delete(String title) {
        return deleteHelper(root, title, 0);
    }
    
    private boolean deleteHelper(TrieNode node, String title, int index) {
        if (index == title.length()) {
            if (!node.isEndOfWord) {
                return false; // Title doesn't exist
            }
            node.isEndOfWord = false;
            return node.children.isEmpty();
        }
        
        char ch = title.charAt(index);
        TrieNode childNode = node.children.get(ch);
        
        if (childNode == null) {
            return false; // Title doesn't exist
        }
        
        boolean shouldDeleteChild = deleteHelper(childNode, title, index + 1);
        
        if (shouldDeleteChild) {
            node.children.remove(ch);
            return !node.isEndOfWord && node.children.isEmpty();
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TitleTree titleTree = new TitleTree();
        
        System.out.println("Enter Titles:");
        String[] titles = scanner.nextLine().split(" ");
        
        for (String title : titles) {
            titleTree.insert(title);
        }
        System.out.println("Titles are inserted in catalog.");
        
        System.out.println("Enter a Title to search:");
        String searchTitle = scanner.nextLine();
        System.out.println(titleTree.search(searchTitle));
        
        System.out.println("Enter a prefix to check:");
        String prefix = scanner.nextLine();
        System.out.println(titleTree.startsWith(prefix));
        
        System.out.println("Enter a Title to delete:");
        String deleteTitle = scanner.nextLine();
        if (titleTree.delete(deleteTitle)) {
            System.out.println("Deleted successfully");
        } else {
            System.out.println("Title not found");
        }
        
        scanner.close();
    }
}
