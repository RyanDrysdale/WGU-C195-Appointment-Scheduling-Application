package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javaapplication8.Customers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import Utils.DBConnection;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Timestamp;
import java.util.Optional;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
 
public class CustomersController implements Initializable {
    
    private static Connection conn = DBConnection.getConnection();
    
    ObservableList<Customers> customerList = FXCollections.observableArrayList();
    
    @FXML private TableView <Customers> customerTable;
    @FXML private TableColumn <Customers, String> phoneNumberColumn;
    @FXML private TableColumn <Customers, String> customerNameColumn;
    @FXML private TableColumn <Customers, String> addressColumn;
    @FXML private TableColumn <Customers, Integer> idColumn;
    @FXML private TableColumn <Customers, String> divisionColumn;
    @FXML private TableColumn <Customers, String> zipCodeColumn;
    
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    
    //Attempt at Lambda Expression
    /*int customerId = customer.getId();
    ObservableList<Customer> allCustomers = Customers.getAllCustomers();
    
    ObservableList<Customer> filteredCustomer = allCustomers.filtered( ->{
        if(c.getCustomerId() == customerId)
            return true;
        return false;
    });
    
    customerTable.setItems(filteredCustomer); */
    
    /**
     * 
     */
    
    private static Customers selectedCustomer;
    public static Customers getSelectedCustomer(){
        return selectedCustomer;
    }
    
    /**
     * This method removes the customer and all associated appointments.
     * This will give a customized alert asking if you are sure 
     * you want to delete this customer and all of their appointments.
     * @param event deleteButtonPushed
     * @throws IOException 
     */ 
    
    @FXML void deleteButtonPushed(ActionEvent event) throws IOException {
        
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        
        if(selectedCustomer != null)
        { 
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete " + selectedCustomer.getCustomerName() + "? This will also delete all associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == ButtonType.OK)
            {
                try
                {   
                    PreparedStatement ps = conn.prepareStatement(
                    "DELETE appointments.* FROM appointments WHERE Customer_ID =?");
                    
                    PreparedStatement ps2 = conn.prepareStatement(
                    "DELETE customers.* FROM customers WHERE Customer_ID =?");
                    
                    ps.setInt(1, selectedCustomer.getCustomerId());
                    ps2.setInt(1, selectedCustomer.getCustomerId());
                    int rs = ps.executeUpdate();
                    int rs2 = ps2.executeUpdate();
                    
                    if(rs > 0)
                    { 
                        System.out.println("Customer Deleted");
                        populateTable();
                    }
                    else
                    {
                        System.out.println("Deletion Failed");
                    }    
                } 
                catch (SQLException ex) 
                {
                    Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }    
        }    
        
        
    }
    
    /**
     * This method will bring you to the update screen for the selected customer and throw
     * an alert if you don't have anyone selected.
     * @param event updateButtonPushed
     * @throws IOException 
     */
    
    @FXML
    void updateButtonPushed(ActionEvent event) throws IOException
    {
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
       
        if(customerTable.getSelectionModel().getSelectedItem() != null)
        {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
       
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Customer Selected");
            alert.showAndWait();
        }

    }
    
    /**
     * This method will bring you to the Add Customer screen.
     * @param event addButtonPushed
     * @throws IOException 
     */
    
    @FXML void addButtonPushed(ActionEvent event) throws IOException 
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * This method will take you to the main screen
     * @param event mainMenuButtonPushed
     * @throws IOException 
     */
    
    @FXML void mainMenuButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * This method pulls all customers from the database and sets them all into
     * the customer table view.
     */
    
    @FXML void populateTable()
    {
        try
        {
            customerList.clear();
            PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM customers, first_level_divisions, countries "
            + "WHERE customers.Division_ID = first_level_divisions.Division_ID "
            + "AND first_level_divisions.COUNTRY_ID = countries.Country_ID "
            + "ORDER BY Customer_ID"); 
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                
                customerList.add(new Customers(customerId, customerName, address, 
                postalCode, phone, createDate, createdBy, lastUpdate,
                lastUpdatedBy, divisionId, divisionName, countryId, countryName));  
            }
            
            customerTable.setItems(customerList);
        } catch (SQLException ex) {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /**
         * assigns all related properties to the table view columns.
         */
        /**
         * LAMBDA binding the column to the table view
         */
        
        idColumn.setCellValueFactory((cellData -> cellData.getValue().getAnotherId().asObject()));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        
        /**
         * pulls the populateTable method.
         */
        
        populateTable();
        
    
    
    }    
    
}
