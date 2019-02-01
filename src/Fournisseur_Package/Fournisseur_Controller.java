package Fournisseur_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Person;
import Utilities_Package.User;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Fournisseur_Controller implements Initializable
{
    @FXML
    private Button new_Fournisseur_btn;
    @FXML
    public  TableView<Person> Fournisseur_Table;
    @FXML
    public  TableColumn<Person,Integer> Id_column;
    public  TableColumn<Person,String> name_column;
    public  TableColumn<Person,String> adress_column;
    public  TableColumn<Person,String> telephone_column;
    public  TableColumn<Person,Double> sold_column;

    public ObservableList<Person> data;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    Connection cnn;

    {
        try {
            cnn = conn.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void loadData() throws SQLException {

        try{


            data = FXCollections.observableArrayList();

            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.fournisseur_table");

            while(rs.next()){

                data.add(new Person(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5)));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        adress_column.setCellValueFactory(new PropertyValueFactory<>("addresse"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));

        Fournisseur_Table.setItems(null);
        Fournisseur_Table.setItems(data);


    }
    @FXML
    public void showOnClick()throws SQLException{

        try{
            Person person = Fournisseur_Table.getSelectionModel().getSelectedItem();

            String qyery = ("SELECT * FROM demo.users");
            preparesStatemnt = conn.connect().prepareStatement(qyery);
            int i = person.getid();
            String s = String.valueOf(i);

            loadData();

            preparesStatemnt.close();

        }

        catch(SQLException ex){

        }

    }
















    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
    }
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        new Utility().go_Stock(event);
    }
    @FXML
    public void open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);
    }
    @FXML
    public void open_Client_Window(Event event) throws IOException {

        new Utility().go_Client(event);
    }
    // Caisse
    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }
    // Open New Fournisseur Form
    @FXML
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException{

        new Utility().show_New_Fournisseur_Window(event);
    }
    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException{

        new Utility().show_Reglement_Window("Fournissur :",event);
    }
    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
