import java.util.*;

class LowestCommonManager {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        if (left != null && right != null) {
            return root;
        }
        
        return left != null ? left : right;
    }
    
    private TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        
        TreeNode left = findNode(root.left, val);
        if (left != null) return left;
        
        return findNode(root.right, val);
    }
    
    private TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) return null;
        
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode current = queue.poll();
            
            if (i < values.length && values[i] != null && values[i] != -1) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            i++;
            
            if (i < values.length && values[i] != null && values[i] != -1) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
            i++;
        }
        
        return root;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        String[] input = scanner.nextLine().split(" ");
        Integer[] values = new Integer[input.length];
        
        for (int i = 0; i < input.length; i++) {
            if (input[i].equals("-1")) {
                values[i] = null;
            } else {
                values[i] = Integer.parseInt(input[i]);
            }
        }
        
        String[] employees = scanner.nextLine().split(" ");
        int emp1 = Integer.parseInt(employees[0]);
        int emp2 = Integer.parseInt(employees[1]);
        
        LowestCommonManager solution = new LowestCommonManager();
        TreeNode root = solution.buildTree(values);
        
        TreeNode node1 = solution.findNode(root, emp1);
        TreeNode node2 = solution.findNode(root, emp2);
        
        TreeNode lca = solution.lowestCommonAncestor(root, node1, node2);
        
        System.out.println(lca.val);
        
        scanner.close();
    }
}
