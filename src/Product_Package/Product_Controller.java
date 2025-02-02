package Product_Package;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class Product_Controller implements Initializable {

    @FXML
    public TableView<Product> product_Table;
    @FXML
    public TableColumn<Product,Integer> id_column;
    public TableColumn<Product,String>  ref_column;
    public TableColumn<Product,String>  des_column;
    public TableColumn<Product,Integer> nbr_pcs_crt__column;
    public TableColumn<Product,Double> quantity_column;
    public TableColumn<Product,Integer> nbr_pcs_column;
    public TableColumn<Product,String>  code_bare_column;
    public TableColumn<Product,String>  date_entre_column;
    public TableColumn<Product,Integer> alert_column;
    public TableColumn<Product,String>  expiration_column;
    public TableColumn<Product,Double>  prix_achat_column;
    public TableColumn<Product,Double>  prix_vent_column;
    public TableColumn<Product,String>  fournisseur_column;
    public TableColumn<Product,Double>  value_column;
    @FXML
    public TextField search_Textfield;
    @FXML
    public Button closeButton;

    //***********************************
    // Declaration of variables
    public ObservableList<Product> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    Notification notification = new Notification();
    //************************
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quan,nbr_pcs,code_bare,DATE_FORMAT(date_entre, '%d-%m-%Y') date_entre,alert,DATE_FORMAT(expiration, '%d-%m-%Y') expiration,prix_achat,prix_vent,personID,value FROM demo.product_table");
            while(rs.next()){
                data.add(new Product(
                        rs.getInt("id"),
                        rs.getString("ref"),
                        rs.getString("des"),
                        rs.getInt("nbr_pcs_crt"),
                        rs.getDouble("quan"),
                        rs.getInt("nbr_pcs"),
                        rs.getString("code_bare"),
                        rs.getString("date_entre"),
                        rs.getInt("alert"),
                        rs.getString("expiration"),
                        rs.getDouble("prix_achat"),
                        rs.getDouble("prix_vent"),
                       utility.get_Person_Name(rs.getInt("personID")),
                        rs.getDouble("value")


                ));
            }
        }
        catch(SQLException eX){
           eX.printStackTrace();
        }

        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
        des_column.setCellValueFactory(new PropertyValueFactory<>("designiation"));
        nbr_pcs_crt__column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs_crt"));
        nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs"));
        quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        code_bare_column.setCellValueFactory(new PropertyValueFactory<>("code_bare"));
        date_entre_column.setCellValueFactory(new PropertyValueFactory<>("date_entre"));
        alert_column.setCellValueFactory(new PropertyValueFactory<>("alert"));
        expiration_column.setCellValueFactory(new PropertyValueFactory<>("expiration"));
        prix_achat_column.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
        prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_ventt"));
        fournisseur_column.setCellValueFactory(new PropertyValueFactory<>("fournisseur"));
        value_column.setCellValueFactory(new PropertyValueFactory<>("value"));

        product_Table.setItems(data);

        cnn.close();


    }

    @FXML
    public void fournisseurSearchThread( ) throws SQLException{

        ref_column.setCellValueFactory(cellData -> cellData.getValue().refProperty());
        des_column.setCellValueFactory(cellData -> cellData.getValue().designiationProperty());
        code_bare_column.setCellValueFactory(cellData -> cellData.getValue().code_bareProperty());
        expiration_column.setCellValueFactory(cellData -> cellData.getValue().expirationProperty());
        fournisseur_column.setCellValueFactory(cellData -> cellData.getValue().fournisseurProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(data, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        search_Textfield.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (product.getRef().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Ref
                } else if (product.getDesigniation().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Des.
                }
                else if (product.getCode_bare().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches code bare.
                }
                else if (product.getFournisseur().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches product.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(product_Table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        product_Table.setItems(sortedData);
    }

    // Delete
    @FXML
    private void delete_Product() throws SQLException{

        if(! product_Table.getSelectionModel().isEmpty() ) {

            try{
                String query = "DELETE FROM demo.product_table WHERE id =?";
                Product product =  product_Table.getSelectionModel().getSelectedItem();
                int i = product.getId();
                String s = String.valueOf(i);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.setString(1, s);
                    preparesStatemnt.executeUpdate();
                    preparesStatemnt.close();
                    loadData();
                    notification.show_Confirmation("Product has been deleted");
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

    }



    // Go Home Page
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        closeButtonAction();
    }
    // Go New Product
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().show_New_Product_Window(event);
    }
    // Go To Stock
    @FXML
    public void open_Stock_Window(Event event) throws IOException {

        new Utility().go_Stock(event);

    }
    // Go Client
    @FXML
    public void Open_Client_Window(Event event) throws IOException {
        new Utility().go_Client(event);
    }
    // Go Fournisseur
    @FXML
    public void Open_Fournisseur_Window(Event event) throws IOException {
        new Utility().go_Fournisseur(event);
    }
    // Go Caisse
    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }


    @FXML
    public void open_Edit_Product_Window(Event event)throws IOException {

        if(!product_Table.getSelectionModel().isEmpty() ) {

            Product product = product_Table.getSelectionModel().getSelectedItem();

            Product_Edit_Controller.ID              = product.getId()  ;
            Product_Edit_Controller.DESIGNIATON     = product.getDesigniation();
            Product_Edit_Controller.REF             = product.getRef();
            Product_Edit_Controller.CODE_BARE       = product.getCode_bare();
            Product_Edit_Controller.EXPIRATION      = product.getExpiration();
            Product_Edit_Controller.NBR_PCS         = product.getNbr_pcs();
            Product_Edit_Controller.NBR_PCS_CRT     = product.getNbr_pcs_crt();
            Product_Edit_Controller.QUANT           = product.getQuantite();
            Product_Edit_Controller.ALERT           = product.getAlert();
            Product_Edit_Controller.FOURNISSEUR     = product.getFournisseur();
            Product_Edit_Controller.Prix_ACHAT      = product.getPrix_achat();
            Product_Edit_Controller.Prix_VENT       = product.getPrix_ventt();
            Product_Edit_Controller.DATE            = product.getDate_entre();

            new Utility().show_Edit_Product_Window();
              }

        else {
            utility.showAlert("Nothing is Selected");
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
            case N:
                open_Add_New_Product_Form(event);break;
            case F:
                Open_Fournisseur_Window(event);break;
            case C:
                Open_Client_Window(event);break;
            case S:
                open_Stock_Window(event);break;
            case ALT_GRAPH:
                Open_Caisse_Window(event);break;
            case H:
                closeButtonAction();break;
            case DELETE:
                delete_Product();break;
            case M:
                open_Edit_Product_Window(event);break;
            case F5:
                loadData();break;



        }
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