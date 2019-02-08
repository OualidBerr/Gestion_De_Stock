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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Fournisseur_Controller implements Initializable
{
    @FXML
    private Button new_Fournisseur_btn;
    private Button verssement_btn;

    @FXML
    public DatePicker reglement_datePicker;

    @FXML
    public  TableView<Fournisseur> Fournisseur_Table;
    @FXML
    public  TableColumn<Fournisseur,Integer> Id_column;
    public  TableColumn<Fournisseur,String> name_column;
    public  TableColumn<Fournisseur,String> adress_column;
    public  TableColumn<Fournisseur,String> telephone_column;
    public  TableColumn<Fournisseur,Double> sold_column;

    @FXML
    public TextField filterField,verssement_txt;
    public static Fournisseur Fournisseur;
    public ObservableList<Fournisseur> data;
    public ObservableList<Reglement> data_2;

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    @FXML
    public void reglement_rapid() throws SQLException{
        Fournisseur fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();
        if (!Fournisseur_Table.getSelectionModel().isEmpty()){

            double amount = Double.parseDouble(verssement_txt.getText());
            int Reglement_id ;
            int  max_id  = utility.getMax_ID("demo.fournisseur_reglement_table","id") ;
            Reglement_id = max_id +1;   // 1) Reglement id
            String note = "/";  // 5) Note
            String fournisseur_Name = fournisseur.getFournisseurName(); // 6) Fournisseur Name
            String mode = "Espece"; // 7) Mode
            String date = reglement_datePicker.getValue().toString(); // 8 payement date
            int fournisseurID = fournisseur.getFournisseurId(); //9) FournisseurID
            double old_Sold =  fournisseur.getFournisseurSold(); // 3) Old Sold
            double new_sold = old_Sold - amount; // 4) new sold






            if (!verssement_txt.getText().isEmpty()){

                String query =
                        "INSERT INTO demo.fournisseur_reglement_table"                +
                                " (id,name,date,mode,amount,oldsold,sold,note,fournisseurID)" +
                                " VALUES (?,?,?,?,?,?,?,?,?)"                                 ;

                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt   (1, Reglement_id);
                preparesStatemnt.setString(2, fournisseur_Name);
                preparesStatemnt.setString(3, date);
                preparesStatemnt.setString(4, mode);
                preparesStatemnt.setDouble(5, amount);
                preparesStatemnt.setDouble(6, old_Sold);
                preparesStatemnt.setDouble(7, new_sold);
                preparesStatemnt.setString(8, note);
                preparesStatemnt.setInt   (9, fournisseurID);
                preparesStatemnt.execute();

                String query_sold = "UPDATE demo.fournisseur_table SET sold =? Where id="+fournisseurID;
                preparesStatemnt = conn.connect().prepareStatement(query_sold);
                preparesStatemnt.setDouble(1,new_sold);
                preparesStatemnt.executeUpdate();

                utility.showAlert("Verssement : " + amount + " DZD"+ " received !");
                preparesStatemnt.close();
                loadData();
                verssement_txt.clear();
                conn.connect().close();
            }

           }


    }
    @FXML
    public void fournisseurSearchThread( ) throws SQLException{

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
            ResultSet rs = cnn.createStatement().executeQuery("SELECT id,name,address,telephone,ROUND(sold, 2) FROM demo.fournisseur_table");
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
        filterField.clear();
        cnn.close();
        verssement_txt.setVisible(false);

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

            try{
                Fournisseur fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();
                int   fournisseurID = fournisseur.getFournisseurId();
                String query = "DELETE FROM demo.fournisseur_table WHERE id ="+fournisseurID;

                preparesStatemnt = conn.connect().prepareStatement(query);
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
    public void open_Reglement_Form(Event event) throws IOException, SQLException {

        if(! Fournisseur_Table.getSelectionModel().isEmpty() ) {
            Fournisseur fournisseur =  Fournisseur_Table.getSelectionModel().getSelectedItem();

            Reglement_Controller.FOURNISSEUR_NAME     =   fournisseur.getFournisseurName()   ;
            Reglement_Controller.FOURNISSEUR_ADDESS   =   fournisseur.getFournisseurAdress()   ;
            Reglement_Controller.FOURNISSEUR_PHONE    =   fournisseur.getFournisseurTelephone() ;
            Reglement_Controller.FOURNISSEUR_ID       =   fournisseur.getFournisseurId()  ;
            Reglement_Controller.FOURNISSEUR_OLD_SOLD =   fournisseur.getFournisseurSold()  ;


            String Titel = "Fournisseur : " +fournisseur.getFournisseurName()+ "    Address : " +fournisseur.getFournisseurAdress()+ "  Numero de Telephone : "+fournisseur.getFournisseurTelephone() ;

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

    public void add_One() throws SQLException {

        int  max_id  = utility.getMax_ID("demo.order_table","id") ;

        int Id= max_id + 1;

        String query ="INSERT INTO demo.order_table (id) VALUES ("+Id+") ";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.execute();
        preparesStatemnt.close();

        utility.showAlert("One added");
        System.out.println(query);


    }


    @FXML
    public void open_Bon_Fournissur_Form() throws IOException, SQLException {

         ArrayList<String> data = null;

        if(!Fournisseur_Table.getSelectionModel().isEmpty())
            {
            Fournisseur fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();

                Connection cnn = conn.connect();

                try{
                    data = new ArrayList<String>();
                    ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table where fournisseurID="+fournisseur.getFournisseurId());
                    while(rs.next()){
                        data.add(rs.getString(3) );
                    }
                }
                catch(SQLException eX){
                    System.out.println("error ! Not Connected to Db****");
                }

                cnn.close();

                Bon_Command_Fournisseur_Controller.FOURNISSEUR_ID   = fournisseur.getFournisseurId();
                Bon_Command_Fournisseur_Controller.FOURNISSEUR_NAME = fournisseur.getFournisseurName();
                Bon_Command_Fournisseur_Controller.data_2 = data;

                add_One();
                utility.show_Bon_Fournisseur_Window(fournisseur.getFournisseurName());

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
            Fournisseur fournisseur = Fournisseur_Table.getSelectionModel().getSelectedItem();

            Bon_Fournisseur_Global_Controller.FOURNISSEUR_ID = fournisseur.getFournisseurId();
            utility.show_Bon_Fournisseur_Global_Window(fournisseur.getFournisseurName());

        }

        else
        {
            utility.showAlert("No Fournisseur is selected");
        }


    }


        // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }
    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case V :
                open_Reglement_Form(event); break;
            case S:
                open_Stock_Window(event); break;
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
                    goBack_To_Home_Window(event);break;
            case P:
                    open_Product_Window(event);break;
            case F5:
                loadData();break;
            case M:
                show_Edit_Window(event);break;

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)  {



        reglement_datePicker.setValue(LocalDate.now());

        verssement_txt.setVisible(false);
        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
