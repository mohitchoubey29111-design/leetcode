
class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int mod = 1000000007;

        int[][][] dp = new int[m][n][2];

        // Start by moving right or down
        dp[0][1][0] = 1;
        dp[1][0][1] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                for (int d = 0; d < 2; d++) {

                    int ways = dp[i][j][d];

                    if (ways == 0) {
                        continue;
                    }

                    if (grid[i][j] == 0) {

                        // Move right
                        if (j + 1 < n) {
                            dp[i][j + 1][0] =
                                (dp[i][j + 1][0] + ways) % mod;
                        }

                        // Move down
                        if (i + 1 < m) {
                            dp[i + 1][j][1] =
                                (dp[i + 1][j][1] + ways) % mod;
                        }

                    } else {

                        // Mirror: entered while moving right
                        if (d == 0 && i + 1 < m) {
                            dp[i + 1][j][1] =
                                (dp[i + 1][j][1] + ways) % mod;
                        }

                        // Mirror: entered while moving down
                        else if (d == 1 && j + 1 < n) {
                            dp[i][j + 1][0] =
                                (dp[i][j + 1][0] + ways) % mod;
                        }
                    }
                }
            }
        }

        return (dp[m - 1][n - 1][0]
              + dp[m - 1][n - 1][1]) % mod;
    }
}
