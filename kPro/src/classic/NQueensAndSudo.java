package classic;

import java.util.ArrayList;

public class NQueensAndSudo {

    public static void main(String[] args) {
        NQueensAndSudo queue = new NQueensAndSudo();
        System.out.println(queue.solveNQueens(4).size());
    }

    //在一个n*n的棋盘上放置皇后，要求：一个皇后的同一行、同一列、同一条对角线上不允许出现其他皇后。请给出所有的放置方案
    public ArrayList<String[]> solveNQueens(int n) {
        ArrayList<String[]> res = new ArrayList<>();
        dfs(n, 0, new int[n], res);
        return res;
    }

    private void dfs(int n, int row, int[] list, ArrayList<String[]> res) {
        if (row == n) {
            String[] item = new String[n];
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    if (list[i] == j)
                        sb.append("Q");
                    else
                        sb.append(".");
                }
                item[i] = sb.toString();
            }
            res.add(item);
            return;
        }
        for (int i = 0; i < n; i++) {
            list[row] = i;
            if (check(row, list)) {
                dfs(n, row + 1, list, res);
            }
        }
    }

    private boolean check(int row, int[] list) {
        for (int i = 0; i < row; i++) {
            if (list[i] == list[row] || Math.abs(list[row] - list[i]) == row - i) {
                return false;
            }
        }
        return true;
    }

    //n皇后的总解法
    public int totalNQueens(int n) {
        boolean[] col = new boolean[n];
        boolean[] bias1 = new boolean[2 * n - 1];
        boolean[] bias2 = new boolean[2 * n - 1];
        int[] res = new int[1];
        processQueue(n, 0, res, col, bias1, bias2);
        return res[0];
    }

    private void processQueue(int n, int row, int[] res, boolean[] col, boolean[] bias1, boolean[] bias2) {
        if (row == n) {
            res[0]++;
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!col[i] && !bias1[row + i] && !bias2[row - i + n - 1]) {
                col[i] = true;
                bias1[row + i] = true;
                bias2[row - i + n - 1] = true;
                processQueue(n, row + 1, res, col, bias1, bias2);
                col[i] = false;
                bias1[row + i] = false;
                bias2[row - i + n - 1] = false;
            }
        }
    }


    //请编写一个程序，给数独中的剩余的空格填写上数字
    //9*9,1-9数字。 行列1-9，3*3方格1-9
    //数独盘面可以被部分填写，空的位置用字符'.'.表示
    public void solveSudoku(char[][] board) {
        boolean[][] rowNums = new boolean[9][9];
        boolean[][] colNums = new boolean[9][9];
        boolean[][] nineNums = new boolean[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    rowNums[i][num] = true;
                    colNums[j][num] = true;
                    nineNums[i / 3 * 3 + j / 3][num] = true;
                }
            }
        }

        find(board, rowNums, colNums, nineNums, 0);
    }

    boolean find(char[][] board, boolean[][] rowNums, boolean[][] colNums, boolean[][] nineNums, int position) {
        if (position > 80) {
            return true;
        } else {
            int row = position / 9;
            int col = position % 9;
            int nine = row / 3 * 3 + col / 3;
            if (board[row][col] != '.') {
                return find(board, rowNums, colNums, nineNums, position + 1);
            } else {
                for (int i = 0; i < 9; i++) {
                    if (!rowNums[row][i] && !colNums[col][i] && !nineNums[nine][i]) {
                        char ch = (char) ('1' + i);
                        board[row][col] = ch;
                        rowNums[row][i] = true;
                        colNums[col][i] = true;
                        nineNums[nine][i] = true;
                        if (find(board, rowNums, colNums, nineNums, position + 1)) return true;
                        board[row][col] = '.';
                        rowNums[row][i] = false;
                        colNums[col][i] = false;
                        nineNums[nine][i] = false;
                    }
                }
            }
        }
        return false;
    }


    //判断给出的局面是不是一个符合规则的数独局面
    //数独盘面可以被部分填写，空的位置用字符'.'.表示
    public boolean isValidSudoku(char[][] board) {
        boolean[][] rowVisited = new boolean[9][9];
        boolean[][] colVisited = new boolean[9][9];
        boolean[][] blockVisited = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }
                int num = c - '0' - 1;
                if (rowVisited[i][num] || colVisited[j][num] || blockVisited[i / 3 * 3 + j / 3][num]) {
                    return false;
                }
                rowVisited[i][num] = true;
                colVisited[j][num] = true;
                blockVisited[i / 3 * 3 + j / 3][num] = true;
            }
        }
        return true;
    }


}
