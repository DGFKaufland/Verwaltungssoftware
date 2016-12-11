package kSilenceCollector;

import java.util.HashMap;

public class Status {
 
	public static StoreData store;
	public static TopicFetcher fetch;
	public static String mqttGateway = "tcp://iot.eclipse.org:1883";
	public static boolean mqttConnected = false;
	
	// Collection Thread Run State
	public static boolean bCollectionRunning = false;
	
	// Shutdown System
	public static boolean bShutdown = false;
	
	public static HashMap <String, String> topics;
}