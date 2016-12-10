package models;

import java.io.Serializable;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement

public class Sensordata implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String logical_sensor_id;
    private String value;
    private Timestamp timestamp;
    

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Sensordata()
    {
    }


    public Sensordata(final Integer ID, final String logical_sensor_id, final String value, final Timestamp timestamp)
    {
        this.setID(ID);
        this.setLogical_sensor_id(logical_sensor_id);
        this.setValue(value);
        this.setTimestamp(timestamp);
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLogical_sensor_id() {
		return logical_sensor_id;
	}


	public void setLogical_sensor_id(String logical_sensor_id) {
		this.logical_sensor_id = logical_sensor_id;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}




	




}
