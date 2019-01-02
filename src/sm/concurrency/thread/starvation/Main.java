package sm.concurrency.thread.starvation;

public class Main {

	private static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Worker(), "Priority-10");
		Thread t2 = new Thread(new Worker(), "Priority-8");
		Thread t3 = new Thread(new Worker(), "Priority-6");
		Thread t4 = new Thread(new Worker(), "Priority-4");
		Thread t5 = new Thread(new Worker(), "Priority-2");
		
		t1.setPriority(10);
		t2.setPriority(8);
		t3.setPriority(6);
		t4.setPriority(4);
		t5.setPriority(2);
		
		t2.start();
		t4.start();
		t3.start();
		t5.start();
		t1.start();
	}

	private static class Worker implements Runnable {
		private int runCount = 1;

		@Override
		public void run() {
			for (int i=0; i<100; i++) {
				synchronized (lock) {
					System.out.println(Thread.currentThread().getName() + " rowCount : " + runCount++);
					// execute critical section
				}
			}
		}
	}
}
