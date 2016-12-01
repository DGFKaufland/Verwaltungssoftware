/*
 * Datei erstellt am 09.12.2014 von Fabian Heuberger
 *
 *
 */

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import models.Sensordata;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_Sensordata extends DBVerbindung
{
	private static Logger log = Logger.getLogger( DBStatements_Sensordata.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_Sensordata()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_Sensordata( final String config )
    {
        createConnectionFactory( config );
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Laden
    // ///////////////////////////////////////////////////////////////////////////

  
        
    /**
     * Eintrag von Sensordaten in die Tabelle Sensordaten
     */  
    public final void insertSensordata ( final Sensordata sensor )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Sensordaten (LogischeSensor_ID,Wert,Timestamp)"
        		+ "Select * FROM (SELECT ? AS LogischeSensor_ID, ? AS Wert, ? AS Timestamp) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, sensor.getLogical_sensor_id());
            stmt.setString( 2, sensor.getValue());
            stmt.setDate( 3, sensor.getTimestamp());      
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Schreiben von Sensordaten in die Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
 
 

    
}
