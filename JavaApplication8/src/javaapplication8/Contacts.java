package javaapplication8;

/**
 * Class for Contacts
 * @author Ryan Drysdale
 */

public class Contacts {
    
    private int contactId;
    private String contactName;
    private String email;
    
    /**
     * Contacts Constructor
     * @param contactId fo the Contact ID
     * @param contactName for the Contact Name 
     * @param email for the Contact Email 
     */
    
    public Contacts(int contactId, String contactName, String email)
    {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
    
    /**
     * Getter for contactId
     * @return contactId
     */
    
    public int getContactId()
    {
        return contactId;
    }
    
    /**
     * Setter for contactId
     * @param contactId for the Contact ID 
     */
    
    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }        
    
    /**
     * Getter for contactName
     * @return contactName
     */
    
    public String getContactName()
    {
        return contactName;
    }        
    
    /**
     * Setter for contactName
     * @param contactName for the Contact Name 
     */
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }        
    
    /**
     * Getter for email
     * @return email
     */
    
    public String getEmail()
    {
        return email;
    }        
    
    /**
     * Setter for email
     * @param email for the Contact Email  
     */
    
    public void setEmail(String email)
    {
        this.email = email;
    }        
}
