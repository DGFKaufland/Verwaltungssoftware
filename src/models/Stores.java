package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement

public class Stores implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String country_code;
    private String store_number;
    private String opening_hours_from;
    private String opening_hours_to;
    private int store_manager_id;
    private String store_manager_name;
    private int sales_manager_id;
    private String sales_manager_name;
    private int store_leitstand_id;
    private String store_leitstand_name;
    

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Stores()
    {
    }

    public Stores(final Integer ID, final String country_code, final String store_number, final String opening_hours_from,
    		final String opening_hours_to, final Integer store_manager_id, final String store_manager_name,
    		final Integer sales_manager_id, final String sales_manager_name, final Integer store_leitstand_id, 
    		final String store_leitstand_name)
    {
        this.setID(ID);
        this.setCountry_code(country_code);
        this.setStore_number(store_number);
        this.setOpening_hours_from(opening_hours_from);
        this.setOpening_hours_to(opening_hours_to);
        this.setStore_manager_id(store_manager_id);
        this.setStore_manager_name(store_manager_name);        
        this.setSales_manager_id(sales_manager_id);
        this.setSales_manager_name(sales_manager_name);
        this.setStore_leitstand_id(store_leitstand_id);
        this.setStore_leitstand_name(store_leitstand_name);
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public String getCountry_code() {
		return country_code;
	}


	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}


	public String getStore_number() {
		return store_number;
	}


	public void setStore_number(String store_number) {
		this.store_number = store_number;
	}


	public String getOpening_hours_from() {
		return opening_hours_from;
	}


	public void setOpening_hours_from(String opening_hours_from) {
		this.opening_hours_from = opening_hours_from;
	}


	public String getOpening_hours_to() {
		return opening_hours_to;
	}


	public void setOpening_hours_to(String opening_hours_to) {
		this.opening_hours_to = opening_hours_to;
	}


	public int getStore_manager_id() {
		return store_manager_id;
	}


	public void setStore_manager_id(int store_manager_id) {
		this.store_manager_id = store_manager_id;
	}


	public String getStore_manager_name() {
		return store_manager_name;
	}


	public void setStore_manager_name(String store_manager_name) {
		this.store_manager_name = store_manager_name;
	}


	public int getSales_manager_id() {
		return sales_manager_id;
	}


	public void setSales_manager_id(int sales_manager_id) {
		this.sales_manager_id = sales_manager_id;
	}


	public String getSales_manager_name() {
		return sales_manager_name;
	}


	public void setSales_manager_name(String sales_manager_name) {
		this.sales_manager_name = sales_manager_name;
	}

	public String getStore_leitstand_name() {
		return store_leitstand_name;
	}

	public void setStore_leitstand_name(String store_leitstand_name) {
		this.store_leitstand_name = store_leitstand_name;
	}

	public int getStore_leitstand_id() {
		return store_leitstand_id;
	}

	public void setStore_leitstand_id(int store_leitstand_id) {
		this.store_leitstand_id = store_leitstand_id;
	}

   

}
