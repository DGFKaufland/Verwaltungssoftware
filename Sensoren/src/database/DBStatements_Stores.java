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

import models.Stores;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_Stores extends DBVerbindung
{
	private static Logger log = Logger.getLogger( DBStatements_Stores.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_Stores()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_Stores( final String config )
    {
        createConnectionFactory( config );
    }

    // ///////////////////////////////////////////////////////////////////////////
    // Laden
    // ///////////////////////////////////////////////////////////////////////////

    
    
    /**
     * Auslesen von Daten aus der Tabelle Sensordaten
     */  
    

    
    ///////////////DBS Methode zum Auslesen der Werte eines ausgewähten Marktes//////////////
    
    public final Stores selectStore( Stores store1)
    {
        Stores store = new Stores();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Laenderkuerzel,Marktnummer, Oeffnungszeiten_von, Oeffnungszeiten_bis, Hausleiter_id,"
        		+ "Verkaufsleiter_id, Leitstand_id FROM Markt WHERE ID = ?";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, store1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	store.setID(rs.getInt("ID"));
             	store.setCountry_code(rs.getString("Laenderkuerzel"));
             	store.setStore_number(rs.getString("Marktnummer"));
             	store.setOpening_hours_from(rs.getString("Oeffnungszeiten_von"));
             	store.setOpening_hours_to(rs.getString("Oeffnungszeiten_bis"));
             	store.setStore_manager_id(rs.getInt("Hausleiter_id"));
             	//store.setStore_manager_name(rs.getString("Hausleiter_name"));
             	store.setSales_manager_id(rs.getInt("Verkaufsleiter_id"));             	
             	//store.setSales_manager_name(rs.getString("Verkaufsleiter_name"));
               	store.setStore_leitstand_id(rs.getInt("Leitstand_id"));
               	//store.setStore_leitstand_name(rs.getString("Leitstand_name"));
           	
            	return store;
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
     * Eintrag von Daten in die Tabelle Sensoren
     */  
    public final void createStore ( final Stores store )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Markt (Laenderkuerzel,Marktnummer, Oeffnungszeiten_von, Oeffnungszeiten_bis, Hausleiter_id,"
        		+ "Verkaufsleiter_id, Leitstand_id) "
        		+ "Select * FROM (SELECT ? AS Laenderkuerzel, ? AS Marktnummer, ? AS Oeffnungszeiten_von, ? AS Oeffnungszeiten_bis,"
        		+ "? AS Hausleiter_id,? AS Verkaufsleiter_id,? AS Leitstand_id) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, store.getCountry_code());
            stmt.setString( 2, store.getStore_number());
            stmt.setString( 3, store.getOpening_hours_from());
            stmt.setString( 4, store.getOpening_hours_to());
            stmt.setInt( 5, store.getStore_manager_id());
            //stmt.setString( 6, store.getStore_manager_name()); 
            stmt.setInt( 6, store.getSales_manager_id());       
            //stmt.setString( 8, store.getSales_manager_name()); 
            stmt.setInt( 7, store.getStore_leitstand_id());
            //stmt.setString( 10, store.getStore_leitstand_name());
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Erstellen von Stores in der Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
    
    /////////////////Alle Stores aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Stores> getAllStores()
    {
        List<Stores> allStores = new ArrayList<Stores>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Laenderkuerzel,Marktnummer, Oeffnungszeiten_von, Oeffnungszeiten_bis, Hausleiter_id,"
        		+ "Verkaufsleiter_id, Leitstand_id FROM Markt ORDER BY Marktnummer";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Stores store = new Stores();

                store.setID(rs.getInt("ID"));
             	store.setCountry_code(rs.getString("Laenderkuerzel"));
             	store.setStore_number(rs.getString("Marktnummer"));
             	store.setOpening_hours_from(rs.getString("Oeffnungszeiten_von"));
             	store.setOpening_hours_to(rs.getString("Oeffnungszeiten_bis"));
             	store.setStore_manager_id(rs.getInt("Hausleiter_id"));
             	//store.setStore_manager_name(rs.getString("Hausleiter_name"));
             	store.setSales_manager_id(rs.getInt("Verkaufsleiter_id"));             	
             	//store.setSales_manager_name(rs.getString("Verkaufsleiter_name"));
             	store.setStore_leitstand_id(rs.getInt("Leitstand_id"));
               	//store.setStore_leitstand_name(rs.getString("Leitstand_name"));

                allStores.add( store );
            }

            return allStores;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllStores() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    /////////////////Alle Laender aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Stores> getAllCountries()
    {
        List<Stores> allCountries = new ArrayList<Stores>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT DISTINCT Laenderkuerzel FROM Markt ORDER BY Marktnummer";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Stores store = new Stores();

             	store.setCountry_code(rs.getString("Laenderkuerzel"));

             	allCountries.add( store );
            }

            return allCountries;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllCountries() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    public final List<Stores> getAllStoresToCountry(final Stores store1)
    {
        List<Stores> allStores = new ArrayList<Stores>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID, Laenderkuerzel,Marktnummer, Oeffnungszeiten_von, Oeffnungszeiten_bis, Hausleiter_id,"
        		+ "Verkaufsleiter_id, Leitstand_id FROM Markt Where Laenderkuerzel = ? ORDER BY Marktnummer";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, store1.getCountry_code() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Stores store = new Stores();

                store.setID(rs.getInt("ID"));
             	store.setCountry_code(rs.getString("Laenderkuerzel"));
             	store.setStore_number(rs.getString("Marktnummer"));
             	store.setOpening_hours_from(rs.getString("Oeffnungszeiten_von"));
             	store.setOpening_hours_to(rs.getString("Oeffnungszeiten_bis"));
             	store.setStore_manager_id(rs.getInt("Hausleiter_id"));
             	//store.setStore_manager_name(rs.getString("Hausleiter_name"));
             	store.setSales_manager_id(rs.getInt("Verkaufsleiter_id"));             	
             	//store.setSales_manager_name(rs.getString("Verkaufsleiter_name"));
             	store.setStore_leitstand_id(rs.getInt("Leitstand_id"));
               	//store.setStore_leitstand_name(rs.getString("Leitstand_name"));

                allStores.add( store );
            }

            return allStores;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllStoresToCountry() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    
    /**
     * Update der Daten in die Tabelle Sensordaten
     */     
    public final void updateStore ( final Stores store )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE Markt SET Oeffnungszeiten_von = ?, "
        		+ "Oeffnungszeiten_bis = ?,Hausleiter_id = ?, Verkaufsleiter_id = ?, "
        		+ "Leitstand_id = ? WHERE ID = ?";
        
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            stmt.setString( 1, store.getOpening_hours_from());
            stmt.setString( 2, store.getOpening_hours_to());
            stmt.setInt( 3, store.getStore_manager_id());
            //stmt.setString( 4, store.getStore_manager_name()); 
            stmt.setInt( 4, store.getSales_manager_id());       
            //stmt.setString( 6, store.getSales_manager_name()); 
            stmt.setInt( 5, store.getStore_leitstand_id());
            //stmt.setString( 8, store.getStore_leitstand_name());
            
            
            stmt.setInt( 6, store.getID());           
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Updaten von updateStore in die Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }   
    

    /**
     * Löschen der Daten in die Tabelle Sensordaten
     */ 
    public final void deleteStore( final Stores store )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM Markt WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
           
                stmt.setInt( 1, store.getID() );
                stmt.execute();
        }
        catch ( SQLException e )
        {
        	 log.error( "Fehler beim Updaten von deleteStore in die Datenbank!", e );
        	 e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
 

    
}
