/*
 * Datei erstellt am 16.12.2014 von Fabian Heuberger.
 *
 *
 */

package controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import models.Topics;

//import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import database.DBStatements_Topics;

/**
 *
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@Path( "/topics" )
public class Topic_Controller
{
	//private static Logger log = Logger.getLogger( Topic_Controller.class.getName() );
    private static JSONObject jsonObject;

    private DBStatements_Topics dbs_topics = new DBStatements_Topics();

   
    @GET
    @Path( "/getAllTopics" )
    @Produces( "application/json" )
    public Response getAllSensordata() throws JSONException
    {
        jsonObject = new JSONObject();

        List<Topics> topics = dbs_topics.getAllTopics();

        jsonObject.put( "getAllTopics", topics );
        String result = "" + jsonObject;
        return Response.status( 200 ).entity( result ).build();
    }
}
