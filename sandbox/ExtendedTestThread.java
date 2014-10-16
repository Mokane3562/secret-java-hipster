

public class ExtendedTestThread extends Thread {
	public static void main(String[] args) {
		Thread hello = new ExtendedTestThread("Hello");
		Thread world = new ExtendedTestThread("World");
		hello.start();
		world.start();
	}

	private String msg;

	public ExtendedTestThread(String msg) {
		this.msg = msg;
	}

	@Override
	public void run() {
		while(true) {
			System.out.println(msg);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
