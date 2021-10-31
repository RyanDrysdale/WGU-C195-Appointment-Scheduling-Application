package javaapplication8;

/**
 *
 * @author Ryan Drysdale
 */
public class Report {
    
    private String month;
    private String type;
    private int total;
    
    /**
    Report Constructor
    @param month for month view
    @param type for type view 
    @param total for total view 
    */
    
    public Report(String month, String type, int total)
    {
        this.month = month;
        this.type = type;
        this.total = total;
        
    }
    
    /**
    Getter for type 
    @return type
    */
    
    public String getType()
    {
        return type;
    }  
    
    /**
    Setter for type    
    @param type for the Type of appointment 
    */
    
    public void setType(String type)
    {
        this.type = type;
    } 
    
    /**
    Getter for month 
    @return month
    */
    
    public String getMonth()
    {
        return month;
    }  
    
    /**
    Setter for month
    @param month for the month view 
    */
    
    public void setMonth(String month)
    {
        this.month = month;
    } 
    
    /**
    Getter for total
    @return total
    */
    
    public int getTotal()
    {
        return total;
    } 
    
    /**
    Setter for total  
    @param total appointments 
    */
    
    public void setTotal(int total)
    {
        this.total = total;
    }        
}
