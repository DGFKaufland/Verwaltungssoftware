package kSilenceCollector;

import java.util.HashMap;

public class Status {
 
	public static StoreData store;
	public static TopicFetcher fetch;
	public static String mqttGateway1 = "tcp://dgfmqtt.northeurope.cloudapp.azure.com:1883";
	public static String mqttGateway2 = "tcp://iot.eclipse.org:1883";
	public static String mqttGatewayActive = mqttGateway1;
	public static boolean mqttConnected = false;
	
	// Collection Thread Run State
	public static boolean bCollectionRunning = false;
	
	// Shutdown System
	public static boolean bShutdown = false;
	
	public static HashMap <String, String> topics;
}