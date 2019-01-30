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
    private Button goHome_btn;
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility("/Home_Package/Home_View.fxml","Home Page", event );

    }
    @FXML
    public void open_Product_WindowFunction(Event event) throws IOException     {

        try {
            goBack_To_Home_Window(event);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
