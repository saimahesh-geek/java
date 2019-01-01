package sm.concurrency.threadpools.producerconsumer.blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static sm.concurrency.threadpools.producerconsumer.blockingqueue.Main.EOM;

public class Main {

	public static final String EOM = "EOM";
	
	public static void main(String[] args) {
		ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(3);
		// thread interference while add/get/remove
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		executorService.execute(new Producer(buffer));
		executorService.execute(new Consumer(buffer));
		executorService.execute(new Consumer(buffer));
		
		Future<String> future =  executorService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("Being printed from Callable class");
				return "Callable result";
			}
		});
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		executorService.shutdown();
	}

}

class Producer implements Runnable {
	private ArrayBlockingQueue<String> buffer;

	public Producer(ArrayBlockingQueue<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		Random random = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		
		for (String num : nums) {
			System.out.println("Adding : " + num);
			try {
				buffer.put(num);
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Adding EOM");
		try {
			buffer.put(EOM);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Consumer implements Runnable {
	private ArrayBlockingQueue<String> buffer;

	public Consumer(ArrayBlockingQueue<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (buffer) {
				// still need synchronize
				try {
					if (buffer.isEmpty()) {
						// System.out.println(Thread.currentThread().getName() + " buffer empty");
						// bufferLock.unlock(); // Exception in thread "Thread-1" java.lang.Error: Maximum lock count exceeded
						continue;
					}
					
					if (EOM.equals(buffer.peek())) {
						System.out.println("Exiting..");
						// bufferLock.unlock(); // Exception in thread "Thread-1" java.lang.Error: Maximum lock count exceeded
						break;
					} else {
						System.out.println("removed : " + buffer.take());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
