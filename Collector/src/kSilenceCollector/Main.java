// http://www.beingjavaguys.com/2013/08/spring-maven-web-application-in-eclipse.html

package kSilenceCollector;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@Controller
public class Main {
	String message0 = "";
	String message1 = "Sensordaten Sammlung wurde gestartet ... (aktueller Status siehe Haupseite)";
	String message2 = "Die Datensammlung wurde beendet.";
	String message3 = "Ein Sample wurde an tcp://iot.eclipse.org:1883 gesendet:<br><br>";
	Queue<String> sampleQueue;

	int counter = 0;

	public Main() {
		Status.topics = new HashMap<String, String>();
		Status.store = new StoreData();
		Status.fetch = new TopicFetcher();
		sampleQueue = new LinkedList<String>();
	}

	@RequestMapping("/collector")
	public ModelAndView init() {
		System.out.println("UI: Got /collector, Request #" + ++counter);
		if (Status.mqttConnected) {
			message0 = "Datensammlung l&auml;uft ... <br> ";
		} else
			message0 = "keine Datensammlung aktiv<br>";

		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("message", message0);
		mav.addObject("gateway", Status.mqttGateway);
		return mav;
	}
	
	@RequestMapping("/switch")
	public ModelAndView swicth() {
		if (Status.mqttConnected) {
			message0 = "Datensammlung l&auml;uft ... <br> ";
		} else
			message0 = "keine Datensammlung aktiv<br>";
		
		if (Status.mqttGateway.equals("tcp://iot.eclipse.org:1883"))
			Status.mqttGateway = "tcp://yowhanpi.ddns.net:1883";
		else
			Status.mqttGateway = "tcp://iot.eclipse.org:1883";
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		mav.addObject("message", message0);
		mav.addObject("gateway", Status.mqttGateway);
		return mav;
	}
	
	@RequestMapping("/start")
	public ModelAndView startCollection() {
		System.out.println("UI: Got /start, Request #" + ++counter);

		Status.bShutdown = false;
		if (!Status.bCollectionRunning) {

			// Fetch topics from Database
			Status.fetch.getTopics();

			// Start MQTT Collection Threads
			ReceiveMqttSignal R1 = new ReceiveMqttSignal("ReceiveMqttSignal-1");
			R1.start();
			Status.bCollectionRunning = true;
		}

		return new ModelAndView("start", "message", message1);
	}

	@RequestMapping("/stop")
	public ModelAndView stopCollection() {
		System.out.println("UI: Got /stop, Request #" + ++counter);

		// Stop MQTT Collection Threads
		Status.bShutdown = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return new ModelAndView("stop", "message", message2);
	}

	@RequestMapping("/publish")
	public ModelAndView publishSample() {
		System.out.println("UI: Got /publish, Request #" + ++counter);

		int value = randInt(1, 1000);
		if (sampleQueue.size() > 10)
			sampleQueue.remove();
		sampleQueue.add("Topic: kmunda\\sens1   Wert: " + value + "<br>");

		PublishSample testSample = new PublishSample();
		try {
			String sValue = value + "";
			testSample.publish("kmunda/sens1", sValue);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String message4;
		if (Status.bCollectionRunning)
			message4 = "<h4>Datensammlung l&auml;uft ... </h4>" + message3;
		else
			message4 = message3;

		String samples = sampleQueue.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "");
		return new ModelAndView("publish", "message", message4 + samples);
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value,

		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}
