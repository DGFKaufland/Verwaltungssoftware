/*
 * Datei erstellt am 09.12.2014 von Fabian Heuberger
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

import models.Departures_type;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_DeparturesType extends DBVerbindung
{
	
	private static Logger log = Logger.getLogger( DBStatements_DeparturesType.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_DeparturesType()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_DeparturesType( final String config )
    {
        createConnectionFactory( config );
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Laden
    // ///////////////////////////////////////////////////////////////////////////

    
    
    /**
     * Auslesen von Daten aus der Tabelle Abteilungstyp
     */  
    

    
    ///////////////DBS Methode zum Auslesen der Werte eines ausgewähten Marktes//////////////
    
    public final Departures_type selectDepartureType( Departures_type deptyp1)
    {
    	Departures_type deptyp = new Departures_type();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Bezeichnung, Grenzwert FROM Abteilungstyp WHERE ID = ?";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, deptyp1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	deptyp.setID(rs.getInt("ID"));
            	
             	deptyp.setCountry(rs.getString("Land"));
             	deptyp.setDescription(rs.getString("Bezeichnung"));
             	deptyp.setLimit_value(rs.getInt("Grenzwert"));
               	
            	return deptyp;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Laden bestimmter Abteilungstypen zur ID aus der Datenbank!", e );
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
     * Eintrag von Daten in die Tabelle Abteilungstyp
     */  
    public final void createDepartureType ( final Departures_type deptyp )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Abteilungstyp (Land,Bezeichnung,Grenzwert) "
        		+ "Select * FROM (SELECT ? AS Land, ? AS Bezeichnung, ? AS Grenzwert) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, deptyp.getCountry());
            stmt.setString( 2, deptyp.getDescription());
            stmt.setInt( 3, deptyp.getLimit_value());  
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Erstellen eines Abteilungstyps in die Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
   
    
    
    /////////////////Alle Abteilungstypen aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Departures_type> getAllDeparturesTypes()
    {
        List<Departures_type> allDeptyp = new ArrayList<Departures_type>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Land, Bezeichnung, Grenzwert FROM Abteilungstyp ORDER BY ID, Bezeichnung";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
            	Departures_type deptyp1 = new Departures_type();

                deptyp1.setID(rs.getInt("ID"));
             	deptyp1.setCountry(rs.getString("Land"));
             	deptyp1.setDescription(rs.getString("Bezeichnung"));
             	deptyp1.setLimit_value(rs.getInt("Grenzwert"));

             	allDeptyp.add( deptyp1 );
            }

            return allDeptyp;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllDeparturesTypes() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    /////////////////Alle Abteilungstypen zu einem Land aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Departures_type> getAllDeparturesTypesToCountry(final Departures_type deptyp)
    {
        List<Departures_type> allDeptyp = new ArrayList<Departures_type>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Land, Bezeichnung, Grenzwert FROM Abteilungstyp Where Land = ? ORDER BY ID,Bezeichnung";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, deptyp.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
            	Departures_type deptyp1 = new Departures_type();

                deptyp1.setID(rs.getInt("ID"));
             	deptyp1.setCountry(rs.getString("Land"));
             	deptyp1.setDescription(rs.getString("Bezeichnung"));
             	deptyp1.setLimit_value(rs.getInt("Grenzwert"));

             	allDeptyp.add( deptyp1 );
            }

            return allDeptyp;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllDeparturesTypes() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
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
    public final void updateDepartureType ( final Departures_type deptyp )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE Abteilungstyp SET Land = ?,Bezeichnung = ?, Grenzwert = ? WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            stmt.setString( 1, deptyp.getCountry());
            stmt.setString( 2, deptyp.getDescription());
            stmt.setInt( 3, deptyp.getLimit_value());
  
            stmt.setInt( 4, deptyp.getID());
                      
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Updaten einer Abeilung zu einem Markt in die Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }   

    

    /**
     * Löschen der Daten in die Tabelle Abeilungstyp
     */ 
    public final void deleteDepartureType( final Departures_type deptyp )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM Abteilungstyp WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
           
                stmt.setInt( 1, deptyp.getID() );
                stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Löschen eines Abteilungstyps zu einem Markt in die Datenbank!", e );
        	 e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }

}
