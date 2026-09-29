class Solution {
    Boolean[][][] dp;
    int m, n;

    public boolean hasValidPath(char[][] grid) {
        m = grid.length;
        n = grid[0].length;

        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        if (grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }

        dp = new Boolean[m][n][m + n];

        return dfs(grid, 0, 0, 0);
    }

    private boolean dfs(char[][] grid, int r, int c, int balance) {

        if (grid[r][c] == '(') {
            balance++;
        } else {
            balance--;
        }

        if (balance < 0) {
            return false;
        }

        if (r == m - 1 && c == n - 1) {
            return balance == 0;
        }

        if (dp[r][c][balance] != null) {
            return dp[r][c][balance];
        }

        boolean result = false;

        if (r + 1 < m) {
            result = dfs(grid, r + 1, c, balance);
        }

        if (!result && c + 1 < n) {
            result = dfs(grid, r, c + 1, balance);
        }

        return dp[r][c][balance] = result;
    }
}