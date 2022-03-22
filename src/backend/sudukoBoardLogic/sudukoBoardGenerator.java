package backend.sudukoBoardLogic;

import java.util.*;

public class sudukoBoardGenerator {
    public sudukoBoardGenerator(){}

    private static final int row = 9;
    private static final int col = 9;
    private static final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    public static int[][]mainBoard = new int[9][9];
    public static int[][]tempBoard = new int[9][9];
    public static int count = 0;

    private static void randomize(){
        Collections.shuffle(sudukoBoardGenerator.values);
    }
    private static boolean generateSolvedGrid(int[][]board){
        randomize();
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (board[i][j] == 0){
                    for (int x = 0; x < 9; x++){
                        if (sudukoSolver.checker(board, values.get(x), i, j)){
                            board[i][j] = values.get(x);
                            if (generateSolvedGrid(board)){
                                return true;
                            }
                            else{
                                board[i][j] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void createSuduko(int[][]board){
        int rounds = 200;
        Random r = new Random();
        int x;
        int y;
        while (rounds != 0){
            x = r.nextInt(0,9);
            y = r.nextInt(0,9);

            int removedNum = board[x][y];
            board[x][y] = 0;
            System.arraycopy(board, 0,mainBoard, 0, board.length);
            count = 0;
            int counter = sudukoSolver.solver(mainBoard, count);
            if (counter == 1){
                board[x][y] = 0;
            }else{
                board[x][y] = removedNum;
            }
            rounds--;


        }
    }

    public static void getGenerateBoard(int[][] board){
        generateSolvedGrid(board);
    }

}