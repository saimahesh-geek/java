package sm.concurrency.examples.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
	
	private double balance;
	private String accountNumber;
	private Lock lock;
	
	public BankAccount(double initialBalance, String accountNumber) {
		super();
		this.balance = initialBalance;
		this.accountNumber = accountNumber;
		this.lock = new ReentrantLock();
	}
	
	public void deposit(double amount) {
		boolean status = false;
		
		try {
			if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
				try {
					balance += amount;
					status = true;
				} finally {
					lock.unlock();
				}
			} else {
				System.out.println("Couldn't get the lock");
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Transaction status : " + status);
	}
	
	public void withdraw(double amount) {
		lock.lock();
		try {
			balance -= amount;
		} finally {
			lock.unlock();
		}
	}

	public double getBalance() {
		return balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
}

