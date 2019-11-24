import java.util.HashMap;
import java.util.HashSet;

public class Solution {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        System.out.println(maxClassValue(6, 6, new int[]{1, 3, 5, 2, 5, 4}, new int[]{1, 1, 0, 1, 0, 0}));


        System.out.println(new Solution().findMatch("tbcacbdata", new char[]{'a', 'b', 't', 'f'}, 4, 10));

    }

    public static int maxClassValue(int m, int n, int[] valueList, int[] stateList) {
        if (m == 0)
            return 0;

        int res = 0;
        for (int i = 0; i < m; i++) {
            if (stateList[i] == 1)
                res += valueList[i];
        }

        int maxRec = 0;
        int tempRec = 0;
        for (int i = 0; i < m; i++) {
            if (stateList[i] == 0) {
                tempRec += valueList[i];
            }
            if (i - n >= 0 && stateList[i - n] == 0) {
                tempRec -= valueList[i - n];
            }
            maxRec = Math.max(maxRec, tempRec);
        }
        return res + maxRec;
    }


    public int findMatch(String str, char[] cList, int m, int n) {
        if (str == null || str.length() == 0) {
            return -1;
        }
        if (cList == null || cList.length == 0) {
            return -1;
        }
        HashMap<Integer, Integer> hMap = new HashMap<>();
        HashSet<Integer> hSet = new HashSet<Integer>();
        for (char ch : cList) {
            hSet.add((int) ch);
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            int temp = (int) str.charAt(i);
            if (hSet.contains(temp)) {
                if (!hMap.containsKey(temp)) {
                    count++;
                    hMap.put(temp, i);
                } else {
                    hMap.put(temp, i);
                }
            }
            if (i >= m && hSet.contains((int) str.charAt(i - m)) && str.charAt(i - m) != str.charAt(i) && hMap.containsKey((int) str.charAt(i - m))) {
                if (hMap.get((int) str.charAt(i - m)) == (i - m)) {
                    count--;
                    hMap.remove((int) str.charAt(i - m));
                }
            }
            if (count == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

}