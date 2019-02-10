package Bon_Command_Package;

import Utilities_Package.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
    public TableColumn<Product, Integer>   quant_show_column;
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
    public static String NAME;
    public static String ADDRESS;
    public static String PHONE;
    public static int ID;
    public static double SOLD;



    public ObservableList<Bon_Command_Client> data;

    public ObservableList<Product> show_data_List;

    public ArrayList myProduct_List = new ArrayList();

    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;
    Utility utility = new Utility();

    // refresh
    public void refresh(int bonID) throws SQLException {

        Connection cnn = conn.connect();
        try {

            data = FXCollections.observableArrayList();


            ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix,value,clientID,bonID,date FROM demo.bon_command_client_table WHERE bonID=" + bonID);
            while (rs.next()) {

                data.add(new Bon_Command_Client(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11)
                ));


            }
        } catch (SQLException eX) {
            System.out.println("error ! Not Connected to Db****");
        }

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

        cnn.close();


    }
    // Load List
    public void loadData() throws SQLException {
        Connection cnn = conn.connect();
        try {
            data = FXCollections.observableArrayList();
            ResultSet rs = cnn.createStatement().executeQuery("SELECT  id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix,value,clientID,bonID,date FROM demo.bon_command_client_table");
            while (rs.next()) {

                data.add(new Bon_Command_Client(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getDouble(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getString(11)
                ));

            }
        } catch (SQLException eX) {
            System.out.println("error ! Not Connected to Db!!!");
            eX.printStackTrace();
        }
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

        cnn.close();

      }
    @FXML
    public void save_bon() throws SQLException {

        int Id = utility.getMax_ID("demo.bon_table", "id");
        int Bon_Order_Id = utility.getMax_ID(" demo.order_table", "id");
        String BON_NUM = "Bon_" + Bon_Order_Id;
        String date = "";
        int clientID = 0;
        double amount = 0;
        double sum_amount = 0;

        Connection cnn = conn.connect();
        try {

            ResultSet rs = cnn.createStatement().executeQuery("SELECT  max(clientID),max(date),sum(value) FROM demo.bon_command_client_table Where bonID= " + Bon_Order_Id);
            if (rs.next()) {
                clientID = rs.getInt(1);
                date = rs.getString(2);
                amount = rs.getDouble(3);
            }

            String query =
                    "INSERT INTO demo.bon_table" +
                            " (id,bon_num,amount,date,clientID,bonID)" +
                            " VALUES (?,?,?,?,?,?)";
            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt(1, Id + 1);
            preparesStatemnt.setString(2, BON_NUM);
            preparesStatemnt.setDouble(3, amount);
            preparesStatemnt.setString(4, date);
            preparesStatemnt.setInt(5, clientID);
            preparesStatemnt.setInt(6, Bon_Order_Id);
            preparesStatemnt.execute();
            preparesStatemnt.close();
            utility.showAlert("Added successfully");
             }

        catch (Exception e)
            {

            }

    }

    @FXML
      public void add_Client_Bon() throws SQLException {

          int Id = utility.getMax_ID("demo.bon_command_client_table","id")+1; // ID
          String des = product_txt.getText();
          int quantite    = Integer.parseInt(quant_txt.getText());
          int productID = utility.getProduct_ID(des);
          int  bonID  = utility.getMax_ID("demo.order_table","id") ;
          int clientID = ID;
          String date = datePicker.getValue().toString();
          double value;
          int nbr_pcs    ;
          int nbr_pcs_crt = 0 ;
          String product_Ref = null;
          double prix =0;

          Connection cnn = conn.connect();
          try{
              ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table where des='"+des+"'");
                 while(rs.next()){
                  prix        = rs.getDouble  (12);
                  nbr_pcs_crt = rs.getInt     (4);
                  product_Ref = rs.getString  (2);
              }
          }
          catch(SQLException eX){
              System.out.println("error ! Not Connected to Db****");
          }

          nbr_pcs = nbr_pcs_crt*quantite;
          value = nbr_pcs*prix;

          ////////////// INSERT FUNCTION /////////////

          String query =
                  "INSERT INTO demo.bon_command_client_table"                +
                          " (id,ref,des,nbr_pcs_crt,quant,nbr_pcs,prix,value,clientID,bonID,date)" +
                          " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
          preparesStatemnt = conn.connect().prepareStatement(query);
          preparesStatemnt.setInt   (1, Id);
          preparesStatemnt.setString(2, product_Ref);
          preparesStatemnt.setString(3, des);
          preparesStatemnt.setInt   (4, nbr_pcs_crt);
          preparesStatemnt.setInt   (5, quantite);
          preparesStatemnt.setInt   (6, nbr_pcs);
          preparesStatemnt.setDouble(7, prix);
          preparesStatemnt.setDouble(8, value);
          preparesStatemnt.setInt   (9, clientID);
          preparesStatemnt.setInt   (10, bonID);
          preparesStatemnt.setString(11, date);
          preparesStatemnt.execute();
          refresh( bonID);
          preparesStatemnt.close();
          total_sum_calculator( bonID ,clientID);
           clear();

        // Update Client Sold

        double amount = 0;
        try {

            ResultSet    rs = cnn.createStatement().executeQuery("SELECT sum(value) FROM demo.bon_command_client_table Where clientID="+clientID);
            if (rs.next()) {
                amount = rs.getDouble(1);

            }

            double old_sold = utility.get_Client_Sold(clientID);
            utility.update_Client_Sold(amount, old_sold, clientID);
            utility.showAlert("Update Client Sold function worked !!!");

             }

        catch (SQLException eX) {
            System.out.println("error ! Not Connected to Db****");
             }

           ///// UP DATE STOCK //////
             int Old_quant = 0;
             int new_quant = 0;
             ResultSet rs = cnn.createStatement().executeQuery("SELECT  quan FROM demo.product_table Where id= " + productID);
             if (rs.next()) { Old_quant = rs.getInt(1);}
             new_quant = (Old_quant - quantite);
             utility.update_stock(productID,new_quant);
             utility.showAlert("Stock updated SUCCESSFULLY!");
             utility.setTextFieldFocus(product_txt);
             conn.connect().close();


      }

     public void total_sum_calculator(int bonId,int clientID) throws SQLException {

         double sum_amount=0.25;

         String Query =" SELECT sum(value)  FROM demo.bon_command_client_table where clientID = "+clientID+" " + " and bonID="+bonId   ;
         ResultSet   rs = conn.connect().createStatement().executeQuery(Query);
         if (rs.next()){

             sum_amount = rs.getDouble(1);

         }


      show_lb.setText(sum_amount+"");

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

        des_show_column.setCellValueFactory(new PropertyValueFactory<>("designiation"));
        nbr_pcs_crt_show_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs_crt"));
        nbr_pcs_show_column.setCellValueFactory(new PropertyValueFactory<>("Nbr_pcs"));
        quant_show_column.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prix_show_column.setCellValueFactory(new PropertyValueFactory<>("prix_ventt"));

        show_table.setItems(show_data_List);

        cnn.close();



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
                    add_Client_Bon();
                }
                else if (!product_txt.getText().isEmpty() && quant_txt.getText().isEmpty())
                {
                    utility.setTextFieldFocus(quant_txt);
                }

                break;
            case ESCAPE:
               closeButtonAction();
                break;

        }
    }
    @FXML
    public void closeButtonAction() throws SQLException {
        utility.showAlert("CLOSE BUTTON HAS BEEN CLICKED");
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        save_bon();
        stage.close();


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
       // try {
      //  loadData();
        //   } catch (SQLException e) {
          //    e.printStackTrace();
         //  }

        name_lb.setText(NAME);
        address_lb.setText(ADDRESS);
        phone_lb.setText(PHONE);
        TextFields.bindAutoCompletion(product_txt,myProduct_List);





    }
}
