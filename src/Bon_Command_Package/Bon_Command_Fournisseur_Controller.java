package Bon_Command_Package;

import Utilities_Package.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

    public class Bon_Command_Fournisseur_Controller implements Initializable {
        @FXML
        public Label show_lb;
        @FXML
        public Button get_Itembtn;
        @FXML
        public Button savebtn, closeButton;

        @FXML
        public TextField fournisseurID_TXT;
        @FXML
        public TextField fournisseur_Name_TXT;

        /////// Entry data
        @FXML
        public TextField des_TXT;
        @FXML
        public TextField quant_TXT;
        @FXML
        public TextField prix_achat_TXT;
        @FXML
        public TextField prix_vent_TXT;

        @FXML
        public TextField ref__TXT;

        @FXML
        public ListView list = new ListView();
        @FXML
        public DatePicker datePicker;


        @FXML
        public TableView<Bon_Command_Fournisseur> bon_command_fournisseur_table;
        @FXML
        public TableColumn<Product, Integer> id_column;
        public TableColumn<Product, String> ref_column;
        public TableColumn<Product, String> des_column;
        public TableColumn<Product, Integer> quantity_column;
        public TableColumn<Product, Integer> nbr_pcs_crt__column;
        public TableColumn<Product, Integer> nbr_pcs_column;
        public TableColumn<Product, Double> prix_vent_column;
        public TableColumn<Product, Double> prix_achat_column;
        public TableColumn<Product, Double> value_column;
        public TableColumn<Product, Integer> date_column;


        public static String FOURNISSEUR_NAME;
        public static int FOURNISSEUR_ID;

        public ObservableList<Bon_Command_Fournisseur> data;
        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet resultSet = null;
        Utility utility = new Utility();
        public static ArrayList data_2;

        String N_value;

        // refresh
        public void refresh(int bonID) throws SQLException {

            Connection cnn = conn.connect();
            try {

                data = FXCollections.observableArrayList();


                ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date FROM demo.bon_command_fournisseur_table WHERE bonID=" + bonID);
                while (rs.next()) {

                    data.add(new Bon_Command_Fournisseur(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getDouble(7),
                            rs.getDouble(8),
                            rs.getDouble(9),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getString(12)
                    ));


                }
            } catch (SQLException eX) {
                System.out.println("error ! Not Connected to Db****");
            }

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_column.setCellValueFactory(new PropertyValueFactory<>("des"));
            nbr_pcs_crt__column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs_crt"));
            nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
            prix_achat_column.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
            value_column.setCellValueFactory(new PropertyValueFactory<>("value"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

            bon_command_fournisseur_table.setItems(data);

            cnn.close();


        }


        // Load List
        public void loadData() throws SQLException {
            Connection cnn = conn.connect();
            try {

                data = FXCollections.observableArrayList();


                ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date FROM demo.bon_command_fournisseur_table");
                while (rs.next()) {

                    data.add(new Bon_Command_Fournisseur(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getDouble(7),
                            rs.getDouble(8),
                            rs.getDouble(9),
                            rs.getInt(10),
                            rs.getInt(11),
                            rs.getString(12)
                    ));


                }
            } catch (SQLException eX) {
                System.out.println("error ! Not Connected to Db****");
            }

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_column.setCellValueFactory(new PropertyValueFactory<>("des"));
            nbr_pcs_crt__column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs_crt"));
            nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
            prix_achat_column.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
            value_column.setCellValueFactory(new PropertyValueFactory<>("value"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

            bon_command_fournisseur_table.setItems(data);

            cnn.close();

        }


        @FXML
        public void save_bon() throws SQLException {

            int Id = utility.getMax_ID("demo.bon_table", "id");
            int Bon_Order_Id = utility.getMax_ID(" demo.order_table", "id");
            String BON_NUM = "Bon_" + Bon_Order_Id;

            String date = "";
            int fournisseurID = 0;
            double amount = 0.25;
            double sum_amount = 0.25;


            Connection cnn = conn.connect();
            try {

                ResultSet rs = cnn.createStatement().executeQuery("SELECT  max(fournisseurID),max(date),sum(value) FROM demo.bon_command_fournisseur_table Where bonID= " + Bon_Order_Id);

                if (rs.next()) {
                    fournisseurID = rs.getInt(1);
                    date = rs.getString(2);
                    amount = rs.getDouble(3);

                }


                String query =
                        "INSERT INTO demo.bon_table" +
                                " (id,bon_num,amount,date,fournisseurID,bonID)" +
                                " VALUES (?,?,?,?,?,?)";
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt(1, Id + 1);
                preparesStatemnt.setString(2, BON_NUM);
                preparesStatemnt.setDouble(3, amount);
                preparesStatemnt.setString(4, date);
                preparesStatemnt.setInt(5, fournisseurID);
                preparesStatemnt.setInt(6, Bon_Order_Id);

                preparesStatemnt.execute();



                // Update Fournisseur Sold

                try {

                    rs = cnn.createStatement().executeQuery("SELECT sum(value) FROM demo.bon_command_fournisseur_table Where fournisseurID="+fournisseurID);
                    if (rs.next()) {
                        amount = rs.getDouble(1);

                    }

                    double old_sold = utility.get_Sold(fournisseurID);
                    utility.update_Fournisseur_Sold(amount, old_sold, fournisseurID);

                }

                catch (SQLException eX) {
                    System.out.println("error ! Not Connected to Db****");
                }



                // Update product


                preparesStatemnt.close();
                bon_command_fournisseur_table.setItems(null);
                utility.showAlert("Added successfully");
                closeButtonAction();
            }

            catch (Exception e)
            {

            }

        }











    @FXML
    public void add_Bon() throws SQLException {

            if (!des_TXT.getText().isEmpty()&& !prix_achat_TXT.getText().isEmpty()&& !prix_vent_TXT.getText().isEmpty()&& !quant_TXT.getText().isEmpty() ){
                String ref= "";
                int nbr_pcs_crt=0;

                int productID =utility.getProduct_ID(des_TXT.getText());

                Connection cnn = conn.connect();
                try{

                    ResultSet rs = cnn.createStatement().executeQuery("SELECT  ref,nbr_pcs_crt FROM demo.product_table Where id = "+productID);
                    while (rs.next()){
                        ref =           rs.getString(1) ;
                        nbr_pcs_crt =   rs.getInt(2) ;
                    }
                }
                catch(SQLException eX){
                    System.out.println("error ! Not Connected to Db****");
                }

                int  Id  = utility.getMax_ID("demo.bon_command_fournisseur_table","id") ;
                String des = des_TXT.getText();
                int quan = Integer.parseInt(quant_TXT.getText() )  ;
                int nbr_pcs = quan*nbr_pcs_crt ;
                double prix_vent = Double.parseDouble(prix_vent_TXT.getText() );
                double prix_achat = Double.parseDouble(prix_achat_TXT.getText() );
                double value= prix_achat*nbr_pcs;
                int fournisseurId = FOURNISSEUR_ID;
                int  max_id_bon  = utility.getMax_ID("demo.order_table","id") ;
                int bonID = max_id_bon  ;
                String date = datePicker.getValue().toString();
                String query =
                        "INSERT INTO demo.bon_command_fournisseur_table"                +
                                " (id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date)" +
                                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt   (1, Id+1);
                preparesStatemnt.setString(2, ref);
                preparesStatemnt.setString(3, des);
                preparesStatemnt.setInt   (4, nbr_pcs_crt);
                preparesStatemnt.setInt   (5, quan);
                preparesStatemnt.setInt   (6, nbr_pcs);
                preparesStatemnt.setDouble(7, prix_vent);
                preparesStatemnt.setDouble(8, prix_achat);
                preparesStatemnt.setDouble(9, value);
                preparesStatemnt.setInt   (10, fournisseurId);
                preparesStatemnt.setInt   (11, bonID);
                preparesStatemnt.setString(12, date);
                preparesStatemnt.execute();
                preparesStatemnt.close();
                refresh(bonID);

                // Update product

              int product_Old_Quantity = utility.get_Product_quantity(productID);
              int new_product_quantity= product_Old_Quantity +  quan  ;
              int new_Nbr_Pcs = new_product_quantity*nbr_pcs_crt;
              double new_value = new_Nbr_Pcs*prix_achat;


                try{
                    PreparedStatement preparesStatemnt = null;

                    String Query  = "UPDATE demo.product_table SET quan =?,nbr_pcs=?,prix_achat =?,prix_vent =?,date_entre=?,value=? Where id="+productID;

                    preparesStatemnt = conn.connect().prepareStatement(Query);

                    preparesStatemnt.setInt      (1,  new_product_quantity);
                    preparesStatemnt.setInt      (2,  new_Nbr_Pcs);
                    preparesStatemnt.setDouble   (3,  prix_achat);
                    preparesStatemnt.setDouble   (4,  prix_vent);
                    preparesStatemnt.setString   (5, date);
                    preparesStatemnt.setDouble   (6,  new_value);

                    utility.showAlert("User has been Updated");
                    preparesStatemnt.executeUpdate();
                    preparesStatemnt.close();
                    conn.connect().close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                double sum_amount=0.25;

                String Query =" SELECT sum(value)  FROM demo.bon_command_fournisseur_table where fournisseurID = "+fournisseurId+" " + " and bonID="+bonID   ;
                ResultSet   rs = cnn.createStatement().executeQuery(Query);
                if (rs.next()){

                    sum_amount = rs.getDouble(1);

                }


                utility.showAlert("Added successfully");

                des_TXT.clear();
                quant_TXT.clear();
                prix_achat_TXT.clear();
                prix_vent_TXT.clear();
                show_lb.setText(sum_amount+"");
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        list.requestFocus();
                        list.scrollTo(1);
                        list.getSelectionModel().select(1);
                    }
                });

            }

            else
                {
                    utility.showAlert("Some fields are empty");
                }

        conn.connect().close();
      }

        @FXML
        public void closeButtonAction(){
            utility.showAlert("CLOSE BUTTON HAS BEEN CLICKED");
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

        @FXML
        public void handlekeyPressed(KeyEvent event) throws Exception {

            switch (event.getCode()) {
                case ENTER:
                    add_Bon(); break;

            }
        }

     @Override
     public void initialize(URL location, ResourceBundle resources) {


        datePicker.setValue(LocalDate.now());
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                N_value= newValue  ;

            }
        });


        get_Itembtn.setOnAction((event) -> {
            des_TXT.setText(N_value);
            utility.setTextFieldFocus(quant_TXT);
        });



        list.getItems().addAll(data_2);

      //  try {
           // loadData();
     //   } catch (SQLException e) {
      //      e.printStackTrace();
      //  }
        fournisseurID_TXT.setText(FOURNISSEUR_ID+"");
        fournisseur_Name_TXT.setText(FOURNISSEUR_NAME);
        TextFields.bindAutoCompletion(des_TXT, data_2);

    }
}
