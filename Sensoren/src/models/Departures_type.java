package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement
public class Departures_type implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String country;
    private String description;
    private int limit_value;

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Departures_type()
    {
    }


    public Departures_type(final Integer ID, final String country, final String description, final Integer limit_value)
    {
        this.setID(ID);
        this.setCountry(country);
        this.setDescription(description);
        this.setLimit_value(limit_value);
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getLimit_value() {
		return limit_value;
	}


	public void setLimit_value(int limit_value) {
		this.limit_value = limit_value;
	}

}
