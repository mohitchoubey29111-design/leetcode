class Solution {
    public void wiggleSort(int[] nums) {

        int n = nums.length;

        int[] sorted = nums.clone();
        java.util.Arrays.sort(sorted);

        int mid = (n + 1) / 2;
        int large = n;

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                nums[i] = sorted[--mid];
            } else {
                nums[i] = sorted[--large];
            }
        }
    }
}