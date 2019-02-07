package Bon_Command_Package;

import Utilities_Package.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

    public class Bon_Command_Fournisseur_Controller implements Initializable {

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
        public TableView<Bon_Command_Fournisseur> bon_command_fournisseur_table;
        @FXML
        public TableColumn<Product,Integer> id_column;
        public TableColumn<Product,String>  ref_column;
        public TableColumn<Product,String>  des_column;
        public TableColumn<Product,Integer> quantity_column;
        public TableColumn<Product,Integer> nbr_pcs_crt__column;
        public TableColumn<Product,Integer> nbr_pcs_column;
        public TableColumn<Product,Double>  prix_vent_column;
        public TableColumn<Product,Double>  prix_achat_column;
        public TableColumn<Product,Double>  value_column;
        public TableColumn<Product,Integer> date_column;


        public static String FOURNISSEUR_NAME;
        public static int   FOURNISSEUR_ID;

        public ObservableList<Bon_Command_Fournisseur> data;


        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet resultSet = null;
        Utility utility = new Utility();
        public static ArrayList data_2;

        // Load List
        public  void loadData() throws SQLException {
            Connection cnn = conn.connect();
            try{

                data = FXCollections.observableArrayList();


                ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date FROM demo.bon_command_fournisseur_table");
                while(rs.next()){

                    data.add(new Bon_Command_Fournisseur(
                            rs.getInt(     1),
                            rs.getString(  2),
                            rs.getString(  3),
                            rs.getInt(     4),
                            rs.getInt(     5),
                            rs.getInt(     6),
                            rs.getDouble(  7),
                            rs.getDouble(  8),
                            rs.getDouble(  9),
                            rs.getInt(    10),
                            rs.getInt(    11),
                            rs.getString( 12)
                          ));


                       }
                }
            catch(SQLException eX){
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
    public void add_Bon() throws SQLException {
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
        double value= prix_vent*nbr_pcs;
        int fournisseurId = FOURNISSEUR_ID;
        int  max_id_bon  = utility.getMax_ID("demo.order_table","id") ;
        int bonID = max_id_bon  ;
        String date = "2019-02-02";


        String query =
                "INSERT INTO demo.bon_command_fournisseur_table"                +
                        " (id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent,prix_achat,value,fournisseurID,bonID,date)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.setInt   (1, Id+1);
        preparesStatemnt.setString(2, ref);
        preparesStatemnt.setString(3, des);

        preparesStatemnt.setInt(4, nbr_pcs_crt);
        preparesStatemnt.setInt(5, quan);
        preparesStatemnt.setInt(6, nbr_pcs);
        preparesStatemnt.setDouble(7, prix_vent);
        preparesStatemnt.setDouble(8, prix_achat);
        preparesStatemnt.setDouble(9, value);
        preparesStatemnt.setInt   (10, fournisseurId);
        preparesStatemnt.setInt   (11, bonID);
        preparesStatemnt.setString(12, date);


        preparesStatemnt.execute();
        preparesStatemnt.close();
        utility.showAlert("Added successfully");

        loadData();

        conn.connect().close();

      }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.getItems().addAll(data_2);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fournisseurID_TXT.setText(FOURNISSEUR_ID+"");
        fournisseur_Name_TXT.setText(FOURNISSEUR_NAME);
        TextFields.bindAutoCompletion(des_TXT, data_2);



    }
}
