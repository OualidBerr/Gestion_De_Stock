package Home_Package;

import Utilities_Package.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Home_Controller implements Initializable {
    @FXML
    private Button products_btn;










    public void Open_Product_Window(Event event) throws IOException {

        new Utility("/Product_Package/Product_View.fxml","Products Page", event );

    }

    @FXML
    public void open_Product_WindowFunction(Event event) throws IOException     {

        try {
            Open_Product_Window(event);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
