package Controller;

import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication8.Appointments;
import javaapplication8.Contacts;
import javaapplication8.Customers;
import javaapplication8.Users;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.collections.transformation.FilteredList;



/**
 * FXML Controller class
 *
 * @author Ryan Drysdale
 */
public class AppointmentsController implements Initializable {
    
    @FXML TableView<Appointments> appointmentTable;
    @FXML TableColumn<Appointments, Integer> appointmentIdColumn;
    @FXML TableColumn<Appointments, String> titleColumn;
    @FXML TableColumn<Appointments, String> descriptionColumn;
    @FXML TableColumn<Appointments, String> locationColumn;
    @FXML TableColumn<Appointments, String> contactColumn;
    @FXML TableColumn<Appointments, String> typeColumn;
    @FXML TableColumn<Appointments, String> startTimeColumn;
    @FXML TableColumn<Appointments, String> endTimeColumn;
    @FXML TableColumn<Appointments, Integer> customerIdColumn;
    @FXML TableColumn<Appointments, String> customerNameColumn;
    @FXML private RadioButton allAppointmentsRadioButton;
    @FXML private RadioButton monthlyViewRadioButton;
    @FXML private RadioButton weeklyViewRadioButton;
   
    private static Appointments selectedAppointment;
    public static Appointments getSelectedAppointment(){
        return selectedAppointment;
    }
    
       
    private static Connection conn = (Connection) DBConnection.getConnection();
    private ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
    private ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> startTime = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> endTime = FXCollections.observableArrayList();
    private Users userNow;
    
    /**
     * this is the default setting for the appointment view before you choose to filter by week or month. Shows all appointments
     * @param event when the "all appointments" radio button is selected
     * @throws IOException 
     */
    
    @FXML void allAppointmentsRadioButtonPushed(ActionEvent event) throws IOException
    {
        populateTable();
    }    

    /**
     * Filters the appointment view by month
     * @param event when the monthly view radio button is selected.
     * @throws IOException 
     */
    
    @FXML void monthlyViewRadioButtonPushed(ActionEvent event) throws IOException
    {
         filteredbyEndofMonth(appointmentsList);
    }  
    
    /**
     * filters the appointments by week
     * @param event when the weekly view radio button is selected.
     * @throws IOException 
     */
    
    @FXML void weeklyViewRadioButtonPushed(ActionEvent event) throws IOException
    {
        filteredbyWeek(appointmentsList);
    }        
    
    /**
     * Brings the user back to the main menu.
     * @param event when the main menu button is pushed.
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
     * Deletes the appointment from the database and throws multiple customized alerts.
     * @param event when the delete button is pushed while an appointment is selected
     * @throws IOException
     * @throws SQLException 
     */
    
    @FXML void deleteButtonPushed(ActionEvent event) throws IOException, SQLException {
        
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        
        if(selectedAppointment != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Appointment " + selectedAppointment.getAppointmentId() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            
        if(result.get() == ButtonType.OK)
        {
            try
            {
                PreparedStatement ps = conn.prepareStatement(
                "DELETE appointments.* FROM appointments "
                + "WHERE appointments.Appointment_ID = ?");
                ps.setInt(1, selectedAppointment.getAppointmentId());
                
                int rs = ps.executeUpdate();
                if(rs > 0)
                {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("Appointment ID: " + selectedAppointment.getAppointmentId() + "- " + selectedAppointment.getType() + " deleted!");
                    alert2.show();
                    
                    System.out.println("\nAppointment " + selectedAppointment.getAppointmentId() + " Deleted");
                    populateTable();
                }    
                else
                {
                    System.out.println("\nDeletion Failed");
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
     * pulls all appointments from the database and populates them into the table view.
     */
    
    @FXML private void populateTable()
    {
        try
        {
            appointmentsList.clear();
            allAppointmentsRadioButton.setSelected(true);
            PreparedStatement ps = conn.prepareStatement(
              "SELECT * FROM appointments, customers, users, contacts "
              + "WHERE appointments.User_ID = users.User_ID "
              + "AND appointments.Contact_ID = contacts.Contact_ID "
              + "AND appointments.Customer_ID = customers.Customer_ID "
              + "ORDER BY Start");

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                 
                LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactId = rs.getInt("Contact_ID");
                String email = rs.getString("Email");
                int userId = rs.getInt("User_ID");
                

                appointmentsList.add(new Appointments(appointmentId, title, description,
                  location, type, start, end, createdDate, createdBy, lastUpdate,
                  lastUpdatedBy, customerId, userId, contactId, contactName, customerName));
            }

                appointmentTable.setItems(appointmentsList);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("SQL error!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Non-SQL error!");
        }
    }
    
    /**
     * Bring the user to the add appointment screen.
     * @param event when the add button is pushed.
     * @throws IOException 
     */
    
    @FXML void addAppointmentButtonPushed(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }     
    
    /**
     * bring the user to the update appointment screen with the currently selected appointment
     * @param event when the update button is pushed. 
     * @throws IOException 
     */
    
    @FXML void updateAppointmentButtonPushed(ActionEvent event) throws IOException
    {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        
        if(appointmentTable.getSelectionModel().getSelectedItem() != null)
        {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show(); 
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No Appointment Selected");
            alert.showAndWait();
        }
    }
    
    /**
     * Sets the parameters for the weekly appointment view. 
     * @param aList for the weekly list
     */
    
    public void filteredbyWeek(ObservableList aList)
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = LocalDateTime.now().plusDays(7);

        FilteredList<Appointments> resultWeek = new FilteredList<>(aList);
        resultWeek.setPredicate(a ->
        {
            LocalDateTime date = a.getStart();
            return (date.isEqual(now) || date.isAfter(now)) && (date.isBefore(week) || date.isEqual(week));
        });
        appointmentTable.setItems(resultWeek);
    }
    
    /**
     * Lambda filters the appointments based on the current month
     * Sets the parameters for the monthly view for the appointment list. 
     * @param aList for the monthly list 
     */
    
    public void filteredbyEndofMonth(ObservableList aList)
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endMonth = now.with(TemporalAdjusters.lastDayOfMonth());

        FilteredList<Appointments> resultEnd = new FilteredList<>(aList);
        resultEnd.setPredicate(a ->
        {
            LocalDateTime date = a.getStart();
            return (date.isEqual(now) || date.isAfter(now)) && (date.isBefore(endMonth) || date.isEqual(endMonth));
        });
        appointmentTable.setItems(resultEnd);
    }
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /**
         * Assigns the values for each table column.
         */
        
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        
        /**
         * calls the populate table method to populate the appointment table. 
         */
        
        populateTable();
        
    }    
    
}
