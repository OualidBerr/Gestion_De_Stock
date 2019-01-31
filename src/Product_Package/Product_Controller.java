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

        new Utility( ).go_Home(event);
    }
    // Go New Product
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().show_New_Product_Window(event);
    }

    // Go To Stock
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        new Utility().go_Stock(event);

    }

    // Go Client
    @FXML
    public void Open_Client_Window(Event event) throws IOException {
        new Utility().go_Client(event);
    }

    // Go Fournisseur
    @FXML
    public void Open_Fournisseur_Window(Event event) throws IOException {
        new Utility().go_Fournisseur(event);
    }

    // Go Caisse

    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }

    // Log out
   @FXML
    public void log_Out_Function(Event event) throws IOException {
     new Utility().log_Out(event);
     }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
