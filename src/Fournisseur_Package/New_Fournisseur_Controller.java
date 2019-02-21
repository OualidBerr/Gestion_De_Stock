package Fournisseur_Package;

import Utilities_Package.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Label id_lb,title_lb;
    @FXML
    public TextField nametxt;
    @FXML
    private TextField  addresstxt;
    @FXML
    private TextField    telephonetxt;
    @FXML
    private TextField    idetxt;

    @FXML
    private TextField    registretxt;


    @FXML
    private Button    add_Fournisseur_btn,closeButton;
    @FXML
    private Button   update_fournisseurbtn;

    public static String NAME    ;
    public static String TITLE_LB    ;

    public static String ADDRESS ;
    public static String PHONE   ;
    public static String REGISTRE   ;
    public static int ID;
    public static Fournisseur Fournisseur;

    public static boolean add_button_Visibility;
    public static boolean edit_button_Visibility;


    public final int   Fournisseur_Type = PersonType.Active_Fournisseur;




    public ObservableList<Fournisseur> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement  preparesStatemnt = null;
    Utility utility = new Utility();
    Notification notification = new Notification();

    // Add
    @FXML
    public void add_Fournisseur() throws Exception{

        int max_id = 0;
        max_id  = utility.getMax_ID("person_table","id") ;

        try{
            if (!nametxt.getText().isEmpty() && !addresstxt.getText().isEmpty() && !telephonetxt.getText().isEmpty()){


                String Name = nametxt.getText();
                String Address = addresstxt.getText();
                String Telephon = telephonetxt.getText();
                String Registre = registretxt.getText();

                double Sold = 0.25;

                String query = "INSERT INTO demo.person_table (id,name,address,telephone,sold,registre,PersonType) VALUES (?,?,?,?,?,?,?)";

                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt(1, max_id+1);
                preparesStatemnt.setString(2, Name);
                preparesStatemnt.setString(3, Address);
                preparesStatemnt.setString(4, Telephon);
                preparesStatemnt.setDouble(5, Sold);
                preparesStatemnt.setString(6, Registre);
                preparesStatemnt.setInt(7, Fournisseur_Type);

                preparesStatemnt.execute();

                nametxt.clear();
                addresstxt.clear();
                telephonetxt.clear();
                idetxt.clear();
                registretxt.clear();

                notification.show_Confirmation("New User added successfully");
            }

            else if  (nametxt.getText().isEmpty() && addresstxt.getText().isEmpty() && telephonetxt.getText().isEmpty()){

                notification.show_Warrning("Some fields are empty");
                  }
           }

        catch (Exception ex){ex.printStackTrace();}

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
               }

    }

    // Update
    @FXML
    public void update_Fournisseur() throws SQLException{

        if (!idetxt.getText().isEmpty() && !nametxt.getText().isEmpty() && !addresstxt.getText().isEmpty() && !telephonetxt.getText().isEmpty()) {

            String id_string       = idetxt.getText(); int fournisseurID = Integer.parseInt(id_string);
            String name      = nametxt.getText();
            String telephone = telephonetxt.getText();
            String adress    = addresstxt.getText();
            String registre    = registretxt.getText();

            Person fournisseur = new Person(0,"","","","",0.01);

            fournisseur.setName(name);
            fournisseur.setAddress(adress);
            fournisseur.setTelephone(telephone);
            fournisseur.setRegistre(registre);

            try{
                String query  = "UPDATE demo.person_table SET name =?, address =?, telephone =?,registre=? Where id="+fournisseurID+ " and PersonType="+Fournisseur_Type;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, fournisseur.getName());
                preparesStatemnt.setString(2, fournisseur.getAddress());
                preparesStatemnt.setString(3, fournisseur.getTelephone());
                preparesStatemnt.setString(4, fournisseur.getRegistre());
                preparesStatemnt.executeUpdate();

                }
            catch (Exception e){
                e.printStackTrace();
                }

            finally {
                idetxt.clear();
                nametxt.clear();
                telephonetxt.clear();
                addresstxt.clear();
                idetxt.clear();
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                    }

              }

        else{
            notification.show_Warrning("Some fields are empty");
            }
        notification.show_Confirmation("Fournisseur Updated successfully");

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
            case ESCAPE:
                closeButtonAction();break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        add_Fournisseur_btn.setVisible(add_button_Visibility);
        update_fournisseurbtn.setVisible(edit_button_Visibility);
        idetxt.setVisible(edit_button_Visibility);
        id_lb.setVisible(edit_button_Visibility);
        title_lb.setText(TITLE_LB);
        idetxt.setVisible(edit_button_Visibility);
        id_lb.setVisible(edit_button_Visibility);
        nametxt.setText(NAME);
        addresstxt.setText(ADDRESS);
        telephonetxt.setText(PHONE);
        idetxt.setText(ID+"");
    }
}
