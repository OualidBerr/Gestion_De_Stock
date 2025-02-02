package Product_Package;

import Utilities_Package.*;
import javafx.application.Platform;
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
    private TextField fournisseur_TXT;
    @FXML
    private TextField des_TXT;
    @FXML
    private TextField code_bare_TXT;
    @FXML
    private TextField alert_TXT;
    @FXML
    private TextField nbr_pc_crt_TXT;
    @FXML
    private DatePicker expiratiob_datePicker;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();
    Notification notification = new Notification();
    public ArrayList data_2;


    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();

        try{
            data_2 = new ArrayList();
             rs = cnn.createStatement().executeQuery("SELECT * FROM demo.person_table where ABS(personType)=2");
            while(rs.next()){
                data_2.add(rs.getString("name"));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
         }


    }

    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException, SQLException {

        new Utility().openNewStage("/Fournisseur_Package/New_Fournisseur_View.fxml","Ajouter Nouveau Fournisseur");

    }

    // Add New Product

    @FXML
    public void add_New_Produit() throws Exception{

        int max_id = 0;
        max_id  = utility.getMax_ID("demo.product_table","id") ;

        if (       !fournisseur_TXT.getText().isEmpty()
                && !new_Product_datePicker.getValue().toString().isEmpty()
                && !des_TXT.getText().isEmpty()   && !nbr_pc_crt_TXT.getText().isEmpty()
                && !alert_TXT.getText().isEmpty() && !expiratiob_datePicker.getValue().toString().isEmpty()
           ){


            try{

                int fournisseurID = utility.getFournisseur_ID(fournisseur_TXT.getText());
                int ID = max_id + 1;                                                     // id
                                     // fournisseur
                String reference = "REF0" + max_id + 5;                                // reference
                String des ="#"+des_TXT.getText();                                        // Designation
                String code_bare = code_bare_TXT.getText();                           // code bare
                int Alert = Integer.parseInt(alert_TXT.getText());                    // Alert
                String Expiration = expiratiob_datePicker.getValue().toString();                        // Expiration
                String date_entre = new_Product_datePicker.getValue().toString();    // Date d entre
                int Nbr_pcs_crt =  Integer.parseInt(nbr_pc_crt_TXT.getText()) ;        // Nombre de pieces

                String query = "INSERT INTO product_table " +
                        " (id,ref,des,nbr_pcs_crt,code_bare,date_entre,alert,expiration,personID) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt(   1,  ID       );
                preparesStatemnt.setString(2,  reference);
                preparesStatemnt.setString(3,  des      );
                preparesStatemnt.setInt(   4,  Nbr_pcs_crt  );
                preparesStatemnt.setString(5,  code_bare );
                preparesStatemnt.setString(6,  date_entre);
                preparesStatemnt.setInt(   7,  Alert     );
                preparesStatemnt.setString(8,  Expiration);
                preparesStatemnt.setInt(   9, fournisseurID  );
                preparesStatemnt.execute();

               }

            catch (Exception ex){ex.printStackTrace();}

            finally {
                loadData();
                clear();

                notification.show_Confirmation("New Product added successfully");
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                   }
        }

    }

    public void clear(){

        des_TXT.clear();
        code_bare_TXT.clear();
        alert_TXT.clear();
        nbr_pc_crt_TXT.clear();
        expiratiob_datePicker.setValue(null) ;
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
