class Solution {
    public int countSymmetricIntegers(int low, int high) {
        int count = 0;

        for (int num = low; num <= high; num++) {
            String s = String.valueOf(num);

            // Odd number of digits cannot be symmetric
            if (s.length() % 2 != 0) {
                continue;
            }

            int mid = s.length() / 2;
            int leftSum = 0;
            int rightSum = 0;

            // Sum of first half
            for (int i = 0; i < mid; i++) {
                leftSum += s.charAt(i) - '0';
            }

            // Sum of second half
            for (int i = mid; i < s.length(); i++) {
                rightSum += s.charAt(i) - '0';
            }

            if (leftSum == rightSum) {
                count++;
            }
        }

        return count;
    }
}