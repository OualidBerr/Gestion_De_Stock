package Product_Package;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
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
        @FXML
        private void closeButtonAction(){
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
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
