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
    private Button goHome_btn,newProduct_btn,stock_btn;


    // Go Home Page
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );

    }
    // Go New Product
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().openNewStage("/Product_Package/New_Product_View.fxml","Ajouter Nouveau Produit");
    }

    // Go To Produits
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

       // new Utility("/Stock_Package/Stock_View.fxml","Stock Window");

        new Utility().switchScene("/Stock_Package/Stock_View.fxml","Stock Window",event);

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
