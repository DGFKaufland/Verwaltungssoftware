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

import models.Departures_to_store;
import models.Sensors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

import database.DBStatements_DeparturesStores;


import com.jcraft.jsch.Channel;

import com.jcraft.jsch.ChannelSftp;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;



/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
public class FTP_Controller
{
	
	private static Logger log = Logger.getLogger( FTP_Controller.class.getName() );
	private DBStatements_DeparturesStores dbs_departure = new DBStatements_DeparturesStores();
	
	public void sendOverSFTP(Sensors sen) throws Exception {

	String Hardware_ID = sen.getHardware_id();
    String Logische_ID = sen.getLogical_id();
		
    Departures_to_store dep_new = new Departures_to_store ();
	dep_new.setID(sen.getDeparture_id());
	
	Departures_to_store dep_outut = dbs_departure.selectDepartmentByID(dep_new);	    	
	String ServerANDPort = dep_outut.getServer();
	
	//System.out.print(ServerANDPort);
	
	String server = ServerANDPort.substring(0, ServerANDPort.indexOf( ':' ));
	String port_string = ServerANDPort.substring(ServerANDPort.indexOf( ':' )+1, ServerANDPort.length());
	
	int port = Integer.parseInt(port_string);
    
	String user = "";
    String pass = "";
 	String verzeichnis = "";
    
 	try{
 		InputStream stream = FTP_Controller.class.getClassLoader().getResourceAsStream( "configuration.properties" );
         Properties properties = new Properties();
         properties.load( stream );
	    	user = properties.getProperty( "user" );
	    	pass = properties.getProperty( "pass" );
	    	verzeichnis = properties.getProperty( "verzeichnis" );
 		
 	}
 	catch( Exception e )
     {
         log.error( "Fehler beim Auslesen der Daten aus der configuration.properties!", e );
     }
	
	
    String SFTPHOST = server;
	int SFTPPORT = port;
	String SFTPUSER = user;
	String SFTPPASS = pass;
	String SFTPWORKINGDIR = verzeichnis ;

	Session session = null;
	Channel channel = null;
	ChannelSftp channelSftp = null;
	System.out.println("preparing the host information for sftp.");

	try {
	JSch jsch = new JSch();

	session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
	session.setPassword(SFTPPASS);
	java.util.Properties config = new java.util.Properties();
	config.put("StrictHostKeyChecking", "no");
	session.setConfig(config);
	session.connect();
	System.out.println("Host connected.");
	channel = session.openChannel("sftp");
	channel.connect();
	System.out.println("sftp channel opened and connected.");
	channelSftp = (ChannelSftp) channel;
	channelSftp.cd(SFTPWORKINGDIR);

	File f = new File(Logische_ID+".items");
	
	   FileOutputStream is = new FileOutputStream(f);
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
           
	channelSftp.put(new FileInputStream(f), f.getName());
	
	} catch (Exception ex) {

	System.out.println("Exception found while tranfer the response.");

	throw new Exception();

	} 
	
	finally {

	channelSftp.exit();

	System.out.println("sftp Channel exited.");

	channel.disconnect();

	System.out.println("Channel disconnected.");

	session.disconnect();

	System.out.println("Host Session disconnected.");

	}

}
	
	
	
	
	
	public void deleteOverSFTP(Sensors sen) throws Exception {

		String Hardware_ID = sen.getHardware_id();
	    String Logische_ID = sen.getLogical_id();
			
	    Departures_to_store dep_new = new Departures_to_store ();
		dep_new.setID(sen.getDeparture_id());
		
		Departures_to_store dep_outut = dbs_departure.selectDepartmentByID(dep_new);	    	
		String ServerANDPort = dep_outut.getServer();
		
		//System.out.print(ServerANDPort);
		
		String server = ServerANDPort.substring(0, ServerANDPort.indexOf( ':' ));
		String port_string = ServerANDPort.substring(ServerANDPort.indexOf( ':' )+1, ServerANDPort.length());
		
		int port = Integer.parseInt(port_string);
	    
		String user = "";
	    String pass = "";
	 	String verzeichnis = "";
	    
	 	try{
	 		InputStream stream = FTP_Controller.class.getClassLoader().getResourceAsStream( "configuration.properties" );
	         Properties properties = new Properties();
	         properties.load( stream );
		    	user = properties.getProperty( "user" );
		    	pass = properties.getProperty( "pass" );
		    	verzeichnis = properties.getProperty( "verzeichnis" );
	 		
	 	}
	 	catch( Exception e )
	     {
	         log.error( "Fehler beim Auslesen der Daten aus der configuration.properties!", e );
	     }
		
		
	    String SFTPHOST = server;
		int SFTPPORT = port;
		String SFTPUSER = user;
		String SFTPPASS = pass;
		String SFTPWORKINGDIR = verzeichnis ;

		Session session = null;
		Channel channel = null;
		System.out.println("preparing the host information for sftp.");



			
			try {
				JSch jsch = new JSch();

				session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
				session.setPassword(SFTPPASS);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.connect();
				System.out.println("Host connected.");
				channel = session.openChannel("sftp");
				channel.connect();
				System.out.println("sftp channel opened and connected.");
				ChannelSftp sftp = (ChannelSftp) channel;
				
				sftp.cd(SFTPWORKINGDIR);				
				sftp.rm(Logische_ID+".items");
				
			    Boolean success = true;
		     
			     if (success) {
		            	log.debug( "File wurde erfolgreich vom FTP Server " + server +":" +port + " entfernt");
		                System.out.println("The file was deleted successfully.");
		            } else {
		            	log.debug( "File konnte nicht gelöscht werden auf dem FTP Server " + server +":" +port + " NICHT vorhanden ?");
		                System.out.println("Could not delete the  file, it may not exist.");
		            }
			    
			     sftp.exit();
			     channel.disconnect();
			     session.disconnect();
			} catch (JSchException e) {
			    System.out.println(e.getMessage().toString());
			    e.printStackTrace();  
			} catch (SftpException e) {
			    System.out.println(e.getMessage().toString());
			    e.printStackTrace();
			}

	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	  public void create_sensor_ftp (Sensors sen) {
           				
		  	String Hardware_ID = sen.getHardware_id();
	    	String Logische_ID = sen.getLogical_id();
	    	
	    	//Server und Port auslesen aus der Tabelle Abteilung...
	    	
	    	Departures_to_store dep_new = new Departures_to_store ();
	    	dep_new.setID(sen.getDeparture_id());
	    	
	    	Departures_to_store dep_outut = dbs_departure.selectDepartmentByID(dep_new);	    	
	    	String ServerANDPort = dep_outut.getServer();
	    	
	    	//System.out.print(ServerANDPort);
	    	
	    	String server = ServerANDPort.substring(0, ServerANDPort.indexOf( ':' ));
	    	String port_string = ServerANDPort.substring(ServerANDPort.indexOf( ':' )+1, ServerANDPort.length());
	    	
	    	int port = Integer.parseInt(port_string);
	    	
	    	//System.out.println("server" + server);
	    	
	    	//System.out.println("port_int" + port);
	    	
	     	//String server = "fabi1988.no-ip.org";
	        //int port = 1111;
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
		 
		            //File muss erstmals local erstellt werden... koennte auf dem Server ggf zu Schwierigkeiten fuehren
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
		            String RemoteFile = "/home/pi/Desktop/openhab/configurations/items/"+Logische_ID+".items";
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
		     	//String server = "fabi1988.no-ip.org";
		        //int port = 1111;
	    	
	    	Departures_to_store dep_new = new Departures_to_store ();
	    	dep_new.setID(sen.getDeparture_id());
	    	
	    	Departures_to_store dep_outut = dbs_departure.selectDepartmentByID(dep_new);	    	
	    	String ServerANDPort = dep_outut.getServer();
	    	
	    	//System.out.print(ServerANDPort);
	    	
	    	String server = ServerANDPort.substring(0, ServerANDPort.indexOf( ':' ));
	    	String port_string = ServerANDPort.substring(ServerANDPort.indexOf( ':' )+1, ServerANDPort.length());
	    	
	    	int port = Integer.parseInt(port_string);
	    	
	    	
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
		            	            
		            String fileToDelete = "/home/pi/Desktop/openhab/configurations/items/"+Logische_ID+".items";
		            
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
