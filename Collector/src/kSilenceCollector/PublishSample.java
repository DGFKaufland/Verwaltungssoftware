package kSilenceCollector;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishSample {

	MqttClient client;

	public void publish(String topic, String msg) throws InterruptedException {
		try {
			client = new MqttClient("tcp://iot.eclipse.org:1883", "publishJonas");
			client.connect();

			MqttMessage message = new MqttMessage();
			message.setPayload(msg.getBytes());

			if (client.isConnected()) {
				client.publish(topic, message);
			}

			client.disconnect();
		} catch (

		MqttException e)

		{
			e.printStackTrace();
		}
	}
}
