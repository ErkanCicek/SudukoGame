import java.util.Arrays;

public class sudukoSolver {
    public sudukoSolver() {}

    private final int col = 9;
    private final int row = 9;

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

    public static boolean solver(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] == 0) {
                    for (int clue = 1; clue <= 9; clue++) {
                        if (checker(board, clue, row, column)) {
                            board[row][column] = clue;

                            if (solver(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    } //not in use

    public static void printBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }
    public static void printBoard(boolean[][] board) {
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < 9; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("  |  ");
                }
                System.out.print(Arrays.toString(new boolean[]{board[row][column]}));
            }
            System.out.println();
        }
    }

}