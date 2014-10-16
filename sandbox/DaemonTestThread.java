

import java.util.Timer;
import java.util.TimerTask;

public class DaemonTestThread {

	public static void main(String[] args) {
		TimerTask task = new TimerTask() {
			public void run(){
				System.out.println("zombie thread!");
				return;
			}
		};
		Timer timer = new Timer(true);
		timer.schedule(task, 10000);		
	}

}
