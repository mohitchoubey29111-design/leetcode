class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        int[] best = new int[n];
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        int prefix = 0;
        int answer = INF;
        int minLength = INF;

        map.put(0, -1);

        for (int i = 0; i < n; i++) {
            prefix += arr[i];

            if (map.containsKey(prefix - target)) {
                int j = map.get(prefix - target);
                int length = i - j;

                if (j >= 0 && best[j] < INF) {
                    answer = Math.min(answer, length + best[j]);
                }

                minLength = Math.min(minLength, length);
            }

            best[i] = minLength;

            map.put(prefix, i);
        }

        return answer == INF ? -1 : answer;
    }
}