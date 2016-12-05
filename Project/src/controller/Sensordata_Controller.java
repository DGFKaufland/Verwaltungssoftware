/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.util.Calendar;
import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import models.Sensordata;
import database.DBStatements_Sensordata;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/sensordata" )
public class Sensordata_Controller
{
    //private static JSONObject jsonObject;
	private static Logger log = Logger.getLogger( Sensordata_Controller.class.getName() );
	
    private DBStatements_Sensordata dbs_sensordata = new DBStatements_Sensordata();

    @POST
    @Path( "/insertSensordata" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void insertSensordata (final Sensordata sensor)
    {
    	//get current Date as Timestamp
    	Date date = new Date(Calendar.getInstance().getTime().getTime());
    	System.out.println(date);
    	//Set Timestamp to Sensor Object
    	sensor.setTimestamp(date);
    	
    	dbs_sensordata.insertSensordata(sensor);
    	log.debug( "Ein Sensorwert wurde erfolgreich in die DB geschrieben." );
    }
   
    
   
}
