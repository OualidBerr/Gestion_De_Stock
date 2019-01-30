package Client_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Client_Controller implements Initializable {


    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );
    }

    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        // new Utility("/Stock_Package/Stock_View.fxml","Stock Window");

        new Utility().switchScene("/Stock_Package/Stock_View.fxml","Stock Window",event);

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
