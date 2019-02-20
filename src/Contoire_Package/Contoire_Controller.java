package Contoire_Package;

import Utilities_Package.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Contoire_Controller implements Initializable {
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
    public TableView<Vent> vent_table;
    @FXML
    public TableColumn<Vent,Integer> id_Column;
    public TableColumn<Vent,String> ref_Column;
    public TableColumn<Vent,String> des_Column;
    public TableColumn<Vent,Integer> nbr_pcs_Column;
    public TableColumn<Vent,Double> prix_Column;
    public TableColumn<Vent,Double> value_Column;

    @FXML
    Label client_lb,total_lb;
    @FXML
    public Button closeButton,add_ventbtn;
    @FXML
    public ComboBox client_Combo;
    @FXML
    public TextField product_txt,quant_txt;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Pane pane;
    public ObservableList<Product> show_data_List;
    public ObservableList<Vent> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();
    ArrayList product_list = new ArrayList();


    public static int VENT_ID;

    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        ResultSet rs = null;

        try{

            rs = cnn.createStatement().executeQuery("SELECT * FROM demo.person_table where ABS(personType)=1");
            while(rs.next()){

                client_Combo.getItems().add(rs.getString("name"));

            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (rs != null) {rs.close();}
        }


        try{

            rs = cnn.createStatement().executeQuery("SELECT * FROM demo.product_table");
            while(rs.next()){

                product_list.add(rs.getString("des"));

            }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (rs != null) {rs.close();}
        }










    }


    @FXML
    public void refresh(int ventID) throws SQLException {

        try {

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT v.id , p.ref , p.des , v.nbr_pcs , p.prix_vent , v.value,d.personID,d.id,d.date FROM demo.vent_table v, demo.product_table p, demo.bon_table d where v.productID = p.id and v.ventID = d.id  and v.ventID= "+ventID);
            while (rs.next()) {

                data.add(new Vent(

                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9)

                ));


            }
        } catch (SQLException eX) {
            eX.printStackTrace();
            System.out.println("error ! Not Connected to Db****");
        }

        finally {

            id_Column.setCellValueFactory(new PropertyValueFactory<>("id"));
            ref_Column.setCellValueFactory(new PropertyValueFactory<>("ref"));
            des_Column.setCellValueFactory(new PropertyValueFactory<>("des"));
            nbr_pcs_Column.setCellValueFactory(new PropertyValueFactory<>("nbr_pcs"));
            prix_Column.setCellValueFactory(new PropertyValueFactory<>("prix_vent"));
            value_Column.setCellValueFactory(new PropertyValueFactory<>("value"));
            vent_table.setItems(data);
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }

     }


    public double get_Bon_Total(int bonID,int personID) throws SQLException {
        double total = 0.25;
        rs = conn.connect().createStatement().executeQuery("SELECT * FROM demo.bon_table where id="+bonID+" and personID="+personID);
        if (rs.next()){
            total= rs.getDouble(2);
        }

        return total;
    }


   @FXML
   public void add_vent() throws SQLException {

        String client_Name = client_Combo.getValue().toString();
        int personID = utility.getFournisseur_ID(client_Name);

        int id_vent_order = utility.getMax_ID("demo.vent_table","id")+1;
        int ventID = utility.getMax_ID("demo.bon_table","id");
         VENT_ID = ventID;
        int nbr_pcs = Integer.parseInt(quant_txt.getText());
        String product_Name = product_txt.getText();
        int productID = utility.getProduct_ID(product_Name);
        double prix =utility.get_Product_price(productID);
        double value = nbr_pcs*prix;

       // Insert vent_detail
       if (!product_txt.getText().isEmpty()&& !quant_txt.getText().isEmpty())
       {

           String query_2 = "INSERT INTO demo.vent_table (id,nbr_pcs,value,ventID,productID) Values (?,?,?,?,?)";

           try {
               preparesStatemnt = conn.connect().prepareStatement(query_2);
               preparesStatemnt.setInt   (1, id_vent_order);
               preparesStatemnt.setInt(2, nbr_pcs);
               preparesStatemnt.setDouble(3, value);
               preparesStatemnt.setInt   (4, VENT_ID);
               preparesStatemnt.setInt   (5, productID);
               preparesStatemnt.execute();
               refresh(VENT_ID);
               preparesStatemnt.close();

               conn.connect().close();

           }
           catch (Exception e){ e.printStackTrace(); }
           finally {

               if (conn.connect()   != null) {conn.connect().close();}
               if (preparesStatemnt != null) {preparesStatemnt.close();}
               if (rs != null) {rs.close();}
           }


           //Update Total
           double total=0.25;
           try{
               total=0.25;
               try{
                   String Query =" SELECT sum(value)  FROM demo.vent_table where ventID="+VENT_ID   ;
                   ResultSet   rs = conn.connect().createStatement().executeQuery(Query);
                   if (rs.next()){
                       total = rs.getDouble(1);
                   }
               }

               catch (Exception ex){ex.printStackTrace();}
               String Query  = "UPDATE demo.bon_table SET total =? Where id="+VENT_ID;
               preparesStatemnt = conn.connect().prepareStatement(Query);
               preparesStatemnt.setDouble   (1,  total);
               preparesStatemnt.executeUpdate();
               pane.setVisible(true);
                double total_vent = get_Bon_Total(ventID,personID) ;
               total_lb.setText("TOTAL : "+String.format("%,.2f", total_vent)+" DZD");

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

           // Update product:

           double new_value;

           int old_nbr_pcs = utility.get_Product_Nbr_pcs(productID);
           int  new_nbr_pcs = old_nbr_pcs  - Integer.parseInt(quant_txt.getText());
           new_value = new_nbr_pcs*prix;

           int nbr_pcs_crt = utility.get_Product_Nbr_pcs_crt(productID);
           double new_quantite = ((double) new_nbr_pcs)/nbr_pcs_crt;


           String Query  = "UPDATE demo.product_table SET quan=?, nbr_pcs=? , value=? Where id="+productID;
           preparesStatemnt = conn.connect().prepareStatement(Query);
           preparesStatemnt.setDouble      (1,  new_quantite);
           preparesStatemnt.setInt         (2,  new_nbr_pcs);
           preparesStatemnt.setDouble      (3,  new_value);
           preparesStatemnt.executeUpdate();
           preparesStatemnt.close();
           utility.showAlert("Product updated");

           product_txt.clear();
           quant_txt.clear();
           utility.setTextFieldFocus(product_txt);


       }


   }


   @FXML
   public void open_new_Bon() throws SQLException {
    // OPEN NEW BON
    int id_bon = utility.getMax_ID("demo.bon_table","id")+1;
    double total =0.0;
    String date = datePicker.getValue().toString();
    String client_Name = client_Combo.getValue().toString();
    int clientId = utility.getFournisseur_ID(client_Name);
       client_lb.setText(client_Name);
       client_lb.setVisible(true);
       product_txt.setVisible(true);
       quant_txt.setVisible(true);
       add_ventbtn.setVisible(true);
       utility.setTextFieldFocus(product_txt);


    String query = "INSERT INTO demo.bon_table (id,total,date,personID) Values (?,?,?,?)";

    try {
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.setInt   (1, id_bon);   // id_bon
        preparesStatemnt.setDouble(2, total);    // total
        preparesStatemnt.setString(3, "2019-02-02");     // date
        preparesStatemnt.setInt   (4, clientId);   // clientId
        preparesStatemnt.execute();
        utility.showAlert("new Bon Created");
        preparesStatemnt.close();
        conn.connect().close();
    }
    catch (Exception e){ e.printStackTrace(); }
    finally {
        if (conn.connect()   != null) {conn.connect().close();}
        if (preparesStatemnt != null) {preparesStatemnt.close();}

    }
}

    // Fournisseur
    @FXML
    public void Open_Fournisseur_Window(Event event) throws IOException {

       new Utility().go_Fournisseur(event);
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
                        rs.getDouble(5),
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

    @FXML
    public void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do

        stage.close();
    }

    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {

            case SHIFT:
                if(!product_txt.getText().isEmpty())
                {
                    fill_List(product_txt.getText());
                    show_table.setVisible(true);
                }
                  break;

            case ALT_GRAPH:
                    show_table.setVisible(false);
                break;

            case F:
                Open_Fournisseur_Window(event);  break;
            case ENTER:
                if(!product_txt.getText().isEmpty() && quant_txt.getText().isEmpty())
                {
                    utility.setTextFieldFocus(quant_txt);

                }
                else if (!product_txt.getText().isEmpty() && !quant_txt.getText().isEmpty())
                {
                    //  call add bon function
                     add_vent();
                }
                break;
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        quant_txt.setVisible(false);
        add_ventbtn.setVisible(false);
        show_table.setVisible(false);
        client_lb.setVisible(false);
        pane.setVisible(false);
        datePicker.setValue(LocalDate.now());
        client_Combo.setValue("Client_Vent");
        try {
            loadData();
        } catch (SQLException e) { e.printStackTrace(); }

        TextFields.bindAutoCompletion(product_txt,product_list);


    }
}
