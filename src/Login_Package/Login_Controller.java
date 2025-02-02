


package Login_Package;
import Utilities_Package.Db_Connection;
import Utilities_Package.Notification;
import Utilities_Package.Product;
import Utilities_Package.Utility;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
private TextField txtusername,db_txt,db_usernametxt,db_passwordtxt,Server_txt;
@FXML
private PasswordField txtpassword;

    Db_Connection conn = new Db_Connection();
    PreparedStatement  preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    Notification notification = new Notification();

    public void Login(Event event) throws SQLException {

        String username = txtusername.getText();
        String password = txtpassword.getText();
        String role_User = admcambo.getValue().toString();

        String dbuser = db_usernametxt.getText();
        String dbpas = db_passwordtxt.getText();
        String DB = db_txt.getText();
        String server = Server_txt.getText();

        Db_Connection.USERNAME = dbuser;
        Db_Connection.PASSWORD = dbpas;
        Db_Connection.DB_Chema = DB;
        Db_Connection.Server = server;


        String query = "SELECT * FROM demo.users Where username = '" + username + "' &&  password = '" + password + "' && role = '" + role_User + "' ";
        Connection cnn = conn.connect();

        try {


            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery(query);
            if (!resultSet.next()) {
                System.out.println("Enter Valid username and password");
               // utility.showAlert("Please Enter Valid Username and Password");
                notification.show_Error("Please Enter Valid Username and Password");
            } else {
                if (role_User.equals("Admin")) {
                    new Utility().log_In("Admin", event);
                   // utility.show_TrayNotification("Welcome");

                } else if (role_User.equals("User")) {
                    new Utility().log_In("User", event);

                }

            }

        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            if (cnn != null) cnn.close();

              }

    }



    @FXML
    public void LoginBtnFunction(Event event) throws IOException {

        try {
            Login(event);
             } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case ENTER:
                LoginBtnFunction(event);  break;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        admcambo.getItems().addAll("User","Admin");
        admcambo.setValue("Admin");

        admcambo_2.getItems().addAll("User","Admin");
        admcambo_2.setValue("Admin");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtusername.requestFocus();
            }
        });



    }
    }
