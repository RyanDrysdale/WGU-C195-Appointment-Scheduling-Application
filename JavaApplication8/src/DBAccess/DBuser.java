/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBAccess;

import Utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author 13149
 */
public class DBuser {
    public static int validateUser (String userName, String password) {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        try {
            PreparedStatement PS = DBConnection.getConnection().prepareStatement(sql);
            PS.setString(1, userName);
            PS.setString(2, password);
            ResultSet RS = PS.executeQuery();
            
            while(RS.next()){
                int userId = RS.getInt("User_ID");
                return userId;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        
        
        return 0;
    }
    
}
