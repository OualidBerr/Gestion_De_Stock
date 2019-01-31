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

        new Utility().go_Home(event);
    }

    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        new Utility().go_Stock(event);

    }

    @FXML
    public void open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);

    }

    @FXML
    public void open_Founisseur_Window(Event event) throws IOException {

        new Utility().go_Fournisseur(event);

    }



    // Open New Client Form
    @FXML
    public void open_Add_New_Client_Form(Event event) throws IOException{
        new Utility().show_New_Client_Window(event);
    }

    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException{

        new Utility().show_Reglement_Window("Client :",event);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
