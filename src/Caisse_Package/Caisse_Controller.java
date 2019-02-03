package Caisse_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Caisse_Controller implements Initializable {

    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
    }
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

    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
                Open_Fournisseur_Window(event); break;
            case C:
                Open_Client_Window(event);break;
            case H:
                goBack_To_Home_Window(event);break;
            case P:
                Open_Product_Window(event);break;
            case S:
                Open_Stock_Window(event);break;


        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
