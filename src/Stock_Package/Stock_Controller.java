package Stock_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.Product;
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

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Stock_Controller  implements Initializable {

    @FXML
    public TableView<Product> stock_table;
    @FXML
    public TableColumn<Product,Integer> id_column;
    public TableColumn<Product,String> ref_column;
    public TableColumn<Product,String> des_column;
    public TableColumn<Product,Double> quantity_column;
    public TableColumn<Product,Integer> nbr_pcs_crt__column;
    public TableColumn<Product,Integer> nbr_pcs_column;
    public TableColumn<Product,Double> prix_vent_column;
    public TableColumn<Product,Double> value_column;
    @FXML
    public TextField search_Textfield;
    @FXML
    public Button closeButton;

     public ObservableList<Product> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();

    @FXML
    public void fournisseurSearchThread() throws SQLException{

        ref_column.setCellValueFactory(cellData -> cellData.getValue().refProperty());
        des_column.setCellValueFactory(cellData -> cellData.getValue().designiationProperty());

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
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(stock_table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        stock_table.setItems(sortedData);
    }
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
             rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quan,nbr_pcs,prix_vent,value FROM demo.product_table");
            while(rs.next()){
                data.add(new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDouble(5),rs.getInt(6),rs.getDouble(7),rs.getDouble(8)));
            }
           }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
            }
        finally {

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_column.setCellValueFactory(new PropertyValueFactory<>("designiation"));
            nbr_pcs_crt__column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs_crt"));
            nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_ventt"));
            value_column.setCellValueFactory(new PropertyValueFactory<>("value"));
            stock_table.setItems(data);
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
         }



          }
    @FXML
    private Button goHome_btn,produit_btn,charge_btn,newProduct_btn;

   // Go Home
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        closeButtonAction();

    }
    // Go Product
    @FXML
    public void go_Back_To_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);
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
    // Caisse
    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }
    // Show Add new Product Window
    @FXML
    public void open_Add_New_Product_Form(Event event) throws IOException{

        new Utility().show_New_Product_Window(event);
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
            case F:
                Open_Fournisseur_Window(event); break;
            case C:
                Open_Client_Window(event);break;
            case H:
                goBack_To_Home_Window(event);break;
            case P:
                go_Back_To_Product_Window(event);break;
            case ALT_GRAPH:
                Open_Caisse_Window(event);break;
            case N:
                open_Add_New_Product_Form(event);break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new Utility().Button_request_focus(goHome_btn);

    }
}
