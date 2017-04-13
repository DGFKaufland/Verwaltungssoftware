/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




import models.Sensors;
import controller.FTP_Controller;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_Sensors;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/sensors" )
public class Sensors_Controller
{
	private static Logger log = Logger.getLogger( Sensors_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_Sensors dbs_sensor = new DBStatements_Sensors();
   // private DBStatements_Departures dbs_departures = new DBStatements_Departures();
  
    
    
    //Anzahl an Sensoren pro Markt / Abteilung herausfinden & logische Sensor ID zusammensetzen
    @POST
    @Path( "/getAmountSensorFromStoreDeparture" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Response getAmountSensorFromStoreDeparture (Object sen) throws JSONException, org.codehaus.jettison.json.JSONException
    {    	
    	JSONArray arr = new JSONArray(sen.toString()); 
    	org.codehaus.jettison.json.JSONObject jsonSensorObject = arr.getJSONObject(0);   

    	//System.out.println(jsonSensorObject);
    
 
    	Integer store_id = jsonSensorObject.getInt("store_id");
    	Integer departure_id = jsonSensorObject.getInt("departure_id");
    	String country_code = jsonSensorObject.getString("country_code");
    	String country_code_trim = country_code.trim();
    	String store_number = jsonSensorObject.getString("store_number");
    	String store_number_trim = store_number.trim();
    	String departure_name = jsonSensorObject.getString("departure_name");
    	String departure_name_trim = departure_name.trim();
  

    	System.out.println("storeID: " + store_id);
    	System.out.println("departureID: " + departure_id);
    	System.out.println("departureName: " + departure_name_trim);
    	System.out.println("CountryCode: " + country_code_trim);
    	System.out.println("Store number: " + store_number_trim);

    	
    	Sensors sen_1 = new Sensors();
    	sen_1.setStore_id(store_id);
    	sen_1.setDeparture_id(departure_id);
    	
    	String check_amount = dbs_sensor.checkAmountFromStoreDeparture(sen_1);
    	Integer output = 0;
    	
    	
    	if (check_amount.contains("TRUE")){
    		log.debug( "Es existieren bereits Sensoren zu dem Markt: " + store_id + "-" + country_code + "_" + store_number + 
    				"und der Abteilung " + departure_id + "-" + departure_name );
    		System.out.println("Es existieren bereits Sensoren zu diesem Markt und der Abteilung!!!");
    		
    		Integer get_amount = dbs_sensor.getAmountFromStoreDeparture(sen_1);
    		//get_amount muss um eins erhoeht werden, da wir einen neuen Sensor anlegen wollen;
    		output = get_amount + 1;
    	}
    	else if (check_amount.contains("FALSE")){
    		log.debug( "Es sind noch keine Sensoren zu dem Markt: " + store_id + "-" + store_number + 
    				"und der Abteilung " + departure_id + "-" + departure_name + "vorhanden");
    		System.out.println("noch kein Sensor zu diesem Markt und der Abteilung vorhanden!!");	
    		output = 1;	
    	}
    	
    	
    	String logical_sensor_id;
    	logical_sensor_id = country_code_trim + "_" + store_number_trim + "_" + departure_name_trim + "_" + output;
    	
        log.debug( "Logische ID " + logical_sensor_id +  "wurde erfolgreich angelegt." );
    	
    	jsonObject = new JSONObject();			
        jsonObject.put( "getAmountSensorFromStoreDeparture", logical_sensor_id );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
       
    } 
   
    
    
    @POST
    @Path( "/createSensor" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void createSensor (Sensors sen) throws Exception
    {
    	//Files_Controller files = new FilesController();
    	dbs_sensor.createSensor(sen);	
    	//hier erfolgt die Erstellung der Files im openHAB Folder.....
    	createFileForSensor(sen);	
    	
        log.debug( "Sensor wurde erfolgreich angelegt." );
 	}
    
    
    
    @POST
    @Path( "/getAllSensorsToStoreDepartment" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Response getAllSensorsToStoreDepartment ( final Sensors sen) throws JSONException
    {    	
    	jsonObject = new JSONObject();

        List<Sensors> sensors = dbs_sensor.getAllSensorsToStoreDepartment(sen);

      //  System.out.println(dep.get(1).getName());
			
        jsonObject.put( "getAllSensorsToStoreDepartmentResponse", sensors );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();

    }   
  

    
    //Daten für den zu bearbeitenden Sensor erhalten
    @POST
    @Path( "/getSensorToStoreDepartment" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Sensors getSensorToStoreDepartment( final Sensors sen)
    {
    	Sensors output = dbs_sensor.getSensorToStoreDepartment(sen);
    	return output;
    }
    
    
    @POST
    @Path( "/editSensor" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void editSensor (final Sensors sen) throws Exception
    {
    	System.out.println(sen.getID());
    	System.out.println(sen.getManufacturer());
    	System.out.println(sen.getSensor_type());
    	System.out.println(sen.getHardware_id());
    	System.out.println(sen.getLogical_id());
    	System.out.println(sen.getStatus());
    	
    	dbs_sensor.updateSensor(sen);
    	editFileForSensor(sen);  	
    	log.debug( "Sensor wurde erfolgreich upgedated." );
    }
    
    
    //Sensor aus DB löschen!
    @POST
    @Path( "/deleteSensor" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void deleteSensor (final Sensors sen) throws Exception
    {
    	//Code, der die Files entsprechend vom openHAB löscht
    	deleteFileForSensor(sen);
    	//Code, der die Sensor aus DB löscht
    	dbs_sensor.deleteSensor(sen);
    	log.debug( "Sensor wurde erfolgreich geloescht." );
    	
    }
    

    
    
    ////CODE für die Files schreiben / aendern / loeschen
    
    public void createFileForSensor (Sensors sen) throws Exception
    {
    	String Logische_ID = sen.getLogical_id();
    	String Sensortyp = sen.getSensor_type();
    	String Status = sen.getStatus();
    	String Hersteller = sen.getManufacturer();
    	//Integer Markt_ID = sen.getStore_id();
    	//Integer Abteilung_ID = sen.getDeparture_id();
    	    	
    	//Serveradresse für das schreiben des Files wird noch benötigt
    	//Servers server = new Servers();   	
    	//server.setStore_id(Markt_ID);
    	//server.setDeparture_id(Abteilung_ID);
    	//Servers server_output = dbs_departures.getServerToStoreDeparture(server);
    	   //Serveradresse zum Schreiben
    	//String Serveradresse = server_output.getName();
    	/*
    	System.out.println("Hardware_ID writeFiles:  " + Hardware_ID);
    	System.out.println("Logische_ID writeFiles:  " + Logische_ID);
    	System.out.println("Hersteller writeFiles:  " + Hersteller);
    	System.out.println("Sensortyp writeFiles:  " + Sensortyp);
    	System.out.println("Status writeFiles:  " + Status);
    	System.out.println("Logische_ID deleteFiles #of digits from field_input:  " + Logische_ID.length());
    	*/
    	
    	if (Status.contains("passiv")){
    		System.out.println("nichts zu machen, da status des Sensors passiv ist");
    	}
    	else{
    		
        	if (Hersteller.contains("EnOcean")){
        		//enOcean spezifische Generierung des Sensorfiles
        		
        			if (Sensortyp.contains("Temperatursensor")){
        				 
        				FTP_Controller con = new FTP_Controller();
        				con.sendOverSFTP(sen);
        				
        				/*
        				try {
        	     	            File statText = new File("http://192.168.178.88/home/linaro/OpenHABitems/"+Logische_ID+".items");
        	     	            FileOutputStream is = new FileOutputStream(statText);
        	     	            OutputStreamWriter osw = new OutputStreamWriter(is);    
        	     	            Writer w = new BufferedWriter(osw);
        	     	                     
        	     		            w.write("Group All");
        	     		            w.write("\r\n");
        	     		            //'\"'+"Testausgabe"+'\"'        	     		            
        	     		            String sensorbinding = "";
        	     		            sensorbinding = "Number " + Logische_ID + " " + '\"' +"Temperature [%.1f °C]"+ '\"' + "<temperature> (All) {enocean=" + '\"' + "{id="+Hardware_ID+", eep=A5:02:05, parameter=TEMPERATURE}" + '\"' + ",mqtt=" + '\"' + "<[mosquitto:localnet/openHAB/"+Logische_ID+":state:default]" + '\"' + "}";  
        	     		            w.write(sensorbinding);
        	     	            
        	     	            w.close();
        	     	        } catch (IOException e) {
        	     	            System.err.println("Problem writing to the file statsTest.txt");
        	     	        }
        				
        				*/
        				
        			}
        			else{
        				log.debug( Logische_ID + " ist kein Temperatursensor" );
        				System.out.println("es ist kein Temperatursensor");
        			}
        			
        		
        		
        	}
        	else if (Hersteller.contains("Endiio")){
        		//Endiio spezifische Generierung des Sensorfiles
        	}
        	else{
        		log.debug( Hersteller + " ist nicht spezifiziert" );
        		System.out.println("Hersteller ist nicht spezifiziert");
        	}
    		
    	}
 	
 	}
    
       
    
    public void editFileForSensor (Sensors sen) throws Exception
    {

    	
    	//Sensor ID wird zwingend benötigt
    	//Integer Sensor_ID = sen.getID();
    	String Hardware_ID = sen.getHardware_id();
    	String Logische_ID = sen.getLogical_id();
    	String Sensortyp = sen.getSensor_type();
    	String Status = sen.getStatus();
    	String Hersteller = sen.getManufacturer();
    	//Integer Markt_ID = sen.getStore_id();
    	//Integer Abteilung_ID = sen.getDeparture_id();
    	    	
    	//Serveradresse für das schreiben des Files wird noch benötigt
    	//Servers server = new Servers();   	
    	//server.setStore_id(Markt_ID);
    	//server.setDeparture_id(Abteilung_ID);
    	//Servers server_output = dbs_departures.getServerToStoreDeparture(server);
    	  //Serveradresse zum Schreiben
    	//String Serveradresse = server_output.getName();
    	
    	System.out.println("Hardware_ID writeFiles:  " + Hardware_ID);
    	System.out.println("Logische_ID writeFiles:  " + Logische_ID);
    	System.out.println("Hersteller writeFiles:  " + Hersteller);
    	System.out.println("Sensortyp writeFiles:  " + Sensortyp);
    	System.out.println("Status writeFiles:  " + Status);
    	
    	
    	if (Status.contains("passiv")){
    		System.out.println("da status des Sensors passiv ist, muss dieser gelöscht werden, da nichts gesendet werden soll");
    		deleteFileForSensor(sen);
    	}
    	else{
    		
        	if (Hersteller.contains("EnOcean")){
        		//file erst löschen, dann mit neuen Parametern neu generieren
        		deleteFileForSensor(sen);
        		//enOcean spezifische Generierung des Sensorfiles
        		
        			if (Sensortyp.contains("Temperatursensor")){
        				
        				FTP_Controller con = new FTP_Controller();
        				con.sendOverSFTP(sen);
        				
        				/*
        				try {
        	     	            //Whatever the file path is. Dynamisch zusammengesetzt durch Hardware ID
        	        			//hier fehlt noch die Implementierung, an welche Pfad / IP ein Sensorfile auf den OpenHAB geschrieben werden soll
        	        			 //aktuelle Implementierung sieht vor, dass es lokal abgelegt wird auf dem Desktop (von einem MAC)
        	        			 
        	     	            File statText = new File("/Users/Fabian/Desktop/OPENHABITEMS/"+Logische_ID+".items");
        	     	            FileOutputStream is = new FileOutputStream(statText);
        	     	            OutputStreamWriter osw = new OutputStreamWriter(is);    
        	     	            Writer w = new BufferedWriter(osw);
        	     	                     
        	     		            w.write("Group All");
        	     		            w.write("\r\n");
        	     		            w.write("\r\n");
        	     		            
        	     		            //'\"'+"Testausgabe"+'\"'
        	     		            
        	     		            String sensorbinding = "";
        	     		            sensorbinding = "Number " + Logische_ID + " " + '\"' +"Temperature [%.1f °C]"+ '\"' + "<temperature> (All) {enocean=" + '\"' + "{id="+Hardware_ID+", eep=A5:02:05, parameter=TEMPERATURE}" + '\"' + ",mqtt=" + '\"' + "<[mosquitto:localnet/openHAB/"+Logische_ID+":state:default]" + '\"' + "}";  
        	     		            w.write(sensorbinding);
        	     	            
        	     	            w.close();
        	     	        } catch (IOException e) {
        	     	            System.err.println("Problem writing to the file statsTest.txt");
        	     	        }
        	     	        */
        				
        			}
        			else{
        				log.debug( Logische_ID + " ist kein Temperatursensor" );
        				System.out.println("ist ein Feuchtigkeitssensor....");
        			}
        			
        		
        		
        	}
        	else if (Hersteller.contains("Endiio")){
        		//Endiio spezifische Generierung des Sensorfiles
        	}
        	else{
        		log.debug( Hersteller + " ist nicht spezifiziert" );
        		System.out.println("Hersteller ist nicht spezifiziert");
        	}
    		
    	}
    	

    	
 	}
    
    
    
    public void deleteFileForSensor (Sensors sen) throws Exception
    {
    	
    	System.out.println("Sensor ID zu löschen:" + sen.getID());
    	
    	Sensors sensor = getSensorToStoreDepartment(sen);

    	String Hardware_ID = sensor.getHardware_id();
    	String Logische_ID = sensor.getLogical_id();
    	Integer Markt_ID = sensor.getStore_id();
    	Integer Abteilung_ID = sensor.getDeparture_id();
    	    	
    	//Serveradresse für das schreiben des Files wird noch benötigt
    	//Servers server = new Servers();   	
    	//server.setStore_id(Markt_ID);
    	//server.setDeparture_id(Abteilung_ID);
    	//Servers server_output = dbs_departures.getServerToStoreDeparture(server);
    	  //Serveradresse zum Schreiben
    	//String Serveradresse = server_output.getName();
    	
    	System.out.println("Hardware_ID deleteFiles:  " + Hardware_ID);
    	System.out.println("Logische_ID deleteFiles:  " + Logische_ID);
    	
    	System.out.println("Logische_ID deleteFiles #of digits from DB:  " + Logische_ID.length());
    	
    	String logische_id_bereinigt = Logische_ID.replaceAll(" ", "");
    	
    	System.out.println("Logische_ID deleteFiles #of digits from DB (bereinigt):  " + logische_id_bereinigt.length());
    	
    	FTP_Controller con = new FTP_Controller();
		con.deleteOverSFTP(sensor);
    	
			/*
		
        		try {
        	     	            //Whatever the file path is. Dynamisch zusammengesetzt durch Hardware ID
        	        			//hier fehlt noch die Implementierung, an welche Pfad / IP ein Sensorfile auf den OpenHAB der Server gelöscht werden soll
        	        			 //aktuelle Implementierung sieht vor, dass das File lokal gelöscht wird -> lokal auf dem Desktop (von einem MAC)
        	        			 
        	     	           File statText = new File("/Users/Fabian/Desktop/OPENHABITEMS/"+Logische_ID+".items");
        	     	            
	        	     	          if(statText.exists() && statText.isFile()) {
	        	     	        	 if(statText.delete()){
	         	     	    			System.out.println(statText.getName() + " is deleted!");
	         	     	    		}else{
	         	     	    			System.out.println("Delete operation is failed.");
	         	     	    		}
	        	     	 			}
	        	     	          else{
	        	     	        	  System.out.println("File existiert nicht! Daher nichts zu löschen");
	        	     	          }
        	     	           
        	     	          
        	     	            
        	     	           
        	     	        } catch (Exception e) {
        	     	            System.err.println("Problem by deleting to the file...");
        	     	        }
    		*/
    	}

     
}
