import java.util.Random;

public class Q1e_Treap {
    private static class Node {
        int key, priority, size;
        Node left, right;
        
        Node(int key) {
            this.key = key;
            this.priority = new Random().nextInt(100000);
            this.size = 1;
        }
    }
    
    private Node root;
    
    private int getSize(Node node) {
        return node == null ? 0 : node.size;
    }
    
    private void updateSize(Node node) {
        if (node != null) {
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }
    }
    
    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        updateSize(node);
        updateSize(left);
        return left;
    }
    
    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        updateSize(node);
        updateSize(right);
        return right;
    }
    
    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        
        if (key < node.key) {
            node.left = insert(node.left, key);
            if (node.left.priority > node.priority) {
                node = rotateRight(node);
            }
        } else if (key > node.key) {
            node.right = insert(node.right, key);
            if (node.right.priority > node.priority) {
                node = rotateLeft(node);
            }
        }
        
        updateSize(node);
        return node;
    }
    
    private Node delete(Node node, int key) {
        if (node == null) return null;
        
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null && node.right == null) return null;
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            
            if (node.left.priority > node.right.priority) {
                node = rotateRight(node);
                node.right = delete(node.right, key);
            } else {
                node = rotateLeft(node);
                node.left = delete(node.left, key);
            }
        }
        
        updateSize(node);
        return node;
    }
    
    public void insert(int key) {
        root = insert(root, key);
    }
    
    public void delete(int key) {
        root = delete(root, key);
    }
    
    public boolean search(int key) {
        Node curr = root;
        while (curr != null) {
            if (key == curr.key) return true;
            curr = key < curr.key ? curr.left : curr.right;
        }
        return false;
    }
    
    public static void main(String[] args) {
        Q1e_Treap treap = new Q1e_Treap();
        treap.insert(10);
        treap.insert(20);
        treap.insert(5);
        
        System.out.println("Search 10: " + treap.search(10));
        System.out.println("Search 15: " + treap.search(15));
        
        treap.delete(10);
        System.out.println("Search 10 after deletion: " + treap.search(10));
    }
}
