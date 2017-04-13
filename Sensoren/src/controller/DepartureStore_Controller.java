/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Departures_to_store;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_DeparturesStores;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/departures" )
public class DepartureStore_Controller
{
	private static Logger log = Logger.getLogger( DepartureStore_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_DeparturesStores dbs_departure = new DBStatements_DeparturesStores();

   
    @POST
    @Path( "/createDepartureToStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void createDepartureToStore (Object departure) throws org.codehaus.jettison.json.JSONException
    {
    	JSONArray arr = new JSONArray(departure.toString());
    	
    	Departures_to_store dep = new Departures_to_store();
    	
		for (int i=0; i<arr.length(); i++){
			org.codehaus.jettison.json.JSONObject jsonDepartureObject = arr.getJSONObject(i);
			
			dep.setStore_id(jsonDepartureObject.getInt("Departures_store_id"));
			dep.setDeparture_type_id(jsonDepartureObject.getInt("Departures_departure_id"));
        	dep.setWbl_id(jsonDepartureObject.getInt("departure_WBL_id"));
        	dep.setServer(jsonDepartureObject.getString("Servers_name"));
        	
        	
		}	
		
		dbs_departure.createDepartureToStore(dep);		
		
		log.debug( "Ein Abteilung wurde erfolgreich angelegt." );
			
 }
   
    
    
    
    @POST
    @Path( "/getDepartmentByID" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Response getDepartmentByID ( final Departures_to_store dep) throws JSONException
    {    	
    	jsonObject = new JSONObject();

        Departures_to_store dep_output = dbs_departure.selectDepartmentByID(dep);
    	

      //  System.out.println(dep.get(1).getName());
			
        jsonObject.put( "getDepartmentByID", dep_output );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();

    }   
    
    @POST
    @Path( "/getAllDepartmentsToCountryAndStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Response getAllDepartmentsToCountryAndStore ( final Departures_to_store dep1) throws JSONException
    {    	
    	jsonObject = new JSONObject();

        List<Departures_to_store> dep = dbs_departure.selectDepartureFromCountryAndStoreID(dep1);
    	

      //  System.out.println(dep.get(1).getName());
			
        jsonObject.put( "getAllDepartmentsToCountryAndStore", dep );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();

    }   
    
    @POST
    @Path( "/editShowDepartureToStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Departures_to_store editShowDepartureToStore( final Departures_to_store dep)
    {
    	Departures_to_store output = dbs_departure.selectDepartureToStore(dep);
    	return output;
    }
    

  

    @POST
    @Path( "/editDepartureToStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void editDepartureToStore (final Departures_to_store dep)
    {
    	dbs_departure.updateDepartureToStore(dep);
    	log.debug( "Abteilung wurde erfolgreich bearbeitet." );
    }


      
    @POST
    @Path( "/deleteDepartureToStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void deleteDepartureToStore (final Departures_to_store dep)
    {
    	dbs_departure.deleteDepartureToStore(dep);
    	log.debug( "Ein Abteilung wurde erfolgreich geloescht." );
    	
    }
    
    
   
}
