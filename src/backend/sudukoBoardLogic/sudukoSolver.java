package backend.sudukoBoardLogic;

public class sudukoSolver {
    public sudukoSolver() {}

    public static int counter1 = 0;

    public static boolean columnChecker(int[][] board, int numberToCheck, int column) {
            for (int i = 0; i < 9; i++) {
                if (board[i][column] == numberToCheck) {
                    return true;
                }
            }
            return false;
        }
    public static boolean rowChecker(int[][] board, int numberToCheck, int row) {
            for (int i = 0; i < 9; i++) {
                if (board[row][i] == numberToCheck) {
                    return true;
                }
            }
            return false;
        }
    public static boolean boxChecker(int[][] board, int numberToCheck, int row, int column) {
            int localRow = row - row % 3;
            int localCol = column - column % 3;

            for (int i = localRow; i < localRow + 3; i++) {
                for (int j = localCol; j < localCol + 3; j++) {
                    if (board[i][j] == numberToCheck) {
                        return true;
                    }
                }
            }
            return false;
        }
    public static boolean checker(int[][] board, int numberToCheck, int row, int column) {
            return !columnChecker(board, numberToCheck, column) &&
                    !rowChecker(board, numberToCheck, row) &&
                    !boxChecker(board, numberToCheck, row, column);
        }
    public static int solver(int[][] board, int count) {
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                if (board[i][j] == 0) {

                    for (int n = 1; n <= 9 && count < 2; n++) {

                        if (checker(board, n, i,j)) {

                            board[i][j] = n;
                            int cache = solver(board, count);
                            if (cache > count) {
                                count = cache;
                                for (int k = 0; k < board.length; k++) {
                                    for (int l = 0; l < board.length; l++) {
                                        if (board[k][l] != 0) {
                                            sudukoBoardGenerator.mainBoard[k][l] = board[k][l];
                                        }

                                    }
                                }
                            }
                            board[i][j] = 0;
                        }
                    }
                    return count;
                }
            }
        }
        return count + 1;
    }
}