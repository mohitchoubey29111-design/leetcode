import java.util.*;

class Solution {

    static class Interval {
        int start;
        int end;
        int weight;
        int index;

        Interval(int start, int end, int weight, int index) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.index = index;
        }
    }

    static class Result {
        long score;
        List<Integer> indices;

        Result(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    Result[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            List<Integer> x = intervals.get(i);

            arr[i] = new Interval(
                x.get(0),
                x.get(1),
                x.get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.start != b.start)
                return Integer.compare(a.start, b.start);

            if (a.end != b.end)
                return Integer.compare(a.end, b.end);

            return Integer.compare(a.index, b.index);
        });

        dp = new Result[n][5];

        Result ans = solve(0, 4);

        int[] result = new int[ans.indices.size()];

        for (int i = 0; i < ans.indices.size(); i++) {
            result[i] = ans.indices.get(i);
        }

        return result;
    }

    private Result solve(int i, int remaining) {

        if (i == arr.length || remaining == 0) {
            return new Result(0, new ArrayList<>());
        }

        if (dp[i][remaining] != null) {
            return dp[i][remaining];
        }

        // Option 1: Skip current interval
        Result skip = solve(i + 1, remaining);

        // Option 2: Take current interval
        int next = findNext(i);

        Result nextResult = solve(next, remaining - 1);

        long takeScore = arr[i].weight + nextResult.score;

        List<Integer> takeIndices =
            new ArrayList<>(nextResult.indices);

        takeIndices.add(arr[i].index);

        Collections.sort(takeIndices);

        Result take = new Result(takeScore, takeIndices);

        // Choose the better result
        if (take.score > skip.score) {
            dp[i][remaining] = take;
        } 
        else if (take.score < skip.score) {
            dp[i][remaining] = skip;
        } 
        else {
            // Same score → lexicographically smaller
            if (compare(take.indices, skip.indices) < 0) {
                dp[i][remaining] = take;
            } else {
                dp[i][remaining] = skip;
            }
        }

        return dp[i][remaining];
    }

    private int findNext(int i) {

        int low = i + 1;
        int high = arr.length;

        int end = arr[i].end;

        while (low < high) {

            int mid = low + (high - low) / 2;

            if (arr[mid].start > end) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    private int compare(List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(a.get(i), b.get(i));
            }
        }

        return Integer.compare(a.size(), b.size());
    }
}