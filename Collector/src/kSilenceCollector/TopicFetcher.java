package kSilenceCollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TopicFetcher {

	public void getTopics() {

		try {

			URL url = new URL("http://dgf-vsw.azurewebsites.net/Sensoren/services/topics/getAllTopics");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = br.readLine();
			System.out.println("Topics fetched from Server:");
			System.out.println(output);

			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(output);
				JSONObject obj0 = (JSONObject) obj;
				JSONArray array = (JSONArray) obj0.get("getAllTopics");

				System.out.println("ID   "+"Topic   "+"Logical Sensor ID");
				System.out.println("---+----------+-------------------");
				for (int i = 0; i < array.size(); i++) {
					JSONObject jsonobject = (JSONObject) array.get(i);
					long iID = (long) jsonobject.get("ID");
					String sLSID = (String) jsonobject.get("LSID");
					String sTopic_tmp = (String) jsonobject.get("LSID");
					String sTopic = (sTopic_tmp).replace("_", "/");
					System.out.println(iID + "     " + sTopic + "  " + sLSID);
					Status.topics.put(sTopic, sLSID);
				}

			} catch (ParseException pe) {

				System.out.println("position: " + pe.getPosition());
				System.out.println(pe);
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
