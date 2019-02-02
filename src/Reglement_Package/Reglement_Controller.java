package Reglement_Package;

import Login_Package.Manage_Users_Controller;
import Utilities_Package.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reglement_Controller implements Initializable {
     @FXML
     public TextField show_operation_N_txt;
     public TextField show_date_txt;
     public TextField show_montant_txt;
     public TextField show_encient_sold_txt;
     public TextField show__sold_txt;
     public TextField show__note_txt;
     public Label show_Label;

    @FXML
    public  TextField f_Id_txt;
    public TextField f_name_txt;
    public TextField f_address_txt;
    public TextField f_old_sold_txt;
    public TextField f_phone_txt;
    @FXML
    public TextField NumberTextField;
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
    public ObservableList<Reglement> data_2;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    @FXML
      public void refresh(){

        try{

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT * FROM fournisseur_reglement_table Where name = '"+f_name_txt.getText()+"'");
            while(rs.next()){
                data.add(new Reglement(
                        rs.getInt(   1),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getString(8)
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
        reglement_tableView.setItems(data);

      }

     public void  loadData() throws SQLException {

        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();

            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM fournisseur_reglement_table Where name = '"+FOURNISSEUR_NAME+"'");
            while(rs.next()){
                data.add(new Reglement(
                        rs.getInt(   1),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7),
                        rs.getString(8)
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
        reglement_tableView.setItems(data);
        cnn.close();

    }



    // Add Verssement
    @FXML
    public void add_Reglement_To_Fournisseur() throws Exception{

        int max_id = 0;
        max_id  = utility.getMax_ID("demo.fournisseur_reglement_table","id") ;

        if (!NumberTextField.getText().isEmpty() && !reglement_datePicker.getValue().toString().isEmpty() && !payement_Mod_cambo.getItems().isEmpty()){

            int ID = max_id + 1;    // id
            String Name = f_name_txt.getText(); // Name
            String Date = reglement_datePicker.getValue().toString(); // Date
            String Mode = payement_Mod_cambo.getValue().toString(); // Mode
            String Amount_Value = NumberTextField.getText();
            double Amount = Double.parseDouble(Amount_Value);  // Amount
            String Old_Sold_Value =  f_old_sold_txt.getText();
            double Old_Sold = Double.parseDouble(Old_Sold_Value);// Old Sold
            double New_Sold = Old_Sold - Amount;    // Sold
            String Note = reglement_note_txt.getText(); // Note
            PreparedStatement  preparesStatemnt = null;
            String query = "INSERT INTO fournisseur_reglement_table (id,name,date,mode,amount,oldsold,sold,note) VALUES (?,?,?,?,?,?,?,?)";
            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt(1, max_id+1);
            preparesStatemnt.setString(2, Name);
            preparesStatemnt.setString(3, Date);
            preparesStatemnt.setString(4, Mode);
            preparesStatemnt.setDouble(5, Amount);
            preparesStatemnt.setDouble(6, Old_Sold);
            preparesStatemnt.setDouble(7, New_Sold);
            preparesStatemnt.setString(8, Note);
            preparesStatemnt.execute();
            clear();
            preparesStatemnt.close();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    NumberTextField.requestFocus();
                }
            });



            utility.showAlert("New User added successfully");
        }

        else if  (NumberTextField.getText().isEmpty() && reglement_datePicker.getValue().toString().isEmpty() && payement_Mod_cambo.getItems().isEmpty()){
            utility.showAlert("Some fields are empty");
        }

    }

    @FXML
    public void showOnClick() throws SQLException {

        Reglement reglement =  reglement_tableView.getSelectionModel().getSelectedItem();

        String qyery = ("SELECT * FROM demo.fournisseur_reglement_table");
        preparesStatemnt = conn.connect().prepareStatement(qyery);

        int i = reglement.getId();
        String value_amount = reglement.getAmount()+"";
        String note = reglement.getNote();

        String mode = reglement.getMode();
        NumberTextField.setText(value_amount);
        reglement_note_txt.setText(note);
        payement_Mod_cambo.setValue(mode);
        preparesStatemnt.close();
        show_operation_N_txt.setText(i+"");
        show_date_txt.setText(reglement.getDate());
        show_montant_txt.setText( reglement.getAmount()+"");
        show_encient_sold_txt.setText(reglement.getOld_sold()+"");
        show__sold_txt.setText(reglement.getSold()+"");
        show__note_txt.setText(reglement.getNote());
        show_Label.setText(f_name_txt.getText());

    }
    @FXML
    public void clear(){
        NumberTextField.clear();
        reglement_note_txt.setText("/");
        reglement_datePicker.setValue(LocalDate.now());
        payement_Mod_cambo.setValue("Espece");
        refresh();
    }

    // Update
    @FXML
    public  int update_Reglement() throws SQLException {

        if(! reglement_tableView.getSelectionModel().isEmpty()) {
            Reglement reglement = reglement_tableView.getSelectionModel().getSelectedItem();
            int  id = reglement.getId();
            double amount = Double.parseDouble(NumberTextField.getText());
            reglement.setAmount(amount);
            reglement.setNote(reglement_note_txt.getText());
            reglement.setDate(reglement_datePicker.getValue().toString());
            reglement.setMode(payement_Mod_cambo.getValue().toString());
            Connection cnn = conn.connect();
            try{
                String query = "UPDATE demo.fournisseur_reglement_table SET amount =?, date =?, mode =?, note =? Where id="+id;
                preparesStatemnt = cnn.prepareStatement(query);
                preparesStatemnt.setDouble(1,reglement.getAmount());
                preparesStatemnt.setString(2,reglement.getDate());
                preparesStatemnt.setString(3,reglement.getMode());
                preparesStatemnt.setString(4,reglement.getNote());
                preparesStatemnt.executeUpdate();
               clear();
                utility.showAlert("User has been Updated");
                cnn.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            cnn.close();
        }


        return 0;
    }


    // Delete
    @FXML
    private void delet_Reglement() throws SQLException{

        if(! reglement_tableView.getSelectionModel().isEmpty() ) {

            try{
                String query = "DELETE FROM demo.fournisseur_reglement_table WHERE id =?";
                Reglement reglement =  reglement_tableView.getSelectionModel().getSelectedItem();
                int i = reglement.getId();
                String s = String.valueOf(i);
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, s);
                preparesStatemnt.executeUpdate();
                preparesStatemnt.close();
                refresh();
                utility.showAlert("User has been deleted");
                reglement_note_txt.clear();
                NumberTextField.clear();
                payement_Mod_cambo.setValue("Espece");
                conn.connect().close();
            }

            catch(SQLException e){
                e.printStackTrace();
            }

        }

    }

    @FXML
    private void OnlyNumbers(KeyEvent event){



    }







    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try { loadData();} catch (Exception e) {}
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                NumberTextField.requestFocus();
            }
        });
        payement_Mod_cambo.getItems().addAll("Espece","Check","Verssement");
        payement_Mod_cambo.setValue("Espece");
         reglement_datePicker.setValue(LocalDate.now());
         f_Id_txt.setText(FOURNISSEUR_ID+"");
         f_name_txt.setText(FOURNISSEUR_NAME);
         f_address_txt.setText(FOURNISSEUR_ADDESS);
         f_old_sold_txt.setText(FOURNISSEUR_OLD_SOLD+"");
         f_phone_txt.setText("Tel: "+FOURNISSEUR_PHONE);
         reglement_note_txt.setText("/");



        /////// Value changed listener in the Table
        reglement_tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        try {
                            showOnClick();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage_Users_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
        );



    }

}
