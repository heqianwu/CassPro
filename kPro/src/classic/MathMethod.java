package classic;

public class MathMethod {


    public static void main(String[] args) {
        System.out.println(new MathMethod().multiply("123", "123"));
    }

    //两个字符串表示的数相乘
    public String multiply(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();
        StringBuilder sb = new StringBuilder();
        int[] tmp = new int[n1 + n2];

        for (int i = n1 - 1; i >= 0; i--) {
            for (int j = n2 - 1; j >= 0; j--) {
                tmp[i + j + 1] += (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
            }
        }
        int carrybit = 0;//从个位开始，carrybit是进位
        for (int i = tmp.length - 1; i >= 0; i--) {
            tmp[i] += carrybit;
            carrybit = tmp[i] / 10;
            tmp[i] = tmp[i] % 10;
        }
        int left = 0;
        while (left < tmp.length - 1 && tmp[left] == 0)
            left++;
        for (; left < tmp.length; left++) {
            sb.append(tmp[left]);
        }
        return sb.toString();
    }

    //字符串转换为int类型
    public int atoi(String str) {
        if (str == null || str.length() == 0) return 0;
        int pos = 0;
        while (str.charAt(pos) == ' ') pos++;
        int flag = 1;
        if (str.charAt(pos) == '+') pos++;
        if (str.charAt(pos) == '-') {
            flag = -1;
            pos++;
        }
        int res = 0;
        for (int i = pos; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') break;
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && c > '7')) return Integer.MAX_VALUE;
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && c > '8')) return Integer.MIN_VALUE;
            res = res * 10 + (c - '0') * flag;
        }
        return res;
    }

}
