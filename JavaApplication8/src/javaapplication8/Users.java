package javaapplication8;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
User Class
@author Ryan Drysdale
 */

public class Users {
    
        private static int userId;
        private static String userName;
        private static String password;
        private static LocalDateTime createDate;
        private static String createdBy;
        private static Timestamp lastUpdate;
        private static String lastUpdatedBy;
        private static int countryId;
        
        /**
        The Users Constructor
     
        @param userId the user ID
        @param userName the user name
        @param password the password
        @param createDate the creation date
        @param createdBy the creator user name
        @param lastUpdate the date of last update
        @param lastUpdatedBy the updater username
        @param countryId the country ID
     */
        
        public Users(int userId, String userName, String password,
            LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId)
        {
            this.userId = userId;
            this.userName = userName;
            this.password = password;
            this.createDate = createDate;
            this.createdBy = createdBy;
            this.lastUpdate = lastUpdate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.countryId = countryId;
        }        
        
        
        /**
        Getter for userId
        @return userId of the User
        */
        
        public static int getUserId()
        {
            return userId;
        }  
        
        /**
        Setter for UserId
        @param userId of the User
        */
        
        public static void setUserId(int userId)
        {
            Users.userId = userId;
        }
        
        /**
        Getter for userName
        @return userName of the User
        */
        
        public static String getUserName()
        {
            return userName;
        }   
        
        /**
        Setter for userName
        @param userName of the user
        */
        
        public static void setUserName(String userName)
        {
            Users.userName = userName;
        }  
        
        /**
        getter for password 
        @return password 
        */
        
        public static String getPassword()
        {
            return password;
        } 
        
        /**
        Setter for password 
        @param password for the password 
        */
        
        public static void setPassword(String password)
        {
            Users.password = password;
        }
        
        /**
        Getter for createDate
        @return createDate 
        */
        
        public static LocalDateTime getCreateDate()
        {
            return createDate;
        }  
        
        /**
        Setter for createDate
        @param createDate for the create date
        */
        
        public static void setCreateDate(LocalDateTime createDate)
        {
            Users.createDate = createDate;
        }  
        
        /**
        Getter for createdBy
        @return createdBy
        */
        
        public static String getCreatedBy()
        {
            return createdBy;
        } 
        
        /**
        Setter for createdBy 
        @param createdBy for who created it 
        */
        
        public static void setCreatedBy(String createdBy)
        {
            Users.createdBy = createdBy;
        } 
        
        /**
        Getter for lastUpdate
        @return lastUpdate
        */
        
        public static Timestamp getLastUpdate()
        {
            return lastUpdate;
        } 
        
        /**
        Setter for lastUpdate
        @param lastUpdate for the last time Customer was updated 
        */
        
        public static void setLastUpdate(Timestamp lastUpdate)
        {
            Users.lastUpdate = lastUpdate;
        }  
        
        /**
        Getter for lastUpdatedBy 
        @return lastUpdatedBy
        */
        
        public static String getLastUpdatedBy()
        {
            return lastUpdatedBy;
        } 
        
        /**
        Setter for lastUpdatedBy 
        @param lastUpdatedBy for who last updated the customer file 
        */
        
        public static void setLastUpdatedBy(String lastUpdatedBy)
        {
            Users.lastUpdatedBy = lastUpdatedBy;
        }  
        
        /**
        Getter for countryId
        @return countryId
        */
        
        public static int getCountryId()
        {
            return countryId;
        }  
        
        /**
        Setter for countryId 
        @param countryId for the Country ID 
        */
        
        public static void setCountryId(int countryId)
        {
            Users.countryId = countryId;
        }        
}
