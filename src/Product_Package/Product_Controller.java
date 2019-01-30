package Product_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Product_Controller implements Initializable {

    @FXML
    private Button goHome_btn,newProduct_btn;

    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );

    }
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().openNewStage("/Product_Package/New_Product_View.fxml","Ajouter Nouveau Produit");
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
