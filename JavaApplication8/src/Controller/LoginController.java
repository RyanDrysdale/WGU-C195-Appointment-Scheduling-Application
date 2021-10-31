package Controller;

import DBAccess.DBuser;
import Utils.DBConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaapplication8.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;




/**
 * FXML Controller class
 *
 * @author 13149
 */
public class LoginController implements Initializable {
    
    private ResourceBundle myBundle = ResourceBundle.getBundle("Language/lang");
    
     
    

    @FXML public TextField UsernameText;
    @FXML private TextField PasswordText;
    @FXML private Label zoneLabel;
    @FXML private Label UsernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label loginLabel;
    @FXML private Button submitButton;
    @FXML private Button resetButton;
    private ObservableList<Appointments> alertList = FXCollections.observableArrayList();
    private static Connection conn = (Connection) DBConnection.getConnection();
    
    /** 
     * clears the username and password text fields. 
     * @param event reset button is pushed. 
     */
    
    @FXML
    void resetButtonPushed(ActionEvent event) {

      UsernameText.setText("");
      PasswordText.setText("");
    }
    
    /**
     * LAMBDA gets response from the Alert and then change screens in the "OK" case.
     * validates the log in credentials and sends them to the main menu if correct. 
     * @param event
     * @throws IOException 
     * 
     */
    
    @FXML 
    void submitButtonPushed(ActionEvent event) throws IOException {
        
        String Username = UsernameText.getText();
        String Password = PasswordText.getText();
        int userId = DBuser.validateUser(Username, Password);
        
        if (userId > 0){
            
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));
            printwriter.append("Succesful Login on " + Instant.now() + " by " + UsernameText.getText() + "\n");
            System.out.println("Successful Login Recorded");
            printwriter.close();
            
                        
            appointmentAlert();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setContentText("Login Successful!");
            
        alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Alerting!");
                    Parent main = null;
                    try {
                        Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                        Scene scene = new Scene(parent);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();

                    
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));
        
    
            /*Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show(); */
        }
        
        else {
            
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(new File("login_activity.txt"),true));
            printwriter.append("Invalid Login on " + Instant.now() + " by " + UsernameText.getText() + "\n");
            System.out.println("Invalid Login Recorded");
            printwriter.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(myBundle.getString("login_failure_title"));
            alert.setHeaderText(myBundle.getString("login_failure_header"));
            alert.setContentText(myBundle.getString("login_failure_content"));
            alert.showAndWait();
            
        }  
        
 
    
    }
    
    /**
     * Searches the database for appointments and
     * provides the user, after valid login credentials, with an alert for an appointment with the next 15 minutes
     * or an alert the tells them there is no appointment within 15 minutes. 
     */
    
     private void appointmentAlert()
    {
        try
        {
            
            PreparedStatement ps = conn.prepareStatement
            ("SELECT * FROM appointments WHERE Start BETWEEN ? AND "
             + "ADDDATE(?, INTERVAL 15 MINUTE)");
            
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            ResultSet rs = ps.executeQuery();

            if(!rs.next())
            {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No Appointments Within 15 Minutes");
                alert.show();
                return;
            }
            else
            {
                do
                {  
                    
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    LocalDateTime alertDate = rs.getTimestamp("Start").toLocalDateTime();

                    alertList.add(new Appointments(appointmentId, alertDate));
                    
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);                    
                    alert2.setContentText("Appointment " + appointmentId + " Starting at " + alertDate);
                    alert2.show();                    
                }
                while(rs.next());
            }
        }
        catch (SQLException ex) 
        {
        Logger.getLogger(CustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    /**
     * populates the labels with english or french based off zoneID. 
     *  
     */
    
    
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    zoneLabel.setText(ZoneId.systemDefault().toString());
    UsernameLabel.setText(myBundle.getString("Username"));
    passwordLabel.setText(myBundle.getString("Password"));
    loginLabel.setText(myBundle.getString("Login"));
    submitButton.setText(myBundle.getString("Submit"));
    resetButton.setText(myBundle.getString("Reset"));
   
    }    
    
}
