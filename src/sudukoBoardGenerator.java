import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class sudukoBoardGenerator {
    public sudukoBoardGenerator(){}

    private static final int row = 9;
    private static final int col = 9;
    private static final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    public static int[][]solvedBoard = new int[9][9];
    public static boolean[][]unavoidableSetsArray = new boolean[9][9];

    private static void randomize(){
        Collections.shuffle(sudukoBoardGenerator.values);
    }
    private static boolean generateSolvedGrid(int[][]board){
        randomize();
        Arrays.stream(unavoidableSetsArray).forEach(i -> Arrays.fill(i,false));
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
    private static void readyBoard(int[][]board){
        int count = 0;
        Random rand = new Random();
        int rowI = rand.nextInt(0,9);
        int colI= rand.nextInt(0,9);
        while (count != 50){
            while (board[rowI][colI] != 0){

                board[rowI][colI] = 0;
                count++;
            }
            rowI = rand.nextInt(0,9);
            colI = rand.nextInt(0,9);
        }
    }
    public static void getGenerateBoard(int[][] board){
        generateSolvedGrid(board);
    }
    public static void getReadyBoard(int[][]board){
        readyBoard(board);
    }
    public static void copyArray(int[][] board){
        for (int i = 0; i < board.length; i++){
            System.arraycopy(board[i], 0, solvedBoard[i], 0, board.length);
        }
    }

    private static void unavoidableSetX(int col){
        int localrow = 0;
        int localcol = col - col % 3;

        for (int i = localrow; i < localrow + 3; i++){
            for (int j = localcol; j < localcol + 2; j++){
                int pair1 = solvedBoard[i][j];
                int pair2 = solvedBoard[i][j+1];
                for (int k = 0; k < solvedBoard.length; k++){
                    if (solvedBoard[k][j] == pair2 && solvedBoard[k][j+1] == pair1){
                        unavoidableSetsArray[k][j] = true;
                        unavoidableSetsArray[k][j+1] = true;
                    }
                }
            }
        }
    }
    private static void unavoidableSetY(int row){
        int localrow = row - row % 3;
        int localcol = 0;

        for (int i = localcol; i < localcol + 3; i++){
            for (int j = localrow; j < localrow + 2; j++){
                int pair1 = solvedBoard[j][i];
                int pair2 = solvedBoard[j+1][i];
                for (int k = 0; k < solvedBoard.length; k++) {
                    if (solvedBoard[j][k] == pair2 && solvedBoard[j+1][k] == pair1) {
                        unavoidableSetsArray[j][k] = true;
                        unavoidableSetsArray[j+1][k] = true;
                    }

                }
            }
        }
    }
    public static void unavoidableSet_checker() {
        for (int i = 2; i < 8; i+=2){
            unavoidableSetX(i);
            unavoidableSetY(i);
        }
    }

}