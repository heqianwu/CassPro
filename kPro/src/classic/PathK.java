package classic;

public class PathK {

    //给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
    //每次只能向下或者向右移动一步。
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }


    //一个机器人位于一个 m x n 网格的左上角
    //机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角
    //总共有多少条不同的路径
    public int uniquePaths(int m, int n) {
        if (m == 1 || n == 1)
            return 1;
        if (m > n) {
            n = m + n;
            m = n - m;
            n = n - m;
        }
        long res = 1;
        for (int i = m + n - 2; i > n - 1; i--) {
            res *= i;
        }
        for (int i = 2; i < m; i++) {
            res /= i;
        }
        return (int) res;
    }

    //uniquePaths中添加了障碍物
    public int uniquePathsWithObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int dp[][] = new int[m][n];
        if (grid[0][0] == 1 || grid[m - 1][n - 1] == 1)
            return 0;
        dp[0][0] = 1;
        for (int i = 1; i < m; i++)
            if (dp[i - 1][0] == 1 && grid[i][0] == 0)
                dp[i][0] = 1;
        for (int i = 1; i < n; i++)
            if (dp[0][i - 1] == 1 && grid[0][i] == 0)
                dp[0][i] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = 0;
                if (grid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

}
