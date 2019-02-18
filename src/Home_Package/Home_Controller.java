package Home_Package;

import Bon_Command_Package.Bon_Command_Client_Controller;
import Utilities_Package.Utility;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_Controller implements Initializable {


    @FXML
    private Button products_btn,client_btn,stock_btn,fournisseur_btn,logout_btn,test_btn;

    @FXML
    private MenuItem manage_Users;





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

    // Go Contoire
    @FXML
    public void Open_Contoire_Window(Event event)throws IOException  {
        new Utility().go_Contoir(event);
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
