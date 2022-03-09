import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class sudukoBoardGenerator {
    public sudukoBoardGenerator(){}

    private static final int row = 9;
    private static final int col = 9;
    private static final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

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
    private static void readyBoard(int[][]board){
        int count = 0;
        Random rand = new Random();
        int index1 = rand.nextInt(0,9);
        int index2 = rand.nextInt(0,9);
        while (count != 60){
            while (board[index1][index2] != 0){
                board[index1][index2] = 0;
                count++;
            }
            index1 = rand.nextInt(0,9);
            index2 = rand.nextInt(0,9);
        }
    }
    public static void getGenerateBoard(int[][] board){
        generateSolvedGrid(board);
    }
    public static void getReadyBoard(int[][]board){
        readyBoard(board);
    }

}
