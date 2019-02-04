package Stock_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Stock_Controller  implements Initializable {

@FXML
private Button goHome_btn,produit_btn,charge_btn,newProduct_btn;

   // Go Home
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);

    }
    // Go Product
    @FXML
    public void go_Back_To_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);
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
    // Caisse
    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }
    @FXML
   public void go_To_Charge_Stock_Window(Event event) throws IOException{
        new Utility().openNewStage("/Stock_Package/Charge_View.fxml","Charge Stock");

    }
    // Show Add new Product Window
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().show_New_Product_Window(event);
    }
    // Log out
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
                go_Back_To_Product_Window(event);break;
            case ALT_GRAPH:
                Open_Caisse_Window(event);break;
            case N:
                open_Add_New_Product_Form(event);break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        new Utility().Button_request_focus(goHome_btn);

    }
}
