package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement

public class Servers implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private int store_id;
    private int departure_id;
    private String name;
    

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Servers()
    {
    }


    public Servers(final Integer ID, final Integer store_id, final Integer departure_id, final String name)
    {
        this.setID(ID);
        this.setStore_id(store_id);
        this.setDeparture_id(departure_id);
        this.setName(name);
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


	public int getDeparture_id() {
		return departure_id;
	}


	public void setDeparture_id(int departure_id) {
		this.departure_id = departure_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}




}
