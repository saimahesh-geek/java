package sm.concurrency.examples.synchronize;

public class BankAccount {
	private double balance;
	private String accountNumber;
	
	public BankAccount(double initialBalance, String accountNumber) {
		super();
		this.balance = initialBalance;
		this.accountNumber = accountNumber;
	}
	
	public void deposit(double amount) {
		synchronized (this) {
			balance += amount;
		}
	}
	
	public void withdraw(double amount) {
		synchronized (this) {
			balance -= amount;
		}
	}

	public double getBalance() {
		return balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
}
