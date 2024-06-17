package aufgabe4;

public class UnionFind {
    private final int[] parent;
    private final int[] rank;

    public UnionFind(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int e) {
        if (parent[e] != e) {
            parent[e] = find(parent[e]);
        }
        return parent[e];
    }

    public void union(int s1, int s2) {
        int root1 = find(s1);
        int root2 = find(s2);
        if (root1 == root2) {
            return;
        }
        if (rank[root1] < rank[root2]) {
            parent[root1] = root2;
        } else if (rank[root1] > rank[root2]) {
            parent[root2] = root1;
        } else {
            parent[root2] = root1;
            rank[root1]++;
        }
    }

    public int size() {
        return parent.length;
    }

    public static void main(String[] args) {
        // Test UnionFind
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(6, 7);
        for (int i = 0; i < uf.size(); i++) {
            System.out.println(i + " -> " + uf.find(i));
        }

    }
}
