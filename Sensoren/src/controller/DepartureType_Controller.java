/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Departures_type;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_DeparturesType;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/departurestype" )
public class DepartureType_Controller
{
	private static Logger log = Logger.getLogger( DepartureType_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_DeparturesType dbs_departure_types = new DBStatements_DeparturesType();

    
    @GET
    @Path( "/getAllDeparturesTypes" )
    @Produces( "application/json" )
    public Response getAllDeparturesTypes() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Departures_type> deptypes = dbs_departure_types.getAllDeparturesTypes();

        jsonObject.put( "getAllDeparturesTypes", deptypes );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    @POST
    @Path( "/getAllDeparturesTypesToCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Departures_type> getAllDeparturesTypesToCountry( final Departures_type deptyp)
    {
    	List<Departures_type> output = dbs_departure_types.getAllDeparturesTypesToCountry(deptyp);
    	return output;
    }
    
    
    @POST
    @Path( "/createDepartureType" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void createDepartureType ( final Departures_type deptyp )
    {
    	dbs_departure_types.createDepartureType(deptyp);   
    	log.debug( "Abteilungstyp wurde erfolgreich angelegt." );
    }   

    @POST
    @Path( "/editShowDepartureType" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Departures_type editShowDepartureType( final Departures_type deptyp)
    {
    	Departures_type output = dbs_departure_types.selectDepartureType(deptyp);
    	return output;
    }


    @POST
    @Path( "/editDepartureType" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void editDepartureType (final Departures_type deptyp)
    {
    	dbs_departure_types.updateDepartureType(deptyp);
    	log.debug( "Abteilungstyp wurde erfolgreich bearbeitet." );
    }

    
      
    @POST
    @Path( "/deleteDepartureType" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void deleteDepartureType (final Departures_type deptyp)
    {
    	dbs_departure_types.deleteDepartureType(deptyp);
    	log.debug( "Ein Abteilungstyp wurde erfolgreich geloescht." );
    	
    }
    
    
   
}
