package kSilenceCollector;

public class ReceiveMqttSignal implements Runnable {

	private Thread t;
	private String threadName;

	ReceiveMqttSignal(String name) {
		threadName = name;
		System.out.println("Creating Thread " + threadName);
	}

	public void run() {
		try {
			System.out.println(threadName + ": " + "Listening on " + Status.mqttGateway + " ...");
			new PahoDemo().doDemo();
			Status.bShutdown = true;
		} catch (InterruptedException e) {
			System.out.println("Thread " + threadName + " interrupted.");
			Status.bShutdown = true;
		}
		Status.bCollectionRunning = false;
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}