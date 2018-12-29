package sm.concurrency.thread.create;

public class AnotherThread extends Thread {

	@Override
	public void run() {
		System.out.println("Hello from " + currentThread().getName());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Other thread woke me up !! I was interrupted");
			// e.printStackTrace(); // java.lang.InterruptedException: sleep interrupted
			return;
		}
		
		System.out.println("2 seconds have passed. I'm awake !! " + currentThread().getName());
	}

}
