package kSilenceCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StoreData {

	private HttpURLConnection conn;
	private OutputStream os;

	public StoreData() {
	}

	public void storeSensorData(String logiSensID, double value) {

		try {
			URL url = new URL("http://dgf-vsw.azurewebsites.net/services/sensordata/insertSensordata");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			os = conn.getOutputStream();

			String input = "{\"logical_sensor_id\":\"" + logiSensID + "\",\"value\":" + value + "}";
			System.out.println("Got Input for DB: " + input);

			os.write(input.getBytes());
			System.out.println("Wrote data... ");
			os.flush();
			
			if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
				
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			System.out.println("Buffered reader... ");

			String output;
			System.out.println("Stored in Database: " + input);
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
