package Fournisseur_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Fournisseur_Controller implements Initializable
{

    @FXML
  private Button new_Fournisseur_btn;


    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
    }
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        new Utility().go_Stock(event);
    }
    @FXML
    public void open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);
    }

    @FXML
    public void open_Client_Window(Event event) throws IOException {

        new Utility().go_Client(event);
    }

    // Caisse

    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }

    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException{

        new Utility().show_New_Fournisseur_Window(event);
    }

    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException{

        new Utility().show_Reglement_Window("Fournissur :",event);
    }

    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
