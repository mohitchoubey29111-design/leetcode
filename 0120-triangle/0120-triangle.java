
import java.util.*;

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        Integer[][] dp = new Integer[n][n];

        return solve(0, 0, triangle, dp);
    }

    private int solve(int i, int j, List<List<Integer>> triangle,
                      Integer[][] dp) {

        if (i == triangle.size() - 1) {
            return triangle.get(i).get(j);
        }

        if (dp[i][j] != null) {
            return dp[i][j];
        }

        int down = solve(i + 1, j, triangle, dp);
        int diagonal = solve(i + 1, j + 1, triangle, dp);

        dp[i][j] = triangle.get(i).get(j)
                 + Math.min(down, diagonal);

        return dp[i][j];
    }
}

