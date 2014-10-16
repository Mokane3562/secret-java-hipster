

public class TestThread {

	public static void main(String[] args) {		
		Thread hw1 = new Thread(new Runnable() {
			public void run() {
				String msg = "Hello";
				int count = 30;
				
				try {
					for (int i = count; i > 0; i--) {
						System.out.printf("%s %d\n", msg, i);
						Thread.sleep(0);
						Thread.yield();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread hw2 = new Thread(new Runnable() {
			public void run() {
				String msg = "World";
				int count = 30;
				
				try {
					for (int i = count; i > 0; i--) {
						System.out.printf("%s %d\n", msg, i);
						Thread.sleep(0);
						Thread.yield();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		hw1.start();
		hw2.start();
	}

}
