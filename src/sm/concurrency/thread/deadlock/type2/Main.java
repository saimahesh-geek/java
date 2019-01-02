package sm.concurrency.thread.deadlock.type2;

public class Main {

	public static void main(String[] args) {
		final PolitePerson personA = new PolitePerson("PersonA");
		final PolitePerson personB = new PolitePerson("PersonB");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				personA.sayHello(personB);
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				personB.sayHello(personA);
			}
		}).start();
	}

	static class PolitePerson {
		private final String name;
		
		public PolitePerson(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public synchronized void sayHello(PolitePerson person) {
			System.out.format("%s: %s has said hello to me! %n", this.getName(), person.getName());
			person.sayHelloBack(this);
		}
		
		public synchronized void sayHelloBack(PolitePerson person) {
			System.out.format("%s: %s has said hello back to me! %n", this.getName(), person.getName());
		}
	}
}
