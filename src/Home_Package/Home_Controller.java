package Home_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_Controller implements Initializable {
    @FXML
    private Button products_btn,client_btn,stock_btn,fournisseur_btn,logout_btn;

   // product
    @FXML
    public void Open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);

    }

    // Stock
    @FXML
    public void Open_Stock_Window(Event event) throws IOException {
        new Utility().go_Stock(event);
    }
    // Client
    @FXML
    public void Open_Client_Window(Event event) throws IOException {
        new Utility().go_Client(event);
    }
    // Fournisseur
    @FXML
    public void Open_Fournisseur_Window(Event event) throws IOException {
        new Utility().go_Fournisseur(event);
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
