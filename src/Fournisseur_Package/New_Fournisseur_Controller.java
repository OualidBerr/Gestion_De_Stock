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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class New_Fournisseur_Controller implements Initializable {

    @FXML
    public TextField nametxt;
    @FXML
    private TextField  addresstxt;
    @FXML
    private TextField    telephonetxt;
    @FXML
    private TextField    idetxt;

    @FXML
    private Button    add_Fournisseur_btn;
    @FXML
    private Button   delete_fournisseurbtn;

    public static String NAME    ;
    public static String ADDRESS ;
    public static String PHONE   ;
    public static int ID;
    public static  Person PERSON;


    public ObservableList<Person> data;

    Db_Connection conn = new Db_Connection();


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

            PreparedStatement  preparesStatemnt = null;
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
    // Update
    @FXML
    public void update_Fournisseur() throws SQLException{

        String id        = idetxt.getText();
        String name      = nametxt.getText();
        String telephone = telephonetxt.getText();
        String adress    = addresstxt.getText();

       Person person  = new Person(0,"","","");

        person.setFournisseurName(name);
        person.setFournisseurAdress(adress);
        person.setFournisseurTelephone(telephone);


        try{
            PreparedStatement  preparesStatemnt = null;

            String query  = "UPDATE demo.fournisseur_table SET name =?, address =?, telephone =? Where id="+id;
            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setString(1,person.getFournisseurName());
            preparesStatemnt.setString(2,person.getFournisseurAdress());
            preparesStatemnt.setString(3,person.getFournisseurTelephone());

            preparesStatemnt.executeUpdate();

            utility.showAlert("User has been Updated");




        }
        catch (Exception e){
            e.printStackTrace();
        }



    }










    @Override
    public void initialize(URL location, ResourceBundle resources) {
      new Fournisseur_Controller();

        nametxt.setText(NAME);
        addresstxt.setText(ADDRESS);
        telephonetxt.setText(PHONE);
        idetxt.setText(ID+"");
    }
}
