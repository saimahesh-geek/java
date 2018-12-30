package sm.concurrency.thread.producerconsumer.messages;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Message msg = new Message();
		
		new Thread(new Writer(msg)).start();
		new Thread(new Reader(msg)).start();
	}
	
}

// Shared object
class Message {
	private String message;
	private boolean empty = true;
	
	public synchronized String read() {
		while (empty) {
		// loops until buffer non-empty	
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = true;
		notifyAll();
		return message;
	}
	
	public synchronized void write(String message) {
		while (!empty) {
		// loops until buffer is empty
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = false;
		this.message = message;
		notifyAll();
	}
}

// Producer
class Writer implements Runnable {
	private Message message;

	public Writer(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		String[] messages = {"message 1",
					"message 2",
					"message 3",
					"message 4"};
		Random random = new Random();
		for (int i=0; i<messages.length; i++) {
			message.write(messages[i]);
			try {
				Thread.sleep(random.nextInt(2000));
			} catch(InterruptedException e) {
				;
			}
		}
		
		message.write("Finished");
	}
}

// Consumer
class Reader implements Runnable {
	private Message message;

	public Reader(Message message) {
		this.message = message;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (String latestMsg = message.read(); !latestMsg.equals("Finished"); latestMsg = message.read()) {
			System.out.println(latestMsg);
			try {
				Thread.sleep(random.nextInt(3000));
			} catch(InterruptedException e) {
				;
			}
		}
	}
}
