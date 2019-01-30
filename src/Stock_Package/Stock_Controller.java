package Stock_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Stock_Controller  implements Initializable {

@FXML
private Button goHome_btn,produit_btn;

    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );

    }

    @FXML
    public void go_Back_To_Product_Window(Event event) throws IOException {


        new Utility().switchScene("/Product_Package/Product_View.fxml","Produits Window",event);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
