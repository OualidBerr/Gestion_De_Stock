package Fournisseur_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Fournisseur;
import Utilities_Package.Utility;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.net.URL;
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
    private Button    add_Fournisseur_btn,closeButton;
    @FXML
    private Button   update_fournisseurbtn;

    public static String NAME    ;
    public static String ADDRESS ;
    public static String PHONE   ;
    public static int ID;
    public static Fournisseur Fournisseur;


    public ObservableList<Fournisseur> data;

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
            idetxt.clear();

            utility.showAlert("New User added successfully");
        }

        else if  (nametxt.getText().isEmpty() && addresstxt.getText().isEmpty() && telephonetxt.getText().isEmpty()){
            utility.showAlert("Some fields are empty");
            }


    }
    // Update
    @FXML
    public void update_Fournisseur() throws SQLException{

        if (!idetxt.getText().isEmpty() && !nametxt.getText().isEmpty() && !addresstxt.getText().isEmpty() && !telephonetxt.getText().isEmpty()) {


            String id        = idetxt.getText();
            String name      = nametxt.getText();
            String telephone = telephonetxt.getText();
            String adress    = addresstxt.getText();

            Fournisseur fournisseur = new Fournisseur(0,"","","");

            fournisseur.setFournisseurName(name);
            fournisseur.setFournisseurAdress(adress);
            fournisseur.setFournisseurTelephone(telephone);

            try{
                PreparedStatement  preparesStatemnt = null;

                String query  = "UPDATE demo.fournisseur_table SET name =?, address =?, telephone =? Where id="+id;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, fournisseur.getFournisseurName());
                preparesStatemnt.setString(2, fournisseur.getFournisseurAdress());
                preparesStatemnt.setString(3, fournisseur.getFournisseurTelephone());
                utility.showAlert("User has been Updated");
                preparesStatemnt.executeUpdate();

                idetxt.clear();
                nametxt.clear();
                telephonetxt.clear();
                addresstxt.clear();
                idetxt.clear();

                conn.connect().close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else{

            utility.showAlert("Fields are not filled");
          }


    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case ENTER:
                add_Fournisseur();  break;
            case SHIFT:
                update_Fournisseur();break;
            case ESCAPE:
                closeButtonAction();break;


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nametxt.setText(NAME);
        addresstxt.setText(ADDRESS);
        telephonetxt.setText(PHONE);
        idetxt.setText(ID+"");
    }
}
