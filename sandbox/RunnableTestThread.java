

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunnableTestThread implements Runnable {
	private static final byte ESC = 0x1b; // ESC
    private static final byte SQB = 0x5b; // [
    private static final byte ZERO = 0x30; // 0
    private static final byte TWO = 0x32; // 2
    private static final byte FOUR = 0x34; // 4
    private static final byte UJ = (byte)'J'; // J
    private static final byte UH = (byte)'H'; // H
    private static final byte LH = (byte)'h'; // h
    private static final byte LL = (byte)'l'; // l
    private static final byte SEMICOLON = (byte)';'; // ;

    private static final byte[] clear_msg = { ESC, SQB, TWO, UJ };
    private static final byte[] replace_mode_msg = { ESC, SQB, FOUR, LL };
    private static final byte[] insert_mode_msg = { ESC, SQB, FOUR, LH };
	
	private String msg;
	private int count;
	private boolean stop;
	

	public RunnableTestThread(String msg, int count) {
		this.msg = msg;
		this.count = count;
	}

	@Override
	public void run() {
		try {
			for (int i = count; i > 0; i--) {
				System.out.printf("%s %d\n", msg, i);
				if (stop) return;
				System.out.write(clear_msg);
				Thread.sleep(1000);
				Thread.yield();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void stop() {
		stop = true;
	}

	public static void main(String[] args) throws InterruptedException{
		final long start = System.nanoTime();
		
		RunnableTestThread hello = new RunnableTestThread("Hello", 10);
		RunnableTestThread world = new RunnableTestThread("World", 10);
		Thread hw1 = new Thread(hello);
		Thread hw2 = new Thread(world);
		hw1.start();
		hw2.start();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			while ((line = in.readLine()) != null && hw1.isAlive() && hw2.isAlive()){
				if (line.equals("stop")){
					hello.stop();
					world.stop();
				}	break;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.printf("Took %d nanoseconds\n", System.nanoTime() - start);
	}
}
