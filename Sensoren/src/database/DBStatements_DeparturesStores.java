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

import models.Departures_to_store;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_DeparturesStores extends DBVerbindung
{
	
	private static Logger log = Logger.getLogger( DBStatements_DeparturesStores.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_DeparturesStores()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_DeparturesStores( final String config )
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
    
    public final Departures_to_store selectDepartureToStore( Departures_to_store dep1)
    {
        Departures_to_store dep = new Departures_to_store();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT abt.ID AS DepartureID, abt.Abteilungstyp_ID AS AbteilungstypID, abt.Markt_ID AS MarktID, "
        		+ "abt.Server AS Server, abttyp.Land AS Land, abttyp.Bezeichnung AS Bezeichnung, usr.ID AS UserID, "
        		+ "usr.Vorname AS Vorname, usr.Nachname AS Nachname FROM Abteilung abt, Abteilungstyp abttyp, User usr "
        		+ "WHERE abttyp.ID = abt.Abteilungstyp_ID AND usr.ID = abt.Warenbereichsleiter_id AND abt.ID = ?";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, dep1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	dep.setID(rs.getInt("DepartureID"));
                //System.out.println(rs.getInt("DepartureID"));
                dep.setStore_id(rs.getInt("MarktID"));
            	//System.out.println(rs.getInt("MarktID"));
            	dep.setDeparture_type_id(rs.getInt("AbteilungstypID"));
            	//System.out.println(rs.getInt("AbteilungstypID"));
            	dep.setDeparture_type_name(rs.getString("Bezeichnung"));
            	//System.out.println(rs.getString("Bezeichnung"));
            	dep.setCountry(rs.getString("Land"));
            	//System.out.println(rs.getString("Land"));
            	dep.setWbl_id(rs.getInt("UserID"));
            	//System.out.println(rs.getInt("UserID"));
             	dep.setWbl_name(rs.getString("Vorname") + " " + rs.getString("Nachname"));
             	//System.out.println(rs.getString("Vorname"));
             	dep.setServer(rs.getString("Server"));
             	//System.out.println(rs.getString("Server"));
               	
            	return dep;
            } 
            
        }

        catch ( SQLException e )
        {
        	log.error( "Fehler beim Laden aller Abteilungen aus der Datenbank!", e );
            e.printStackTrace();
            // TODO Logger implementieren
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
 

    

    
     
    public final List<Departures_to_store> selectDepartureFromCountryAndStoreID(Departures_to_store dep1)
    {
        List<Departures_to_store> allDepartures = new ArrayList<Departures_to_store>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT abt.ID AS DepartureID, abt.Abteilungstyp_ID AS AbteilungstypID, abt.Markt_ID AS MarktID, "
        		+ "abt.Server AS Server, abttyp.Land AS Land, abttyp.Bezeichnung AS Bezeichnung, usr.ID AS UserID, "
        		+ "usr.Vorname AS Vorname, usr.Nachname AS Nachname FROM Abteilung abt, Abteilungstyp abttyp, User usr "
        		+ "WHERE abttyp.ID = abt.Abteilungstyp_ID AND usr.ID = abt.Warenbereichsleiter_id AND abttyp.Land = ? AND abt.Markt_ID = ?";
        
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, dep1.getCountry() );
            stmt.setInt( 2, dep1.getStore_id() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Departures_to_store dep = new Departures_to_store();

                dep.setID(rs.getInt("DepartureID"));
                //System.out.println(rs.getInt("DepartureID"));
                dep.setStore_id(rs.getInt("MarktID"));
            	//System.out.println(rs.getInt("MarktID"));
            	dep.setDeparture_type_id(rs.getInt("AbteilungstypID"));
            	//System.out.println(rs.getInt("AbteilungstypID"));
            	dep.setDeparture_type_name(rs.getString("Bezeichnung"));
            	//System.out.println(rs.getString("Bezeichnung"));
            	dep.setCountry(rs.getString("Land"));
            	//System.out.println(rs.getString("Land"));
            	dep.setWbl_id(rs.getInt("UserID"));
            	//System.out.println(rs.getInt("UserID"));
            	dep.setWbl_name(rs.getString("Vorname") + " " + rs.getString("Nachname"));
             	//System.out.println(rs.getString("Vorname"));
             	dep.setServer(rs.getString("Server"));
             	//System.out.println(rs.getString("Server"));
	
                allDepartures.add( dep );
               // System.out.println(dep.getName());
            }

            return allDepartures;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Laden selectDepartureFromMarktID aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
        
    /**
     * Eintrag von Daten in die Tabelle Abteilung
     */  
    public final void createDepartureToStore ( final Departures_to_store dep )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO Abteilung (Abteilungstyp_ID, Markt_ID,Warenbereichsleiter_id,Server) "
        		+ "Select * FROM (SELECT ? AS Abteilungstyp_ID, ? AS Markt_ID, ? AS Warenbereichsleiter_id, ? AS Server) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, dep.getDeparture_type_id());
            stmt.setInt( 2, dep.getStore_id());          
            stmt.setInt( 3, dep.getWbl_id());  
            stmt.setString( 4, dep.getServer());  
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Erstellen einer Abteilung in die Datenbank!", e );
            e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    

   
    
    
    /**
     * Update der Daten in die Tabelle Abteilung
     */     
    public final void updateDepartureToStore ( final Departures_to_store dep )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE Abteilung SET Abteilungstyp_ID = ?, Markt_ID = ?, Warenbereichsleiter_id = ?, Server = ? WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            stmt.setInt( 1, dep.getDeparture_type_id());
            stmt.setInt( 2, dep.getStore_id());
            stmt.setInt( 3, dep.getWbl_id());
            stmt.setString( 4, dep.getServer());
  
            stmt.setInt( 5, dep.getID());
                      
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
     * Löschen der Daten in die Tabelle Abeilung und alle zugehörigen Werte aus den Tabellen Grenzwert und Servers
     */ 
    public final void deleteDepartureToStore( final Departures_to_store dep )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM Abteilung WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
           
                stmt.setInt( 1, dep.getID() );
                stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Löschen einer Abteilung zu einem Markt in die Datenbank!", e );
        	 e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
 
    

}
