package Product_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Fournisseur;
import Utilities_Package.Reglement;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class New_Product_Controller implements Initializable {

    @FXML
    private Button new_Fournisseur_btn,closeButton;
    @FXML
    private DatePicker new_Product_datePicker;
    @FXML
    private ComboBox fournisseur_Cambo;
    @FXML
    private TextField fournisseur_TXT;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    public ArrayList data_2;


    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();

        try{
            data_2 = new ArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.fournisseur_table");
            while(rs.next()){
                data_2.add(rs.getString(2));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        cnn.close();
    }

    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException, SQLException {

        new Utility().openNewStage("/Fournisseur_Package/New_Fournisseur_View.fxml","Ajouter Nouveau Fournisseur");

    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    // Event Handler

    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case ESCAPE: closeButtonAction();
                 break;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TextFields.bindAutoCompletion(fournisseur_TXT, data_2);
        new_Product_datePicker.setValue(LocalDate.now());
        utility.setTextFieldFocus(fournisseur_TXT);


    }
}
