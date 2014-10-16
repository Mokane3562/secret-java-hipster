

import java.util.ArrayList;
import java.util.Random;

public class Matrix {
	public static void main(String[] args) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ArrayList<String> arr = new ArrayList<String>();
					Random rand = new Random();
					arr.add("0");
					arr.add("1");
					arr.add(" ");
					while(true) {
						System.out.print(arr.get(rand.nextInt(3)));
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
				}
			}
		});
		t.run();
	}
}
