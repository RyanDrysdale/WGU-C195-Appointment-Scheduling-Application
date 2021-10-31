package Controller;
import Utils.DBConnection;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javaapplication8.Countries;
import javaapplication8.FirstLevelDivisions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class UpdateCustomerController implements Initializable {
   
    @FXML private TextField userIdText;
    @FXML private TextField nameText;
    @FXML private TextField addressText;
    @FXML private ComboBox<FirstLevelDivisions> stateComboBox;
    @FXML private ComboBox<Countries> countryComboBox;
    @FXML private TextField zipCodeText;
    @FXML private TextField phoneNumberText;
    ObservableList<Countries> countryList = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();
    private static final Connection conn = DBConnection.getConnection();
    PreparedStatement ps;
   
    
    int divisionId = -1;
    private int getDivisionId(String temp)
    {
        for(FirstLevelDivisions look : firstLevelDivisionsList)
        {
           if(look.getDivision().trim().toLowerCase().contains(temp.trim().toLowerCase())) 
           {
               return look.getDivisionId();
           }    
        }  
        return -1;
    }
    
    /**
     * pulls the data from the database to populate the country combo box
     * @throws SQLException 
     */
    
    @FXML private void countryComboBox() throws SQLException
    {
        ObservableList<String> countryCombo = FXCollections.observableArrayList();
        try
        {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM countries");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                
                //countryCombo.add(country);
                
                countryList.add(new Countries(countryId, country, createDate, createdBy, lastUpdate, lastUpdatedBy));
                        
            }   
            countryComboBox.setItems(countryList);
        }    
        catch (SQLException ex) {
        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    /**
     * pulls the states from the database to populate the state combo box
     * @param countryId
     * @throws SQLException 
     */
    
    @FXML private void firstLevelCombo(int countryId) throws SQLException
    {
        firstLevelDivisionsList.clear();
        ObservableList<String> stateCombo = FXCollections.observableArrayList();
        try
        {
            ps = conn.prepareStatement(
                "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ? "
                + "ORDER By Division");
                
            ps.setInt(1, countryId);
                
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int divisionId = rs.getInt("Division_ID"); 
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryIdx = rs.getInt("COUNTRY_ID");
                
                stateCombo.add(division);
                
                firstLevelDivisionsList.add(new FirstLevelDivisions(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryIdx));
                
            }  
            
            stateComboBox.setItems(firstLevelDivisionsList);
        } 
        catch (SQLException ex) {
        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * updates the customer in the database while providing several customized alerts to protect data integrity. 
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    
    @FXML void saveButtonPushed(ActionEvent event) throws SQLException, IOException
    {
        try{
            
            String customerName = nameText.getText();
            String address = addressText.getText();
            String zipCode = zipCodeText.getText();
            String phoneNumber = phoneNumberText.getText();
            String lastUpdatedBy = "Test"; //problem 
           
            if(stateComboBox.getValue() == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("A State Needs To Be Selected");
                alert.show();
                return;
            }
            if(stateComboBox.getValue() != null);
            {
                divisionId = ( stateComboBox.getValue().getDivisionId()); //Check divisionId = getDivisionId(stateComboBox.getValue());
            }    
            if(customerName.isEmpty())
            {    
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Name Can Not Be Empty");
                alert.show();
                return;
            }
            if(address.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Address Can Not Be Empty");
                alert.show();
                return;
            }    
            if (countryComboBox.getValue() == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("A Country Needs To Be Selected"); 
               alert.show();
               return;
            }
            if(zipCode.isEmpty())
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Zip Code Can Not Be Blank");
               alert.show();
               return;
            }  
            if(phoneNumber.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Phone Number Can Not Be Blank");
               alert.show();
               return;
            } 
   
                PreparedStatement ps = conn.prepareStatement("UPDATE customers "
                  + "Set Customer_Name = ?, Address = ?, Postal_Code = ?, "
                  + "Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, "
                  + "Division_ID =? "
                  + "WHERE Customer_ID = ?");

                ps.setString(1, customerName);
                ps.setString(2, address);
                ps.setString(3, zipCode);
                ps.setString(4, phoneNumber);
                ps.setString(5, lastUpdatedBy);
                ps.setInt(6, divisionId);
                ps.setInt(7, selectedCustomerId);

                int result = ps.executeUpdate();
                if(result == 1)
                {
                    System.out.println("\n" + customerName + " updated.\n");
                    Parent parent = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    System.out.println("\nSave error\n");
                }
        }
        catch (SQLException ex) 
        {
          Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    int selectedCustomerId = CustomersController.getSelectedCustomer().getCustomerId();
    
    /**
     * brings the customer back to the customer screen. 
     * @param event customer button pushed. 
     * @throws IOException 
     */
    
    @FXML void cancelButtonPushed(ActionEvent event) throws IOException 
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    
    /**
     * filters the states based off which country was selected. 
     * @param event country combo box selection made 
     * @throws SQLException 
     */
    
   @FXML void countryComboBoxPushed(ActionEvent event) throws SQLException
    {
        firstLevelCombo(countryComboBox.getValue().getCountryId());    
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         *sets the fields to the selected customer for updating.  
         */
        userIdText.setText("" + (CustomersController.getSelectedCustomer().getCustomerId()));
        nameText.setText(CustomersController.getSelectedCustomer().getCustomerName());
        addressText.setText(CustomersController.getSelectedCustomer().getAddress());
        zipCodeText.setText(CustomersController.getSelectedCustomer().getPostalCode());
        phoneNumberText.setText(CustomersController.getSelectedCustomer().getPhone());
        
        //stateComboBox.setValue(CustomersController.getSelectedCustomer().getDivisionName());
        //countryComboBox.setValue(CustomersController.getSelectedCustomer().getCountryName());
        
        try{
        firstLevelCombo(CustomersController.getSelectedCustomer().getCountryId());
        }
        catch (SQLException ex)
        {
          Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        countryComboBox();
        } 
        catch (SQLException ex) 
        {
          Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Countries C: countryComboBox.getItems())
        {
            if(C.getCountryId() == CustomersController.getSelectedCustomer().getCountryId())
            {
                countryComboBox.setValue(C);
            }    
        } 
        
        for(FirstLevelDivisions D: stateComboBox.getItems())
        {
            if(D.getDivisionId() == CustomersController.getSelectedCustomer().getDivisionId())
            {
                stateComboBox.setValue(D);
            }    
        }
            
        
    }   

   
    
}
