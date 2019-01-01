package sm.concurrency.thread.producerconsumer.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static sm.concurrency.thread.producerconsumer.reentrantlock.Main.EOM;

public class Main {

	public static final String EOM = "EOM";
	
	public static void main(String[] args) {
		List<String> buffer = new ArrayList<String>();
		// thread interference while add/get/remove
		ReentrantLock bufferLock = new ReentrantLock(); 
		new Thread(new Producer(buffer, bufferLock)).start();
		new Thread(new Consumer(buffer, bufferLock)).start();
		new Thread(new Consumer(buffer, bufferLock)).start();
	}

}

class Producer implements Runnable {
	private List<String> buffer;
	private ReentrantLock bufferLock;

	public Producer(List<String> buffer, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.bufferLock = bufferLock;
	}

	@Override
	public void run() {
		Random random = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		
		for (String num : nums) {
			System.out.println("Adding : " + num);
			bufferLock.lock();
			try {
				buffer.add(num);
			} finally {
				bufferLock.unlock();
			}
			
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Adding EOM");
		bufferLock.lock();
		try {
			buffer.add(EOM);
		} finally {
			bufferLock.unlock();
		}
	}
}

class Consumer implements Runnable {
	private List<String> buffer;
	private ReentrantLock bufferLock;

	public Consumer(List<String> buffer, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.bufferLock = bufferLock;
	}

	@Override
	public void run() {
		while (true) {
			if (bufferLock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						// System.out.println(Thread.currentThread().getName() + " buffer empty");
						// bufferLock.unlock(); // Exception in thread "Thread-1" java.lang.Error: Maximum lock count exceeded
						continue;
						
						
					}
					
					if (EOM.equals(buffer.get(0))) {
						System.out.println("Exiting..");
						// bufferLock.unlock(); // Exception in thread "Thread-1" java.lang.Error: Maximum lock count exceeded
						break;
					} else {
						System.out.println("removed : " + buffer.remove(0));
					}
				} finally {
					bufferLock.unlock();
				}
			}
		}
	}
}

