package classic;

public class BitOperator {
    public static void main(String[] args) {

    }

    //题目： 两个整数相除，不能用*,/,%操作符
    //>> 带符号右移。正数右移高位补0，负数右移高位补1
    //>>> 无符号右移。无论是正数还是负数，高位通通补0
    //很多类似题目操作int类型时，都先转化为long型，避免溢出的处理
    public static int divide(int d1, int d2) {
        // 如果没有(long)d1, 对Integer.MIN_VALUE求绝对值会溢出,像(-128,127)
        long nd1 = Math.abs((long) d1);
        long nd2 = Math.abs((long) d2);
        if (nd2 == 0) {
            return 0;
        }
        int count = 0;
        while (nd1 >= nd2) {
            // 如果不是long,temp << 1,这一步有可能溢出
            long temp = nd2;
            int power = 1;
            while (nd1 >= temp << 1) {
                temp = temp << 1;
                power = power << 1;
            }
            nd1 -= temp;
            count += power;
        }
        //无符号右移31位,才能把符号位移到该数的最低位,再做异或操作
        return (d1 >>> 31 ^ d2 >>> 31) == 1 ? -count : count;
    }


}
