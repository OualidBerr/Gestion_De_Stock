package Reglement_Package;

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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Reglement_Controller implements Initializable {
     @FXML
     public TextField show_operation_N_txt;
     public TextField show_date_txt;
     public TextField show_montant_txt;
     public TextField show_encient_sold_txt;
     public TextField show__sold_txt;
     public TextField show__note_txt;

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
    public Button closeButton;
    @FXML
    public TableView<Reglement>            reglement_tableView;
    public TableColumn<Reglement,Integer>  Id_column    ;
    public TableColumn<Reglement,  Date>   date_column ;
    public TableColumn<Reglement,String>   mode_column ;
    public TableColumn<Reglement,Double>   amount_column ;
    public TableColumn<Reglement,Double>   old_soldcolumn;
    public TableColumn<Reglement,Double>   sold_column ;
    public TableColumn<Reglement,String>   note_column ;

     public static String FOURNISSEUR_NAME;
     public static double FOURNISSEUR_OLD_SOLD;
     public static String FOURNISSEUR_PHONE;
     public static String FOURNISSEUR_ADDESS;
     public static int    FOURNISSEUR_ID ;
     public static String  FOURNISSEUR_DATE;

     public  Label show_Label;

    public ObservableList<Reglement> data;
    public ObservableList<Reglement> data_2;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();
    Notification notification = new Notification();


    public void loadTable() throws SQLException {
        try{

            data = FXCollections.observableArrayList();
            rs = conn.connect().createStatement().executeQuery("SELECT * FROM person_reglement_table where  personID="+FOURNISSEUR_ID);
            while(rs.next()){
                data.add(new Reglement(

                        rs.getInt(   1),
                        rs.getDouble(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(   8)));
            }
        }
        catch(SQLException eX){
            eX.printStackTrace();
            System.out.println("error ! Not Connected to Db****");
        }
        finally{
            Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
            old_soldcolumn.setCellValueFactory(new PropertyValueFactory<>("oldsold"));
            sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
            mode_column.setCellValueFactory(new PropertyValueFactory<>("mode"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
            note_column.setCellValueFactory(new PropertyValueFactory<>("note"));
            reglement_tableView.setItems(data);
               conn.connect().close();


        }


    }
    public void loadTable(int F_Id) throws SQLException {
        try{

            data = FXCollections.observableArrayList();
            rs = conn.connect().createStatement().executeQuery("SELECT * FROM person_reglement_table where  personID="+F_Id);
            while(rs.next()){
                data.add(new Reglement(

                        rs.getInt(   1),
                        rs.getDouble(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(   8)));
            }

        }
        catch(SQLException eX){
            eX.printStackTrace();
            System.out.println("error ! Not Connected to Db****");
        }
        finally{
            Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
            old_soldcolumn.setCellValueFactory(new PropertyValueFactory<>("oldsold"));
            sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
            mode_column.setCellValueFactory(new PropertyValueFactory<>("mode"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
            note_column.setCellValueFactory(new PropertyValueFactory<>("note"));
            reglement_tableView.setItems(data);

            conn.connect().close();
            rs.close();
            preparesStatemnt.close();

        }
    }
    @FXML
    public void delet_reglement() throws SQLException {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Reglement reglement = reglement_tableView.getSelectionModel().getSelectedItem();
            int fournisseurID = reglement.getPersonID();
            double deleted_amount = reglement.getAmount();
            double old_sold = Double.parseDouble(f_old_sold_txt.getText());
            double sold = deleted_amount + old_sold;

            // Update Sold
            try
            {
                String query  = "UPDATE demo.person_table SET sold ="+sold+" Where id="+fournisseurID;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.executeUpdate();
                preparesStatemnt.close();
                conn.connect().close();
                f_old_sold_txt.setText(sold+"");
            }
            catch (Exception ex)
            {
                ex.getStackTrace();
            }
            finally{
                conn.connect().close();
                rs.close();
                preparesStatemnt.close();
            }

            // Now delete

            if(! reglement_tableView.getSelectionModel().isEmpty() ) {

                try {

                    int reglement_id = reglement.getId();
                    String query = "DELETE FROM  demo.person_reglement_table WHERE id =" + reglement_id;
                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.executeUpdate();
                    preparesStatemnt.close();
                    conn.connect().close();
                }

                catch (SQLException e) { e.printStackTrace(); }
                finally {
                    loadTable(fournisseurID);
                    if (conn.connect() != null) {
                        conn.connect().close();
                    }
                }
                notification.show_Confirmation(deleted_amount+" DZD deleted Successfully !");

            }



           }

        else {
            // ... user chose CANCEL or closed the dialog


        }












    }
    public void clear(){
       reglement_note_txt.setText("/");
       NumberTextField.clear();
       reglement_datePicker.setValue(LocalDate.now());
        show_operation_N_txt.clear();
        show_montant_txt.clear();
        show_encient_sold_txt.clear();
        show__sold_txt.clear();
        show_date_txt.clear();
        show__note_txt.clear();


       }
    @FXML
    public void edit_reglment() throws SQLException {


        if (!reglement_tableView.getSelectionModel().isEmpty()){

            Reglement reglement = reglement_tableView.getSelectionModel().getSelectedItem();

            int id = reglement.getId();
            int fournisseurID = reglement.getPersonID();
            reglement.setNote(reglement_note_txt.getText());
            reglement.setMode(payement_Mod_cambo.getValue().toString());
            reglement.setDate(reglement_datePicker.getValue().toString());

            try
            {

                String query  = "UPDATE  demo.person_reglement_table SET note =?,mode=?,date=? Where id="+id ;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, reglement.getNote());
                preparesStatemnt.setString(2,reglement.getMode());
                preparesStatemnt.setString   (3,reglement.getDate());
                preparesStatemnt.executeUpdate();
                loadTable(fournisseurID);
                notification.show_Confirmation("Transaction updated Successfully!");
                preparesStatemnt.close();
                conn.connect().close();

            }
            catch (Exception ex)
            {
                ex.getStackTrace();
            }
            finally{

                conn.connect().close();
                rs.close();
                preparesStatemnt.close();

            }


        }
         else if (reglement_tableView.getSelectionModel().isEmpty()){

           notification.show_Warrning("Nothing is Selected");
        }




       }
    @FXML
    public void add_reglement() throws SQLException {

        if  (   !NumberTextField.getText().isEmpty() &&
                !reglement_datePicker.getValue().toString().isEmpty()&&
                !payement_Mod_cambo.getValue().toString().isEmpty()&&
                !reglement_note_txt.getText().isEmpty()
            )

          {

            int fournisseurID = Integer.parseInt(f_Id_txt.getText());
            double amount     =  Double.parseDouble(NumberTextField.getText());
            double oldsold    =  utility.get_Sold(fournisseurID);
            double sold       = oldsold - amount;
            String mode       = payement_Mod_cambo.getValue().toString();
            String date       = reglement_datePicker.getValue().toString();
            String note       = reglement_note_txt.getText();

            try{
                int id = utility.getMax_ID("demo.person_reglement_table","id")+1;

                String query = "INSERT INTO demo.person_reglement_table (id,amount,old_sold,sold,mode,date,note,personID) VALUES (?,?,?,?,?,?,?,?)";
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt(1, id);
                preparesStatemnt.setDouble(2, amount);
                preparesStatemnt.setDouble(3, oldsold);
                preparesStatemnt.setDouble(4, sold);
                preparesStatemnt.setString(5, mode);
                preparesStatemnt.setString(6, date);
                preparesStatemnt.setString(7, note);
                preparesStatemnt.setInt(8, fournisseurID);
                preparesStatemnt.execute();
                conn.connect().close();
                rs.close();
                preparesStatemnt.close();
                utility.setTextFieldFocus(NumberTextField);
                clear();
                notification.show_Confirmation("Verssement : " + amount + " DZD"+ " received !");
            }
            catch (Exception e){e.printStackTrace();}
            finally{
                conn.connect().close();
                rs.close();
                preparesStatemnt.close();
            }

            // Update Sold

            try
            {

                String query  = "UPDATE demo.person_table SET sold ="+sold+" Where id="+fournisseurID;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.executeUpdate();
                f_old_sold_txt.setText(sold+"");

            }
            catch (Exception ex)
            {
                ex.getStackTrace();
            }
            finally{
                loadTable(fournisseurID);
                conn.connect().close();
                rs.close();
                preparesStatemnt.close();

            }


        }

        else if   (    NumberTextField.getText().isEmpty() ||
                       reglement_datePicker.getValue().toString().isEmpty()||
                       payement_Mod_cambo.getValue().toString().isEmpty() ||
                       reglement_note_txt.getText().isEmpty()
                  )

             {
                 notification.show_Warrning("One of the fields is empty");
             }


    }
    public void showOnClick(){

        Reglement reglement = reglement_tableView.getSelectionModel().getSelectedItem();
        int id = reglement.getId();
        double amount = reglement.getAmount();
        double oldsold = reglement.getOldsold();
        double sold = reglement.getSold();
        String mode = reglement.getMode();
        String note = reglement.getNote();
        String date = reglement.getDate();

        NumberTextField.setText(amount+"");
        payement_Mod_cambo.setValue(mode);
      //  reglement_datePicker.setValue(utility.stringToDateConverter(date));
        reglement_note_txt.setText(note);

        show_operation_N_txt.setText(id+"");
        show_montant_txt.setText(amount+"");
        show_encient_sold_txt.setText(oldsold+"");
        show__sold_txt.setText(sold+"");
        show_date_txt.setText(date);
        show__note_txt.setText(note);

    }
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do

        stage.close();
    }
    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {

            case ENTER:
                add_reglement();
                break;
            case ESCAPE:
                closeButtonAction();break;

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        payement_Mod_cambo.setValue("Espece");

        /////// Value changed listener in the Table
        reglement_tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        showOnClick();

                    }
                }
        );


        utility.setTextFieldFocus(NumberTextField);
        reglement_datePicker.setValue(LocalDate.now());
        payement_Mod_cambo.getItems().addAll("Espece","Verssement","Check");

       f_Id_txt.setText(FOURNISSEUR_ID +"");
       f_name_txt.setText(FOURNISSEUR_NAME);
       show_Label.setText(FOURNISSEUR_NAME);
       f_old_sold_txt.setText(FOURNISSEUR_OLD_SOLD+"");
       f_address_txt.setText(FOURNISSEUR_ADDESS);
       f_phone_txt.setText(FOURNISSEUR_PHONE);
       reglement_note_txt.setText("/");
        try {
            loadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
