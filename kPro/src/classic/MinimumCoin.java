package classic;

public class MinimumCoin {
    public static void main(String[] args){
        int[] coins=new int[]{1,3,5};
        System.out.println(minimunCoin(coins,189));
        System.out.println(minimunCoin(coins,187));
    }
    private static int minimunCoin(int[] coins,int count){
        int[] dp=new int[count+1];
        for(int i=0;i<=count;i++){
            dp[i]=Integer.MAX_VALUE;
        }
        dp[0]=0;
        for(int i=1;i<=count;i++){
            for(int j=0;j<coins.length;j++){
                if(i>=coins[j]&&dp[i-coins[j]]!=Integer.MAX_VALUE){
                    dp[i]=Math.min(dp[i],dp[i-coins[j]]+1);
                }
            }
        }
        if(dp[count]==Integer.MAX_VALUE)
            return -1;
        return dp[count];
    }
}
