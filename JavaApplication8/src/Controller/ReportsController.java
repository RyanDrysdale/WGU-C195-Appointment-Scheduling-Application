package Controller;

import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javaapplication8.Appointments;
import javaapplication8.Contacts;
import javaapplication8.Report;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class ReportsController implements Initializable {
    
    private static Connection conn = (Connection) DBConnection.getConnection();
    ObservableList<Report> reportList = FXCollections.observableArrayList();
    ObservableList<Contacts> contactListReport = FXCollections.observableArrayList();
    ObservableList<Appointments> AppointmentListReport = FXCollections.observableArrayList();
    
    @FXML private ComboBox<String> contactComboBox;
    @FXML private Button mainMenuButton;
    
    @FXML private TableView<Report> reportTable;
    @FXML private TableColumn<Report, String> reportTypeColumn;
    @FXML private TableColumn<Report, String> monthColumn;
    @FXML private TableColumn<Report, Integer> totalColumn;
    
    @FXML private TableView<Appointments> contactTable;
    @FXML private TableColumn<Appointments, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointments, String> titleColumn;
    @FXML private TableColumn<Appointments, String> typeColumn;
    @FXML private TableColumn<Appointments, String> descriptionColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> startColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> endColumn;
    @FXML private TableColumn<Appointments, Integer> customerIdColumn;
    @FXML private TableColumn<Appointments, String> nameColumn;
    @FXML private BarChart barChart;
    ;
    
    @FXML private CategoryAxis xAxis;

    @FXML private NumberAxis yAxis;
    
    /**
     * main menu button is pushed bringing the user back to the main menu 
     * @param event main menu button is pushed
     * @throws IOException 
     */
    
    @FXML void mainMenuButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * sets the results for month and total table view
         */
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        reportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        /**
         * Sets the results for the second tab tableView
         */
        
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        try 
        {
            populateReportTab();
            populateContactCombobox();
            populatecontactTab();
            populateBarChart();
        } 
        catch (SQLException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    
    /**
     * pulls all the results for the 1st report tab from the database and adds them to the report list array
     * @throws SQLException 
     */
    
    private void populateReportTab() throws SQLException
    {
        try
        {
            PreparedStatement ps = conn.prepareStatement(
              "SELECT MONTHNAME(Start) AS Month, Type, "
              + "COUNT(Type) AS TOTAL "
              + "FROM appointments "
              + "GROUP BY Month, Type");

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int total = rs.getInt("TOTAL");

                reportList.add(new Report(month, type, total));

            }
            reportTable.setItems(reportList);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * pulls all the results from the database for the second report tab
     * @throws SQLException 
     */
    
    private void populatecontactTab() throws SQLException
    {
        try
        {
            AppointmentListReport.clear();
            
            PreparedStatement ps = conn.prepareStatement(
              "SELECT * FROM appointments, customers, users, contacts "
              + "WHERE appointments.User_ID = users.User_ID "
              + "AND appointments.Contact_ID = contacts.Contact_ID "
              + "AND appointments.Customer_ID = customers.Customer_ID "
              + "ORDER BY Start");

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                // Other data not needed for table 
                LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactID = rs.getInt("Contact_ID");
                String email = rs.getString("Email");

                int userID = 1;

                AppointmentListReport.add(new Appointments(appointmentID, title, description,
                location, type, start, end, createdDate, createdBy, lastUpdate,
                lastUpdatedBy, customerID, userID, contactID, contactName, customerName));
            }

            
            contactTable.setItems(AppointmentListReport);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Populates all contacts from the database to fill the combo box
     * @throws SQLException 
     */
    
    private void populateContactCombobox() throws SQLException
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

                contactListReport.add(new Contacts(contactId, contactName, email));
            }
            contactComboBox.setItems(contactCombo);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    /**
     * pulls the appointment total for each appointment type from the database to populate it into the bar graph.
     */
    
    private void populateBarChart() {
        
        ObservableList<String>typesList = FXCollections.observableArrayList("Planning Session", "De-Briefing", "New Customer", "Progress Review", "Close Account");
        ObservableList<XYChart.Data<String, Integer>> data = FXCollections.observableArrayList();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

            try 
            { 
                for(String Type: typesList)
                {
                    
                    
                    
                PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM appointments WHERE Type = ?");
                ps.setString(1, Type);
                
    
                ResultSet rs = ps.executeQuery();


                if (rs.next()) {
                        
                        Integer count = rs.getInt("COUNT(*)");
                        data.add(new Data<>(Type, count));
                }
              }
            }
            catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
                         
        series.getData().addAll(data);
        barChart.getData().add(series);
    }
     
    /**
     * filters appointments based of which contact was selected in the second report tab. 
     * @param event contact selected. 
     * @throws SQLException 
     */
    
    @FXML void contactSelected(ActionEvent event) throws SQLException
    {
        try
        {
            AppointmentListReport.clear();
            String selectedContact = contactComboBox.getValue();
            PreparedStatement ps = conn.prepareStatement(
              "SELECT * FROM appointments, customers, users, contacts "
              + "WHERE appointments.User_ID = users.User_ID "
              + "AND appointments.Contact_ID = contacts.Contact_ID "
              + "AND appointments.Customer_ID = customers.Customer_ID AND "
              + "Contact_Name = ? "
              + "ORDER BY Start");

            ps.setString(1, selectedContact);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                String contactName = rs.getString("Contact_Name");
                String customerName = rs.getString("Customer_Name");

                
                LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int contactId = rs.getInt("Contact_ID");
                String email = rs.getString("Email");

                int userID = 1;

                AppointmentListReport.add(new Appointments(appointmentID, title, description,
                  location, type, start, end, createdDate, createdBy, lastUpdate,
                  lastUpdatedBy, customerID, userID, contactId, contactName, customerName));
            }

            // Set all appointments on the table
            contactTable.setItems(AppointmentListReport); 
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
}
