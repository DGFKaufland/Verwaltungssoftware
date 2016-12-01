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

import models.Users;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_Users;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/users" )
public class User_Controller
{
	private static Logger log = Logger.getLogger( User_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_Users dbs_users = new DBStatements_Users();

   
    @POST
    @Path( "/createUser" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void createStore ( final Users user )
    {
    	dbs_users.createUser(user);   
    	log.debug( "User wurde erfolgreich angelegt." );
    }   
    

    @GET
    @Path( "/getAllUsers" )
    @Produces( "application/json" )
    public Response getAllUsers() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Users> user = dbs_users.getAllUsers();

        jsonObject.put( "getAllUsers", user );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    
    @POST
    @Path( "/getAllUsersToCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Users> getAllUsersToCountry( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Users> output = dbs_users.getAllUsersToCountry(user);
    	return output;
    }
    
    
    
    @GET
    @Path( "/getAllStoreManagers" )
    @Produces( "application/json" )
    public Response getAllStoreManagers() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Users> user = dbs_users.getAllStoreManagers();

        jsonObject.put( "getAllStoreManagers", user );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    
    @POST
    @Path( "/getAllStoreManagersForCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Users> getAllStoreManagersForCountry( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Users> output = dbs_users.getAllStoreManagersForCountry(user);
    	return output;
    }
    
    @GET
    @Path( "/getAllSalesManagers" )
    @Produces( "application/json" )
    public Response getAllSalesManagersForStore() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Users> user = dbs_users.getAllSalesManagers();

        jsonObject.put( "getAllSalesManagers", user );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    @POST
    @Path( "/getAllSalesManagersForCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Users> getAllSalesManagersForCountry( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Users> output = dbs_users.getAllSalesManagersForCountry(user);
    	return output;
    }
    
    
    @GET
    @Path( "/getAllLeitstand" )
    @Produces( "application/json" )
    public Response getAllLeitstand() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Users> user = dbs_users.getAllLeitstand();

        jsonObject.put( "getAllLeitstand", user );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    @POST
    @Path( "/getAllLeitstandForCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Users> getAllLeitstandForCountry( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Users> output = dbs_users.getAllLeitstandForCountry(user);
    	return output;
    }
     
    
    @GET
    @Path( "/getAllWBL" )
    @Produces( "application/json" )
    public Response getAllWBLForStore() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Users> user = dbs_users.getAllWBL();

        jsonObject.put( "getAllWBL", user );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
    
    
    @POST
    @Path( "/getAllWBLForCountry" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public List<Users> getAllWBLForCountry( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	List<Users> output = dbs_users.getAllWBLForCountry(user);
    	return output;
    }
    
    
    @POST
    @Path( "/editShowUser" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public Users editShowUser( final Users user)
    {
    	//System.out.println("editShowSensordata StoreID (input) "+ store.getID());
    	Users output = dbs_users.selectUser(user);
    	return output;
    }
    
    @POST
    @Path( "/editUser" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void editSensordata (final Users user)
    {
    	dbs_users.updateUser(user);
    	log.debug( "User wurde erfolgreich bearbeitet." );
    }
    
    
  

    @POST
    @Path( "/deleteUser" )
    @Consumes( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML } )
    public void deleteSensordata (final Users user)
    {
    	dbs_users.deleteUser(user);
    	log.debug( "User wurde erfolgreich geloescht." );
    }
    
    
    
    
   
}
