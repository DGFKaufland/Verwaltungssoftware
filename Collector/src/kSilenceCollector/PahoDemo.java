package kSilenceCollector;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Set;

public class PahoDemo implements MqttCallback {

	MqttClient client;
	String result = "0";

	public PahoDemo() {
	}

	public void doDemo() throws InterruptedException {
		try {
			client = new MqttClient(Status.mqttGatewayActive, "Yownhan3");
			MqttConnectOptions options = new MqttConnectOptions();
			options.setConnectionTimeout(5);

			// Set keySet = Database.topics.keySet();
			// String[] topicList = keySet.toArray(new String[0]));

			Set<String> tmp = Status.topics.keySet();
			String topicList[] = tmp.toArray(new String[tmp.size()]);

			System.out.println("Subscribing to: " + Status.topics.keySet());
			client.setCallback(this);
			client.connect(options);
			client.subscribe(topicList);

			Status.mqttConnected = true;
			while (!Status.bShutdown) {
				Thread.sleep(1000);
			}
			client.disconnect();
			Status.mqttConnected = false;
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	public double parseWithDefault(String number, double defaultVal) {
		try {
			return Double.parseDouble(number);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}

	public void connectionLost(Throwable arg0) {
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {

		String sMqttMsg = new String(arg1.toString());

		if (sMqttMsg.equals("alive")) {
			System.out.println("...alivecheck...");
		} else {
			System.out.println("Received Sensor Data for Topic: " + arg0 + " | Value: " + sMqttMsg);
			Status.store.storeSensorData(Status.topics.get(arg0), parseWithDefault(sMqttMsg, 0.0));
		}
	}
}