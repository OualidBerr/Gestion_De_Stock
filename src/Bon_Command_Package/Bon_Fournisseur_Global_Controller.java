package Bon_Command_Package;

import Login_Package.Manage_Users_Controller;
import Utilities_Package.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public ToggleButton toggleButton;




    public static String getString_value() {
        return string_value.get();
    }

    public static StringProperty string_valueProperty() {
        return string_value;
    }

    public static void setString_value(String string_value) {
        Bon_Fournisseur_Global_Controller.string_value.set(string_value);
    }


    private static StringProperty string_value = new SimpleStringProperty("Show Details");



    @FXML
    private  void handel_toggle_Button_event(){

        if(toggleButton.isSelected())
              {
                  bon_command_fournisseur_table.setVisible(true);
                  setString_value("Hide Details");
             }
        else
             {
                 bon_command_fournisseur_table.setVisible(false);
            setString_value("Show Details");
              }

    }













    public static int FOURNISSEUR_ID;
    public ObservableList<Bon_Fournisseur_Global> data;
    public ObservableList<Bon_Command_Fournisseur> data_2;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    @FXML
    // refresh
    public void refresh() throws SQLException {

      Bon_Fournisseur_Global bon_fournisseur_global = Bon_Fourniseur_Global_table.getSelectionModel().getSelectedItem();

            Connection cnn = conn.connect();
            try {

                data_2 = FXCollections.observableArrayList();

                ResultSet rs = cnn.createStatement().executeQuery("SELECT id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix_vent," +
                        "prix_achat,value,fournisseurID,bonID,date " + " FROM demo.bon_command_fournisseur_table Where" +
                        " bonID="+bon_fournisseur_global.getBonID() +" and fournisseurID="+FOURNISSEUR_ID  );
                while (rs.next()) {

                    data_2.add(new Bon_Command_Fournisseur(
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

            ////////////////////////////////

        double sum_amount=0.25;

        String Query =" SELECT sum(value)  FROM demo.bon_command_fournisseur_table where fournisseurID = "+FOURNISSEUR_ID+" " + " and bonID="+bon_fournisseur_global.getBonID()   ;
        ResultSet   rs = cnn.createStatement().executeQuery(Query);
        if (rs.next()){
            sum_amount = rs.getDouble(1);
        }

     show_total.setText(sum_amount+"");

        bon_command_fournisseur_table.setItems(data_2);

            cnn.close();


    }
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_table where fournisseurID="+FOURNISSEUR_ID);
            while(rs.next()){

                data.add(new Bon_Fournisseur_Global(

                             rs.getInt(   1),
                             rs.getString(2),
                             rs.getDouble(3),
                             rs.getString(4),
                             rs.getInt(   6)
                ));

            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        n_bon_column.setCellValueFactory(new PropertyValueFactory<>("n_bon"));
        valeur_column.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

        Bon_Fourniseur_Global_table.setItems(data);

        cnn.close();

       }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bon_command_fournisseur_table.setVisible(false);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
