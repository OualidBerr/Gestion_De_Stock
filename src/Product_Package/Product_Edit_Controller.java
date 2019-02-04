package Product_Package;

import Utilities_Package.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

    public class Product_Edit_Controller implements Initializable {

        @FXML
        public TextField ref_txt;
        @FXML
        public TextField nbr_pcs_crt_txt;
        @FXML
        public TextField nbr_pcs_txt;
        @FXML
        public TextField quant_txt;
        @FXML
        public TextField code_bare_txt;
        @FXML
        public TextField fournisseur_txt;
        @FXML
        public TextField alert_txt;
        @FXML
        public TextField prix_achat_txt;
        @FXML
        public TextField prix_vent_txt;
        @FXML
        public DatePicker date_DatePicker;
        @FXML
        public TextField id_txt;
        @FXML
        public TextField des_txt;
        @FXML
        public TextField expiration_txt;

        public static int ID;
        public static String DESIGNIATON;
        public static String REF;
        public static int NBR_PCS_CRT;
        public static int QUANT;
        public static int NBR_PCS;
        public static String CODE_BARE;
        public static String FOURNISSEUR;
        public static String DATE;
        public static int  ALERT;
        public static String EXPIRATION;
        public static double Prix_ACHAT;
        public static double Prix_VENT;
        @FXML
        public Button closeButton;

        public static Fournisseur Fournisseur;
        public ObservableList<Fournisseur> data;

        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet resultSet = null;
        Utility utility = new Utility();


        @FXML
        private void closeButtonAction(){
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
        public boolean istextFieldsNotEmpty(){
            if(
            !ref_txt.getText().isEmpty() &&
            !nbr_pcs_crt_txt.getText().isEmpty()&&
            !nbr_pcs_txt.getText().isEmpty() &&
            !quant_txt.getText().isEmpty() &&
            !code_bare_txt.getText().isEmpty() &&
            !fournisseur_txt.getText().isEmpty() &&
            !alert_txt.getText().isEmpty() &&
            !prix_achat_txt.getText().isEmpty() &&
            !prix_vent_txt.getText().isEmpty() &&
            !ref_txt.getText().isEmpty()&&
            !des_txt.getText().isEmpty()&&
            !expiration_txt.getText().isEmpty())

            {

            }

            return true;
        }

        // Update
        @FXML
        public void update_Product() throws SQLException {

            if (istextFieldsNotEmpty()) {

                String   reference         =  ref_txt.getText();
                int      nombre_de_pcs_crt = Integer.parseInt(nbr_pcs_crt_txt.getText()) ;
                int      nombre_de_pcs     = Integer.parseInt(nbr_pcs_txt.getText())  ;
                int      quantite          = Integer.parseInt(quant_txt.getText())  ;
                String   code_bare         = code_bare_txt.getText();
                String   fourniseur       = fournisseur_txt.getText();
                int   alert             =  Integer.parseInt(alert_txt.getText()) ;
                double   prix_achat           = Double.parseDouble(quant_txt.getText())  ;
                double   prix_vent            = Double.parseDouble(prix_vent_txt.getText())  ;
                String      designiation   = des_txt.getText();
                String   expiration        = expiration_txt.getText();
                int       Id               = Integer.parseInt(id_txt.getText())  ;

               // date_DatePicker.setValue();

                Product product = new Product(0,"","",0,0,0,
                                                   "","",0,"",
                                                  0.25,0.25,"");
                product.setDesigniation(designiation);
                product.setRef(reference);
                product.setNbr_pcs_crt(nombre_de_pcs_crt);
                product.setNbr_pcs(nombre_de_pcs);
                product.setQuantite(quantite);
                product.setCode_bare(code_bare);
                product.setFournisseur(fourniseur);
                product.setPrix_achat(prix_achat);
                product.setPrix_ventt(prix_vent);
                product.setAlert(alert);
                product.setExpiration(expiration);
                product.setId(Id);

                try{
                    PreparedStatement preparesStatemnt = null;

                    String query  = "UPDATE demo.product_table SET ref =?, des =?, nbr_pcs_crt =? , quan =? , nbr_pcs =?" +
                            " , code_bare =? , alert =? , expiration =? , prix_achat =? , prix_vent =? , fournisseur = ?  Where id="+Id;

                    preparesStatemnt = conn.connect().prepareStatement(query);

                    preparesStatemnt.setString(1, product.getRef());
                    preparesStatemnt.setString(2, product.getDesigniation());
                    preparesStatemnt.setInt   (3, product.getNbr_pcs_crt());
                    preparesStatemnt.setInt   (4, product.getQuantite());
                    preparesStatemnt.setInt   (5, product.getNbr_pcs());
                    preparesStatemnt.setString(6, product.getCode_bare());
                    preparesStatemnt.setInt   (7, product.getAlert());
                    preparesStatemnt.setString(8, product.getExpiration());
                    preparesStatemnt.setDouble(9, product.getPrix_achat());
                    preparesStatemnt.setDouble(10, product.getPrix_ventt());
                    preparesStatemnt.setString(11, product.getFournisseur());
                    preparesStatemnt.executeUpdate();

                    utility.showAlert("User has been Updated");
                    clear();
                    conn.connect().close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                    }

            else{

                utility.showAlert("Fields are not filled");
                 }


        }

    // Clear all textFields
    public void clear(){
        ref_txt.clear();
        nbr_pcs_crt_txt.clear();
        nbr_pcs_txt.clear();
        quant_txt.clear();
        code_bare_txt.clear();
        fournisseur_txt.clear();
        alert_txt.clear();
        prix_achat_txt.clear();
        prix_vent_txt.clear();
        date_DatePicker.setValue(LocalDate.now());
        id_txt.clear();
        des_txt.clear();
        expiration_txt.clear();

    }

        public void handlekeyPressed(KeyEvent event) throws Exception {

            switch (event.getCode()) {
                case ENTER: break;
                case ESCAPE:
                    closeButtonAction();break;
            }
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       ref_txt.setText(REF);
       nbr_pcs_crt_txt.setText(NBR_PCS_CRT+"");
       nbr_pcs_txt.setText(NBR_PCS+"");
       quant_txt.setText(QUANT+"");
       code_bare_txt.setText(CODE_BARE);
       fournisseur_txt.setText(FOURNISSEUR);
       alert_txt.setText(ALERT+"");
       prix_achat_txt.setText(Prix_ACHAT+"");
       prix_vent_txt.setText(Prix_VENT+"");
       date_DatePicker.setValue(LocalDate.now());
       id_txt.setText(ID+"");
       des_txt.setText(DESIGNIATON);
       expiration_txt.setText(EXPIRATION);

    }
}
