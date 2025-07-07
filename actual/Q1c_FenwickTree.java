public class Q1c_FenwickTree {
    private int[] tree;
    private int n;
    
    public Q1c_FenwickTree(int size) {
        n = size;
        tree = new int[n + 1];
    }
    
    public void update(int idx, int val) {
        for (int i = idx; i <= n; i += i & (-i)) {
            tree[i] += val;
        }
    }
    
    public int query(int idx) {
        int sum = 0;
        for (int i = idx; i > 0; i -= i & (-i)) {
            sum += tree[i];
        }
        return sum;
    }
    
    public int rangeQuery(int left, int right) {
        return query(right) - query(left - 1);
    }
    
    public static void main(String[] args) {
        Q1c_FenwickTree ft = new Q1c_FenwickTree(10);
        
        ft.update(1, 5);
        ft.update(3, 2);
        ft.update(5, 8);
        
        System.out.println("Sum from 1 to 5: " + ft.query(5));
        System.out.println("Range sum 2 to 4: " + ft.rangeQuery(2, 4));
    }
}
