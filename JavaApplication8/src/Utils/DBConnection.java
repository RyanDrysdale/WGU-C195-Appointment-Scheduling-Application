package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DBConnection {
    
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ08e7K";
    //jdbc database url 
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    // Driver interface reference 
    private static final String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null; 
    
    private static final String userName = "U08e7K";
    private static String password = "53689266654";
    
    public static Connection startConnection() 
    {
        try{
            Class.forName(mysqlJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection Successfull");  
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void closeConnection() throws SQLException
    {
        try{
        conn.close();
        System.out.println("Connection closed!");
    }
    catch(SQLException e)
    {
        System.out.println("Error: " + e.getMessage());
    }
  }
}

