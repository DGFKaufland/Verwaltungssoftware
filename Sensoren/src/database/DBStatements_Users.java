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

import models.Users;

/**
 * Diese Klasse enthaelt alle Datenbankabfragen.
 *
 * @author Fabian Heuberger
 * @version 1.0
 */
public class DBStatements_Users extends DBVerbindung
{
	private static Logger log = Logger.getLogger( DBStatements_Users.class.getName() );
	
    /**
     * Parameterloser Konstruktor der Klasse.
     */
    public DBStatements_Users()
    {
        createConnectionFactory( null );
    }

    /**
     * Konstruktor der Klasse DBSatements.
     * 
     * @param config
     *            Konfiguration fuer die ConnectionFactory.
     */
    public DBStatements_Users( final String config )
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
    
    public final Users selectUser( Users user1)
    {
        Users user = new Users();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User WHERE ID = ? ORDER BY Vorname,Nachname";
        
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setInt( 1, user1.getID() );
            rs = stmt.executeQuery();
            
            while ( rs.next() )
            {
            	user.setID(rs.getInt("ID")); 
            	user.setCountry(rs.getString("Land")); 
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));
               	
            	return user;
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
    public final void createUser ( final Users user )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "INSERT INTO User (Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle) "
        		+ "Select * FROM (SELECT ? AS Land, ? AS Anrede, ? AS Vorname, ? AS Nachname, ? AS Email,? AS Telefon,? AS Rolle) AS tmp ";
      
        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user.getCountry());
            stmt.setString( 2, user.getSalutation());
            stmt.setString( 3, user.getFirstname());
            stmt.setString( 4, user.getLastname());
            stmt.setString( 5, user.getEmail());    
            stmt.setString( 6, user.getPhone());  
            stmt.setString( 7, user.getRole());  
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
    
    
    /////////////////Alle Users aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllUsers()
    {
        List<Users> allUsers = new ArrayList<Users>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));            	
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
 /////////////////Alle Users zu einem bestimmten Land aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllUsersToCountry(Users user1)
    {
        List<Users> allUsersToCountry = new ArrayList<Users>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;        
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User WHERE Land = ? ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user1.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsersToCountry.add( user );
            }

            return allUsersToCountry;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    
    /////////////////Alle Hausleiter aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllStoreManagers()
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'HL' ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    /////////////////Alle Hausleiter aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllStoreManagersForCountry(Users user1)
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'HL' And Land = ? ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user1.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    /////////////////Alle Verkaufsleiter aus der DB Laden und in einer Liste speichern/////////////
      
      public final List<Users> getAllSalesManagers()
      {
          List<Users> allUsers = new ArrayList<Users>();
          
          Connection con = null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
          		+ "WHERE Rolle = 'VKL' ORDER BY Vorname,Nachname";

          try
          {
              con = getCf().createConnection();
              stmt = con.prepareStatement( query );
              
              rs = stmt.executeQuery();

              while ( rs.next() )
              {
                  Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));
               	user.setSalutation(rs.getString("Anrede"));
               	user.setFirstname(rs.getString("Vorname"));
               	user.setLastname(rs.getString("Nachname"));
               	user.setEmail(rs.getString("Email"));
               	user.setPhone(rs.getString("Telefon"));
               	user.setRole(rs.getString("Rolle"));

                  allUsers.add( user );
              }

              return allUsers;
          }
          catch ( SQLException e )
          {
          	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
              //log.error( "Fehler beim Laden aller Regeln!", e );
          }
          finally
          {
              close( con, stmt, rs );
          }
          return null;
      }
    
    
    
    public final List<Users> getAllSalesManagersForCountry(Users user1)
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'VKL' And Land = ? ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user1.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
  
    
  /////////////////Alle Leitstaende aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllLeitstand()
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'LS' ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID")); 
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    } 
    
    
 /////////////////Alle Leitstaende nach Land aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllLeitstandForCountry(Users user1)
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'LS' AND Land = ? ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user1.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID")); 
                user.setCountry(rs.getString("Land"));
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    } 
    
    
  /////////////////Alle Warenbereichsleiter aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllWBL()
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'WBL' ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));            	
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
            //log.error( "Fehler beim Laden aller Regeln!", e );
        }
        finally
        {
            close( con, stmt, rs );
        }
        return null;
    }
    
    
    
  /////////////////Alle Warenbereichsleiter nach Land aus der DB Laden und in einer Liste speichern/////////////
    
    public final List<Users> getAllWBLForCountry(Users user1)
    {
        List<Users> allUsers = new ArrayList<Users>();
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT ID,Land,Anrede,Vorname,Nachname,Email,Telefon,Rolle FROM User "
        		+ "WHERE Rolle = 'WBL' AND Land = ? ORDER BY Vorname,Nachname";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            stmt.setString( 1, user1.getCountry() );
            rs = stmt.executeQuery();

            while ( rs.next() )
            {
                Users user = new Users();

                user.setID(rs.getInt("ID"));
                user.setCountry(rs.getString("Land"));     
             	user.setSalutation(rs.getString("Anrede"));
             	user.setFirstname(rs.getString("Vorname"));
             	user.setLastname(rs.getString("Nachname"));
             	user.setEmail(rs.getString("Email"));
             	user.setPhone(rs.getString("Telefon"));
             	user.setRole(rs.getString("Rolle"));

                allUsers.add( user );
            }

            return allUsers;
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Auslesen von getAllUsers() aus der Datenbank!", e );
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
    public final void updateUser ( final Users user )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "UPDATE User SET Land = ?, Anrede = ?,Vorname = ?,Nachname = ?,Email = ?, Telefon = ?, Rolle = ? WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
            
            stmt.setString( 1, user.getCountry());
            stmt.setString( 2, user.getSalutation());
            stmt.setString( 3, user.getFirstname());
            stmt.setString( 4, user.getLastname());
            stmt.setString( 5, user.getEmail()); 
            stmt.setString( 6, user.getPhone());  
            stmt.setString( 7, user.getRole());  
            
            stmt.setInt(8, user.getID());
                      
            stmt.execute();
        }
        catch ( SQLException e )
        {
        	log.error( "Fehler beim Updaten von updateUser in die Datenbank!", e );
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
    public final void deleteUser( final Users user )
    {
        Connection con = null;
        PreparedStatement stmt = null;
        String query = "DELETE FROM User WHERE ID = ?";

        try
        {
            con = getCf().createConnection();
            stmt = con.prepareStatement( query );
           
                stmt.setInt( 1, user.getID() );
                stmt.execute();
        }
        catch ( SQLException e )
        {
        	 log.error( "Fehler beim Loeschen von deleteUser in die Datenbank!", e );
        	 e.printStackTrace();
        }
        finally
        {
            close( con, stmt, null );
        }
    }
    
 

    
}
