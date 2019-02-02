package Fournisseur_Package;

import Reglement_Package.Reglement_Controller;
import Utilities_Package.Db_Connection;
import Utilities_Package.Fournisseur;

import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    public  TableView<Fournisseur> Fournisseur_Table;
    @FXML
    public  TableColumn<Fournisseur,Integer> Id_column;
    public  TableColumn<Fournisseur,String> name_column;
    public  TableColumn<Fournisseur,String> adress_column;
    public  TableColumn<Fournisseur,String> telephone_column;
    public  TableColumn<Fournisseur,Double> sold_column;
    @FXML
    public TextField filterField;
    public static Fournisseur Fournisseur;
    public ObservableList<Fournisseur> data;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    @FXML
    public void fournisseurSearchThread() throws SQLException{

        name_column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        adress_column.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        telephone_column.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Fournisseur> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fournisseur -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (fournisseur.getFournisseurName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (fournisseur.getFournisseurAdress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches adress.

                }
                else if (fournisseur.getFournisseurTelephone().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.

                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Fournisseur> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(Fournisseur_Table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        Fournisseur_Table.setItems(sortedData);


    }

    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.fournisseur_table");
            while(rs.next()){
                data.add(new Fournisseur(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5)));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        adress_column.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
        Fournisseur_Table.setItems(null);
        Fournisseur_Table.setItems(data);
        cnn.close();


    }
    @FXML
    public void showOnClick()throws SQLException{

        try{
            Fournisseur fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();

            String qyery = ("SELECT * FROM demo.users");
            preparesStatemnt = conn.connect().prepareStatement(qyery);
            int i = fournisseur.getFournisseurId();
            String s = String.valueOf(i);

            loadData();

            preparesStatemnt.close();

        }

        catch(SQLException ex){

        }

    }
    @FXML
    private void delete_Fournisseur() throws SQLException{

        if(! Fournisseur_Table.getSelectionModel().isEmpty()    ) {

            try{
                String query = "DELETE FROM demo.fournisseur_table WHERE id =?";
                Fournisseur fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();
                int i = fournisseur.getFournisseurId();
                String s = String.valueOf(i);
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, s);
                preparesStatemnt.executeUpdate();
                preparesStatemnt.close();
                loadData();
                utility.showAlert("User has been deleted");
                conn.connect().close();
            }

            catch(SQLException e){
                e.printStackTrace();
            }

        }

        else {

            utility.showAlert("Nothing is selected");
        }


    }
      @FXML
      public void show_Edit_Window(Event e)throws IOException{

          if(! Fournisseur_Table.getSelectionModel().isEmpty() ) {
              Fournisseur fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();

              New_Fournisseur_Controller.NAME =    fournisseur.getFournisseurName()        ;
              New_Fournisseur_Controller.ADDRESS = fournisseur.getFournisseurAdress()    ;
              New_Fournisseur_Controller.PHONE =   fournisseur.getFournisseurTelephone()   ;
              New_Fournisseur_Controller.ID =   fournisseur.getFournisseurId()   ;


              utility.show_New_Fournisseur_Window(e);

             }
          else
              {
                  utility.showAlert("Nothing is Selected");

              }

          New_Fournisseur_Controller.NAME =    null;
          New_Fournisseur_Controller.ADDRESS = null;
          New_Fournisseur_Controller.PHONE =   null;
          New_Fournisseur_Controller.ID =      0   ;


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

        if(! Fournisseur_Table.getSelectionModel().isEmpty() ) {
            Fournisseur fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();

            Reglement_Controller.FOURNISSEUR_NAME     =   fournisseur.getFournisseurName()   ;
            Reglement_Controller.FOURNISSEUR_ADDESS   =   fournisseur.getFournisseurAdress()   ;
            Reglement_Controller.FOURNISSEUR_PHONE    =   fournisseur.getFournisseurTelephone() ;
            Reglement_Controller.FOURNISSEUR_ID       =   fournisseur.getFournisseurId()  ;
            Reglement_Controller.FOURNISSEUR_OLD_SOLD =   fournisseur.getFournisseurSold()  ;

            new Utility().show_Reglement_Window("Fournissur :",event);
        }
        else
        {
            utility.showAlert("Nothing is Selected");
        }

        Reglement_Controller.FOURNISSEUR_NAME =    null;
        Reglement_Controller.FOURNISSEUR_ADDESS = null;
        Reglement_Controller.FOURNISSEUR_PHONE =   null;
        Reglement_Controller.FOURNISSEUR_ID    =     0 ;
        Reglement_Controller.FOURNISSEUR_OLD_SOLD = 0.25;

    }
    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {



        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
