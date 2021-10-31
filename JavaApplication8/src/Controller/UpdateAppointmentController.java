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


public class UpdateAppointmentController implements Initializable {

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
    
    int selectedAppointmentId = AppointmentsController.getSelectedAppointment().getAppointmentId();
    int contactId = -1;
    int customerId = -1;
    Timestamp start = null;
    Timestamp end = null;
    PreparedStatement ps;
    int appointmentId1 = 0;
    
    
    /**
     * updates the appointment in the database. provides several customized alerts to protect data integrity. 
     * @param event save button pushed
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
            if(!hoursOfOperation(startBox))
            {
                return;
            }
            if(!hoursOfOperation(endBox))
            {
                return;
            }
           
                PreparedStatement ps = conn.prepareStatement("UPDATE appointments "
                  + "SET Title = ?, Description = ?, Location = ?, "
                  + "Type = ?, Start = ?, End = ?, Last_Update = NOW(), "
                  + "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? "
                  + "WHERE Appointment_ID = ?");

                ps.setString(1, title);
                ps.setString(2, description);
                ps.setString(3, location);
                ps.setString(4, type);
                ps.setTimestamp(5, start);
                ps.setTimestamp(6, end);
                ps.setString(7, lastUpdatedBy);
                ps.setInt(8, customerId);
                ps.setInt(9, userId);
                ps.setInt(10, contactId);
                ps.setInt(11, Integer.parseInt(appointmentIdText.getText()));
                

                int result = ps.executeUpdate();
                if(result > 0)
                {
                    System.out.println("\nUpdate Successful!\n");
                    Parent parent = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
                else
                {
                    System.out.println("\nUpdate Failed\n");
                }

                
                
            
           
       }
       catch (SQLException ex) 
       {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }  
    
    /**
     * brings the user back to the appointment screen.
     * @param event cancel button is pushed
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
     * pulls the location data from the database to populate the combo box. 
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
     * pulls the data from the database and populates the combo box 
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
     * pulls the type data from the database to populate the type box. 
     */
    
    @FXML private void typeComboBox()
    {
        ObservableList<String> typeCombo = FXCollections.observableArrayList();
        
        typeCombo.addAll("Planning Session", "De-Briefing", "New Customer", "Progress Review", "Close Account");
        typeComboBox.setItems(typeCombo);
    }        
    
    /**
     * populates the time combo boxes and sets the date picker to current date. 
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
     * pulls the appointment data from the database and prevents an appointment from being updated to a time frame already taken up by another appointment. 
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
     * prevents appointment start to end times being created outside business hours. 
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
     * pulls data from the database to populate customer combo box. 
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
     * pulls data from the database to populate the customer name after customer id is selected. 
     * 
     * @param event 
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /**
         * populates all fields to the settings of the selected customer
         */
        
        appointmentIdText.setText("" + (AppointmentsController.getSelectedAppointment().getAppointmentId()));
        descriptionText.setText((AppointmentsController.getSelectedAppointment().getDescription()));
        titleText.setText((AppointmentsController.getSelectedAppointment().getTitle()));
        locationComboBox.setValue((AppointmentsController.getSelectedAppointment().getLocation()));
        contactComboBox.setValue((AppointmentsController.getSelectedAppointment().getContactName()));
        typeComboBox.setValue(AppointmentsController.getSelectedAppointment().getType());
        customerIdComboBox.setValue(AppointmentsController.getSelectedAppointment().getCustomerId());
        customerNameText.setText((AppointmentsController.getSelectedAppointment().getCustomerName()));
        startTimeComboBox.setValue(AppointmentsController.getSelectedAppointment().getStart());
        endTimeComboBox.setValue(AppointmentsController.getSelectedAppointment().getEnd());
        
       
        
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
