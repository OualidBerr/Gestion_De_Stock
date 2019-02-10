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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bon_Client_Global_Controller implements Initializable {

    @FXML
    public TableView<Bon_Command_Client> bon_command_client_table;
    @FXML
    public TableColumn<Product, Integer> Id_column;
    public TableColumn<Product, String> ref_column;
    public TableColumn<Product, String> des_column;
    public TableColumn<Product, Integer> quantity_column;
    public TableColumn<Product, Integer> nbr_pcs_crt__column;
    public TableColumn<Product, Integer> nbr_pcs_column;
    public TableColumn<Product, Double> prix_vent_column;
    public TableColumn<Product, Double> valu_column;
    public TableColumn<Product, Integer> dat_column;

    /////////////////////////////////////////
    @FXML
    public TableView<Bon_Fournisseur_Global> Bon_Client_Global_table;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Integer> id_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> n_bon_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Double> valeur_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> date_column;
    @FXML
    public Label show_total;
    @FXML
    public Label client_name_lb;
    @FXML
    public Label address_lb;
    @FXML
    public Label phone_lb;
    @FXML
    public Label registre_lb;
    @FXML
    public Button closeButton;

    public static int    CLIENT_ID;
    public static String CLIENT_NAME;
    public static String CLIENT_ADDRESS;
    public static String CLIENT_PHONE;
    public static String CLIENT_REGISTRE;

    @FXML
    public ToggleButton toggleButton = new ToggleButton();

    public ObservableList<Bon_Fournisseur_Global> data;
    public ObservableList<Bon_Command_Client> data_2;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    @FXML
    private  void handel_toggle_Button_event(){

        if(toggleButton.isSelected())
        {
            toggleButton.setText("Hide Details");
            bon_command_client_table.setVisible(true);
            show_total.setVisible(true);

        }
        else
        {
            bon_command_client_table.setVisible(false);
            show_total.setVisible(false);
            toggleButton.setText("Show Details");
        }

    }
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_table where clientID="+CLIENT_ID);
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

        Bon_Client_Global_table.setItems(data);

        cnn.close();

    }
    @FXML
    // refresh
    public void refresh() throws SQLException {

        Bon_Fournisseur_Global bon_client_global = Bon_Client_Global_table.getSelectionModel().getSelectedItem();

        Connection cnn = conn.connect();
        try {

            data_2 = FXCollections.observableArrayList();

            ResultSet rs = cnn.createStatement().executeQuery("SELECT id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix," +
                    "value,clientID,bonID,date " + " FROM demo.bon_command_client_table Where" +
                    " bonID="+bon_client_global.getBonID() +" and clientID="+CLIENT_ID  );
            while (rs.next()) {
                data_2.add(new Bon_Command_Client(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11)
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
        valu_column.setCellValueFactory(new PropertyValueFactory<>("value"));
        dat_column.setCellValueFactory(new PropertyValueFactory<>("date"));

        ////////////////////////////////

        double sum_amount=0.25;
        String Query =" SELECT sum(value)  FROM demo.bon_command_client_table where clientID = "+CLIENT_ID+" " + " and bonID="+bon_client_global.getBonID()   ;
        ResultSet   rs = cnn.createStatement().executeQuery(Query);
        if (rs.next()){
            sum_amount = rs.getDouble(1);
        }
        show_total.setText(sum_amount+"");
        bon_command_client_table.setItems(data_2);
        cnn.close();

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
                if(!Bon_Client_Global_table.getSelectionModel().isEmpty())
                {
                    toggleButton.setText("Hide Details");
                    bon_command_client_table.setVisible(true);
                    show_total.setVisible(true);
                }
                break;
            case ALT_GRAPH:
                    toggleButton.setText("Show Details");
                    bon_command_client_table.setVisible(false);
                    show_total.setVisible(false);
                break;





        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toggleButton.setText("Details");
        client_name_lb.setText(CLIENT_NAME);
        address_lb.setText(CLIENT_ADDRESS);
        phone_lb.setText("Telephone : "+CLIENT_PHONE);
        registre_lb.setText("RC/N°:"+CLIENT_REGISTRE);

        bon_command_client_table.setVisible(false);
        show_total.setVisible(false);
        try {
            loadData();
        } catch (SQLException e) { e.printStackTrace(); }

        /////// Value changed listener in the Table
        Bon_Client_Global_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
