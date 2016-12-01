/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import models.Sensors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;



/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
public class FTP_Controller
{
	
	private static Logger log = Logger.getLogger( FTP_Controller.class.getName() );
   
	  public void create_sensor_ftp (Sensors sen) {
           				
		  	String Hardware_ID = sen.getHardware_id();
	    	String Logische_ID = sen.getLogical_id();
	    	
	    	//Server und Port sollten vom der Klasse Servers.name ausgelesen werden
	     	String server = "fabi1988.no-ip.org";
	        int port = 1111;
	        String user = "";
	        String pass = "";
	    	
	    	try{
	    		InputStream stream = FTP_Controller.class.getClassLoader().getResourceAsStream( "configuration.properties" );
	            Properties properties = new Properties();
	            properties.load( stream );
		    	user = properties.getProperty( "user" );
		    	pass = properties.getProperty( "pass" );
	    		
	    	}
	    	catch( Exception e )
	        {
	            log.error( "Fehler beim Auslesen der Daten aus der configuration.properties!", e );
	        }
	    	
		 
		        FTPClient ftpClient = new FTPClient();
		        try {		 
		            ftpClient.connect(server, port);
		            ftpClient.login(user, pass);
		            ftpClient.enterLocalPassiveMode();
		 
		            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		 
		            //File muss erstmals local erstellt werden... 
		            File LocalFile = new File("/Users/Fabian/Desktop/OPENHABITEMS/"+Logische_ID+".items");
		            FileOutputStream is = new FileOutputStream(LocalFile);
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
		 
     		        //lokal erzeugtes File wird dann entsprechend auf den Server geladen
		            String RemoteFile = "/home/openhab/items/"+Logische_ID+".items";
		            InputStream inputStream = new FileInputStream(LocalFile);
		 
		            System.out.println("Start uploading Sensor file " + LocalFile.getName());
		            boolean done = ftpClient.storeFile(RemoteFile, inputStream);
		            inputStream.close();
		            if (done) {
		            	log.debug( "File wurde erfolgreich auf dem FTP Server " + server +":" +port + " geschrieben");
		                System.out.println("The file is uploaded successfully." + LocalFile.getName());
		            }
		  
		        } catch (IOException ex) {
		        	log.error( "Fehler beim Schreiben von Daten auf dem FTP Server " + server +":" +port + " geschrieben", ex );
		            System.out.println("Error: " + ex.getMessage());
		            ex.printStackTrace();
		        } finally {
		            try {
		                if (ftpClient.isConnected()) {
		                    ftpClient.logout();
		                    ftpClient.disconnect();
		                }
		            } catch (IOException ex) {
		            	log.error( "Fehler bei Verbindungsbeendigung des FTP Servers....", ex );
		                ex.printStackTrace();
		            }
		        }
		    }
	  
	  
	  
	  public void delete_sensor_ftp (Sensors sen) {
				
		  //	String Hardware_ID = sen.getHardware_id();
	    	String Logische_ID = sen.getLogical_id();
		  		
	    		//Server und Port sollten vom der Klasse Servers.name ausgelesen werden
		     	String server = "fabi1988.no-ip.org";
		        int port = 1111;
		        String user = "";
		        String pass = "";
		        
		        try{
		    		InputStream stream = FTP_Controller.class.getClassLoader().getResourceAsStream( "configuration.properties" );
		            Properties properties = new Properties();
		            properties.load( stream );
			    	user = properties.getProperty( "user" );
			    	pass = properties.getProperty( "pass" );
		    		
		    	}
		    	catch( Exception e )
		        {
		            log.error( "Fehler beim Auslesen der Daten aus der configuration.properties!", e );
		        }
		 
		        FTPClient ftpClient = new FTPClient();
		        try {		 
		            ftpClient.connect(server, port);
		            boolean success = ftpClient.login(user, pass);
		            ftpClient.enterLocalPassiveMode();
		 
		            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		            
		            if (!success) {
		                System.out.println("Could not login to the server");
		                return;
		            }
		            	            
		            String fileToDelete = "/home/openhab/items/"+Logische_ID+".items";
		            
		            boolean deleted = ftpClient.deleteFile(fileToDelete);
		            if (deleted) {
		            	log.debug( "File wurde erfolgreich vom FTP Server " + server +":" +port + " entfernt");
		                System.out.println("The file was deleted successfully.");
		            } else {
		            	log.debug( "File konnte nicht gelöscht werden auf dem FTP Server " + server +":" +port + " NICHT vorhanden ?");
		                System.out.println("Could not delete the  file, it may not exist.");
		            }
		            
		        } catch (IOException ex) {
		        	log.error( "Fehler beim Löschen von Daten auf dem FTP Server " + server +":" +port, ex );
		            System.out.println("Error: " + ex.getMessage());
		            ex.printStackTrace();
		        } finally {
		            try {
		                if (ftpClient.isConnected()) {
		                    ftpClient.logout();
		                    ftpClient.disconnect();
		                }
		            } catch (IOException ex) {
		            	log.error( "Fehler bei Verbindungsbeendigung des FTP Servers....", ex );
		                ex.printStackTrace();
		            }
		        }
		    }
	  
	  
	
     
}
