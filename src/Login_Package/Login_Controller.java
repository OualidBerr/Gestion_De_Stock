


package Login_Package;
import Utilities_Package.Db_Connection;
import Utilities_Package.Utility;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {

@FXML
private ComboBox admcambo,admcambo_2;
@FXML
private Button loginbtn;
@FXML
private TextField txtusername,db_txt,db_usernametxt,db_passwordtxt;
@FXML
private PasswordField txtpassword;



    Db_Connection conn = new Db_Connection();
    PreparedStatement  preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    public void Login(Event event) throws SQLException {
    String username = txtusername.getText();
    String password = txtpassword.getText();
    String role_User = admcambo.getValue().toString();

    String query = "SELECT * FROM demo.users Where username = '"+username+"' &&  password = '"+password+"' && role = '"+role_User+"' ";


    try    {
            Connection cnn = conn.connect();

             preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery(query);
             if (!resultSet.next()){
              System.out.println("Enter Valid username and password");
             }
             else
                 {
                     if (role_User.equals("Admin")){
                         new Utility().log_In("Admin",event);
                         utility.showAlert("Done Successfully .. Admin!");
                       }
                     else if (role_User.equals("User")){
                         new Utility().log_In("User",event);
                         utility.showAlert("Done Successfully! .. User");
                      }

                 }

                 }
     catch (Exception e)
             {
                 e.printStackTrace();
             }


}

    @FXML
    public void LoginBtnFunction(ActionEvent event) throws IOException {

        try {
            Login(event);
             } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        admcambo.getItems().addAll("User","Admin");
        admcambo.setValue("Admin");

        admcambo_2.getItems().addAll("User","Admin");
        admcambo_2.setValue("Admin");





    }
    }
