package Fournisseur_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Person;
import Utilities_Package.User;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class New_Fournisseur_Controller extends Fournisseur_Controller implements Initializable {

    @FXML
    private TextField nametxt;
    @FXML
    private TextField  addresstxt;
    @FXML
    private TextField    telephonetxt;
    @FXML
    private Button    add_Fournisseur_btn;
    @FXML
    private Button   delete_fournisseurbtn;

    public ObservableList<Person> data;

    Db_Connection conn = new Db_Connection();

    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;

    Utility utility = new Utility();



    // Add
    @FXML
    public void add_Fournisseur() throws Exception{

        int max_id = 0;
        max_id  = utility.getMax_ID("fournisseur_table","id") ;

        if (!nametxt.getText().isEmpty() && !addresstxt.getText().isEmpty() && !telephonetxt.getText().isEmpty()){


            String Name = nametxt.getText();
            String Address = addresstxt.getText();
            String Telephon = telephonetxt.getText();
            double Sold = 0.25;


            String query = "INSERT INTO demo.fournisseur_table (id,name,address,telephone,sold) VALUES (?,?,?,?,?)";

            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt(1, max_id+1);
            preparesStatemnt.setString(2, Name);
            preparesStatemnt.setString(3, Address);
            preparesStatemnt.setString(4, Telephon);
            preparesStatemnt.setDouble(5, Sold);
            preparesStatemnt.execute();
            preparesStatemnt.close();

            nametxt.clear();
            addresstxt.clear();
            telephonetxt.clear();

            utility.showAlert("New User added successfully");
        }

        else{
            utility.showAlert("Data Can not be Dulicated");
        }


    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
