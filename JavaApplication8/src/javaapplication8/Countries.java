package javaapplication8;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Countries Class
 * @author Ryan Drysdale
 */

public class Countries {
    
    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    
    /**
     * Countries Constructor
     * @param countryId for the Country ID
     * @param country for the Country
     * @param createDate for the Create Date
     * @param createdBy for the Created By 
     * @param lastUpdate for The last time it was updated
     * @param lastUpdatedBy for who last updated it
     */
    
    public Countries(int countryId, String country, LocalDateTime createDate,
        String createdBy, Timestamp lastUpdate, String lastUpdatedBy)
    {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }     
    
    /**
     * Getter for countryId
     * @return countryId
     */
    
    public int getCountryId()
    {
        return countryId;
    }        
    
    /**
     * Setter for countryId
     * @param countryId for the Country ID
     */
    
    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }        
    
    /**
     * Getter for country
     * @return country
     */
    
    public String getCountry()
    {
        return country;
    }        
    
    /**
     * Setter for country
     * @param country for the Country 
     */
    
    public void setCountry(String country)
    {
        this.country = country;
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
     * @param createDate for the date it was created
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
     * Setter for createdBy
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
     * @param lastUpdate for the last time it was updated
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
     * @param lastUpdatedBy for who last updated it
     */
    
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }        
    
    @Override
    
    public String toString()
    {
        return country;
    }        
}

