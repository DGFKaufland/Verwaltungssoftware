package models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Jonas Fritzsch
 * @version 1.0
 *
 */
@XmlRootElement

public class Topics implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int ID;
    private String LSID;
    private String Topic;
    
    public Topics() { }

    public Topics(final String aLSID, final String aTopic)
    {
        this.setID(ID);
        this.setLSID(aLSID);
        this.setTopic(aTopic);
    }

	public int getID() { return ID;	}

	public void setID(int iD) {	this.ID = iD; }

	public String getLSID() { return LSID; }

	public void setLSID(String aLSID) { this.LSID = aLSID; }

	public String getTopic() { return Topic; }

	public void setTopic(String aTopic) { this.Topic = aTopic; }
}
