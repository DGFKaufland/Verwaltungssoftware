package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement
public class Sensors implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private int store_id;
    private int departure_id;
    private String manufacturer;    
    private String sensor_type;
    private String hardware_id;
    private String logical_id;    
    private String status;
    private String gateway;
    
    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Sensors()
    {
    }


    public Sensors(final Integer ID, final Integer departure_id, final Integer store_id, final String manufacturer, 
    		final String sensor_type, final String hardware_id, final String logical_id, final String status, final String gateway)
    {
        this.setID(ID);
        this.setStore_id(store_id);
        this.setDeparture_id(departure_id);
        this.setManufacturer(manufacturer);
        this.setSensor_type(sensor_type);
        this.setHardware_id(hardware_id);
        this.setLogical_id(logical_id);
        this.setStatus(status);
        this.setGateway(gateway);
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public int getDeparture_id() {
		return departure_id;
	}


	public void setDeparture_id(int departure_id) {
		this.departure_id = departure_id;
	}


	public String getManufacturer() {
		return manufacturer;
	}


	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}


	public String getSensor_type() {
		return sensor_type;
	}


	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}


	public String getHardware_id() {
		return hardware_id;
	}


	public void setHardware_id(String hardware_id) {
		this.hardware_id = hardware_id;
	}


	public String getLogical_id() {
		return logical_id;
	}


	public void setLogical_id(String logical_id) {
		this.logical_id = logical_id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getStore_id() {
		return store_id;
	}


	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}


	public String getGateway() {
		return gateway;
	}


	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

}
