public class Sudoku_Solver {


	public class Solution {

	    boolean found = false;

	    public void solveSudoku(char[][] b) {

	        if (b == null || b.length == 0)     return;

	        dfs(b, 0, 0);
	    }

	    public void dfs (char[][] b, int row, int col) {

	        if (row == b.length) {
	            found = true;
	            return;
	        }

	        for (int i = row; i < b.length; i++) {
	            for (int j = col; j < b[0].length; j++) {
	                if (b[i][j] != '.') {
	                    continue;
                    }

	                // try from 1 to 9
	                int num = 1;
	                while (num <= 9) {

	                    if (isValid(b, i, j, num)) {
	                        b[i][j] = (char)('0' + num); // @note@note: must add cast

	                        if (col < b[0].length - 1) {
	                            dfs(b, row, col + 1);

	                            if (found) {
	                                return;
                                }

	                        } else {
	                            // dfs(b, row + 1, col);
	                            dfs(b, row + 1, 0); // @note@note: index-0 of next row

	                            if (found) {
	                                return;
                                }
	                        }

	                        b[i][j] = '.'; // reset
	                    }

	                    num++;
	                }
	            }
	        }
	    }

	    public boolean isValid (char[][] b, int row, int col, int num) {

	        // search row
	        for (int i = 0; i < b[0].length; i++) {
	            if (b[row][i] == '0' + num)     return false;
	        }

	        // search column
	        for (int i = 0; i < b.length; i++) {
	            if (b[i][col] == '0' + num)     return false;
	        }

	        // search sub-square
	        int rowSub = row / 3;
	        int colSub = col / 3;
	        for (int i = rowSub; i < rowSub + 3; i++) {
	            for (int j = colSub; j < colSub + 3; j++) {
	                if (b[i][j] == '0' + num)     return false;
	            }
	        }

	        return true;
	    }
	}


	public class Solution_2 {

	    // +1 is for index simplicity
	    boolean[][] row = new boolean[9][9 + 1];
	    boolean[][] col = new boolean[9][9 + 1];
	    boolean[][] sub = new boolean[9][9 + 1];

	    // hard code number 9 is not good practise BTW
	    public void solveSudoku(char[][] board) {
	        if(board == null || board.length == 0) {
	            return;
	        }

	        solve(board, 0, 0);
	    }

	    public boolean solve(char[][] b, int i, int j) { // r: row, c: col

	        if(i == 8 && j == 9) { // reaching end of this matrix
	            return true;
	        }

	        if(b[i][j] != '.') {
	            char currentChar = b[i][j];
	            row[i][currentChar - '0'] = true;
	            col[j][currentChar - '0'] = true;
	            sub[i - i % 3 + j / 3][currentChar - '0'] = true;

	            if(i < 9) {
	                 solve(b, i + 1, j);
	            } else {
	                solve(b, i, j + 1);
	            }

	        } else {

	            for(int test = 1; test <= 9; test++) {
	                if(row[i][test] == false && col[j][test] == false && sub[i - i % 3 + j / 3][test] == false) {
	                    // set this test in marking map to be true
	                    row[i][test] = true;
	                    col[j][test] = true;
	                    sub[i - i % 3 + j / 3][test] = true;

	                    boolean isFound = false;
	                    if(i < 9) {
	                        isFound = solve(b, i + 1, j);
	                    } else {
	                        isFound = solve(b, i, j + 1);
	                    }

	                    if(isFound) {
	                        return true;
	                    }

	                    // reset test value
	                    row[i][test] = false;
	                    col[j][test] = false;
	                    sub[i - i % 3 + j / 3][test] = false;

	                }
	            }
	        }

	        return false;
	    }
	}
}

############

class Solution {
    private boolean ok;
    private char[][] board;
    private List<Integer> t = new ArrayList<>();
    private boolean[][] row = new boolean[9][9];
    private boolean[][] col = new boolean[9][9];
    private boolean[][][] block = new boolean[3][3][9];

    public void solveSudoku(char[][] board) {
        this.board = board;
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    t.add(i * 9 + j);
                } else {
                    int v = board[i][j] - '1';
                    row[i][v] = col[j][v] = block[i / 3][j / 3][v] = true;
                }
            }
        }
        dfs(0);
    }

    private void dfs(int k) {
        if (k == t.size()) {
            ok = true;
            return;
        }
        int i = t.get(k) / 9, j = t.get(k) % 9;
        for (int v = 0; v < 9; ++v) {
            if (!row[i][v] && !col[j][v] && !block[i / 3][j / 3][v]) {
                row[i][v] = col[j][v] = block[i / 3][j / 3][v] = true;
                board[i][j] = (char) (v + '1');
                dfs(k + 1);
                row[i][v] = col[j][v] = block[i / 3][j / 3][v] = false;
            }
            if (ok) {
                return;
            }
        }
    }
}