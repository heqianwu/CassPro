package classic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//https://blog.csdn.net/qq_19446965/article/details/81349807#commentsedit
//https://blog.csdn.net/qq_19446965/article/details/81775702
public class BackPack {

    public static void main(String[] args) {
        BackPack bp = new BackPack();
    }

    //第一次层for始终从考不考虑物品i开始
    private int zeroOnePack(int W, int N, int[] w, int[] v) {
        //dp[i]表示重量不超过i的最大价值
        int[] dp = new int[W + 1];
        for (int i = 0; i < N; i++) {
            for (int j = W; j >= w[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[W];
    }

    //完全背包，0到无穷个
    //0，1背包是从大到小遍历，完全背包是从小到大遍历
    private int fullPack(int W, int N, int[] w, int[] v) {
        //dp[i]表示重量不超过i的最大价值
        int[] dp = new int[W + 1];
        for (int i = 0; i < N; i++) {
            for (int j = w[i]; j <= W; j++) {
                dp[j] = Math.max(dp[j], dp[j - w[i]] + v[i]);
            }
        }
        return dp[W];
    }

    //多重背包，0到num[i]个
    private int multiPack(int W, int N, int[] w, int[] v, int[] num) {
        //dp[i]表示重量不超过i的最大价值
        int[] dp = new int[W + 1];
        for (int i = 0; i < N; i++) {
            for (int j = W; j >= w[i]; j--) {
                for (int k = 1; k <= num[i]; i++) {
                    if (j >= k * w[i])
                        dp[j] = Math.max(dp[j], dp[j - k * w[i]] + k * v[i]);
                }
            }
        }
        return dp[W];
    }

    //是否有和为n的组合
    private boolean hasFindSum(int[] a, int n) {
        boolean[] dp = new boolean[n + 1];
        dp[a[0]] = true;
        for (int i = 1; i < a.length; i++) {
            for (int j = n; j >= a[i]; j--) {
                if (dp[j - a[i]]) {
                    dp[j] = true;
                }
            }
            if (dp[n]) {
                return true;
            }
        }
        return false;
    }

    //有几种和为n的组合
    public static int findSumNum(int[] a, int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 0; i < a.length; i++) {
            for (int j = n; j >= a[i]; j--) {
                if (dp[j - a[i]] > 0) {
                    dp[j] += dp[j - a[i]];
                }
            }
        }
        return dp[n];
    }

    //dfs查找和为n的所有组合，dfs会有重复，相当于选或不选，所以其实是2^n的复杂度
    private void dfsfindSumPath(int n, int current, int sum, int[] questions, ArrayList<Integer> path, HashSet<List<Integer>> result) {
        if (sum == n) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (sum > n) {
            return;
        }
        for (int i = current; i < questions.length; i++) {
            path.add(questions[i]);
            dfsfindSumPath(n, i + 1, sum + questions[i], questions, path, result);
            path.remove(path.size() - 1);
        }
    }

    //动归法查找和为n的所有组合，n^2时间复杂度，还没细看
    private List<List<Object>> findSums(int[] a, int n) {
        boolean[] dp = new boolean[n + 1];
        List<List<Object>>[] list = new ArrayList[n + 1];
        for (int i = 0; i < list.length; i++) {
            list[i] = new ArrayList<>();
        }
        List<Object> temp = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i] > n) {
                continue;
            }
            for (int j = n; j >= a[i]; j--) {
                if (dp[j - a[i]]) {
                    dp[j] = true;
                    for (List<Object> arrayList : list[j - a[i]]) {
                        temp = new ArrayList<>(arrayList);
                        temp.add(a[i]);
                        list[j].add(new ArrayList<>(temp));
                    }
                }
            }
            dp[a[i]] = true;
            temp.clear();
            temp.add(a[i]);
            list[a[i]].add(new ArrayList<>(temp));
        }
        return list[n];
    }


}
