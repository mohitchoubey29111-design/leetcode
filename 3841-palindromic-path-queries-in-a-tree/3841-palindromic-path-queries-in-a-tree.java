
import java.util.*;

class Solution {
    int LOG;
    int[] tin, tout, depth, parent, size;
    int[][] up;
    int timer;
    List<Integer>[] tree;
    Fenwick bit;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        tree = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            tree[e[0]].add(e[1]);
            tree[e[1]].add(e[0]);
        }

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        size = new int[n];
        up = new int[LOG][n];

        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0, count = 0;

        Arrays.fill(parent, -1);
        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];
            tin[u] = count;
            order[count++] = u;
            size[u] = 1;

            for (int v : tree[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            if (parent[u] != -1) {
                size[parent[u]] += size[u];
            }
        }

        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + size[u] - 1;
            up[0][u] = parent[u] == -1 ? u : parent[u];
        }

        for (int k = 1; k < LOG; k++) {
            for (int u = 0; u < n; u++) {
                up[k][u] = up[k - 1][up[k - 1][u]];
            }
        }

        bit = new Fenwick(n + 1);
        char[] chars = s.toCharArray();

        for (int u = 0; u < n; u++) {
            int mask = 1 << (chars[u] - 'a');
            rangeXor(tin[u], tout[u], mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                int delta = (1 << (chars[u] - 'a'))
                          ^ (1 << (c - 'a'));

                rangeXor(tin[u], tout[u], delta);
                chars[u] = c;
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int l = lca(u, v);
                int mask = bit.query(tin[u])
                          ^ bit.query(tin[v])
                          ^ (1 << (chars[l] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) return u;

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    void rangeXor(int left, int right, int mask) {
        bit.update(left, mask);
        bit.update(right + 1, mask);
    }

    class Fenwick {
        int[] tree;

        Fenwick(int n) {
            tree = new int[n + 1];
        }

        void update(int index, int value) {
            for (int i = index + 1; i < tree.length; i += i & -i) {
                tree[i] ^= value;
            }
        }

        int query(int index) {
            int result = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                result ^= tree[i];
            }

            return result;
        }
    }
}
