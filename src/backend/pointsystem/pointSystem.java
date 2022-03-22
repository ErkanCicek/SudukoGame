package backend.pointsystem;

import java.util.Scanner;

public class pointSystem implements Runnable{
    public int score;
    boolean isGameRunning;

    public pointSystem(int score, boolean isGameRunning) {
        this.score = score;
        this.isGameRunning = isGameRunning;
    }

    private void change(){
        if (this.score == 5){
            System.out.println();
        }else{
            this.score = this.score - 5;
        }
    }

    @Override
    public void run() {
        try {
            while (isGameRunning){
                if (Thread.interrupted()){
                    break;
                }else {
                    change();
                    Thread.sleep(15000); //ignore this warning?
                }
            }
        } catch (InterruptedException e) {
            System.out.println();
        }
    }

    public static void main(String[] args) {
        boolean gamerunning = true;
        int score = 45;
        pointSystem test = new pointSystem(score, gamerunning);
        Thread thread = new Thread(test);
        thread.start();
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        score = test.score;
        System.out.println(score);
        if (x == 1){
            thread.interrupt();
            test = new pointSystem(score, gamerunning);
            thread = new Thread(test);
            thread.start();
        }
    }
}
