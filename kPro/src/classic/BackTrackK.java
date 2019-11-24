package classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTrackK {
    public List<List<Integer>> combinationSum1(int[] num, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(num);
        dfs1(num, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs1(int[] num, int target, int start, ArrayList<Integer> list, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = start; i < num.length; i++) {
            if (target < num[i]) return;
            list.add(num[i]);
            dfs1(num, target - num[i], i, list, res);
            list.remove(list.size() - 1);
        }
    }


    //给出一组正整数候选数C和一个目标数T，找出候选数中起来和等于T的所有组合。结果中不能包含重复的组合
    //例如：给定的候选数集是[10,1,2,7,6,1,5]，目标数是8。 解集是： [1, 7]  [1, 2, 5]  [2, 6]  [1, 1, 6]
    public List<List<Integer>> combinationSum2(int[] num, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(num);
        dfs2(res, new ArrayList<>(), num, target, 0);
        return res;
    }

    private void dfs2(List<List<Integer>> res, ArrayList<Integer> list, int[] num, int target, int index) {
        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }
        if (target < 0)
            return;
        for (int i = index; i < num.length; i++) {
            //相同值中只能选前n个，不能不选第一个而直接选第2、3…个
            if (i > index && num[i] == num[i - 1]) {
                continue;
            }
            list.add(num[i]);
            dfs2(res, list, num, target - num[i], i + 1);
            list.remove(list.size() - 1);
        }
    }

}
