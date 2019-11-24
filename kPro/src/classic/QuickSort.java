package classic;

public class QuickSort {

    public static void main(String[] args){
        int[] nums = {17, 8, 100, 120, 122, 6, 3, 9};
        quickSort(nums);
        for (int num : nums) {
            System.out.print(" " + num);
        }
    }

    public static void quickSort(int[] nums){
        quickSort(nums,0,nums.length-1);
    }

    public static void quickSort(int[] nums,int start,int end){
        if(start>=end)
            return;
        int low=start;
        int high=end;
        int splitKey=nums[start];
        while(start<end){
            while(start<end&&nums[end]>=splitKey)
                end--;
            nums[start]=nums[end];
            while(start<end&&nums[start]<=splitKey)
                start++;
            nums[end]=nums[start];
        }
        nums[end]=splitKey;
        quickSort(nums,low,end-1);
        quickSort(nums,end+1,high);
    }

}
