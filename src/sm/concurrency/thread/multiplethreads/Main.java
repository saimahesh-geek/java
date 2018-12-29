package sm.concurrency.thread.multiplethreads;

public class Main {

	public static void main(String[] args) {
		Countdown countdown = new Countdown();		
		CountdownThread thread1 = new CountdownThread(countdown);
		CountdownThread thread2 = new CountdownThread(countdown);
		
		thread1.start();
		thread2.start();
	}
}

class Countdown {
	private int i;
	
	public void doCounter() {
		synchronized (this) {
			for (i=10; i>0; i--) {
				System.out.println(Thread.currentThread().getName() + ": i=" + i);
			}
		}
		
	}
}

class CountdownThread extends Thread {
	private Countdown threadCountdown;

	public CountdownThread(Countdown threadCountdown) {
		this.threadCountdown = threadCountdown;
	}
	
	@Override
	public void run() {
		threadCountdown.doCounter();
	}
}
