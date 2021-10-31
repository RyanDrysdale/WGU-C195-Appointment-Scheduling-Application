package Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javaapplication8.Countries;
import javaapplication8.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Utils.DBConnection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication8.Users;
import javafx.scene.control.Alert;
import java.sql.Statement;
import static javaapplication8.Users.getCountryId;
/**
 * FXML Controller class
 *
 * Ryan Drysdale
 */
public class AddCustomerController implements Initializable {
    
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private static Connection conn = DBConnection.getConnection();
    private Users userNow;
    ObservableList<Countries> countryList = FXCollections.observableArrayList();
    ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();
    
    @FXML TextField userIdText;
    @FXML TextField nameText;
    @FXML TextField addressText;
    @FXML private ComboBox<FirstLevelDivisions> stateComboBox;
    @FXML private ComboBox<Countries> countryComboBox;
    @FXML TextField zipCodeText;
    @FXML TextField phoneNumberText;
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
     * populates the first level combo box based on the selection made in the country combo box. 
     * @param event when a country is selected in the country combo box. 
     * @throws SQLException 
     */
    
    @FXML void countryComboBoxPushed(ActionEvent event) throws SQLException
    {
        firstLevelCombo(countryComboBox.getValue().getCountryId());    
    }
    
    /**
     * pulls all the countries from the database to populate the country combo box. 
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
     * Populates the state combo box with filtered state/providence based off of country ID. 
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
     * brings the user back to the customer screen. 
     * @param event cancel button is pushed. 
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
     * add the customer to the database and brings the user back to the customer screen.  
     * several customized alerts to prevent data integrity with a success or failure notification. 
     * @param event when save button is pushed. 
     * @throws SQLException
     * @throws IOException 
     */
    
    @FXML void saveButtonPushed(ActionEvent event) throws SQLException, IOException
    {
        try
        {
            String customerName = nameText.getText();
            String address = addressText.getText();
            String zipCode = zipCodeText.getText();
            String phoneNumber = phoneNumberText.getText();
            String lastUpdatedBy = "Test"; //problem 
            
            if(stateComboBox.getSelectionModel().getSelectedItem() == null || stateComboBox.getValue() == null)
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
            if(customerName.isBlank())
            {    
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Name Can Not Be Empty");
                alert.show();
                return;
            }
            if(address.isBlank())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Address Can Not Be Empty");
                alert.show();
                return;
            }    
            //System.out.println("....." + stateComboBox.getSelectionModel().getSelectedItem());
            
            if( countryComboBox.getValue() == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("A Country Needs To Be Selected"); 
               alert.show();
               return;
            }
            if(zipCode.isBlank())
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Zip Code Can Not Be Blank");
               alert.show();
               return;
            }  
            if(phoneNumber.isBlank())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Phone Number Can Not Be Blank");
               alert.show();
               return;
            }    
            
            {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO customers"
                + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, "
                + "Created_By, Last_Update, Last_Updated_By, Division_ID) "
                + "VALUES(?, ?, ?, ?, NOW(), ?, NOW(), ?, ?)",
                Statement.RETURN_GENERATED_KEYS); 
                
                ps.setString(1, customerName);
                ps.setString(2, address);
                ps.setString(3, zipCode);
                ps.setString(4, phoneNumber);
                ps.setString(5, lastUpdatedBy);
                ps.setString(6, lastUpdatedBy);
                ps.setInt(7, divisionId);
                
                
                int result = ps.executeUpdate();
                if(result > 0)
                {
                    System.out.println("\n" + customerName + " successfully added\n");
                    Parent parent = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } 
                else
                {
                    System.out.println("\n" + "Add Customer Did Not Succeed.\n");
                }    
                
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                {
                    int autoKey = rs.getInt(1);
                    System.out.println("Generated Customer ID: " + autoKey);
                }    
            }     
        }
        
        catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     * populates the country and state combo boxes. 
     * 
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        userIdText.setPromptText("Generated Automatically");
            try{

                
                
        firstLevelCombo(getCountryId());
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
        
       
            
        
    }     
    
}  
    

