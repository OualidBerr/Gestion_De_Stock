package Reglement_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Fournisseur;
import Utilities_Package.Reglement;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class Reglement_Controller implements Initializable {

    @FXML
    public TextField f_Id_txt;
    public TextField f_name_txt;
    public TextField f_address_txt;
    public TextField f_old_sold_txt;
    public TextField f_phone_txt;
    @FXML
    public TextField reglement_amount_txt;
    public TextField reglement_note_txt;
    public ComboBox payement_Mod_cambo;
    public DatePicker reglement_datePicker;
    @FXML
    public Button reglement_add_btn;
    public Button reglement_edit_btn;
    public Button reglement_delete_btn;
    @FXML
    public TableView<Reglement> reglement_tableView    ;
    public TableColumn<Reglement,Integer>  Id_column    ;
    public TableColumn<Reglement,  Date>   date_column ;
    public TableColumn<Reglement,String>   mode_column ;
    public TableColumn<Reglement,String>   amount_column ;
    public TableColumn<Reglement,Double>   old_sold_column;
    public TableColumn<Reglement,Double>   sold_column ;
    public TableColumn<Reglement,String>   note_column ;

     public static String FOURNISSEUR_NAME;
     public static double FOURNISSEUR_OLD_SOLD;
     public static String FOURNISSEUR_PHONE;
     public static String FOURNISSEUR_ADDESS;
     public static int    FOURNISSEUR_ID ;

    public ObservableList<Reglement> data;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM fournisseur_reglement_table");
            while(rs.next()){
                data.add(new Reglement(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getString(7)
                ));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        mode_column.setCellValueFactory(new PropertyValueFactory<>("mode"));
        amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
        old_sold_column.setCellValueFactory(new PropertyValueFactory<>("old_sold"));
        sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
        note_column.setCellValueFactory(new PropertyValueFactory<>("note"));
        reglement_tableView.setItems(null);
        reglement_tableView.setItems(data);
        cnn.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        payement_Mod_cambo.getItems().addAll("Espece","Check","Verssement");
        payement_Mod_cambo.setValue("Espece");

        reglement_datePicker.setValue(LocalDate.now());
         f_Id_txt.setText(FOURNISSEUR_ID+"");
         f_name_txt.setText(FOURNISSEUR_NAME);
         f_address_txt.setText(FOURNISSEUR_ADDESS);
         f_old_sold_txt.setText(FOURNISSEUR_OLD_SOLD+"");
         f_phone_txt.setText(FOURNISSEUR_PHONE);

    }
}
