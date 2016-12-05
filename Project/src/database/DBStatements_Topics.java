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

import models.Topics;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_Topics extends DBVerbindung
{
	private static Logger log = Logger.getLogger( DBStatements_Topics.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_Topics()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_Topics( final String config )
    {
        createConnectionFactory( config );
    }
    

    /**
     * Auslesen der Werte eines ausgewaehlten Topics
     */  
    public final Topics selectTopic( Topics topic1)
    {
        Topics topic = new Topics();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Logische_Sensor_ID FROM Topics WHERE ID = ?";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, topic1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	topic.setID(rs.getInt("ID"));
             	topic.setLSID(rs.getString("Logische_ID"));
              	
            	return topic;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von selectStore aus der Datenbank!", e );
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
     * Alle Topics aus der DB Laden und in einer Liste speichern
     */
    public final List<Topics> getAllTopics()
    {
        List<Topics> allTopics = new ArrayList<Topics>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Logische_ID FROM Sensoren ORDER BY ID";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Topics topic = new Topics();

            	topic.setID(rs.getInt("ID"));
             	topic.setLSID(rs.getString("Logische_ID"));
             	
                allTopics.add(topic);
            }

            return allTopics;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllTopics() aus der Datenbank!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
}
