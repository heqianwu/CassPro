package classic;

public class Others {

    //给定一个未排序的整数数组，找出其中没有出现的最小的正整数
    //输入: [3,4,-1,1]
    //输出: 2
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= len && nums[i] > 0 && nums[i] != i + 1) {
                temp = nums[nums[i] - 1];
                if (temp != nums[i]) {
                    nums[nums[i] - 1] = nums[i];
                    nums[i--] = temp;
                }
            }
        }
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != i + 1)
                return i + 1;
        return len + 1;
    }

}
