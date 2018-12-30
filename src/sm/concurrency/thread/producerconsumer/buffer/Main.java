package sm.concurrency.thread.producerconsumer.buffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sm.concurrency.thread.producerconsumer.buffer.Main.EOM;

public class Main {

	public static final String EOM = "EOM";
	
	public static void main(String[] args) {
		List<String> buffer = new ArrayList<String>();
		// thread interference while add/get/remove
		new Thread(new Producer(buffer)).start();
		new Thread(new Consumer(buffer)).start();
		new Thread(new Consumer(buffer)).start();
	}

}

class Producer implements Runnable {
	private List<String> buffer;

	public Producer(List<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		Random random = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		
		for (String num : nums) {
			System.out.println("Adding : " + num);
			synchronized (buffer) {
				buffer.add(num);
			}
			
			try {
				Thread.sleep(random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Adding EOM");
		synchronized (buffer) {
			buffer.add(EOM);
		}
	}
}

class Consumer implements Runnable {
	private List<String> buffer;

	public Consumer(List<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (buffer) {
				if (buffer.isEmpty()) {
					// System.out.println(Thread.currentThread().getName() + " buffer empty");
					continue;
				}
				
				if (EOM.equals(buffer.get(0))) {
					System.out.println("Exiting..");
					break;
				} else {
					System.out.println("removed : " + buffer.remove(0));
				}
			}
		}
	}
}
