public class Solutionk {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                visited[i][j] = false;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, visited, i, j, m, n, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(char[][] board, String word, boolean[][] visited, int i, int j, int m, int n, int len) {
        if(i>=m||i<0||j>=n||j<0){
            return false;
        }
        if (visited[i][j]||board[i][j] != word.charAt(len)) {
            return false;
        }
        if (len+1 == word.length()) {
            return true;
        }
        visited[i][j] = true;
        if (dfs(board, word, visited, i+1, j, m, n, len + 1)
                || dfs(board, word, visited, i-1, j, m, n, len + 1)
                || dfs(board, word, visited, i, j+1, m, n, len + 1)
                || dfs(board, word, visited, i, j-1, m, n, len + 1)) {
            return true;
        }
        visited[i][j] = false;
        return false;
    }


    public static void main(String[] args){
        int[] map = new int[128];
        System.out.println(map[0]);
    }

}
