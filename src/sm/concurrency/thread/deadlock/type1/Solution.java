package sm.concurrency.thread.deadlock.type1;

public class Solution {
	public static Object lock1 = new Object();
	public static Object lock2 = new Object();
	
	public static void main(String[] args) {
		new Thread(new Thread1()).start();
		new Thread(new Thread2()).start();
	}
	
	private static class Thread1 implements Runnable {

		@Override
		public void run() {
			// Obtain locks in same order lock1 -> lock2
			synchronized (lock1) {
				System.out.println("Thread1: holding lock1");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread1: waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread1: has both lock1 & lock2");
				}
				System.out.println("Thread1: released lock2");
			}
			System.out.println("Thread1: released lock1.. exiting");
		}
	}
	
	private static class Thread2 implements Runnable {

		@Override
		public void run() {
			// Obtain locks in same order lock1 -> lock2
			synchronized (lock1) {
				System.out.println("Thread2: holding lock1");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread2: waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread2: has both lock1 & lock2");
				}
				System.out.println("Thread2: released lock2");
			}
			System.out.println("Thread2: released lock1.. exiting");
		}
	}	
}
