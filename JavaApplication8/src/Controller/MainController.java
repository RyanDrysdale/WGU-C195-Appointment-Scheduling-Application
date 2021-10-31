package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ryan Drysdale
 */
public class MainController implements Initializable {
    
    /**
     * brings user to customer screen
     * @param event when customers button is pushed
     * @throws IOException 
     */
    
    @FXML void customersButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * brings user to appointments screen
     * @param event appointments button is pushed
     * @throws IOException 
     */
    
    @FXML void appointmentsButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * brings user to the reports screen
     * @param event reports button is pushed
     * @throws IOException 
     */
    
    @FXML void reportsButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }    
    
    /**
     * brings user to the login screen
     * @param event logout button is pushed.
     * @throws IOException 
     */
    
    @FXML void logoutButtonPushed(ActionEvent event) throws IOException {
        
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
