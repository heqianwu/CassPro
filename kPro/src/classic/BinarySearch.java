package classic;

public class BinarySearch {
    public static int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    // 4 5 7 8 -2 1 3 旋转排序的二分查找
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            //还是基于整个数组做对比，只缩减low和high和定位mid，而不是递归为low和high范围的数组
            if (nums[0] <= nums[mid]) {
                if (target > nums[mid]) {
                    low = mid + 1;
                } else if (target < nums[0]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            } else {
                if (target > nums[mid] && target < nums[0]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }


    //输入: nums = [5,7,7,8,8,10], target = 6
    //输出: [-1,-1]      target=8,输出[3,4]
    private int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    public int[] searchRange(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false) - 1;

        return targetRange;
    }

    //查找位置，不存在时返回插入的位置。不存在重复元素
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return 0;
        int low=0;
        int high=nums.length-1;
        while(low<=high){
            int mid=(low+high) >> 1;
            if(nums[mid]==target)
                return mid;
            if(nums[mid]>target)
                high=mid-1;
            else low=mid+1;
        }
        return low;
    }

}
