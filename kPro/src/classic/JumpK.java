package classic;

public class JumpK {


    //一个非负整数数组，你最初在数组第一个元素的位置，数组中的元素代表你在这个位置可以跳跃的最大长度，判断你是否能到达数组最后一个元素的位置
    //A =[2,3,1,1,4], 返回 true
    //A =[3,2,1,0,4], 返回 false
    public boolean canJump(int[] A) {
        if (A == null || A.length <= 1)
            return true;
        int len = A.length;
        int maxReach = 0;
        for (int i = 0; i < len - 1 && i <= maxReach; i++) {
            maxReach = Math.max(maxReach, i + A[i]);
        }
        if (maxReach < len - 1)
            return false;
        return true;
    }

    //最少跳跃次数到达最后一个位置
    public int jump(int[] A) {
        int jumps = 0, curEnd = 0, curFarthest = 0;
        for (int i = 0; i < A.length - 1; i++) {
            curFarthest = Math.max(curFarthest, i + A[i]);
            if (i == curEnd) {
                jumps++;
                curEnd = curFarthest;
            }
        }
        return jumps;
    }

}
