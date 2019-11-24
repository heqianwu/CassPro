package classic;

public class HeapSort {

    public static void main(String[] args) {
        int[] nums = {17, 8, 100, 120, 122, 6, 3, 9};
        heapSort(nums);
        for (int num : nums) {
            System.out.print(" " + num);
        }
    }

    public static void heapSort(int[] nums) {
        if (nums == null || nums.length <= 1)
            return;
        int len = nums.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            adjustHeap(len, nums, i);
        }
        for (int i = len - 1; i > 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            adjustHeap(i, nums, 0);
        }
    }

    //这里就是siftdown的实现，可以补充下siftup。siftdown和siftup实际上都是树高复杂度，只需父子节点对比互换
    private static void adjustHeap(int size, int[] nums, int index) {
        if (index * 2 + 1 > size - 1)
            return;
        int k = index * 2 + 1;
        if (index * 2 + 2 < size && nums[index * 2 + 2] > nums[index * 2 + 1]) {
            k++;
        }
        if (nums[index] < nums[k]) {
            int temp = nums[index];
            nums[index] = nums[k];
            nums[k] = temp;
            adjustHeap(size, nums, k);
        }
    }

    //这里就是siftdown的实现，可以补充下siftup。siftdown和siftup实际上都是树高复杂度，只需父子节点对比互换
    //可以用sinkAdjust直接替换adjustHeap
    //sinkAdjust方法和adjustHeap方法类似，只不过去除了adjustHeap里的递归，直接进行下沉
    private static void sinkAdjust(int len, int[] nums, int i) {
        int k = i, temp = nums[i], index = 2 * k + 1;
        while (index < len) {
            if (index + 1 < len) {
                if (nums[index] < nums[index + 1]) {
                    index = index + 1;
                }
            }
            if (nums[index] > temp) {
                nums[k] = nums[index];
                k = index;
                index = 2 * k + 1;
            } else {
                break;
            }
        }
        nums[k] = temp;
    }

}
