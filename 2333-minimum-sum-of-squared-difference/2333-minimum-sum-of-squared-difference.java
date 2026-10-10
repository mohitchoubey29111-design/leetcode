

class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2,
                                 int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        long[] diff = new long[n];
        long total = 0;
        long max = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs((long) nums1[i] - nums2[i]);
            total += diff[i];
            max = Math.max(max, diff[i]);
        }

        if (k >= total) return 0;

        long low = 0, high = max;

        while (low < high) {
            long mid = low + (high - low) / 2;
            long needed = 0;

            for (long d : diff) {
                if (d > mid) {
                    needed += d - mid;
                }
                if (needed > k) break;
            }

            if (needed <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        long level = low;
        long used = 0;

        for (long d : diff) {
            if (d > level) {
                used += d - level;
            }
        }

        long remaining = k - used;
        long ans = 0;

        for (long d : diff) {
            long value = Math.min(d, level);

            if (value == level && remaining > 0) {
                value--;
                remaining--;
            }

            ans += value * value;
        }

        return ans;
    }
}