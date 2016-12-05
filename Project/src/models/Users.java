package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Fabian Heuberger
 * @version 1.0
 *
 */
@XmlRootElement
public class Users implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String country;
    private String salutation;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String role;
    

    /**
     * Leerer Konstruktor der Klasse Sensoren.
     */
    public Users()
    {
    }


    public Users(final String salutation, final String firstname, final String lastname, 
    		final String email, final String phone, final String role, final Integer ID, final String country )
    {
        this.setID(ID);
        this.setSalutation(salutation);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setPhone(phone);
        this.setRole(role);
        this.setCountry(country);
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}


	public String getSalutation() {
		return salutation;
	}


	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	
   

}
