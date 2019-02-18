package Bon_Command_Package;

import Login_Package.Manage_Users_Controller;
import Utilities_Package.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bon_Fournisseur_Global_Controller implements Initializable {

    @FXML
    public TableView<Bon_Command_Fournisseur> bon_command_fournisseur_table;
    @FXML
    public TableColumn<Product, Integer> Id_column;
    public TableColumn<Product, String> ref_column;
    public TableColumn<Product, String> des_column;
    public TableColumn<Product, Integer> quantity_column;
    public TableColumn<Product, Integer> nbr_pcs_crt__column;
    public TableColumn<Product, Integer> nbr_pcs_column;
    public TableColumn<Product, Double> prix_vent_column;
    public TableColumn<Product, Double> prix_achat_column;
    public TableColumn<Product, Double> valu_column;
    public TableColumn<Product, Integer> dat_column;
    @FXML
    public Button show_details;
    @FXML
    public Label show_total;
    @FXML
    public Label fournisseur_lb;
    @FXML
    public Label address_lb;
    @FXML
    public Label phone_lb;
    @FXML
    public Label registre_lb;

////////////////////////////////////////////////////////

    @FXML
    public TableView<Bon_Fournisseur_Global> Bon_Fourniseur_Global_table;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Integer> id_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> n_bon_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Double> valeur_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> date_column;
    @FXML
    public Button closeButton;

    @FXML
    public ToggleButton toggleButton = new ToggleButton();


    public static int FOURNISSEUR_ID;
    public static String FOURNISSEUR_NAME;
    public static String FOURNISSEUR_ADDRESS;
    public static String FOURNISSEUR_PHONE;



    public ObservableList<Bon_Fournisseur_Global> data;
    public ObservableList<Bon_Command_Fournisseur> data_2;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();
    Connection cnn = conn.connect();

    public Bon_Fournisseur_Global_Controller() throws SQLException {
    }

    @FXML
    // refresh
    public void refresh() throws SQLException {
        toggleButton.setVisible(true);

      Bon_Fournisseur_Global bon_fournisseur_global = Bon_Fourniseur_Global_table.getSelectionModel().getSelectedItem();
      int bonID = bon_fournisseur_global.getId();

            try {

                data_2 = FXCollections.observableArrayList();

                 rs = cnn.createStatement().executeQuery("SELECT id,quant,prix_vent," +
                        "prix_achat,value,bonID,productID " + " FROM demo.bon_detail_table Where" +
                        " bonID="+bonID );
                while (rs.next()) {

                    data_2.add(new Bon_Command_Fournisseur(
                            rs.getInt("id"),      // id
                            utility.get_Product_detail_S(rs.getInt("productID"),"ref").toString(),   // ref
                            utility.get_Product_detail_S(rs.getInt("productID"),"des").toString(),   //des
                            (int) utility.get_Product_detail_S(rs.getInt("productID"),"nbr_pcs_crt"),// nbr_pcs/carton
                            rs.getInt("quant"),   // quantity
                            rs.getInt("quant")*((int) utility.get_Product_detail_S(rs.getInt("productID"),"nbr_pcs_crt")), // nbr pcs total
                            rs.getDouble("prix_vent"),   // pris achat
                            rs.getDouble("prix_achat"),    // pris vent
                            rs.getDouble("value"),        //value
                            LocalDate.now().toString()

                    ));



                }
            } catch (SQLException eX) {
                eX.printStackTrace();
                System.out.println("error ! Not Connected to Db****");
            }
            finally {
                Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
                ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
                des_column.setCellValueFactory(new PropertyValueFactory<>("des"));
                nbr_pcs_crt__column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs_crt"));
                nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
                quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
                prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
                prix_achat_column.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
                valu_column.setCellValueFactory(new PropertyValueFactory<>("value"));
                dat_column.setCellValueFactory(new PropertyValueFactory<>("date"));
                bon_command_fournisseur_table.setItems(data_2);

                double Total = get_Bon_Total(bonID,FOURNISSEUR_ID);

                show_total.setText("TOTAL : "+String.format("%,.2f", Total)+" DZD");
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (rs != null) {rs.close();}
            }


            ////////////////////////////////


        conn.connect().close();

    }


    public double get_Bon_Total(int bonID,int personID) throws SQLException {
       double total = 0.25;
        rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_table where id="+bonID+" and personID="+personID);
        if (rs.next()){
          total= rs.getDouble(2);
        }

        return total;
    }


    public  void loadData() throws SQLException {

        try{

            data = FXCollections.observableArrayList();
             rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_table where personID="+FOURNISSEUR_ID);
            while(rs.next()){

                data.add(new Bon_Fournisseur_Global(

                             rs.getInt(   "id"),
                             "Bon_"+rs.getString("id"),
                             rs.getDouble("total"),
                             rs.getString("date"),
                             rs.getInt(   "personID")
                    ));

            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            n_bon_column.setCellValueFactory(new PropertyValueFactory<>("n_bon"));
            valeur_column.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

            Bon_Fourniseur_Global_table.setItems(data);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }

       }


    @FXML
    private  void handel_toggle_Button_event() throws SQLException {

        if(toggleButton.isSelected())
        {
            toggleButton.setText("Hide Details");
            bon_command_fournisseur_table.setVisible(true);
            show_total.setVisible(true);
            refresh();

        }
        else
        {
            bon_command_fournisseur_table.setVisible(false);
            toggleButton.setText("Show Details");
            show_total.setVisible(false);
        }

    }


    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case ESCAPE:
                closeButtonAction(); break;
            case SHIFT:

                if(!Bon_Fourniseur_Global_table.getSelectionModel().isEmpty())
                {
                    toggleButton.setText("Hide Details");
                    bon_command_fournisseur_table.setVisible(true);
                    show_total.setVisible(true);

                }
                else
                {
                    toggleButton.setText("Show Details");
                    bon_command_fournisseur_table.setVisible(false);
                    show_total.setVisible(false);
                }




            break;

        }
    }


    public void delet_empty_bon() throws SQLException{

        String query = "Delete from demo.bon_table where total=0";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.executeUpdate();
        preparesStatemnt.close();
        conn.connect().close();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            delet_empty_bon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        toggleButton.setVisible(false);

        fournisseur_lb.setText(FOURNISSEUR_NAME);
        address_lb.setText(FOURNISSEUR_ADDRESS);
         phone_lb.setText(FOURNISSEUR_PHONE);
         registre_lb.setText("RC/N°: 123658745");

        toggleButton.setText("Details");
        bon_command_fournisseur_table.setVisible(false);
        show_total.setVisible(false);

        /////// Value changed listener in the Table
      Bon_Fourniseur_Global_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                  if (newSelection != null) {
                    try {
                           refresh();
                   } catch (SQLException ex) {
                           Logger.getLogger(Manage_Users_Controller.class.getName()).log(Level.SEVERE, null, ex);
                     }

                    }
              }
      );


    }


}
