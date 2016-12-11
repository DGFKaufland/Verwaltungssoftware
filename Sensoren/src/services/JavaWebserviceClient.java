package services;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;


public class JavaWebserviceClient {

	public static void main(String[] args) {
		
		String Logische_Sensor_ID = "DE3000_MoPro_TESTID";
		String Wert = "123455";


		//Variante 1
		
		try {

			URL url = new URL("http://localhost:8080/services/sensordata/insertSensordata");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{"
					+ "logical_sensor_id: "+ Logische_Sensor_ID + ","
					+ "value: "+ Wert + "}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }

		
		/*
		
		try {
 
 			String string = "{"
				+ "logical_sensor_id: "+ Logische_Sensor_ID + ","
				+ "value: "+ Wert + "}";
 			
			JSONObject jsonObject = new JSONObject(string);
			//oder
			//JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON( string );
			
			System.out.println(jsonObject);
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(host + "/services/sensordata/insertSensordata");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	*/
		
	}
}
	