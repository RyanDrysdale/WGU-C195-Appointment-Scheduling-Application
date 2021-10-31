package javaapplication8;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * FirstLevelDivisions Class
 * @author Ryan Drysdale
 */


public class FirstLevelDivisions {
    
    private int divisionId;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId;
    
    /**
     * Constructor 
     * @param divisionId for the Division Id 
     * @param division for the Division 
     * @param createDate for when it was created 
     * @param createdBy for who created 
     * @param lastUpdate when it was last updated 
     * @param lastUpdatedBy who last updated it 
     * @param countryId for the country ID 
     */
    
    public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate,
        String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId)
    {
        this.divisionId = divisionId;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryId = countryId;
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
     * @param divisionId for the Division Id 
     */
    
    public void setDivisionId(int divisionId)
    {
        this.divisionId = divisionId;
    }        
    
    /**
     * Getter for division
     * @return division
     */
    
    public String getDivision()
    {
        return division;
    }  
    
    /**
     * Setter for division
     * @param division for the Division 
     */
    
    public void setDivision(String division)
    {
        this.division = division;
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
     * @param createDate for the Create Date
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
     * @param lastUpdate for when it was last updated
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
     * @param lastUpdatedBy who last updated it 
     */
    
    public void setLastUpdatedby(String lastUpdatedBy)
    {
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
     * @param countryId for Country ID 
     */
    
    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }    

    @Override
    public String toString()
    {
        return division;
    }        
}
