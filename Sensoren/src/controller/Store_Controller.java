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

import models.Stores;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_Stores;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/stores" )
public class Store_Controller
{
	private static Logger log = Logger.getLogger( Store_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_Stores dbs_stores = new DBStatements_Stores();

   
    @POST
    @Path( "/createStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void createStore ( final Stores store )
    {
    	dbs_stores.createStore(store);   
    	log.debug( "Markt wurde erfolgreich angelegt." );
    }   
    

    @GET
    @Path( "/getAllCountries" )
    @Produces( "application/json" )
    public Response getAllCountries() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Stores> stores = dbs_stores.getAllCountries();

        jsonObject.put( "getAllCountries", stores );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    @GET
    @Path( "/getAllStores" )
    @Produces( "application/json" )
    public Response getAllSensordata() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Stores> stores = dbs_stores.getAllStores();

        jsonObject.put( "getAllStores", stores );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    @POST
    @Path( "/getAllStoresToCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Stores> getAllStoresToCountry( final Stores store)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Stores> output = dbs_stores.getAllStoresToCountry(store);
    	return output;
    }
    

    
    @POST
    @Path( "/editShowStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Stores editShowStore( final Stores store)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	Stores output = dbs_stores.selectStore(store);
    	return output;
    }
    
    @POST
    @Path( "/editStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void editSensordata (final Stores store)
    {
    	dbs_stores.updateStore(store);
    	log.debug( "Markt wurde erfolgreich bearbeitet." );
    }
    
    
   

    @POST
    @Path( "/deleteStore" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void deleteSensordata (final Stores store)
    {
    	dbs_stores.deleteStore(store);
    	log.debug( "Markt wurde erfolgreich geloescht." );
    }
    
    
    
    
   
}
