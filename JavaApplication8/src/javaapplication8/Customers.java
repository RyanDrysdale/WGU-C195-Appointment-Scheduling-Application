package javaapplication8;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Customers Class
 * @author Ryan Drysdale
 */


public class Customers {
    
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String divisionName;
    private int countryId;
    private String countryName;
    private SimpleIntegerProperty anotherId;
    
    /**
     * Customers Constructor
     * @param customerId for the Customer ID
     * @param customerName for the Customer Name
     * @param address for the Customer Address
     * @param postalCode for the Customer zip code
     * @param phone for the customers phone
     * @param createDate for crate date of customer file 
     * @param createdBy for who created customer file 
     * @param lastUpdate for last time the file was updated
     * @param lastUpdatedBy who last updated it 
     * @param divisionId for the division id of the customer
     * @param divisionName for the name of that division 
     * @param countryName  for the country name for the customer
     * @param countryId for the Id of the country 
     */
    
    public Customers(int customerId, String customerName, String address,
        String postalCode, String phone, LocalDateTime createDate, String createdBy,
        Timestamp lastUpdate, String lastUpdatedBy, int divisionId, String divisionName, int countryId, String countryName)
        
    {
        
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
        this.countryName = countryName;
        this.anotherId = new SimpleIntegerProperty(customerId);
        
    }
    
    public SimpleIntegerProperty getAnotherId()
    {
        return anotherId;
    }        
    
    public int getCountryId()
    {
        return countryId;
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
     * @param customerId for Customer ID
     */
    
    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
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
     * @param customerName for Customer Name 
     */
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    /**
     * Getter for address
     * @return address
     */
    
    public String getAddress()
    {
        return address;
    }
    
    /**
     * Setter for address
     * @param address for customer address
     */
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    /**
     * Getter for zipCode
     * @return postalCode
     */
    
    public String getPostalCode()
    {
        return postalCode;
    }
    
    /**
     * Setter for postalCode
     * @param postalCode for customer postal code 
     */
    
    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
    
    /**
     * Getter for phone
     * @return phone
     */
    
    public String getPhone()
    {
        return phone;
    }
    
    /**
     * Setter for phone
     * @param phone for customer phone 
     */
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    /**
     * Getter for createDate
     * @return createDate
     */
    
    public LocalDateTime getCreateDate()
    {
        return createDate;
    }
    
    /**
     * Setter for createDate
     * @param createDate for create date of customer file 
     */
    
    public void setCreateDate(LocalDateTime createDate)
    {
        this.createDate = createDate;
    }
    
    /**
     * Getter for createdBy
     * @return createdBy
     */
    
    public String getCreatedBy()
    {
        return createdBy;
    }
    
    /**
     * Setter for createdBy who created customer file 
     * @param createdBy for who created it 
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
     * Setter for lastUpdate 
     * @param lastUpdate last time customer file was updated 
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
     * @param lastUpdatedBy who last updated the customer file 
     */
    
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    /**
     * Getter for divisionId
     * @return divisionId
     */
    
    public int getDivisionId()
    {
        return divisionId;
    }
    
    /**
     * Setter for divisionId
     * @param divisionId for the division id of the customer 
     */
    
    public void setDivisionId(int divisionId)
    {
        this.divisionId = divisionId;
    }
    
    /**
     * Getter for divisionName
     * @return divisionName
     */
    
    public String getDivisionName()
    {
        return divisionName;
    }
    
    /**
     * Setter for divisionName
     * @param divisionName for the division name of the customer 
     */
    
    public void setDivisionName(String divisionName)
    {
        this.divisionName = divisionName;
    }
    
    /**
     * Getter for countryName
     * @return countryName
     */
    
    public String getCountryName()
    {
        return countryName;
    }
    
    /**
     * Setter for countryName
     * @param countryName for the country name of the customer
     */
    
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }
    
}
