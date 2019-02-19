package Fournisseur_Package;

import Bon_Command_Package.Bon_Command_Fournisseur_Controller;
import Bon_Command_Package.Bon_Fournisseur_Global_Controller;
import Reglement_Package.Reglement_Controller;
import Utilities_Package.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Fournisseur_Controller implements Initializable
{
    @FXML
    private Button new_Fournisseur_btn;
    private Button verssement_btn;
    @FXML
    private Button closeButton;

    @FXML
    public DatePicker reglement_datePicker;

    @FXML
    public  TableView<Person> Fournisseur_Table;
    @FXML
    public  TableColumn<Person,Integer> Id_column;
    public  TableColumn<Person,String> name_column;
    public  TableColumn<Person,String> adress_column;
    public  TableColumn<Person,String> telephone_column;
    public  TableColumn<Person,Double> sold_column;
    public  TableColumn<Person,Double> registre_column;

    @FXML
    public TextField filterField,verssement_txt;
    public static Fournisseur Fournisseur;
    public ObservableList<Person> data;
    public ObservableList<Person> data_2;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    public final int   Fournisseur_Active = PersonType.Active_Fournisseur;
    public final int   Fournisseur_UnActive = PersonType.Deactivated_Fournisseur;

    @FXML
    public void reglement_rapid() throws SQLException{
        Person fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();
        if (!Fournisseur_Table.getSelectionModel().isEmpty()){

            double amount = Double.parseDouble(verssement_txt.getText());
            int Reglement_id ;
            int  max_id  = utility.getMax_ID("demo.person_reglement_table","id") ;
            Reglement_id = max_id +1;   // 1) Reglement id
            String note = "/";  // 5) Note
            String mode = "Espece"; // 7) Mode
            String date = reglement_datePicker.getValue().toString(); // 8 payement date
            int fournisseurID = fournisseur.getId(); //9) FournisseurID
            double old_Sold =  fournisseur.getSold(); // 3) Old Sold
            double new_sold = old_Sold - amount; // 4) new sold

            try{

                if (!verssement_txt.getText().isEmpty()){

                    String query = "INSERT INTO demo.person_reglement_table " +
                            "(id,amount,old_sold,sold,mode,date,note,personID) VALUES (?,?,?,?,?,?,?,?)";

                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.setInt   (1, Reglement_id);
                    preparesStatemnt.setDouble(2, amount);
                    preparesStatemnt.setDouble(3, old_Sold);
                    preparesStatemnt.setDouble(4,new_sold);
                    preparesStatemnt.setString(5, mode);
                    preparesStatemnt.setString(6, date);
                    preparesStatemnt.setString(7, note);
                    preparesStatemnt.setInt   (8, fournisseurID);
                    preparesStatemnt.execute();
                    preparesStatemnt.close();
                    conn.connect().close();

                    String query_sold = "UPDATE demo.person_table SET sold =? Where id="+fournisseurID;
                    preparesStatemnt = conn.connect().prepareStatement(query_sold);
                    preparesStatemnt.setDouble(1,new_sold);
                    preparesStatemnt.executeUpdate();

                    utility.show_TrayNotification("Verssement : " + amount + " DZD"+ " received !");
                    loadData();
                    verssement_txt.clear();

                }

                }
            catch (Exception ex){ex.printStackTrace();}

            finally {
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                    }
           }
    }
    @FXML
    public void fournisseurSearchThread( ) throws SQLException{

        name_column.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        adress_column.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        telephone_column.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fournisseur -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (fournisseur.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (fournisseur.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches adress.

                }
                else if (fournisseur.getTelephone().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.

                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Person> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(Fournisseur_Table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        Fournisseur_Table.setItems(sortedData);


    }
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        ResultSet rs = null;

        try{

            data = FXCollections.observableArrayList();
             rs = cnn.createStatement().executeQuery("SELECT id,name,address,telephone,registre,sold FROM demo.person_table where PersonType="+PersonType.Active_Fournisseur);
             while(rs.next()){
                data.add(new Person(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)));
               }
            }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
          }

        finally {

            Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
            adress_column.setCellValueFactory(new PropertyValueFactory<>("address"));
            telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
            registre_column.setCellValueFactory(new PropertyValueFactory<>("registre"));

            Fournisseur_Table.setItems(data);

            filterField.clear();
            verssement_txt.setVisible(false);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}

            }




    }
    @FXML
    public void Load_Unactive_Fournisseur() throws SQLException {

        Connection cnn = conn.connect();
        ResultSet rs = null;

        try{

            data = FXCollections.observableArrayList();
            rs = cnn.createStatement().executeQuery("SELECT id,name,address,telephone,registre,sold FROM demo.person_table where PersonType="+PersonType.Deactivated_Fournisseur);
            while(rs.next()){
                data.add(new Person(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDouble(6)));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {

            Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
            adress_column.setCellValueFactory(new PropertyValueFactory<>("address"));
            telephone_column.setCellValueFactory(new PropertyValueFactory<>("telephone"));
            sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
            registre_column.setCellValueFactory(new PropertyValueFactory<>("registre"));

            Fournisseur_Table.setItems(null);
            Fournisseur_Table.setItems(data);
            filterField.clear();
            verssement_txt.setVisible(false);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}

        }



    }
    @FXML
    public void showOnClick() {

       if ( !Fournisseur_Table.getSelectionModel().isEmpty()  ){
           verssement_txt.setVisible(true);
       }

    }
    @FXML
    private void delete_Fournisseur() throws SQLException{

        if(! Fournisseur_Table.getSelectionModel().isEmpty()    ) {


            try {
                Person fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();
                int fournisseurID = fournisseur.getId();

                String query = "UPDATE demo.person_table SET PersonType=? Where id=" + fournisseurID;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt(1, Fournisseur_UnActive);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    preparesStatemnt.executeUpdate();
                    preparesStatemnt.close();
                    conn.connect().close();
                    loadData();
                    utility.show_TrayNotification("Fournisseur deleted successfully!");

                   }

                else {
                    // ... user chose CANCEL or closed the dialog
                    utility.show_TrayNotification("was not deleted");

                   }




                   }

            catch (Exception e) { e.printStackTrace(); }

            finally {

                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
            }



        }


        }
    @FXML
    public void show_Edit_Window(Event e)throws IOException{

          if(! Fournisseur_Table.getSelectionModel().isEmpty() ) {
              Person fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();

              New_Fournisseur_Controller.NAME =    fournisseur.getName()       ;
              New_Fournisseur_Controller.ADDRESS = fournisseur.getAddress()   ;
              New_Fournisseur_Controller.PHONE =   fournisseur.getTelephone();  ;
              New_Fournisseur_Controller.ID =   fournisseur.getId()  ;
              New_Fournisseur_Controller.REGISTRE = fournisseur.getRegistre();
              New_Fournisseur_Controller.TITLE_LB = "Modifier Fournisseur Information";

              New_Fournisseur_Controller.add_button_Visibility = false;
              New_Fournisseur_Controller.edit_button_Visibility = true;

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
    public void open_Add_New_Fournisseur_Form(Event event) throws IOException{

        New_Fournisseur_Controller.add_button_Visibility = true;
        New_Fournisseur_Controller.edit_button_Visibility = false;
        New_Fournisseur_Controller.TITLE_LB = "Ajouter Nouveau Fournisseur";
        new Utility().show_New_Fournisseur_Window(event);
    }
    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException, SQLException {

        if(! Fournisseur_Table.getSelectionModel().isEmpty() ) {
            Person fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();

            Reglement_Controller.FOURNISSEUR_NAME     =   fournisseur.getName()   ;
            Reglement_Controller.FOURNISSEUR_ADDESS   =   fournisseur.getAddress()   ;
            Reglement_Controller.FOURNISSEUR_PHONE    =   fournisseur.getTelephone() ;
            Reglement_Controller.FOURNISSEUR_ID       =   fournisseur.getId()  ;
            Reglement_Controller.FOURNISSEUR_OLD_SOLD =   fournisseur.getSold()  ;


            String Titel = "Fournisseur : "+fournisseur.getName()+"    Address : "+fournisseur.getAddress()+"  Numero de Telephone : "+fournisseur.getTelephone() ;

            new Utility().show_Reglement_Window(Titel,event);
        }
        else
        {
            utility.showAlert("Nothing is Selected");
        }
        Reglement_Controller.FOURNISSEUR_NAME =    null;
        Reglement_Controller.FOURNISSEUR_ADDESS =  null;
        Reglement_Controller.FOURNISSEUR_PHONE =   null;
        Reglement_Controller.FOURNISSEUR_ID    =     0 ;
        Reglement_Controller.FOURNISSEUR_OLD_SOLD = 0.25;

    }
    @FXML
    public void open_Bon_Fournissur_Form() throws IOException, SQLException {


         ArrayList<String> data = null;

        if(!Fournisseur_Table.getSelectionModel().isEmpty())
            {
            Person fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();

                Connection cnn = conn.connect();

                try{
                    data = new ArrayList<>();
                    ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table where personID="+fournisseur.getId());
                    while(rs.next()){
                        data.add(rs.getString(3) );
                    }
                }
                catch(SQLException eX){
                    System.out.println("error ! Not Connected to Db****");
                }

                cnn.close();

                Bon_Command_Fournisseur_Controller.FOURNISSEUR_ID   = fournisseur.getId();
                Bon_Command_Fournisseur_Controller.FOURNISSEUR_NAME = fournisseur.getName();
                Bon_Command_Fournisseur_Controller.BON_ID = utility.getMax_ID("demo.bon_table","id")+1;
                Bon_Command_Fournisseur_Controller.data_2 = data;

                // OPEN NEW BON
                int id_bon = utility.getMax_ID("demo.bon_table","id")+1;
                double total =0.0;
                String date = LocalDate.now().toString();
                int fournisseurId = fournisseur.getId();
                String query = "INSERT INTO demo.bon_table (id,total,date,personID) Values (?,?,?,?)";

                try {
                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.setInt   (1, id_bon);
                    preparesStatemnt.setDouble(2, total);
                    preparesStatemnt.setString(3, date);
                    preparesStatemnt.setInt   (4, fournisseurId);
                    preparesStatemnt.execute();
                    preparesStatemnt.close();
                    conn.connect().close();
                   }
                catch (Exception e){ e.printStackTrace(); }
                finally {
                    if (conn.connect()   != null) {conn.connect().close();}
                    if (preparesStatemnt != null) {preparesStatemnt.close();}

                }




                utility.show_Bon_Fournisseur_Window(fournisseur.getName());

            }


        else
            {
                utility.showAlert("Nothing is Slected");
            }

    }
    @FXML
    public void open_Bon_Fournisseur_Global() throws IOException, SQLException{

        if(!Fournisseur_Table.getSelectionModel().isEmpty())
        {
            Person fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();

            Bon_Fournisseur_Global_Controller.FOURNISSEUR_ID = fournisseur.getId();
            Bon_Fournisseur_Global_Controller.FOURNISSEUR_NAME = fournisseur.getName();
            Bon_Fournisseur_Global_Controller.FOURNISSEUR_ADDRESS = fournisseur.getAddress();
            Bon_Fournisseur_Global_Controller.FOURNISSEUR_PHONE = fournisseur.getTelephone();

            utility.show_Bon_Fournisseur_Global_Window(fournisseur.getName());

        }

        else
        {
            utility.showAlert("No Fournisseur is selected");
        }


    }
        // Logout

    public void delet_empty_bon() throws SQLException{

        String query = "Delete from demo.bon_table where total=0";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.executeUpdate();
        preparesStatemnt.close();
        conn.connect().close();
    }
    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case V :
                open_Reglement_Form(event); break;

            case ENTER:
                reglement_rapid();
               break;
            case N:
                     open_Add_New_Fournisseur_Form( event);break;
            case SHIFT:
                     show_Edit_Window(event);break;
            case DELETE:
                    delete_Fournisseur();break;
            case H:
                closeButtonAction();   break;
            case F5:
                loadData();break;
            case M:
                show_Edit_Window(event);break;

        }
    }
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do

        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        try {
            delet_empty_bon();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reglement_datePicker.setValue(LocalDate.now());

        verssement_txt.setVisible(false);
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
