package Product_Package;

import Utilities_Package.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Product_Controller implements Initializable {

    @FXML
    public TableView<Product> product_Table;
    @FXML
    public TableColumn<Product,Integer> id_column;
    public TableColumn<Product,String>  ref_column;
    public TableColumn<Product,String>  des_column;
    public TableColumn<Product,Integer> nbr_pcs_crt__column;
    public TableColumn<Product,Integer> quantity_column;
    public TableColumn<Product,Integer> nbr_pcs_column;
    public TableColumn<Product,String>  code_bare_column;
    public TableColumn<Product,String>  date_entre_column;
    public TableColumn<Product,Integer> alert_column;
    public TableColumn<Product,String>  expiration_column;
    public TableColumn<Product,Double>  prix_achat_column;
    public TableColumn<Product,Double>  prix_vent_column;
    public TableColumn<Product,String>  fournisseur_column;
//***********************************
   // Declaration of variables
    public ObservableList<Product> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();
    //************************

    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try{

            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table");
            while(rs.next()){
                data.add(new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7),rs.getString(8),rs.getInt(9),rs.getString(10), rs.getDouble(11),rs.getDouble(12),rs.getString(13)));
              }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
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

        product_Table.setItems(data);

        cnn.close();


    }


    @FXML
    private Button goHome_btn,newProduct_btn,stock_btn;

    // Go Home Page
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility( ).go_Home(event);
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
    // Log out
   @FXML
    public void log_Out_Function(Event event) throws IOException {
     new Utility().log_Out(event);
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
                goBack_To_Home_Window(event);break;

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
