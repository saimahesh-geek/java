package sm.concurrency.thread.create;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		System.out.println("Hello from main thread");
		
		// thread subclass
		Thread anotherThread = new AnotherThread();
		anotherThread.start();
		anotherThread.setName("another thread");
		// anotherThread.start(); // java.lang.IllegalThreadStateException
		// anotherThread.run();   // this will run in main thread
		
		// anonymous
		new Thread() {
			@Override
			public void run() {
				System.out.println("Hello from anonymous class thread");
			}
		}.start();
		
		// implement runnable
		Thread myRunnableThread = new Thread(new MyRunnable());
		myRunnableThread.start();
		
		// anonymous
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello from anonymous class thread (runnable)");
				try {
					anotherThread.join();
					// anotherThread.join(3000); // timed out
					System.out.println("another class thread terminated, so I'll resume");
				} catch (InterruptedException e) {
					System.out.println("Couldn't wait, I was interrupted");
				}
				System.out.println("End of anonymous class thread (runnable)");
			}
		}).start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		anotherThread.interrupt();
		
		System.out.println("End of main thread");
	}

}
