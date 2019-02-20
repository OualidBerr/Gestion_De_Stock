package Bon_Command_Package;

import Utilities_Package.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Bon_Command_Client_Controller implements Initializable {

    // Helping Table
    @FXML
    public TableView<Product> show_table;
    @FXML
    public TableColumn<Product, String>   des_show_column;
    public TableColumn<Product, Integer>  nbr_pcs_crt_show_column;
    public TableColumn<Product, Integer>  nbr_pcs_show_column;
    public TableColumn<Product, Double>   quant_show_column;
    public TableColumn<Product, Double>   prix_show_column;
    @FXML
    public TableView<Bon_Command_Client> bon_command_client_table;
    @FXML
    public TableColumn<Product, Integer> id_column;
    public TableColumn<Product, String> ref_column;
    public TableColumn<Product, String> des_column;
    public TableColumn<Product, Integer> quantity_column;
    public TableColumn<Product, Integer> nbr_pcs_crt_column;
    public TableColumn<Product, Integer> nbr_pcs_column;
    public TableColumn<Product, Double> prix_vent_column;
    public TableColumn<Product, Double> value_column;
    public TableColumn<Product, Integer> date_column;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Button show_details_btn;
    @FXML
    public Label name_lb;
    @FXML
    public Label address_lb;
    @FXML
    public Label phone_lb;
    @FXML
    public Label show_lb;
    @FXML
    public Label title_lb = new Label();
    @FXML
    public TextField product_txt;
    @FXML
    public Button closeButton;
    @FXML
    public TextField quant_txt;

    public static int ID;
    public static String NAME;
    public static String ADDRESS;
    public static String PHONE;
    public static double SOLD;
    public static int BON_ID;

    public ObservableList<Bon_Command_Client> data;
    public ObservableList<Product> show_data_List;
    public ArrayList myProduct_List = new ArrayList();
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();

    // refresh
    public void refresh(int bonID) throws SQLException {

        try {

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT d.id,p.ref,p.des,d.quant ,p.nbr_pcs_crt,d.quant*p.nbr_pcs_crt,p.prix_vent,d.value,b.date FROM demo.bon_detail_table d, demo.bon_table b, demo.product_table p where d.bonID = b.id and d.productID=p.id and d.bonID="+bonID);
            while (rs.next()) {

                data.add(new Bon_Command_Client(

                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getString(9)

                ));


            }
        } catch (SQLException eX) {
            eX.printStackTrace();
            System.out.println("error ! Not Connected to Db****");
        }

        finally {

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_column.setCellValueFactory(new PropertyValueFactory<>("des"));
            nbr_pcs_crt_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs_crt"));
            nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
            value_column.setCellValueFactory(new PropertyValueFactory<>("value"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
            bon_command_client_table.setItems(data);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }

    }
    // Load List
    public void loadData() throws SQLException {

        try {

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT d.id,p.ref,p.des,d.quant ,p.nbr_pcs_crt,d.quant*p.nbr_pcs_crt,p.prix_vent,d.value,b.date FROM demo.bon_detail_table d, demo.bon_table b, demo.product_table p where d.bonID = b.id and d.productID=p.id and d.bonID="+BON_ID);
            while (rs.next()) {

                data.add(new Bon_Command_Client(

                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getString(9)

                ));


            }
        } catch (SQLException eX) {
            System.out.println("error ! Not Connected to Db****");
        }


        finally {
            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_column.setCellValueFactory(new PropertyValueFactory<>("des"));
            nbr_pcs_crt_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs_crt"));
            nbr_pcs_column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
            quantity_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_vent_column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
            value_column.setCellValueFactory(new PropertyValueFactory<>("value"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));

            bon_command_client_table.setItems(data);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }



    }
    @FXML
    public void save_bon() throws SQLException {

        double total = get_Bon_Total(BON_ID,ID);
        // Update client Sold
        double old_sold = utility.get_Sold(ID);
        utility.update_Fournisseur_Sold(total, old_sold, ID);
        utility.show_TrayNotification("saved successfully");
         closeButtonAction();
    }
    @FXML
    public void add_New_Client_Bon() throws SQLException {
        String product = product_txt.getText();
        int productID = utility.getProduct_ID(product);
        double quant = Double.parseDouble(quant_txt.getText());
        int id_order = utility.getMax_ID("demo.bon_detail_table","id")+1;
        int product_Nbr_pcs_crt = utility.get_Product_Nbr_pcs_crt(productID);
        double prix_vent = utility.get_Product_price(productID);
        double value = quant*prix_vent*product_Nbr_pcs_crt;

        // Insert bon_detail
        if (!product_txt.getText().isEmpty()&& !quant_txt.getText().isEmpty())
          {

            String query_2 = "INSERT INTO demo.bon_detail_table (id,quant,prix_vent,value,bonID,productID) Values (?,?,?,?,?,?)";

            try {
                preparesStatemnt = conn.connect().prepareStatement(query_2);
                preparesStatemnt.setInt   (1, id_order);
                preparesStatemnt.setDouble(2, quant);
                preparesStatemnt.setDouble(3, prix_vent);
                preparesStatemnt.setDouble(4, value);
                preparesStatemnt.setInt   (5, BON_ID);
                preparesStatemnt.setInt   (6, productID);
                preparesStatemnt.execute();
                refresh(BON_ID);

              utility.total_sum_calculator(BON_ID,productID,show_lb);
                preparesStatemnt.close();
                utility.showAlert("Bon added successfully!");
                conn.connect().close();

                   }
            catch (Exception e){ e.printStackTrace(); }
            finally {

                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (rs != null) {rs.close();}
                    }
            }


        //Update Total
        double total=0.25;
        try{
            total=0.25;
            try{
                String Query =" SELECT sum(value)  FROM demo.bon_detail_table where bonID="+BON_ID   ;
                ResultSet   rs = conn.connect().createStatement().executeQuery(Query);
                if (rs.next()){
                    total = rs.getDouble(1);
                }
            }

            catch (Exception ex){ex.printStackTrace();}
            String Query  = "UPDATE demo.bon_table SET total =? Where id="+BON_ID;
            preparesStatemnt = conn.connect().prepareStatement(Query);
            preparesStatemnt.setDouble   (1,  total);
            preparesStatemnt.executeUpdate();
            preparesStatemnt.close();
            conn.connect().close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
              }

        // Update product

        int product_Old_Quantity = utility.get_Product_quantity(productID);
        double new_product_quantity= product_Old_Quantity -  quant  ;
        int new_Nbr_Pcs = (int) new_product_quantity*utility.get_Product_Nbr_pcs_crt(productID);
        double new_value = new_Nbr_Pcs*prix_vent;

        try{

            String Query  = "UPDATE demo.product_table SET quan =?,nbr_pcs=?,date_entre=?,value=? Where id="+productID;

            preparesStatemnt = conn.connect().prepareStatement(Query);
            preparesStatemnt.setDouble      (1,  new_product_quantity);
            preparesStatemnt.setInt      (2,  new_Nbr_Pcs);
            preparesStatemnt.setString   (3, LocalDate.now().toString());
            preparesStatemnt.setDouble   (4,  new_value);
            preparesStatemnt.executeUpdate();
            refresh( BON_ID);

            preparesStatemnt.close();
            conn.connect().close();
           }
        catch (Exception e){
            e.printStackTrace();
          }
        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
          }

           quant_txt.clear();
           product_txt.clear();
           utility.setTextFieldFocus(product_txt);

             }
      @FXML
      public void show_product_details() throws SQLException{

        if (!product_txt.getText().isEmpty()){

            fill_List(product_txt.getText());
            title_lb.setVisible(true);
            show_table.setVisible(true);
            utility.setTextFieldFocus(quant_txt);
            }



        }
    // fill product list
    public void fill_List(String productName) throws SQLException {


        Connection cnn = conn.connect();
        try{

            show_data_List = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table where des='"+productName+"'");
            while(rs.next()){

                show_data_List.add(new Product(

                        rs.getDouble(12),
                        rs.getInt(4),
                        rs.getInt   (5),
                        rs.getInt   (6),
                        rs.getString(3)

                               ));
            }

        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {

            des_show_column.setCellValueFactory(new PropertyValueFactory<>("designiation"));
            nbr_pcs_crt_show_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs_crt"));
            nbr_pcs_show_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs"));
            quant_show_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            prix_show_column.setCellValueFactory(new PropertyValueFactory<>("prix_ventt"));
            show_table.setItems(show_data_List);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }



    }
     public void clear(){
        quant_txt.clear();
        product_txt.clear();

  }
       @FXML
       public void show_Details()  throws SQLException
       {
          // getting the required values : productName, prodeuctID, nbr_pcs_crt, nbr_pcs, quan,

           Connection cnn = conn.connect();
           try {

               ResultSet rs = cnn.createStatement().executeQuery("SELECT *  FROM demo.product_table");
               while (rs.next()) {

                   myProduct_List.add( rs.getString(3));

               }
           } catch (SQLException eX) {
               System.out.println("error ! Not Connected to Db!!!");
               eX.printStackTrace();
           }

           finally {
               if (conn.connect()   != null) {conn.connect().close();}
               if (preparesStatemnt != null) {preparesStatemnt.close();}
               if (rs != null) {rs.close();}
           }


       }
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case SHIFT:
                show_product_details();
                break;
            case ALT_GRAPH:
                show_table.setVisible(false);
                break;
            case CONTROL:
               utility.setTextFieldFocus(product_txt);
                break;

            case ENTER:
                if (!product_txt.getText().isEmpty() && !quant_txt.getText().isEmpty()  )
                {
                    add_New_Client_Bon();
                }
                else if (!product_txt.getText().isEmpty() && quant_txt.getText().isEmpty())
                {
                    utility.setTextFieldFocus(quant_txt);
                }
                break;

        }
    }
    @FXML
    public void closeButtonAction() throws SQLException {
        utility.showAlert("CLOSE BUTTON HAS BEEN CLICKED");
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();


    }

    public double get_Bon_Total(int bonID,int personID) throws SQLException {
        double total = 0.25;
        rs = conn.connect().createStatement().executeQuery("SELECT * FROM demo.bon_table where id="+bonID+" and personID="+personID);
        if (rs.next()){
            total= rs.getDouble(2);
        }

        return total;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        datePicker.setValue(LocalDate.now());
        title_lb.setText("Stock Actual");
        title_lb.setVisible(false);
        show_table.setVisible(false);

        try {
            show_Details();
        } catch (SQLException e) {
            e.printStackTrace();

        }
       try {
      loadData();
          } catch (SQLException e) {
             e.printStackTrace();
          }

        name_lb.setText(NAME);
        address_lb.setText(ADDRESS);
        phone_lb.setText(PHONE);
        TextFields.bindAutoCompletion(product_txt,myProduct_List);





    }
}
