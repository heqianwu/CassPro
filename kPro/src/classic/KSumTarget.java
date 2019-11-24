package classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class KSumTarget {

    public static void main(String[] args) {
        int[] nums = new int[]{-4, -8, 0, 0, 0, 0, 4, 8, 10, 8, 8};
        twoSumZero(nums);
    }

    //一个数组中找出所有两个数的组合，使得和为0。不能存在重复的组合
    //维持left，right两个游标，一次遍历，排序时间复杂度+遍历时间复杂度O(n)
    //也可以用HashMap、HashSet来实现（直接用ArrayList.equals去重），时间复杂度O(n)
    public static ArrayList<ArrayList<Integer>> twoSumZero(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 2)
            return res;
        Arrays.sort(nums);
        int len = nums.length;
        int left = 0, right = len - 1;
        while (left < right) {
            if (nums[left] + nums[right] == 0) {
                System.out.println(nums[left] + "  " + nums[right]);
                Integer[] klist = new Integer[]{nums[left], nums[right]};
                res.add(new ArrayList<>(Arrays.asList(klist)));
                left++;
                right--;
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }

            } else {
                if (nums[left] + nums[right] > 0) right--;
                if (nums[left] + nums[right] < 0) left++;
            }

        }
        return res;
    }

    //可用回溯法，得到O(n*n*n)的实现，不过要注意处理相同的数
    //相当于n*twoSumZero，n*O(n)
    //时间复杂度O(n*n)，确定第一个数后，通过两个下标来实现一次遍历。
    public static ArrayList<ArrayList<Integer>> threeSumZero(int[] nums) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3)
            return res;
        Arrays.sort(nums);
        int left, right;

        for (int i = 0; i < nums.length - 2; i++) {
            left = i + 1;
            right = nums.length - 1;

            if (i != 0 && nums[i] == nums[i - 1]) continue;

            while (left < right) {
                if (nums[i] + nums[left] + nums[right] == 0) {
                    Integer[] klist = new Integer[]{nums[i], nums[left], nums[right]};
                    res.add(new ArrayList<>(Arrays.asList(klist)));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                } else {
                    if (nums[i] + nums[left] + nums[right] > 0) right--;
                    if (nums[i] + nums[left] + nums[right] < 0) left++;
                }
            }

        }

        return res;
    }


    //一个数组中找出三个数的组合，使得sum最接近target。返回sum
    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int length = nums.length;
        int res = nums[0] + nums[1] + nums[2];
        for (int i = 0; i < length - 2; i++) {
            int left = i + 1;
            int right = length - 1;
            while (left < right) {
                int curSum = nums[i] + nums[left] + nums[right];
                if (target == curSum) {
                    return curSum;
                } else if (curSum < target) {
                    ++left;
                } else {
                    --right;
                }
                if (Math.abs(target - curSum) < Math.abs(target - res)) {
                    res = curSum;
                }
            }
        }
        return res;

    }


    //一个数组中找出所有四个数的组合，使得和为target。不能存在重复的组合
    //这里可以看到ArrayList里equals的实现为对比里面的元素，看是否相等，而不是直接对比引用指针
    //与3sum思路类似，只不过这次需要先固定两个数，然后双指针找，直接用ArrayList.contains和ArrayList.equals进行去重
    public static ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        ArrayList<ArrayList<Integer>> all = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);
        for (int i = 0; i < num.length - 3; i++) {
            if (num[i] + num[i + 1] + num[i + 2] + num[i + 3] > target) {
                break;
            }
            if (num[i] + num[num.length - 1] + num[num.length - 2] + num[num.length - 3] < target) {
                continue;
            }
            int result = target - num[i];
            for (int j = i + 1; j < num.length - 2; j++) {
                if (num[i] + num[j] + num[j + 1] + num[j + 2] > target) {
                    break;
                }
                if (num[i] + num[num.length - 1] + num[num.length - 2] + num[j] < target) {
                    continue;
                }
                int result2 = result - num[j];
                int left = j + 1, right = num.length - 1;
                while (left < right) {
                    int sum = num[left] + num[right];
                    if (sum < result2) {
                        left++;
                    } else if (sum > result2) {
                        right--;
                    } else {
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        list.add(num[i]);
                        list.add(num[j]);
                        list.add(num[left]);
                        list.add(num[right]);
                        if (!all.contains(list)) {
                            all.add(list);
                        }
                        left++;
                        right--;
                    }
                }
            }
        }
        return all;
    }


    /**
     * Given an array of integers, find two numbers such that they add up to a specific target number.
     * <p>
     * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
     * <p>
     * You may assume that each input would have exactly one solution.
     * <p>
     * Input: numbers={2, 7, 11, 15}, target=9
     * Output: index1=1, index2=2
     * <p>
     * 数组中两数之和为target，但是要输出两数在原数组中的下标
     */
    public static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> hmap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (hmap.containsKey(numbers[i])) {
                return new int[]{hmap.get(numbers[i]) + 1, i + 1};
            }
            hmap.put(target - numbers[i], i);
        }
        return null;
    }


    public List<List<Integer>> fourSumBetter(int[] nums, int target) {

        List<List<Integer>> result = new ArrayList<>();
        //先排序
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            // 去除指针i可能的重复情况
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                // 去除j可能重复的情况
                if (j != i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int left = j + 1;
                int right = nums.length - 1;

                while (left < right) {
                    // 不满足条件或者重复的，继续遍历
                    if ((left != j + 1 && nums[left] == nums[left - 1]) || nums[i] + nums[j] + nums[left] + nums[right] < target) {
                        left++;
                    } else if ((right != nums.length - 1 && nums[right] == nums[right + 1]) || nums[i] + nums[j] + nums[left] + nums[right] > target) {
                        right--;
                    } else {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[i]);
                        list.add(nums[j]);
                        list.add(nums[left]);
                        list.add(nums[right]);

                        result.add(list);
                        // 满足条件的，进入下一次遍历
                        left++;
                        right--;
                    }
                }

            }
        }

        return result;
    }


}
