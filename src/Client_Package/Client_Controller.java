package Client_Package;





import Bon_Command_Package.Bon_Client_Global_Controller;
import Bon_Command_Package.Bon_Command_Client_Controller;
import Bon_Command_Package.Bon_Command_Fournisseur_Controller;
import Reglement_Package.Reglement_Controller;
import Utilities_Package.*;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
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
    public TableView<Person> client_table;
    @FXML
    public TableColumn<Person,Integer> id_colomn;
    @FXML
    public TableColumn<Person,String> name_colomn;
    @FXML
    public TableColumn<Person,String> address_colomn;
    @FXML
    public TableColumn<Person,String> phone_colomn;
    @FXML
    public TableColumn<Person,String> period_colomn;
    @FXML
    public TableColumn<Person,Double> soldmax_colomn;
    @FXML
    public TableColumn<Person,String> registre_colomn;
    @FXML
    public TableColumn<Person,Double> sold_colomn;
    @FXML
    public Button bon_command_btn = new Button();
    @FXML
    public Button bon_command_Global_btn = new Button();
    @FXML
    public Button closeButton;
    @FXML
    public Label client_lb;


    public ObservableList<Person> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    Notification notification = new Notification();
    // OPEN NEW BON


    @FXML
    public void open_Bon_Command_Global() throws IOException {

        if (!client_table.getSelectionModel().isEmpty()){

            Person client = client_table.getSelectionModel().getSelectedItem();

            Bon_Client_Global_Controller.CLIENT_ID        = client.getId();
            Bon_Client_Global_Controller.CLIENT_NAME      = client.getName();
            Bon_Client_Global_Controller.CLIENT_REGISTRE  =  client.getRegistre();
            Bon_Client_Global_Controller.CLIENT_PHONE     = client.getTelephone();
            Bon_Client_Global_Controller.CLIENT_ADDRESS   = client.getAddress();

            utility.show_Bon_Client_Global_Window("oualid");

        }




    }
    @FXML
    public void open_Bon_Commend_Client(Event event) throws SQLException, IOException {

        if (!client_table.getSelectionModel().isEmpty())
        {
            Person client = client_table.getSelectionModel().getSelectedItem();

            int id_bon = utility.getMax_ID("demo.bon_table","id")+1;
            double total =0.0;
            String date = LocalDate.now().toString();
            int clientID = client.getId();
            String query = "INSERT INTO demo.bon_table (id,total,date,personID) Values (?,?,?,?)";


            Bon_Command_Client_Controller.ID       = client.getId();
            Bon_Command_Client_Controller.NAME     = client.getName();
            Bon_Command_Client_Controller.ADDRESS  = client.getAddress();
            Bon_Command_Client_Controller.PHONE    = client.getTelephone();
            Bon_Command_Client_Controller.BON_ID   = utility.getMax_ID("demo.bon_table","id")+1;
            Bon_Command_Client_Controller.SOLD     = client.getSold();

            try {
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt   (1, id_bon);
                preparesStatemnt.setDouble(2, total);
                preparesStatemnt.setString(3, date);
                preparesStatemnt.setInt   (4, clientID);
                preparesStatemnt.execute();
                preparesStatemnt.close();
                conn.connect().close();
                new Utility().go_Bon_Command(event);

            }
            catch (Exception e){ e.printStackTrace(); }
            finally {
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
            }

        }
        else if (client_table.getSelectionModel().isEmpty()){
            notification.show_Warrning("No Client is Selected from the table");
        }


    }
    @FXML
    public void reglement_rapid() throws SQLException{
        Person client = client_table.getSelectionModel().getSelectedItem();
        if (!client_table.getSelectionModel().isEmpty()){

            double amount = Double.parseDouble(verssement_txt.getText());
            int Reglement_id ;
            int  max_id  = utility.getMax_ID("demo.person_reglement_table","id") ;
            Reglement_id = max_id +1;   // 1) Reglement id
            String note = "/";  // 5) Note

            String mode = "Espece"; // 7) Mode
            String date = reglement_datePicker.getValue().toString(); // 8 payement date
            int clientID = client.getId(); //9) clientID
            double old_Sold =  client.getSold(); // 3) Old Sold
            double new_sold = old_Sold - amount; // 4) new sold
            double new_total=0.02;
            int personType    =  utility.getPerson_Type(clientID);
            double old_total  = utility.get_caisse_total();


            if ( personType == 1 )
            {
                new_total = old_total + amount;  // paiment
            }
            else if (personType == 2)
            {
                new_total = old_total - amount;   // verssement
            }

            if (!verssement_txt.getText().isEmpty()){

                String query = "INSERT INTO demo.person_reglement_table " +
                        "(id,amount,old_sold,sold,mode,date,note,personID,old_total_caisse,total_caisse) VALUES (?,?,?,?,?,?,?,?,?,?)";

                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setInt   (1, Reglement_id);
                preparesStatemnt.setDouble(2, amount);
                preparesStatemnt.setDouble(3, old_Sold);
                preparesStatemnt.setDouble(4,new_sold);
                preparesStatemnt.setString(5, mode);
                preparesStatemnt.setString(6, date);
                preparesStatemnt.setString(7, note);
                preparesStatemnt.setInt   (8, clientID);
                preparesStatemnt.setDouble(9, old_total);
                preparesStatemnt.setDouble(10, new_total);
                preparesStatemnt.execute();
                preparesStatemnt.close();
                conn.connect().close();
                utility.update_Caisse_total(new_total);
                String query_sold = "UPDATE demo.person_table SET sold =? Where id="+clientID;
                preparesStatemnt = conn.connect().prepareStatement(query_sold);
                preparesStatemnt.setDouble(1,new_sold);
                preparesStatemnt.executeUpdate();
                notification.show_Confirmation("Verssement : " + amount + " DZD"+ " received !");
                loadData();
                verssement_txt.clear();

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
        FilteredList<Person> filteredData = new FilteredList<>(data, p -> true);

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
        SortedList<Person> sortedData = new SortedList<>(filteredData);

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
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.person_table where PersonType="+ PersonType.Active_Client);
            while(rs.next()){
                data.add(new Person(   rs.getInt(   1),
                                       rs.getString(2),
                                       rs.getString(3),
                                       rs.getString(4),
                                       rs.getDouble   (5),
                                       rs.getDouble(6),
                                       rs.getInt(7),
                                       rs.getString(8)));
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
                Person client = client_table.getSelectionModel().getSelectedItem();
                int   clientId = client.getId();
                String query = "DELETE FROM demo.person_table WHERE id ="+clientId;


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.executeUpdate();
                    preparesStatemnt.close();
                    loadData();
                    notification.show_Confirmation("Client has been deleted");
                    conn.connect().close();
                }

                else {
                    // ... user chose CANCEL or closed the dialog


                }


            }

            catch(SQLException e){
                e.printStackTrace();
            }

        }

        else {

            notification.show_Confirmation("Nothing is selected");
        }



    }
    @FXML
    public void Open_update_Client_Window() throws IOException {

        if (!client_table.getSelectionModel().isEmpty()){

            Person client = client_table.getSelectionModel().getSelectedItem();

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

        closeButtonAction();
    }
    // Open New Client Form
    @FXML
    public void open_Add_New_Client_Form(Event event) throws IOException{

        New_Client_Controller.edit_button_Visibility = false;
        New_Client_Controller.add_button_Visibility  = true;

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
    @FXML
    public void open_Reglement_Form(Event event) throws IOException, SQLException {

        if(! client_table.getSelectionModel().isEmpty() ) {
            Person client =  client_table.getSelectionModel().getSelectedItem();

            Reglement_Controller.FOURNISSEUR_NAME     =   client.getName()   ;
            Reglement_Controller.FOURNISSEUR_ADDESS   =   client.getAddress()   ;
            Reglement_Controller.FOURNISSEUR_PHONE    =   client.getTelephone() ;
            Reglement_Controller.FOURNISSEUR_ID       =   client.getId()  ;
            Reglement_Controller.FOURNISSEUR_OLD_SOLD =   client.getSold()  ;


            String Titel = "Client : "+client.getName()+"    Address : "+client.getAddress()+"  Numero de Telephone : "+client.getTelephone() ;

            new Utility().show_Reglement_Window(Titel,event);
        }
        else
        {
            utility.showAlert("Nothing is Selected");
        }


    }
    @FXML
    public void showOnClick() {

        if ( !client_table.getSelectionModel().isEmpty()  ){

            Person client = client_table.getSelectionModel().getSelectedItem();
            String client_Name = client.getName();
            client_lb.setVisible(true);
            client_lb.setText(client_Name);
            verssement_txt.setVisible(true);
        }

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

    public void delet_empty_bon() throws SQLException{

        String query = "Delete from demo.bon_table where total=0";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.executeUpdate();
        preparesStatemnt.close();
        conn.connect().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client_lb.setVisible(false);
        /////// Value changed listener in the Table
        client_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        showOnClick();
                    }
                }
        );

        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Search Box");
        tooltip.setStyle("-fx-background-color: brown");

        filterField.setTooltip(tooltip);


        reglement_datePicker.setValue(LocalDate.now());
        verssement_txt.setVisible(false);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
