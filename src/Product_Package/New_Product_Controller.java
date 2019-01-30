package Product_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class New_Product_Controller implements Initializable {

@FXML
private Button new_Fournisseur_btn;



    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException {

        new Utility().openNewStage("/Fournisseur_Package/New_Fournisseur_View.fxml","Ajouter Nouveau Fournisseur");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
