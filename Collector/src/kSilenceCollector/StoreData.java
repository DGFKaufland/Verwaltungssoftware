package kSilenceCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;

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
	

//	public void storeSensorData(String logiSensID, double value) throws IOException  {
//
//		String POST_URL = "http://dgf-vsw.azurewebsites.net/Sensoren/services/sensordata/insertSensordata";
//		String USER_AGENT = "Mozilla/5.0";
//		
//		CloseableHttpClient httpClient = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost(POST_URL);
////		httpPost.addHeader("User-Agent", USER_AGENT);
////		httpPost.setHeader("Content-Type", "application/xml");
//
//		httpPost.addHeader("content-type", "application/json;charset=UTF-8");
//		
//		String jsondata = "{\"logical_sensor_id\":\"" + logiSensID + "\",\"value\":" + value + "}";
//		StringEntity jsonparam = new StringEntity(jsondata);
//		jsonparam.setChunked(true);
//		
//		httpPost.setEntity(jsonparam);
//		
////		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
////		urlParameters.add(new BasicNameValuePair("logical_sensor_id", logiSensID));
////		String valuestring = String.valueOf(value);
////		urlParameters.add(new BasicNameValuePair("userName", valuestring));
////
////		HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
////		httpPost.setEntity(postParams);
//
//		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//
//		System.out.println("POST Response Status: "
//				+ httpResponse.getStatusLine().getStatusCode());
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				httpResponse.getEntity().getContent()));
//
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = reader.readLine()) != null) {
//			response.append(inputLine);
//		}
//		reader.close();
//
//		// print result
//		System.out.println(response.toString());
//		httpClient.close();
//	}
}
