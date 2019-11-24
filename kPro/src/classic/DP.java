package classic;

import java.util.Stack;

public class DP {

    /**
     * Implement regular expression matching with support for'.'and'*'
     * The matching should cover the entire input string (not partial)
     * isMatch("aa","aa") → true
     * isMatch("aaa","aa") → false
     * isMatch("aaa", "a*") → true
     * isMatch("aa", ".*") → true
     * isMatch("ab", ".*") → true
     * isMatch("aab", "c*a*b") → true
     */
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null)
            return false;
        int m = s.length(), n = p.length();
        boolean[][] res = new boolean[m + 1][n + 1];
        res[0][0] = true;
        for (int i = 0; i < n; i++) {
            if (p.charAt(i) == '*' && res[0][i - 1])
                res[0][i + 1] = true;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (p.charAt(j) == '.')
                    res[i + 1][j + 1] = res[i][j];
                if (p.charAt(j) == s.charAt(i))
                    res[i + 1][j + 1] = res[i][j];
                if (p.charAt(j) == '*') {
                    if (s.charAt(i) != p.charAt(j - 1) && p.charAt(j - 1) != '.')
                        res[i + 1][j + 1] = res[i + 1][j - 1];
                    else {
                        //res[i + 1][j - 1] 表示*一个都不匹配;
                        //res[i + 1][j]表示匹配一个
                        //res[i][j + 1]表示匹配多个
                        res[i + 1][j + 1] = res[i + 1][j - 1] || res[i + 1][j] || res[i][j + 1];
                    }
                }
            }
        }
        return res[m][n];
    }

    public boolean isMatchBetter(String s, String p) {
        int ls = s.length(), lp = p.length();
        boolean[][] dp = new boolean[ls + 1][lp + 1];
        dp[0][0] = true;
        for (int j = 2; j <= lp; j++)
            dp[0][j] = dp[0][j - 2] && p.charAt(j - 1) == '*';
        for (int i = 1; i <= ls; i++) {
            for (int j = 1; j <= lp; j++) {
                int m = i - 1, n = j - 1;
                if (p.charAt(n) == '*')
                    dp[i][j] = dp[i][j - 2] || dp[i - 1][j] &&
                            (s.charAt(m) == p.charAt(n - 1) || p.charAt(n - 1) == '.');
                else if (s.charAt(m) == p.charAt(n) || p.charAt(n) == '.')
                    dp[i][j] = dp[i - 1][j - 1];
            }
        }
        return dp[ls][lp];
    }


    //请实现支持'?'and'*'.的通配符模式匹配
    //'?' 可以匹配任何单个字符。 '*' 可以匹配任何字符序列（包括空序列）。 匹配应该覆盖整个输入字符串
    //isMatch2("aa", "*") → true
    //isMatch2("ab", "?*") → true
    //isMatch("aab", "c*a*b") → false
    public boolean isMatch2(String s, String p) {
        int row = s.length();
        int col = p.length();
        boolean[][] dp = new boolean[row + 1][col + 1];
        dp[0][0] = true;
        for (int j = 1; j < col + 1; j++) {
            if (dp[0][j - 1]) {
                if (p.charAt(j - 1) == '*')
                    dp[0][j] = true;
                else
                    break;
            }
        }
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if (p.charAt(j) == s.charAt(i) || p.charAt(j) == '?')
                    dp[i + 1][j + 1] = dp[i][j];
                else if (p.charAt(j) == '*') {
                    dp[i + 1][j + 1] = dp[i + 1][j] || dp[i][j + 1];
                }
            }

        return dp[row][col];
    }


    //最长有效括号

    /**
     * 输入: ")()())"
     * 输出: 4
     * 解释: 最长有效括号子串为 "()()"
     */
    public int longestValidParentheses(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    //最长有效括号,栈实现
    public int longestValidParentheses2(String s) {
        int maxans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    stack.push(i);
                } else {
                    maxans = Math.max(maxans, i - stack.peek());
                }
            }
        }
        return maxans;
    }

}
