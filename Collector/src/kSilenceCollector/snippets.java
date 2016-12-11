package kSilenceCollector;
//import java.io.StringReader;
//import java.io.InputStream;
//import javax.json.Json;
//import javax.json.stream.JsonParser;


// while ((output = br.readLine()) != null) {
			// System.out.println(output);
			// }

			// String test =
			// "[{\"topic\":\"kmunda/test\",\"ID\":1,\"LSID\":\"12345\"},{\"topic\":\"kmunda/test2\",\"ID\":2,\"LSID\":\"67890\"}]\"";
			// String test1 =
			// "{\"getAllStores\":[{\"topic\":\"kmunda/test\",\"ID\":1,\"LSID\":\"12345\"},{\"topic\":\"kmunda/test2\",\"ID\":2,\"LSID\":\"67890\"}]}\"";
			// String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";

			

//		         System.out.println("The 2nd element of array");
//		         System.out.println(array.get(1));
//		         System.out.println();
//
//		         JSONObject obj2 = (JSONObject)array.get(1);
//		         System.out.println("Field \"1\"");
//		         System.out.println(obj2.get("1"));    

//		         s = "{}";
//		         obj = parser.parse(s);
//		         System.out.println(obj);
//
//		         s = "[5,]";
//		         obj = parser.parse(s);
//		         System.out.println(obj);
//
//		         s = "[5,,2]";
//		         obj = parser.parse(s);
//		         System.out.println(obj);
	

			
//			JSONArray jsonMainArr = new JSONArray(test);
//			for (int i = 0; i < jsonMainArr.length(); i++) {  // **line 2**
//			     JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
//			     String name = childJSONObject.getString("name");
//			     int age     = childJSONObject.getInt("age");
//			}
//			
			
//			JSONArray jsonarray = new JSONArray(jsonStr);
//			for (int i = 0; i < jsonarray.length(); i++) {
//			    JSONObject jsonobject = jsonarray.getJSONObject(i);
//			    String name = jsonobject.getString("name");
//			    String url = jsonobject.getString("url");
//			}
//			
//		    JSONParser parser = new JSONParser();
//		    try {
//		         Object obj = parser.parse(br);
//		         JSONArray array = (JSONArray)obj;
//					
//		         System.out.println("The 2nd element of array");
//		         System.out.println(array.get(1));
//		         System.out.println();
//
//		         JSONObject obj2 = (JSONObject)array.get(1);
//		         System.out.println("Field \"topic\"");
//		         System.out.println(obj2.get("topic"));    
//
//		      } catch(ParseException pe) {
//				
//		         System.out.println("position: " + pe.getPosition());
//		         System.out.println(pe);
//		      }

//			JsonParser parser = Json.createParser(new StringReader(br.readLine()));
//			while (parser.hasNext()) {
//			   JsonParser.Event event = parser.next();
//			   switch(event) {
//			      case START_ARRAY:
//			      case END_ARRAY:
//			      case START_OBJECT:
//			      case END_OBJECT:
//			      case VALUE_FALSE:
//			      case VALUE_NULL:
//			      case VALUE_TRUE:
//			         System.out.println(event.toString());
//			         break;
//			      case KEY_NAME:
//			         System.out.print(event.toString() + " " +
//			                          parser.getString() + " - ");
//			         break;
//			      case VALUE_STRING:
//			      case VALUE_NUMBER:
//			         System.out.println(event.toString() + " " +
//			                            parser.getString());
//			         break;
//			   }
//			}
//			






			// String[] topicList = Database.topics.keySet().toArray(new
			// String[Database.topics.size()]);
			// String[] topicList = Database.topics.values().toArray(new
			// String[2]);
			// myset.toArray(new String[myset.size()])
			//
			// String topicList[] = new String[Database.topics.size()];
			// for (int i = 0; i < Database.topics.size(); i++) {
			// topicList[i] = Database.topics. .get(i);
			// }