

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Count down to termination unless stopped.
 */
public class CountDown implements Runnable {

	/**
	 * The initial thread starts with main.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("java CountDown limit");
			System.exit(1);
		}
		int limit = Integer.parseInt(args[0]);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		CountDown cd = new CountDown(limit);
		Thread t = new Thread(cd);
		// start the second thread.
		t.start();
		String line = null;
		while((line = in.readLine()) != null) {
			if (line.equals("stop")) {
				cd.stop();
				System.out.println("stop requested");
				break;
			} else if (line.equals("quit")) {
				System.out.println("quit main");
				break;
			} else {
				System.out.println("what?");
			}
		}
	}
	private int count;

	private boolean stop;

	public CountDown(int count) {
		this.count = count;
		stop = false;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < count; i++) {
				System.out.println("count = " + i);
				if (stop)
					return;
				Thread.sleep(1000);
			}
			System.out.println("too late");
			System.exit(0);
		} catch (InterruptedException ex) {
		}
	}

	public void stop() {
		stop = true;
	}
}