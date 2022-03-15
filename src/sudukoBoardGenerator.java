import java.util.*;

public class sudukoBoardGenerator {
    public sudukoBoardGenerator(){}

    private static final int row = 9;
    private static final int col = 9;
    private static final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
    public static int[][]mainBoard = new int[9][9];
    public static int[][]tempBoard = new int[9][9];
    public static boolean[][]unavoidableSetsArray = new boolean[9][9];
    public static int count = 0;

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

    public static void createSuduko(int[][]board){
        int rounds = 120;
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

    /*public static void copyArraySolvedBoard(int[][] board){
        for (int i = 0; i < board.length; i++){
            System.arraycopy(board[i], 0, mainBoard[i], 0, board.length);
        }
    }*/
    /*public static void copyArrayTempBoard(int[][] board){
        for (int i = 0; i < board.length; i++){
            System.arraycopy(board[i], 0, tempBoard[i], 0, board.length);
        }
    }*/
    /*private static void unavoidableSetX(int col){
        int localrow = 0;
        int localcol = col - col % 3;

        for (int i = localrow; i < localrow + 3; i++){
            for (int j = localcol; j < localcol + 2; j++){
                int pair1 = mainBoard[i][j];
                int pair2 = mainBoard[i][j+1];
                for (int k = 0; k < mainBoard.length; k++){
                    if (mainBoard[k][j] == pair2 && mainBoard[k][j+1] == pair1){
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
                int pair1 = mainBoard[j][i];
                int pair2 = mainBoard[j+1][i];
                for (int k = 0; k < mainBoard.length; k++) {
                    if (mainBoard[j][k] == pair2 && mainBoard[j+1][k] == pair1) {
                        unavoidableSetsArray[j][k] = true;
                        unavoidableSetsArray[j+1][k] = true;
                    }

                }
            }
        }
    }*/
    /*public static void unavoidableSet_checker() {
        for (int i = 2; i < 8; i+=2){
            unavoidableSetX(i);
            unavoidableSetY(i);
        }
    }*/

}