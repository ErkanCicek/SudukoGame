package backend.pointsystem;

public class pointSystem implements Runnable{
    double multiplier;
    boolean isGameRunning;

    public pointSystem(double multiplier, boolean isGameRunning) {
        this.multiplier = multiplier;
        this.isGameRunning = isGameRunning;
    }

    private void change(){
        this.multiplier = this.multiplier - 0.5;
        System.out.println(this.multiplier);
    }

    @Override
    public void run() {
        try {
            while (isGameRunning){
                change();
                Thread.sleep(15000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        double multi = 4.5;
        boolean running = true;
        pointSystem test = new pointSystem(multi, running);
        Thread thread = new Thread(test);
        thread.start();
    }
}
