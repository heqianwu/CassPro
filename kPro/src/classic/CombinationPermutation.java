package classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationPermutation {


    /**
     * The set[1,2,3,…,n]contains a total of n! unique permutations.
     * <p>
     * By listing and labeling all of the permutations in order,
     * We get the following sequence (ie, for n = 3):
     * <p>
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * <p>
     * Given n and k, return the k th permutation sequence.  n between 1 and 9
     */

    //排列组合的第k个
    //n<9， 1~n个数字 构成的n位数中，从小到大的第k个
    public String getPermutation(int n, int k) {
        int[] jc = new int[n]; //阶乘
        List<Integer> list = new ArrayList<>();
        jc[0] = 1;
        for (int i = 1; i < n; i++) {
            list.add(i);
            jc[i] = jc[i - 1] * i;
        }
        list.add(n);
        StringBuilder sb = new StringBuilder();
        int arg1;
        k = k - 1;
        for (int i = 0; i < n; i++) {
            arg1 = k / jc[n - 1 - i];
            k = k - arg1 * jc[n - 1 - i];
            sb.append(list.get(arg1));
            list.remove(arg1);
        }
        return sb.toString();
    }

    //数组中没有重复元素，求数组的全排列
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (num == null || num.length == 0) return res;
        permuteDfs(res, new ArrayList<>(), num);
        return res;
    }

    private void permuteDfs(ArrayList<ArrayList<Integer>> res, ArrayList<Integer> aList, int[] num) {
        if (aList.size() == num.length) {
            res.add(new ArrayList<>(aList));
            return;
        }
        for (int i = 0; i < num.length; i++) {
            if (!aList.contains(num[i])) {
                aList.add(num[i]);
                permuteDfs(res, aList, num);
                aList.remove(aList.size() - 1);
            }
        }
    }


    //给出一组可能包含重复项的数字，返回该组数字的所有排列
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        Arrays.sort(nums);//进行排序之后，重复的元素只能从一个方向进行拿，所以可以保证不重复
        permuteUniqueDfs(list, nums, new boolean[nums.length], res);
        return res;
    }

    private void permuteUniqueDfs(ArrayList<Integer> list, int[] nums, boolean[] used, ArrayList<ArrayList<Integer>> res) {
        if (list.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i] == true) continue;
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
                continue;//只能按一个顺序进行访问连续相同的元素(相同元素只选一个排列)，才能保证唯一
            used[i] = true;
            list.add(nums[i]);
            permuteUniqueDfs(list, nums, used, res);
            list.remove(list.size() - 1);
            used[i] = false;

        }
    }


    //下一个排列
    //实现函数next permutation（下一个排列）：将排列中的数字重新排列成字典序中的下一个更大的排列
    //如果不存在这样的排列，则将其排列为字典序最小的排列，空间复杂度O(1)
    //1,2,3→1,3,2   3,2,1→1,2,3    1,1,5→1,5,1
    //思路：  从后往前先找到不满足递增排序的点，swap这个点与后面大于此的数，然后对后面升序排列
    public void nextPermutation(int[] num) {
        if (num == null || num.length == 0)
            return;
        int i = num.length - 2;
        while (i >= 0 && num[i] >= num[i + 1])
            i--;
        if (i >= 0) {
            int j = i + 1;
            while (j < num.length && num[j] > num[i])
                j++;
            j--;
            swap(num, i, j);
        }
        reverse(num, i + 1, num.length - 1);
    }

    private void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    private void reverse(int[] num, int i, int j) {
        while (i < j) {
            swap(num, i++, j--);
        }
    }


}
