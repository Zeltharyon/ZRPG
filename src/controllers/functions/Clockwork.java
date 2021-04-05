package controllers.functions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class Clockwork {

    private Timer timer;

    public Clockwork(int seconds){
        timer = new Timer();
        timer.schedule(new Task(seconds), 0, 1000);
    }

    private static class Task extends TimerTask {

        private int duration;
        private final int stkduration;

        public Task(int seconds){
            this.duration = seconds;
            this.stkduration = duration;
        }

        @Override
        public void run() {
            if(duration > 0){
                duration--;
                System.out.println(duration);
            }
            else{
                System.out.println("Fin du chrono !" + stkduration + " secondes écoulées.");
                cancel();
            }
        }
    }
}
