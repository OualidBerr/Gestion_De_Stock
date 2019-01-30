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

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );
    }
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        // new Utility("/Stock_Package/Stock_View.fxml","Stock Window");

        new Utility().switchScene("/Stock_Package/Stock_View.fxml","Stock Window",event);

    }


    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException{

        new Utility().openNewStage("/Fournisseur_Package/New_Fournisseur_View.fxml","Ajouter Nouveau Fournisseur");
    }

    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException{

        new Utility().openNewStage("/Reglement_Package/Reglement_View.fxml","Verssement Vers Fournisseur : ");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
