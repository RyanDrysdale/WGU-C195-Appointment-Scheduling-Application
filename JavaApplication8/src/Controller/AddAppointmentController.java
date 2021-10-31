package Controller;

import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication8.Contacts;
import javaapplication8.Appointments;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.scene.control.Alert;

/**
AddAppointmentController
@author Ryan Drysdale
*/

public class AddAppointmentController implements Initializable {

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField appointmentIdText;
    @FXML private TextField titleText;
    @FXML private TextField descriptionText;
    @FXML private ComboBox locationComboBox;
    @FXML private ComboBox contactComboBox;
    @FXML private ComboBox typeComboBox;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox startTimeComboBox;
    @FXML private ComboBox endTimeComboBox;
    @FXML private ComboBox customerIdComboBox;
    @FXML private TextField customerNameText;
    private static Connection conn = (Connection) DBConnection.getConnection();
    private ObservableList<Contacts> contactList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> startTime = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> endTime = FXCollections.observableArrayList();
    private ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    
    int contactId = -1;
    int customerId = -1;
    Timestamp start = null;
    Timestamp end = null;
    int appointmentId1 = 0;
    PreparedStatement ps;
    
    /**
     * Brings the user back to the appointments screen.
     * @param event when the cancel button is pushed.
     * @throws IOException 
     */
    
    @FXML void cancelButtonPushed(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }    

    /**
     * Pulls all first level divisions from the database to provide all the state/providence combo box items. 
     * @throws SQLException 
     */
    
    private void locationComboBox() throws SQLException
    {
        ObservableList<String> locationCombo = FXCollections.observableArrayList();
        try
        {
            PreparedStatement ps = conn.prepareStatement(
            "SELECT first_level_divisions.Division "
            + "FROM first_level_divisions");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                locationCombo.add(rs.getString("Division"));
            }    
            locationComboBox.setItems(locationCombo);
        }  
        catch (SQLException ex)
        {
        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     * pulls all contacts from the database to populate all the contact combo box items. 
     * @throws SQLException 
     */
    
    private void contactComboBox() throws SQLException
    {
        ObservableList<String> contactCombo = FXCollections.observableArrayList();
        try
        {
            PreparedStatement ps = conn.prepareStatement("SELECT * "
            + "FROM contacts");
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next())
            {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                
                contactCombo.add(rs.getString("Contact_Name"));
                
                contactList.add(new Contacts(contactId, contactName, email));
            } 
            contactComboBox.setItems(contactCombo);
        }
        catch (SQLException ex)
        {
        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    /**
     * specifies all the different types of appointments and adds them to an array. 
     */
    
    @FXML private void typeComboBox()
    {
        ObservableList<String> typeCombo = FXCollections.observableArrayList();
        
        typeCombo.addAll("Planning Session", "De-Briefing", "New Customer", "Progress Review", "Close Account");
        typeComboBox.setItems(typeCombo);
    }        
    
    /**
     * populates the start and end time combo boxes with 30 minute time intervals.
     * it also populates the date picker to the current date by default. 
     */
    
    @FXML private void dateTimeComboBoxes()
    {
        LocalTime times = LocalTime.of(0,0);
        
        while(!times.equals(LocalTime.of(23, 30)))
        {
            startTime.add(times);
            endTime.add(times);
            times = times.plusMinutes(30);
        }  
          
       
        
        datePicker.setValue(LocalDate.now());
        startTimeComboBox.setItems(startTime);
        endTimeComboBox.setItems(endTime);
    } 
    
    
    /**
     * pulls all the customers from the database to populate the customers combo box. 
     * @throws SQLException 
     */
    
    @FXML private void customerComboBox() throws SQLException 
    {
        ObservableList<Integer> customerIdCombo = FXCollections.observableArrayList();
        
        try
        {
           PreparedStatement ps = conn.prepareStatement("SELECT customers.Customer_ID "
           + "FROM customers ORDER BY Customer_ID");
           
           ResultSet rs = ps.executeQuery();
           
           while (rs.next())
           {
               customerIdCombo.add(rs.getInt("Customer_ID"));
           } 
           
           customerIdComboBox.setItems(customerIdCombo);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    /**
     * this will fill in the customers name based of their customer Id selected in the customer ID combo box.
     * @param event when the customer Id combo box receives a selected value. 
     */
        
    @FXML void customerName(ActionEvent event)
    {
        try
        {
            int searchID = (int) customerIdComboBox.getValue();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM customers "
              + "WHERE Customer_ID = ?");

            ps.setInt(1, searchID);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                customerNameText.setText(rs.getString("Customer_Name"));
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * Adds the appointment to the database with several customized alerts to protect data integrity. 
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    
    @FXML void saveButtonPushed(ActionEvent event) throws SQLException, IOException
    {
       try
       {
           String title = titleText.getText();
           String description = descriptionText.getText();
           String location = (String) locationComboBox.getValue();
           String type = (String) typeComboBox.getValue();
           String lastUpdatedBy = ("test");
           Integer userId = 1;
           
           LocalTime startBox = (LocalTime) startTimeComboBox.getValue();
           LocalTime endBox = (LocalTime) endTimeComboBox.getValue();
           
           if(startBox != null && endBox != null)
           {
               LocalDate date = datePicker.getValue();
               LocalDateTime apptStart = LocalDateTime.of(date, startBox);
               LocalDateTime apptEnd = LocalDateTime.of(date, endBox);
               start = Timestamp.valueOf(apptStart);
               end = Timestamp.valueOf(apptEnd);
           }   
           
           if(customerIdComboBox.getValue() == null)
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Customer ID Can Not Be Empty");
               alert.show();
               return;
           }    
           if(customerIdComboBox.getValue() != null)
           {
               customerId = (int) customerIdComboBox.getValue();
           }  
           String tempContact = (String) contactComboBox.getValue();
           
           if(tempContact == null)
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Contact Can Not Be Empty");
               alert.show();
               return; 
           }    
           
           if(tempContact != null)
           {
               contactId = getContactId(tempContact);
           }    
           
            if(title.isBlank())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Title Can Not Be Blank");
               alert.show();
               return;
            } 
            if(description.isBlank())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Description Can Not Be Blank");
               alert.show();
               return;
            }
            if(location == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Location Can Not Be Empty");
               alert.show();
               return; 
            } 
            if(typeComboBox == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Type Can Not Be Empty");
               alert.show();
               return; 
            } 
            if(startBox == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Start Can Not Be Empty");
               alert.show();
               return; 
            } 
            if(endBox == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("End Can Not Be Empty");
               alert.show();
               return; 
            } 
            if(datePicker == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Date Can Not Be Empty");
               alert.show();
               return; 
            } 
            
            if(type == null)
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("Type Can Not Be Empty");
               alert.show();
               return; 
            }
            if(appointmentOverlap(start, end))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Appointment Time Unavailable");
                alert.setContentText("The currently selected Timeslot is unavailable. Check the appointment Table for available times");
                alert.showAndWait();
                return;
            } 
            
            if(endBox.isBefore(startBox))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("End Time Cannot Be Before Start Time");
                alert.showAndWait();
                return;
            }    
            
            if(!hoursOfOperation(startBox))
            {
                return;
            }

            
            if(!hoursOfOperation(endBox))
            {
                return;
            }
            
           
           
           PreparedStatement ps = conn.prepareStatement("INSERT INTO appointments"
           + "(Title, Description, Location, Type, Start, "
           + "End, Create_Date, Created_By, Last_Update, Last_Updated_By, "
           + "Customer_ID, User_ID, Contact_ID) "
           + "VALUES(?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)",
           Statement.RETURN_GENERATED_KEYS);
           
           ps.setString(1, title);
           ps.setString(2, description);
           ps.setString(3, location);
           ps.setString(4, type);
           ps.setTimestamp(5, start);
           ps.setTimestamp(6, end);
           ps.setString(7, lastUpdatedBy);
           ps.setString(8, lastUpdatedBy);
           ps.setInt(9, customerId);
           ps.setInt(10, userId);
           ps.setInt(11, contactId);
           
           int result = ps.executeUpdate();
           if(result > 0)
           {
               System.out.println("Appointment Added");
               Parent parent = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
               Scene scene = new Scene(parent);
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               stage.setScene(scene);
               stage.show();
               
           }    
           else
           {
               System.out.println("Save Failed");
           } 
           
           ResultSet rs = ps.getGeneratedKeys();
           if(rs.next())
           {
               int autoKey = rs.getInt(1);
               System.out.println("Generated Appointment ID: " + autoKey);
           }    
       }
       catch (SQLException ex) 
       {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }  
    
    /**
     * This method will throw an error if an appointment that is being added overlaps an already created appointment. 
     * @param startA
     * @param endB
     * @return
     * @throws SQLException 
     */
    
    private boolean appointmentOverlap(Timestamp startA, Timestamp endB) throws SQLException
    {

        try
        {
            if(appointmentIdText.getText().isBlank())
            {
                appointmentId1 = 0;
            }
            else
            {
                appointmentId1 = Integer.parseInt(appointmentIdText.getText().trim());
            }

            int customerID1 = (int) customerIdComboBox.getValue();
            int contactID1 = getContactId((String) contactComboBox.getValue());

            System.out.println("\nSelected Appointment ID: " + appointmentId1);
            System.out.println("Selected Contact ID: " + contactID1);
            System.out.println("Selected Customer ID: " + customerID1);
            System.out.println("Selected Start: " + startA);
            System.out.println("Selected End: " + endB);

            ps = conn.prepareStatement(
              "SELECT * FROM appointments "
              + "WHERE (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR ? < Start AND ? > End) "
              + "AND (Customer_ID = ? AND Appointment_ID != ?)");

            ps.setTimestamp(1, startA);
            ps.setTimestamp(2, endB);
            ps.setTimestamp(3, startA);
            ps.setTimestamp(4, endB);
            ps.setInt(5, customerID1);
            ps.setInt(6, appointmentId1);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
            {
                return true;
            }
            return false;
        }
        
        catch (SQLException ex) 
        {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
           
    }
     
    /**
     * this will throw an error if an appointments start or end time fall outside the business's hours of operation. 
     * @param startTime
     * @return 
     */
    
    private boolean hoursOfOperation(LocalTime startTime)
    {
        LocalTime closed = LocalTime.of(22, 00);
        LocalTime open = LocalTime.of(8, 00);
        LocalDate date = datePicker.getValue();

        ZoneId zoneEST = ZoneId.of("US/Eastern");

        LocalDateTime combined = LocalDateTime.of(date, startTime);
        ZonedDateTime convert = combined.atZone(ZoneId.systemDefault()).withZoneSameInstant(zoneEST);
        LocalTime easternTime = convert.toLocalTime();

        if(easternTime.isBefore(open) || easternTime.isAfter(closed))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Timeframe is Outside of Business Hours of Operation");
            alert.setContentText("Selected Time: " + startTime + "\nEastern Time: "
            + easternTime + "\nBusiness hours: 08:00 to 22:00 US/Eastern");
            alert.showAndWait();
            return false;
        }
        return true;
        
    }
       
    /**
     * pulls the Contact ID
     * @param temp
     * @return temp
     */
    
    private int getContactId(String temp)
    {
        for(Contacts look : contactList)
        {
            if(look.getContactName().trim().toLowerCase().contains(temp.trim().toLowerCase()))
            {
                return look.getContactId();
            }
        }
        return -1;
    }
    
    /**
     * populates all of the combo boxes. 
     *  
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try 
        {
            locationComboBox();
            contactComboBox();
            typeComboBox();
            dateTimeComboBoxes();
            customerComboBox();
            
        }
        catch (SQLException ex)
        {
          Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
