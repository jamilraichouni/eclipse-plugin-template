import org.eclipse.ui.IStartup;

import java.time.LocalTime;

import java.util.Timer;
import java.util.TimerTask;

public class Main implements IStartup {
    @Override
    public void earlyStartup() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                LocalTime currentTime = LocalTime.now();
                System.out.println("\n ---- " + currentTime + " Hello world! \n");
            }
        };
        timer.scheduleAtFixedRate(task, 0, 500);
    }
}
