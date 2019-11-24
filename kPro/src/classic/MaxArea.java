package classic;

import java.util.Stack;

public class MaxArea {


    //两边和x轴构成的container最大
    //height[i]之间宽为1
    //初始low=0，high=height.length-1，不断淘汰短的一边(以该边为边的最大container为当前area)，宽度不断减小
    //height[low]>height[high]时，比当前大的area，双边肯定在 low~high-1 之间选择
    //O(n)
    public static int maxArea(int[] height) {
        int low = 0, high = height.length - 1;
        int area = 0, max = 0;
        while (low < high) {
            area = Math.max(area, (high - low) * Math.min(height[low], height[high]));
            if (height[low] > height[high])
                high--;
            else if (height[low] < height[high])
                low++;
            else {
                low++;
                high--;
            }
        }
        return area;
    }


    //直方图最大矩形，O(n)
    //https://www.jianshu.com/p/b871972747c0
    public static int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0)
            return 0;
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                int cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                res = Math.max(res, (i - left - 1) * heights[cur]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            res = Math.max(res, (heights.length - left - 1) * heights[cur]);
        }
        return res;
    }


    //Given a 2D binary matrix filled with 0's and 1's,
    // find the largest rectangle containing all ones and return its area.
    //O(n*n)
    public static int maximalRectangle(char[][] matrix) {

        if (matrix == null || matrix[0].length == 0) {
            return 0;
        }

        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                if (i == 0) {
                    dp[i][j] = matrix[i][j] == '1' ? 1 : 0;
                } else {
                    dp[i][j] = matrix[i][j] == '0' ? 0 : dp[i - 1][j] + 1;
                }
        }

        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            int temp = largestRectangleArea(dp[i]);
            max = Math.max(max, temp);
        }
        return max;

    }


    //给出n个数字，表示一个高程图，高程图中每一条的宽度为1，请计算下雨之后这个地形可以存储多少水
    //例如，给出[0,1,0,2,1,0,1,3,2,1,2,1],返回6
    public int trap(int[] A) {
        /*计算最高的位置，那么它两边的水就取决于左边高度和右边高度*/
        int maxIndex = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > A[maxIndex]) {
                maxIndex = i;
            }
        }
        int left = 0, right = 0;
        int sum = 0;
        for (int i = 0; i < maxIndex; i++) {
            if (A[i] < left) {
                sum += left - A[i];
            } else {
                left = A[i];
            }
        }
        for (int i = A.length - 1; i > maxIndex; i--) {
            if (A[i] < right) {
                sum += right - A[i];
            } else {
                right = A[i];
            }
        }
        return sum;
    }

}
