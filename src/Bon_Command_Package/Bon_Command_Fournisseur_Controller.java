package Bon_Command_Package;

import Utilities_Package.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

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
        public  static int BON_ID;

        public ObservableList<Bon_Command_Fournisseur> data;
        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet rs = null;
        Utility utility = new Utility();
        public static ArrayList data_2;
        Connection cnn = conn.connect();

        String N_value;

        public Bon_Command_Fournisseur_Controller() throws SQLException {
        }

        // refresh
        public void refresh(int bonID) throws SQLException {


            try {

                data = FXCollections.observableArrayList();
                 rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_detail_table WHERE bonID="+bonID );

                while (rs.next()) {

                    data.add(new Bon_Command_Fournisseur(

                            rs.getInt("id"),      // id
                            utility.get_Product_detail_S(rs.getInt("productID"),"ref").toString(),   // ref
                            utility.get_Product_detail_S(rs.getInt("productID"),"des").toString(),   //des
                            (int) utility.get_Product_detail_S(rs.getInt("productID"),"nbr_pcs_crt"),// nbr_pcs/carton
                            rs.getInt("quant"),   // quantity
                            rs.getInt("quant")*((int) utility.get_Product_detail_S(rs.getInt("productID"),"nbr_pcs_crt")), // nbr pcs total
                            rs.getDouble("prix_vent"),   // pris achat
                            rs.getDouble("prix_achat"),    // pris vent
                            rs.getDouble("value"),        //value
                            LocalDate.now().toString()              // date

                    ));


                }
            } catch (SQLException eX) {
                eX.printStackTrace();
                System.out.println("error ! Not Connected to Db****");
            }

            finally {

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

                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (rs != null) {rs.close();}
            }


        rs.close();conn.connect(); preparesStatemnt.close();

        }

        // Load List
        public void loadData() throws SQLException {

            try {

                data = FXCollections.observableArrayList();

                ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date FROM demo.bon_detail_table");
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


            finally {
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

                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (rs != null) {rs.close();}
            }



        }


        @FXML
        public void save_bon() throws SQLException {

               double total = get_Bon_Total(BON_ID,FOURNISSEUR_ID);
                // Update Fournisseur Sold
                double old_sold = utility.get_Sold(FOURNISSEUR_ID);
                utility.update_Fournisseur_Sold(total, old_sold, FOURNISSEUR_ID);
                utility.show_TrayNotification("saved successfully");

                //  closeButtonAction();

        }
        @FXML
        public void add_New_Bon() throws SQLException {

            // Insert bon_detail
            if (!des_TXT.getText().isEmpty()&& !prix_achat_TXT.getText().isEmpty()&&
                    !prix_vent_TXT.getText().isEmpty()&& !quant_TXT.getText().isEmpty() )
                {
                    String des = des_TXT.getText();
                    int id_order = utility.getMax_ID("demo.bon_detail_table","id")+1;
                    int quant = Integer.parseInt(quant_TXT.getText());
                    double prix_vent = Double.parseDouble(prix_vent_TXT.getText());
                    double prix_achat = Double.parseDouble(prix_achat_TXT.getText());
                    int productID = utility.getProduct_ID(des);
                    int product_Nbr_pcs_crt = utility.get_Product_Nbr_pcs_crt(productID);
                    double value = product_Nbr_pcs_crt*prix_achat*quant;
                    String query_2 = "INSERT INTO demo.bon_detail_table (id,quant,prix_vent,prix_achat,value,bonID,productID) Values (?,?,?,?,?,?,?)";

                    try {
                        preparesStatemnt = conn.connect().prepareStatement(query_2);
                        preparesStatemnt.setInt   (1, id_order);
                        preparesStatemnt.setInt(2, quant);
                        preparesStatemnt.setDouble(3, prix_vent);
                        preparesStatemnt.setDouble(4, prix_achat);
                        preparesStatemnt.setDouble(5, value);
                        preparesStatemnt.setInt   (6, BON_ID);
                        preparesStatemnt.setInt   (7, productID);
                        preparesStatemnt.execute();

                        utility.total_sum_calculator(BON_ID,productID,show_lb);
                        preparesStatemnt.close();
                        conn.connect().close();

                    }
                    catch (Exception e){ e.printStackTrace(); }
                    finally {

                        if (conn.connect()   != null) {conn.connect().close();}
                        if (preparesStatemnt != null) {preparesStatemnt.close();}
                        if (rs != null) {rs.close();}
                      }

                    //Update Total
                    double total=0.25;
                    try{
                         total=0.25;
                        try{
                            String Query =" SELECT sum(value)  FROM demo.bon_detail_table where bonID="+BON_ID   ;
                            ResultSet   rs = conn.connect().createStatement().executeQuery(Query);
                            if (rs.next()){
                                total = rs.getDouble(1);
                            }
                         }

                        catch (Exception ex){ex.printStackTrace();}
                        String Query  = "UPDATE demo.bon_table SET total =? Where id="+BON_ID;
                        preparesStatemnt = conn.connect().prepareStatement(Query);
                        preparesStatemnt.setDouble   (1,  total);
                        preparesStatemnt.executeUpdate();
                        preparesStatemnt.close();
                        conn.connect().close();
                        }
                    catch (Exception e){
                        e.printStackTrace();
                        }
                    finally {
                        if (conn.connect()   != null) {conn.connect().close();}
                        if (preparesStatemnt != null) {preparesStatemnt.close();}
                        if (rs != null) {rs.close();}
                          }


                    // Update product

                    int product_Old_Quantity = utility.get_Product_quantity(productID);
                    int new_product_quantity= product_Old_Quantity +  quant  ;
                    int new_Nbr_Pcs = new_product_quantity*utility.get_Product_Nbr_pcs_crt(productID);
                    double new_value = new_Nbr_Pcs*prix_vent;

                    try{


                        String Query  = "UPDATE demo.product_table SET quan =?,nbr_pcs=?,prix_achat =?,prix_vent =?,date_entre=?,value=? Where id="+productID;

                        preparesStatemnt = conn.connect().prepareStatement(Query);

                        preparesStatemnt.setInt      (1,  new_product_quantity);
                        preparesStatemnt.setInt      (2,  new_Nbr_Pcs);
                        preparesStatemnt.setDouble   (3,  prix_achat);
                        preparesStatemnt.setDouble   (4,  prix_vent);
                        preparesStatemnt.setString   (5, LocalDate.now().toString());
                        preparesStatemnt.setDouble   (6,  new_value);
                        preparesStatemnt.executeUpdate();
                        refresh( BON_ID);

                        preparesStatemnt.close();
                        conn.connect().close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        if (conn.connect()   != null) {conn.connect().close();}
                        if (preparesStatemnt != null) {preparesStatemnt.close();}
                        if (rs != null) {rs.close();}
                    }


                    des_TXT.clear();
                      quant_TXT.clear();
                      prix_achat_TXT.clear();
                      prix_vent_TXT.clear();
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            list.requestFocus();
                            list.scrollTo(1);
                            list.getSelectionModel().select(1);
                        }
                    });



                 }
        }




        @FXML
        public void closeButtonAction() throws SQLException {
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

                    if (!des_TXT.getText().isEmpty()&&!quant_TXT.getText().isEmpty() && prix_achat_TXT.getText().isEmpty() && prix_vent_TXT.getText().isEmpty()){
                        utility.setTextFieldFocus(prix_achat_TXT);
                    }
                    else if (!des_TXT.getText().isEmpty()&&!quant_TXT.getText().isEmpty() && !prix_achat_TXT.getText().isEmpty() && prix_vent_TXT.getText().isEmpty())
                    {
                        utility.setTextFieldFocus(prix_vent_TXT);
                    }
                    else if (!des_TXT.getText().isEmpty()&&quant_TXT.getText().isEmpty() && prix_achat_TXT.getText().isEmpty() && prix_vent_TXT.getText().isEmpty())
                    {
                        utility.setTextFieldFocus(quant_TXT);
                    }

                    else if (!des_TXT.getText().isEmpty()&&!quant_TXT.getText().isEmpty() && !prix_achat_TXT.getText().isEmpty() && !prix_vent_TXT.getText().isEmpty())
                    {
                        add_New_Bon();
                    }

                     break;
                case ESCAPE:
                    closeButtonAction(); break;


            }
        }


        public double get_Bon_Total(int bonID,int personID) throws SQLException {
            double total = 0.25;
            rs = conn.connect().createStatement().executeQuery("SELECT * FROM demo.bon_table where id="+bonID+" and personID="+personID);
            if (rs.next()){
                total= rs.getDouble(2);
            }

            return total;
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

        fournisseurID_TXT.setText(FOURNISSEUR_ID+"");
        fournisseur_Name_TXT.setText(FOURNISSEUR_NAME);
        TextFields.bindAutoCompletion(des_TXT, data_2);

         Platform.runLater(new Runnable() {

             @Override
             public void run() {
                 list.requestFocus();
                 list.scrollTo(1);
                 list.getSelectionModel().select(1);
             }
         });








    }
}
