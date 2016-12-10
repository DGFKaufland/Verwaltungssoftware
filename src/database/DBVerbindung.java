/*
 * Datei erstellt am 09.12.2014 von Fabian Heuberger
 * 
 * 
 */

package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;

/**
 * Diese Klasse stellt eine Verbindung zur Datenbank her.
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
public class DBVerbindung
{

    /**
     * DriverManagerConnectionFactory-Datenfeld.
     */
    private static DriverManagerConnectionFactory cf;

    /**
     * Fehlerbehandlung zur Vorbeugung.
     * Falls es nicht gelingt den jdbc Treiber zu laden, erfolgt eine
     * Fehlermeldung, dass der Treiber nicht geladen werden konnte
     */
    public DBVerbindung()
    {
        try
        {
        	//com.mysql.jdbc.Driver
        	//org.mariadb.jdbc.Driver
            Class.forName( "com.mysql.jdbc.Driver" );
        }

        catch ( Exception e )
        {
            e.printStackTrace();
            // TODO Logger implementieren
        }
    }

    /**
     * Erstellt eine ConnectionFactory.
     * 
     * @param config
     *            config-Datei
     */
    protected final void createConnectionFactory( final String config )
    {
        if ( getCf() == null )
        {
            DBVerbindung.setCf( null );
            Properties properties = new Properties();
            InputStream stream = null;
            if ( config != null )
            {
                stream = DBVerbindung.class.getClassLoader().getResourceAsStream( config );
            }
            else
            {
                stream = DBVerbindung.class.getClassLoader().getResourceAsStream( "dbconfig.properties" );
            }
            try
            {
                properties.load( stream );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
            setCf( new DriverManagerConnectionFactory( properties.getProperty( "url" ), properties.getProperty( "benutzer" ), properties.getProperty( "pw" ) ) );
        }
    }

    /**
     * Schliesst die Verbindung zur Datenbank.
     *
     * @param con
     *            Connection-Objekt
     * @param stmt
     *            PreparedStatemt-Objekt
     * @param rs
     *            ResultSet-Objekt
     */
    public final void close( final Connection con, final PreparedStatement stmt, final ResultSet rs )
    {
        try
        {
            if ( con != null )
            {
                con.close();
            }
            if ( stmt != null )
            {
                stmt.close();
            }
            if ( rs != null )
            {
                rs.close();
            }
        }
        catch ( SQLException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * DrivermanagerConnectionFactory-Getter.
     * 
     * @return
     *         DrivermanagerConnectionFactory
     */
    public static DriverManagerConnectionFactory getCf()
    {
        return cf;
    }

    /**
     * DrivermanagerConnectionFactory-Setter.
     * 
     * @param cf1
     *            DrivermanagerConnectionFactory
     */
    public static void setCf( final DriverManagerConnectionFactory cf1 )
    {
        DBVerbindung.cf = cf1;
    }
}
