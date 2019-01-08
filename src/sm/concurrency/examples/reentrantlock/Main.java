package sm.concurrency.examples.reentrantlock;

import java.util.concurrent.TimeUnit;

import sm.concurrency.examples.reentrantlock.BankAccount;

public class Main {

	public static void main(String[] args) {
		BankAccount bankAccount = new BankAccount(1000, "123-45");
		
		Thread trThread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				bankAccount.deposit(300);
				bankAccount.withdraw(50);
			}
		});
		
		Thread trThread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				bankAccount.deposit(203.75);
				bankAccount.withdraw(100);
			}
		});
		
		trThread1.start();
		trThread2.start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("account number: " + bankAccount.getAccountNumber());
		System.out.println("balance: " + bankAccount.getBalance());
	}

}
