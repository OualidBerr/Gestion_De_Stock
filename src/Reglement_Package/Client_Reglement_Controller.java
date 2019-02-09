package Reglement_Package;

import Login_Package.Manage_Users_Controller;
import Utilities_Package.Db_Connection;
import Utilities_Package.Reglement;
import Utilities_Package.Utility;
import javafx.application.Platform;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_Reglement_Controller implements Initializable {


    @FXML
    public TextField show_operation_N_txt;
    public TextField show_date_txt;
    public TextField show_montant_txt;
    public TextField show_encient_sold_txt;
    public TextField show__sold_txt;
    public TextField show__note_txt;

    @FXML
    public  TextField c_Id_txt;
    public TextField c_name_txt;
    public TextField c_address_txt;
    public TextField c_old_sold_txt;
    public TextField c_phone_txt;
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
    public TableView<Reglement> reglement_tableView    ;
    public TableColumn<Reglement,Integer>  Id_column    ;
    public TableColumn<Reglement, Date>   date_column ;
    public TableColumn<Reglement,String>   mode_column ;
    public TableColumn<Reglement,String>   amount_column ;
    public TableColumn<Reglement,Double>   old_sold_column;
    public TableColumn<Reglement,Double>   sold_column ;
    public TableColumn<Reglement,String>   note_column ;

    public static String CLIENT_NAME;
    public static double CLIENT_OLD_SOLD;
    public static String CLIENT_PHONE;
    public static String CLIENT_ADDESS;
    public static int    CLIENT_ID ;
    public static String  CLIENT_DATE;

    public  Label show_Label;

    public ObservableList<Reglement> data;
    public ObservableList<Reglement> data_2;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    @FXML
    public void refresh() throws SQLException {
        int clientID = utility.getClient_ID(c_name_txt.getText());
        try{

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT id,clientID,name,DATE_FORMAT(date, '%d-%m-%Y') date,mode,amount,oldsold,sold,note FROM demo.client_reglement_table Where clientID ="+clientID);
            while(rs.next()){
                data.add(new Reglement(

                        rs.getInt(   1),  // ID
                        rs.getInt(2   ),     // clientID
                        rs.getString(3),  // name
                        rs.getString(4),  // date
                        rs.getString(5),  // mode
                        rs.getDouble(6),  // amount
                        rs.getDouble(7),  // old sold
                        rs.getDouble(8),  // sold
                        rs.getString(   9) // note
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

        c_old_sold_txt.setText(utility.get_Client_Sold(clientID)+"");

    }
    public void  loadData() throws SQLException {

        Connection cnn = conn.connect();
        try{
            int clientID = utility.getClient_ID(CLIENT_NAME);

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT id,clientID,name,DATE_FORMAT(date, '%d-%m-%Y') date,mode,amount,oldsold,sold,note FROM demo.client_reglement_table Where clientID ="+clientID);
            while(rs.next()){
                data.add(new Reglement(
                        rs.getInt(   1),  // ID
                        rs.getInt(   2),  // clientID
                        rs.getString(3),  // name
                        rs.getString(4),  // date
                        rs.getString(5),  // mode
                        rs.getDouble(6),  // amount
                        rs.getDouble(7),  // old sold
                        rs.getDouble(8),  // sold
                        rs.getString(   9) // note
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


    @FXML
    public void show_Summount() throws SQLException {

        utility.showAlert(utility.get_Sum_Amount(1)+"");


    }


    // Add Verssement
    @FXML
    public void add_Reglement_To_Client() throws Exception{

        int Reglement_id ;
        int  max_id  = utility.getMax_ID("demo.client_reglement_table","id") ;
        Reglement_id = max_id +1;   // 1) Reglement id
        double amount   =  Double.parseDouble(NumberTextField.getText()); // 2) Amount


        String note = reglement_note_txt.getText();  // 5) Note
        String client_Name = c_name_txt.getText(); // 6) Client Name
        String mode = payement_Mod_cambo.getValue().toString(); // 7) Mode
        String date = reglement_datePicker.getValue().toString(); // 8 payement date
        int clientID = utility.getClient_ID(client_Name); //9) ClientID

        double old_Sold =  utility.get_Client_Sold(clientID); // 3) Old Sold

        double new_sold = old_Sold - amount; // 4) new sold


        if (!NumberTextField.getText().isEmpty()){

            String query =
                    "INSERT INTO demo.client_reglement_table"                +
                            " (id,name,date,mode,amount,oldsold,sold,note,clientID)" +
                            " VALUES (?,?,?,?,?,?,?,?,?)"                                 ;

            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt   (1, Reglement_id);
            preparesStatemnt.setString(2, client_Name);
            preparesStatemnt.setString(3, date);
            preparesStatemnt.setString(4, mode);
            preparesStatemnt.setDouble(5, amount);
            preparesStatemnt.setDouble(6, old_Sold);
            preparesStatemnt.setDouble(7, new_sold);
            preparesStatemnt.setString(8, note);
            preparesStatemnt.setInt   (9, clientID);
            preparesStatemnt.execute();

            String query_sold = "UPDATE demo.client_table SET sold =? Where id="+clientID;
            preparesStatemnt = conn.connect().prepareStatement(query_sold);
           preparesStatemnt.setDouble(1,new_sold);
            preparesStatemnt.executeUpdate();

            refresh();
            utility.showAlert("Reglement Add Succsessfully");
            preparesStatemnt.close();
            clear();
            conn.connect().close();
        }

    }
    @FXML
    public void showOnClick() throws SQLException {

        Reglement reglement =  reglement_tableView.getSelectionModel().getSelectedItem();

        String qyery = ("SELECT * FROM demo.client_reglement_table");
        preparesStatemnt = conn.connect().prepareStatement(qyery);

        int i = reglement.getId();
        String value_amount = reglement.getAmount()+"";
        String note = reglement.getNote();
        String ddate = reglement.getDate();
        String mode = reglement.getMode();

        reglement_datePicker.setValue(utility.stringToDateConverter(ddate));
        NumberTextField.setText(value_amount);
        reglement_note_txt.setText(note);
        payement_Mod_cambo.setValue(mode);


        show_operation_N_txt.setText(i+"");
        show_date_txt.setText(reglement.getDate());
        show_montant_txt.setText( reglement.getAmount()+"");
        show_encient_sold_txt.setText(reglement.getOld_sold()+"");
        show__sold_txt.setText(reglement.getSold()+"");
        show__note_txt.setText(reglement.getNote());
        show_Label.setText(c_name_txt.getText());
        preparesStatemnt.close();
    }
    @FXML
    public void clear() throws SQLException {

        NumberTextField.clear();
        reglement_note_txt.setText("/");
        reglement_datePicker.setValue(LocalDate.now());
        payement_Mod_cambo.setValue("Espece");
        show_operation_N_txt.clear();
        show_date_txt.clear();
        show_montant_txt.clear();
        show_encient_sold_txt.clear();
        show__sold_txt.clear();
        show__note_txt.clear();

    }
    // Delete
    @FXML
    private void delet_Reglement() throws SQLException{

        if(! reglement_tableView.getSelectionModel().isEmpty() ) {

            try{
                Reglement reglement =  reglement_tableView.getSelectionModel().getSelectedItem();
                int id_Reglement = reglement.getId();
                String query = "DELETE FROM demo.client_reglement_table WHERE id ="+id_Reglement;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.executeUpdate();
                /////////////////////////////////////

                double old_Sold =  utility.get_Client_Sold(reglement.getClientID()); // 3) Old Sold
                String query_sold = "UPDATE demo.client_table SET sold =? Where id="+reglement.getClientID();
                double new_sold = old_Sold + reglement.getAmount(); // 4) new sold
                preparesStatemnt = conn.connect().prepareStatement(query_sold);
                preparesStatemnt.setDouble(1,new_sold);
                preparesStatemnt.executeUpdate();


                utility.showAlert("Done!! ");
                refresh();
                preparesStatemnt.close();
                conn.connect().close();
            }

            catch(SQLException e){
                e.printStackTrace();
            }

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
            case DELETE:
                delet_Reglement() ; break;
            case ENTER:
                add_Reglement_To_Client();break;

            case ESCAPE:
                closeButtonAction();break;
        }
    }
        @Override
        public void initialize(URL location, ResourceBundle resources) {

            NumberTextField.setStyle("-fx-text-fill: blue; -fx-font-size: 14px;" +
                    " -fx-background-radius: 20; -fx-alignment : Center;" +
                    " -fx-font-weight: Bold;");

            try { loadData();} catch (Exception e) {}
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    NumberTextField.requestFocus();
                }
            });
            payement_Mod_cambo.getItems().addAll("Espece","Check","Verssement");
            payement_Mod_cambo.setValue("Espece");

            try{
                c_old_sold_txt.setText(CLIENT_OLD_SOLD+"");

            } catch (Exception e){}

            c_Id_txt.setText(CLIENT_ID+"");
            c_name_txt.setText(CLIENT_NAME);
            c_address_txt.setText(CLIENT_ADDESS);

            c_phone_txt.setText("Tel: "+CLIENT_PHONE);
            reglement_note_txt.setText("/");
            reglement_datePicker.setValue(LocalDate.now());
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
            );}

}






