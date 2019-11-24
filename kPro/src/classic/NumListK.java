package classic;

public class NumListK {

    //给出一个无序的整数型数组，求不在给定数组里的最小的正整数
    //给出的数组为[3,4,-1,1] 返回2.
    public int firstMissingPositive(int[] A) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            while (A[i] > 0 && A[i] <= n && A[A[i] - 1] != A[i]) {
                swap(A, i, A[i] - 1);
            }
        }
        for (int i = 0; i < n; i++) {
            if (A[i] - 1 != i) {
                return i + 1;
            }
        }
        return n + 1;
    }

    /*每个数换到它应该的位置*/
    public void swap(int[] A, int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

}
