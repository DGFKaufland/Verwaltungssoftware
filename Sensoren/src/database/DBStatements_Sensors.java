/*
 * Datei erstellt am 09.12.2014 von Frank Bayer.
 *
 *
 */

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import models.Sensors;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_Sensors extends DBVerbindung
{
	private static Logger log = Logger.getLogger( DBStatements_Sensors.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_Sensors()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_Sensors( final String config )
    {
        createConnectionFactory( config );
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Laden
    // ///////////////////////////////////////////////////////////////////////////

    
    //Anzahl an Sensoren für Markt und Abteilung herausfinden   
    public final String checkAmountFromStoreDeparture( final Sensors sensor)
    {
    	String output = "";

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT DISTINCT CASE WHEN EXISTS (SELECT * FROM Sensoren WHERE Markt_ID = ? AND Abteilung_ID = ?) THEN 'TRUE' ELSE 'FALSE' END AS Data From Sensoren";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query ); 
            stmt.setInt( 1, sensor.getStore_id() );
            stmt.setInt( 2, sensor.getDeparture_id());
            rs = stmt.executeQuery();
               
            while ( rs.next() )
            {
            	output = rs.getString("Data");
            	return output;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Lesen von checkAmountFromStoreDeparture aus der Datenbank! evtl. kein Eintrag vorhanden ?", e );
            e.printStackTrace();
            // TODO Logger implementieren
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    public final Integer getAmountFromStoreDeparture( final Sensors sensor)
    {
    	Integer output;

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT count(ID) AS Data From Sensoren Where Markt_ID = ? AND Abteilung_ID = ? Group by ID";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query ); 
            stmt.setInt( 1, sensor.getStore_id() );
            stmt.setInt( 2, sensor.getDeparture_id());
            rs = stmt.executeQuery();
               
            while ( rs.next() )
            {
            	output = rs.getInt("Data");
            	return output;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Lesen von getAmountFromStoreDeparture aus der Datenbank!", e );
            e.printStackTrace();
            // TODO Logger implementieren
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    
    /**
     * Eintrag von Daten in die Tabelle Sensoren
     */  
    public final void createSensor ( final Sensors sensor )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Sensoren (Markt_ID,Abteilung_ID, Hersteller, Sensortyp, Hardware_ID, Logische_ID, Status, MqttGateway) "
        		+ "Select * FROM (SELECT ? AS Markt_ID, ? AS Abteilung_ID, ? AS Hersteller, ? AS Sensortyp, ? AS Hardware_ID, ? "
        		+ "AS Logische_ID, ? AS Status, ? AS MqttGateway) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, sensor.getStore_id());
            stmt.setInt( 2, sensor.getDeparture_id()); 
            stmt.setString( 3, sensor.getManufacturer()); 
            stmt.setString( 4, sensor.getSensor_type()); 
            stmt.setString( 5, sensor.getHardware_id()); 
            stmt.setString( 6, sensor.getLogical_id()); 
            stmt.setString( 7, sensor.getStatus());
            stmt.setString( 8, sensor.getGateway()); 
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Erstellen eines Sensors in der Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
    
    
    
    //alle Sensoren auslesen zu einem bestimmten Markt und Abteilung
    
    public final List<Sensors> getAllSensorsToStoreDepartment(Sensors sen)
    {
        List<Sensors> allSensors = new ArrayList<Sensors>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Markt_ID, Abteilung_ID, Hersteller, Sensortyp, Hardware_ID, Logische_ID, Status, MqttGateway FROM Sensoren "
        		+ "WHERE Markt_ID = ? AND Abteilung_ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, sen.getStore_id() );
            stmt.setInt( 2, sen.getDeparture_id() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Sensors sen1 = new Sensors();

                sen1.setID(rs.getInt("ID"));
                sen1.setStore_id(rs.getInt("Markt_ID"));
                sen1.setDeparture_id(rs.getInt("Abteilung_ID"));
                sen1.setManufacturer(rs.getString("Hersteller"));
                sen1.setSensor_type(rs.getString("Sensortyp"));
                sen1.setHardware_id(rs.getString("Hardware_ID"));
                sen1.setLogical_id(rs.getString("Logische_ID"));
                sen1.setStatus(rs.getString("Status"));
                sen1.setGateway(rs.getString("MqttGateway"));
            	
                allSensors.add( sen1 );
               // System.out.println(dep.getName());
            }

            return allSensors;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllSensorsToStoreDepartment aus der Datenbank!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    public final Sensors getSensorToStoreDepartment( Sensors sen1)
    {
        Sensors sen = new Sensors();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Markt_ID, Abteilung_ID, Hersteller, Sensortyp, Hardware_ID, Logische_ID, Status, MqttGateway FROM Sensoren "
        		+ "WHERE ID = ?";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, sen1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	sen.setID(rs.getInt("ID"));
                sen.setStore_id(rs.getInt("Markt_ID"));
                sen.setDeparture_id(rs.getInt("Abteilung_ID"));
                sen.setManufacturer(rs.getString("Hersteller"));
                sen.setSensor_type(rs.getString("Sensortyp"));
                sen.setHardware_id(rs.getString("Hardware_ID"));
                sen.setLogical_id(rs.getString("Logische_ID"));
                sen.setStatus(rs.getString("Status"));
                sen.setGateway(rs.getString("MqttGateway"));
               	
            	return sen;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getSensorToStoreDepartment aus der Datenbank!", e );
            e.printStackTrace();
            // TODO Logger implementieren
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    /**
     * Update der Daten in die Tabelle Server
     */     
    public final void updateSensor ( final Sensors sen )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE Sensoren SET Hersteller = ?,Sensortyp = ?,Hardware_ID = ?,Logische_ID = ?, Status = ?, MqttGateway = ? WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            stmt.setString( 1, sen.getManufacturer());
            stmt.setString( 2, sen.getSensor_type());
            stmt.setString( 3, sen.getHardware_id());
            stmt.setString( 4, sen.getLogical_id());
            stmt.setString( 5, sen.getStatus());
            stmt.setString( 6, sen.getGateway());
            
            stmt.setInt( 7, sen.getID());
                      
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Update von Sensordaten in der Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }   
    
    
    
    
    
    /**
     * Löschen der Daten in die Tabelle Sensoren
     */ 
    public final void deleteSensor( final Sensors sen )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM Sensoren WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
           
                stmt.setInt( 1, sen.getID() );
                stmt.execute();
        }
        catch ( SQLException e )
        {
        	 log.error( "Fehler beim Löschen von Sensoren aus der Datenbank!", e );
        	 e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
    

 

    
}
