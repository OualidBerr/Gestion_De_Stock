package Client_Package;

import Reglement_Package.Client_Reglement_Controller;
import Reglement_Package.Reglement_Controller;
import Utilities_Package.Client;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Client_Controller implements Initializable {
    @FXML
    public TextField filterField,verssement_txt;
    @FXML
    public DatePicker reglement_datePicker;

    @FXML
    public Button delete_btn;
    @FXML
    public Button edit_btn;
    @FXML
    public TableView<Client> client_table;
    @FXML
    public TableColumn<Client,Integer> id_colomn;
    @FXML
    public TableColumn<Client,String> name_colomn;
    @FXML
    public TableColumn<Client,String> address_colomn;
    @FXML
    public TableColumn<Client,String> phone_colomn;
    @FXML
    public TableColumn<Client,String> period_colomn;
    @FXML
    public TableColumn<Client,Double> soldmax_colomn;
    @FXML
    public TableColumn<Client,String> registre_colomn;
    @FXML
    public TableColumn<Client,Double> sold_colomn;

    public ObservableList<Client> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    @FXML
    public void reglement_rapid() throws SQLException{
        Client client = client_table.getSelectionModel().getSelectedItem();
        if (!client_table.getSelectionModel().isEmpty()){

            double amount = Double.parseDouble(verssement_txt.getText());
            int Reglement_id ;
            int  max_id  = utility.getMax_ID("demo.client_reglement_table","id") ;
            Reglement_id = max_id +1;   // 1) Reglement id
            String note = "/";  // 5) Note
            String client_Name = client.getName(); // 6) client Name
            String mode = "Espece"; // 7) Mode
            String date = reglement_datePicker.getValue().toString(); // 8 payement date
            int clientID = client.getId(); //9) clientID
            double old_Sold =  client.getSold(); // 3) Old Sold
            double new_sold = old_Sold - amount; // 4) new sold

            if (!verssement_txt.getText().isEmpty()){

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

        name_colomn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        address_colomn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        phone_colomn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());
        registre_colomn.setCellValueFactory(cellData -> cellData.getValue().telephoneProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Client> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(client -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (client.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches adress.

                }
                else if (client.getTelephone().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches phone.
                }
                else if (client.getRegistre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches registre.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Client> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(client_table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        client_table.setItems(sortedData);


    }

    @FXML
    public void loadData()throws SQLException {

        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.client_table");
            while(rs.next()){
                data.add(new Client(   rs.getInt(   1),
                                       rs.getString(2),
                                       rs.getString(3),
                                       rs.getString(4),
                                       rs.getInt   (5),
                                       rs.getDouble(6),
                                       rs.getString(7),
                                       rs.getDouble(8)));
            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }


        id_colomn.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_colomn.setCellValueFactory(new PropertyValueFactory<>("name"));
        address_colomn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone_colomn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        sold_colomn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        soldmax_colomn.setCellValueFactory(new PropertyValueFactory<>("max_sold"));
        registre_colomn.setCellValueFactory(new PropertyValueFactory<>("registre"));
        period_colomn.setCellValueFactory(new PropertyValueFactory<>("period"));

        client_table.setItems(data);
        verssement_txt.setVisible(false);
        cnn.close();



    }

    @FXML
    public void delete_Client()throws SQLException{

        if(! client_table.getSelectionModel().isEmpty()    ) {

            try{
                Client client = client_table.getSelectionModel().getSelectedItem();
                int   fournisseurID = client.getId();
                String query = "DELETE FROM demo.client_table WHERE id ="+fournisseurID;

                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.executeUpdate();
                preparesStatemnt.close();
                loadData();
                utility.showAlert("Client has been deleted");
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
    public void Open_update_Client_Window() throws IOException {

        if (!client_table.getSelectionModel().isEmpty()){

            Client client = client_table.getSelectionModel().getSelectedItem();

             New_Client_Controller.ID         = client.getId();
             New_Client_Controller.NAME       = client.getName();
             New_Client_Controller.ADDRESS    = client.getAddress();
             New_Client_Controller.TELEPHONE  = client.getTelephone();
             New_Client_Controller.PERIOD     = client.getPeriod();
             New_Client_Controller.REGISTRE   = client.getRegistre();
             New_Client_Controller.MAX_SOLD   = client.getMax_sold();
             New_Client_Controller.edit_button_Visibility =true;
             New_Client_Controller.add_button_Visibility =false;
            utility.show_update_Client_Window();

           }

      else {
            utility.showAlert("Nothing is Selected");
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
    public void open_Founisseur_Window(Event event) throws IOException {

        new Utility().go_Fournisseur(event);

    }
    // Caisse
    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }
    // Open New Client Form
    @FXML
    public void open_Add_New_Client_Form(Event event) throws IOException{
        New_Client_Controller.edit_button_Visibility =false;
        New_Client_Controller.add_button_Visibility =true;

        New_Client_Controller.ID         = 0;
        New_Client_Controller.NAME       = null;
        New_Client_Controller.ADDRESS    = null;
        New_Client_Controller.TELEPHONE  = null;
        New_Client_Controller.PERIOD     = 0;
        New_Client_Controller.REGISTRE   = null;
        New_Client_Controller.MAX_SOLD   = 0.0;
         New_Client_Controller.NAME=     null;

        new Utility().show_New_Client_Window(event);
    }
    // Reglement Form
    @FXML
    public void open_Reglement_Form(Event event) throws IOException{

        Client client = client_table.getSelectionModel().getSelectedItem();

        if (!client_table.getSelectionModel().isEmpty()){

            Client_Reglement_Controller.CLIENT_NAME     =   client.getName()   ;
            Client_Reglement_Controller.CLIENT_ADDESS   =   client.getAddress() ;
            Client_Reglement_Controller.CLIENT_PHONE    =   client.getTelephone() ;
            Client_Reglement_Controller.CLIENT_ID       =   client.getId()  ;
            Client_Reglement_Controller.CLIENT_OLD_SOLD =   client.getSold() ;


            new Utility().show_Client_Reglement_Window("Client :",event);

           }

        else
        {
            utility.showAlert("Nothing is Selected");
        }


    }
    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }
    @FXML
    public void showOnClick() {

        if ( !client_table.getSelectionModel().isEmpty()  ){
            verssement_txt.setVisible(true);
        }

    }

    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
                open_Founisseur_Window(event); break;
            case C:
                Open_Caisse_Window(event);break;
            case H:
                goBack_To_Home_Window(event);break;
            case P:
                open_Product_Window(event);break;
            case N:
                open_Add_New_Client_Form(event);break;
            case DELETE:
                delete_Client();break;
            case M:
                Open_update_Client_Window(); break;
            case F5:
               loadData();break;
            case ENTER:
                reglement_rapid();break;

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reglement_datePicker.setValue(LocalDate.now());
        verssement_txt.setVisible(false);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
