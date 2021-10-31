package javaapplication8;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Appointments Class
 * @author Ryan Drysdale
 */
public class Appointments {
    
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;
    private String customerName;
    
    /**
     * Appointments Constructor
     * @param appointmentId for the appointment ID
     * @param title for the appointment title 
     * @param description for the description of the appointment 
     * @param location for the location of the appointment 
     * @param type for the type of appointment
     * @param start for the start time of the appointment
     * @param end for the end time 
     * @param createDate for the date the appointment was created
     * @param createdBy who created the appointment
     * @param lastUpdate when was the appointment last updated
     * @param lastUpdatedBy who updated it?
     * @param customerId the customer Id of the customer in the appointment 
     * @param userId the user id of the person viewing the appointment
     * @param contactId the contact Id of the contact for the appointment
     * @param contactName the name of the contact
     * @param customerName the name of the customer
     */
    
    public Appointments(int appointmentId, String title, String description,
        String location, String type, LocalDateTime start, LocalDateTime end,
        LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
        int customerId, int userId, int contactId, String contactName, String customerName)
            
            {
            
            this.appointmentId = appointmentId;
            this.title = title;
            this.description = description;
            this.location = location;
            this.type = type;
            this.start = start;
            this.end = end;
            this.createDate = createDate;
            this.createdBy = createdBy;
            this.lastUpdate = lastUpdate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.customerId = customerId;
            this.userId = userId;
            this.contactId = contactId;
            this.contactName = contactName;
            this.customerName = customerName;
                    
        
            }
    /**
     * Constructor for login alerts
     * @param appointmentID for the ID of the appointment
     * @param start for the start time of the appointment 
     */
    
    public Appointments(int appointmentID, LocalDateTime start)
    {
        this.start = start;
        this.appointmentId = appointmentId;
    }
   
    /**
     * Getter for appointmentId
     * @return appointmentId
     */
    public int getAppointmentId() 
    {
         return appointmentId;
    }
    
    /**
     * Setter for appointmentId
     * @param appointmentId for the ID of the appointment
     */
    
    public void setAppointmentId(int appointmentId)
    {
        this.appointmentId = appointmentId;
    }
    
    /**
     * Getter for title
     * @return title 
     */
    
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Setter for title
     * @param title for the title of the appointment 
     */
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * Getter for description
     * @return description
     */
    
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Setter for description
     * @param description for the description
     */
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Getter for location
     * @return location
     */
    
    public String getLocation()
    {
        return location;
    }
    
    /**
     * Setter for location
     * @param location for the location  
     */
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    /**
     * Getter for type
     * @return type 
     */
    
    public String getType()
    {
        return type;
    }
    
    /**
     * Setter for type
     * @param type for the type of appointment
     */
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * Getter for start time of appointments
     * @return start
     */
    
    public LocalDateTime getStart()
    {
        return start;
    }
    
    /**
     * Setter for start time of appointments
     * @param start for the start time of the appointment
     */
    
    public void setStart(LocalDateTime start)
    {
        this.start = start;
    }
    
    /**
     * Getter for end time of appointments
     * @return end
     */
    
    public LocalDateTime getEnd()
    {
        return end;
    }
    
    /**
     * Setter for end time of appointments
     * @param end for the end time of the appointment
     */
    
    public void setEnd(LocalDateTime end)
    {
        this.end = end;
    }
    
    /**
     * Getter for createDate of appointments
     * @return createDate
     */
    
    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    
    /**
     * Setter for createDate of appointments
     * @param createDate for the date the appointment was created
     */
    
    public void setCreateDate(LocalDateTime createDate)
    {
        this.createDate = createDate;
    }
    
    /**
     * Getter for createdBy who created the appointment
     * @return createdBy
     */
    
    public String getCreatedBy()
    {
        return createdBy;
    }
    
    /**
     * Setter for createdBy
     * @param createdBy for who created the appointment
     */
    
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    
    /**
     * Getter for lastUpdate
     * @return lastUpdate 
     */
    
    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }
    
    /**
     * Setter for lastUpdate of the appointment
     * @param lastUpdate for the last time the appointment was updated
     */
    
    public void setLastUpdate(Timestamp lastUpdate)
    {
        this.lastUpdate = lastUpdate;
    }
    
    /**
     * Getter for lastUpdatedBy
     * @return lastUpdatedBy
     */
    
    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    
    /**
     * Setter for lastUpdatedBy
     * @param lastUpdatedBy for who updated the appointment last 
     */
    
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Getter for customerId
     * @return customerId
     */
    
    public int getCustomerId()
    {
        return customerId;
    }
    
    /**
     * Setter for customerId
     * @param customerId for the ID of the customer in the appointment 
     */
    
    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }
    
    /**
     * Getter for userId
     * @return userId
     */
    
    public int getUserId()
    {
        return userId;
    }
    
    /**
     * Setter for userId
     * @param userId fo the user id of the appointment 
     */
    
    public void setUserId(int userId)
    {
        this.userId = userId;
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
     * @param contactId for the contactID of the contact for the appointment 
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
     * @param contactName for the contact name of the cotact for the appointment 
     */
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    /**
     * Getter for customerName
     * @return customerName
     */
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    /**
     * Setter for customerName
     * @param customerName for the customer name 
     */
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
}
