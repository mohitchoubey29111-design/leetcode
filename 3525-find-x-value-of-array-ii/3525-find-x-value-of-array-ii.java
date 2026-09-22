class Solution {

    class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            prod = 1;
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;

    Node merge(Node left, Node right) {
        Node result = new Node(k);

        result.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside left
        for (int i = 0; i < k; i++) {
            result.cnt[i] = left.cnt[i];
        }

        // Prefixes that use left + some prefix of right
        for (int r = 0; r < k; r++) {
            int newRemainder = (left.prod * r) % k;
            result.cnt[newRemainder] += right.cnt[r];
        }

        return result;
    }

    void build(int node, int start, int end, int[] nums) {

        if (start == end) {
            tree[node] = new Node(k);

            int value = nums[start] % k;

            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = (start + end) / 2;

        build(node * 2, start, mid, nums);
        build(node * 2 + 1, mid + 1, end, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int start, int end,
                int index, int value) {

        if (start == end) {

            tree[node] = new Node(k);

            value %= k;

            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = (start + end) / 2;

        if (index <= mid) {
            update(node * 2, start, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, end, index, value);
        }

        tree[node] = merge(tree[node * 2],
                           tree[node * 2 + 1]);
    }

    Node query(int node, int start, int end,
               int left, int right) {

        if (left <= start && end <= right) {
            return tree[node];
        }

        int mid = (start + end) / 2;

        if (right <= mid) {
            return query(node * 2, start, mid,
                         left, right);
        }

        if (left > mid) {
            return query(node * 2 + 1, mid + 1, end,
                         left, right);
        }

        Node leftNode = query(node * 2, start, mid,
                              left, right);

        Node rightNode = query(node * 2 + 1, mid + 1, end,
                               left, right);

        return merge(leftNode, rightNode);
    }

    public int[] resultArray(int[] nums, int k,
                             int[][] queries) {

        this.k = k;

        int n = nums.length;

        tree = new Node[4 * n];

        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }

        build(1, 0, n - 1, nums);

        int[] answer = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Permanent update
            update(1, 0, n - 1, index, value);

            // Query nums[start ... n-1]
            Node result = query(1, 0, n - 1,
                                start, n - 1);

            answer[q] = result.cnt[x];
        }

        return answer;
    }
}