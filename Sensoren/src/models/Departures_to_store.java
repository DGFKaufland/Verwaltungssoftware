package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement
public class Departures_to_store implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String country;
    private int store_id;
    private int departure_type_id;
    private String departure_type_name;
    private int wbl_id;
    private String wbl_name;
    private String server;
    

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Departures_to_store()
    {
    }


    public Departures_to_store(final String country, final Integer store_id, final Integer ID, 
    		final String wbl_name, final Integer wbl_id, final Integer departure_type_id, final String departure_type_name, final String server)
    {
    	this.setCountry(country);
        this.setID(ID);
        this.setStore_id(store_id);
        this.setDeparture_type_id(departure_type_id);
        this.setDeparture_type_name(departure_type_name);
        this.setWbl_id(wbl_id);
        this.setWbl_name(wbl_name);
        this.setServer(server);

    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public int getStore_id() {
		return store_id;
	}


	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getWbl_id() {
		return wbl_id;
	}


	public void setWbl_id(int wbl_id) {
		this.wbl_id = wbl_id;
	}

	public String getWbl_name() {
		return wbl_name;
	}


	public void setWbl_name(String wbl_name) {
		this.wbl_name = wbl_name;
	}


	public int getDeparture_type_id() {
		return departure_type_id;
	}


	public void setDeparture_type_id(int departure_type_id) {
		this.departure_type_id = departure_type_id;
	}


	public String getDeparture_type_name() {
		return departure_type_name;
	}


	public void setDeparture_type_name(String departure_type_name) {
		this.departure_type_name = departure_type_name;
	}


	public String getServer() {
		return server;
	}


	public void setServer(String server) {
		this.server = server;
	}






}
