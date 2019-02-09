package Contoire_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Contoire_Controller implements Initializable {


    @FXML
    public void Test(){
        utility.showAlert("Hello World");
    }


    Utility utility;
    @FXML
    private Button products_btn,client_btn,stock_btn,fournisseur_btn,logout_btn;

    @FXML
    private MenuItem manage_Users;

   // product
    @FXML
    public void Open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);

    }

    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
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

    // Caisse

    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }


    @FXML
    public void manage_Users()throws IOException {
      new Utility().show_Manage_Users();

    }
    // Open_Bon_Command_Window
    @FXML
    public void Open_Bon_Command_Window(Event event) throws IOException {
        new Utility().go_Bon_Command(event);
    }
    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }

    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
                Open_Fournisseur_Window(event);  break;
            case P:
                Open_Product_Window(event);break;
            case C:
                Open_Client_Window(event);break;
            case S:
                Open_Stock_Window(event);break;
            case ALT_GRAPH:
                Open_Caisse_Window(event);break;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
