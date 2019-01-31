package Login_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Manage_Users_Controller implements Initializable {

    @FXML
    private ComboBox camboBox;
    @FXML
    private TextField  usernametxt, passtxt;
    @FXML
    private DatePicker date_txt;


    Db_Connection conn = new Db_Connection();
    PreparedStatement  preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();


    @FXML
    public void addPerson() throws SQLException {


        String query = "select max(id) from demo.users";
        int idmax= 0;

        Connection cnn = conn.connect();
        preparesStatemnt = cnn.prepareStatement(query);
        resultSet = preparesStatemnt.executeQuery();

        if(resultSet.next()){

            idmax = resultSet.getInt(1);
        }


       System.out.println(idmax);



    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        camboBox.getItems().addAll("User","Admin");
        camboBox.setValue("User");
        camboBox.getStyleClass().add("center-aligned");
    }


}
