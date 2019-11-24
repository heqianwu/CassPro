package classic;

import java.util.ArrayList;

public class Palindrome {

    public static void main(String[] args) {
        System.out.println(longestPalindromeMid("cafgfkc"));
        System.out.println(getLongestPalindrome2("abba"));
        System.out.println(getLongestPalindrome2("aabbbbba"));
        System.out.println(longestPalindrome("cafgfkc"));
        System.out.println(longestPalindrome("ksdfefrds"));
    }

    public static String longestPalindromeMid(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }


    //中心扩展法，O(n*n)
    //最大回文子串
    public static int getLongestPalindrome(String str) {
        if (str == null || str.length() == 0)
            return 0;
        int max = 0;
        int len = str.length();
        int j;
        for (int i = 0; i < len; i++) {
            //当回文为奇数时
            for (j = 1; i + j < len && i - j >= 0; j++) {
                if (str.charAt(i - j) != str.charAt(i + j))
                    break;
            }
            max = Math.max(max, j * 2 - 1);
            for (j = 0; i + j + 1 < len && i - j >= 0; j++) {
                if (str.charAt(i - j) != str.charAt(i + j + 1))
                    break;
            }
            max = Math.max(max, j * 2);
        }
        return max;
    }

    //中心扩展法，O(n*n)
    //最大回文子串,返回唯一的最大子串
    public static String getLongestPalindromeStr(String str) {
        if (str == null || str.length() == 0)
            return "";
        int max = 0;
        int len = str.length();
        int start = 0;
        int j;
        for (int i = 0; i < len; i++) {
            //当回文为奇数时
            for (j = 1; i + j < len && i - j >= 0; j++) {
                if (str.charAt(i - j) != str.charAt(i + j))
                    break;
            }
            if (j * 2 - 1 > max) {
                max = j * 2 - 1;
                start = i - j + 1;
            }
            for (j = 0; i + j + 1 < len && i - j >= 0; j++) {
                if (str.charAt(i - j) != str.charAt(i + j + 1))
                    break;
            }
            if (j * 2 > max) {
                max = j * 2;
                start = i - j + 1;
            }
        }
        return str.substring(start, start + max);
    }


    //动态规划法，O(n*n)
    //dp[i][j]表示索引i到j的子串是否是回文
    //最大回文子串
    public static int getLongestPalindrome2(String str) {
        if (str == null || str.length() == 0)
            return 0;
        int len = str.length();
        boolean[][] dp = new boolean[len][len];
        int max = 1;
        for (int i = 0; i < len; i++)
            dp[i][i] = true;
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = str.charAt(i) == str.charAt(i + 1);
            if (dp[i][i + 1])
                max = 2;
        }
        for (int k = 2; k <= len; k++) {
            for (int i = 0; i < len - k; i++) {
                dp[i][i + k] = dp[i + 1][i + k - 1] && str.charAt(i) == str.charAt(i + k);
                if (dp[i][i + k])
                    max = k + 1;
            }
        }
        return max;
    }


    //Manacher法，O(n)
    //待补充
    //https://www.cnblogs.com/liuzhen1995/p/6407397.html
    //https://www.jianshu.com/p/38a399de5278
    //最大回文子串
    public static int getLongestPalindrome3(String str) {
        return 0;
    }


    //最大回文子序列
    //dp[ i ] [ j ]表示从S字符串中第i到j最长回文子序列的长度
    public static int longestPalindrome(String str) {
        if (str == null || str.length() == 0)
            return 0;
        int len = str.length();
        int[][] dp = new int[len][len];
        for (int i = 0; i < len; i++)
            dp[i][i] = 1;
        for (int i = 0; i < len - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                dp[i][i + 1] = 2;
            } else dp[i][i + 1] = 1;
        }
        for (int k = 2; k <= len; k++) {
            for (int i = 0; i < len - k; i++) {
                dp[i][i + k] = str.charAt(i) == str.charAt(i + k) ? 2 + dp[i + 1][i + k - 1] : Math.max(dp[i][i + k - 1], dp[i + 1][i + k]);
            }
        }
        return dp[0][len - 1];
    }

    public static boolean isPalindrome(String str) {
        if (str == null || str.length() == 0)
            return true;
        int start = 0;
        int end = str.length() - 1;
        while (start < end) {
            if (str.charAt(start) != str.charAt(end))
                return false;
            start++;
            end--;
        }
        return true;
    }

    //求最小分割数，分割后的所有字符串都是回文
    //复杂度O(n*n*n)？
    public static int minCutPalindrome(String str) {
        if (str == null || str.length() <= 0)
            return 0;
        int len = str.length();
        int[] dp = new int[len];
        for (int i = 0; i < len; i++)
            dp[i] = i;
        for (int i = 1; i < len; i++) {
            if (isPalindrome(str.substring(0, i + 1))) {
                dp[i] = 0;
                continue;
            }
            for (int j = 0; j < i; j++) {
                if (isPalindrome(str.substring(j + 1, i + 1)))
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                else
                    dp[i] = Math.min(dp[i], dp[j] + i - j);
            }
        }
        return dp[len - 1];
    }

    //取所有有效分割，使得分割后所有字符串都是回文
    public static ArrayList<ArrayList<String>> getAllPartition(String str) {
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<String> palindromeList = new ArrayList<String>();
        dfsGetAllPartition(str, 0, palindromeList, res);
        return res;
    }

    public static void dfsGetAllPartition(String str, int index, ArrayList<String> pList, ArrayList<ArrayList<String>> res) {
        if (index == str.length()) {
            ArrayList<String> mList = new ArrayList<String>();
            mList.addAll(pList);
            res.add(mList);
            return;
        }
        for (int i = index + 1; i <= str.length(); i++) {
            if (isPalindrome(str.substring(index, i))) {
                pList.add(str.substring(index, i));
                dfsGetAllPartition(str, i, pList, res);
                pList.remove(pList.size() - 1);
            }
        }
    }


}
