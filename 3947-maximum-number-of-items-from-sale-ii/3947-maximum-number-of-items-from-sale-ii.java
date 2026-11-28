
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;

        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
        }

        // Step 1: Count frequency of each factor
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Step 2: Calculate free item opportunities
        int[] gain = new int[maxFactor + 1];

        for (int f = 1; f <= maxFactor; f++) {
            for (int j = f; j <= maxFactor; j += f) {
                gain[f] += freq[j];
            }
            gain[f]--;
        }

        // Step 3: Create priority queue
        // Each entry: {price, value, available count}
        PriorityQueue<long[]> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(
                b[1] * a[0],
                a[1] * b[0]
            )
        );

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            // First gain purchases give 2 items
            if (gain[factor] > 0) {
                pq.offer(new long[]{price, 2, gain[factor]});
            }

            // Remaining purchases give 1 item
            pq.offer(new long[]{price, 1, Long.MAX_VALUE});
        }

        // Step 4: Greedy purchasing
        long ans = 0;
        long money = budget;

        while (!pq.isEmpty() && money > 0) {

            long[] curr = pq.poll();

            long price = curr[0];
            long value = curr[1];
            long count = curr[2];

            long buy = Math.min(count, money / price);

            ans += buy * value;
            money -= buy * price;
        }

        return (int) ans;
    }
}
