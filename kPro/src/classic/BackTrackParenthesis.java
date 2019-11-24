package classic;

import java.util.ArrayList;

public class BackTrackParenthesis {


    /**
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * <p>
     * For example, given n = 3, a solution set is:
     * <p>
     * "((()))", "(()())", "(())()", "()(())", "()()()"
     */

    //DFS && Backtrack
    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> res = new ArrayList<String>();
        gp(res, "", 0, 0, n);
        return res;
    }

    private void gp(ArrayList<String> res, String str, int left, int right, int n) {
        if (right == n) {
            res.add(str);
            return;
        }
        if (left < n) {
            gp(res, str + "(", left + 1, right, n);
        }
        if (left > right) {
            gp(res, str + ")", left, right + 1, n);
        }
    }


    //给出一个仅包含字符'('和')'的字符串，计算最长的格式正确的括号子串的长度
    //对于字符串")()())",来说，最长的格式正确的子串是"()()"，长度为4
    public int longestValidParentheses(String s) {
        char[] chars = s.toCharArray();
        int[] dp = new int[chars.length];
        int maxLength = 0;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == ')') {
                if (chars[i - 1] == '(') {
                    dp[i] = 2 + (i - 2 >= 0 ? dp[i - 2] : 0);
                } else {
                    int pre = i - 1 - dp[i - 1];
                    if (pre >= 0 && chars[pre] == '(') {
                        dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                    }
                }
                if (maxLength < dp[i])
                    maxLength = dp[i];
            }
        }
        return maxLength;
    }

}
