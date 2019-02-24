package Contoire_Package;

import Login_Package.Manage_Users_Controller;
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
import javafx.scene.layout.VBox;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Contoire_Controller implements Initializable {
    @FXML
    public TableView<Bon_Fournisseur_Global> Bon_Client_Global_table;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Integer> id_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> n_bon_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,Double> valeur_column;
    @FXML
    public TableColumn<Bon_Fournisseur_Global,String> date_column;




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
    public ToggleButton toggleButton = new ToggleButton();

    @FXML
    Label client_lb,total_lb;
    @FXML
    public Button closeButton,add_ventbtn,paiment_btn,delete_btn;
    @FXML
    public Button edit_btn,save_btn;

    @FXML
    public ComboBox client_Combo;
    @FXML
    public TextField product_txt,quant_txt,paiment_txt;
    @FXML
    public DatePicker datePicker;
    @FXML
    public Pane pane;


    public ObservableList<Product> show_data_List;
    public ObservableList<Vent> data;
    public ObservableList<Bon_Fournisseur_Global> data_2;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();
    Notification notification = new Notification();
    ArrayList product_list = new ArrayList();


    public static int VENT_ID;
    public  void loadData() throws SQLException {
        Connection cnn = conn.connect();
        ResultSet rs = null;

        try{
            rs = cnn.createStatement().executeQuery("SELECT * FROM demo.person_table where ABS(personType)=1");
            while(rs.next()){

                //client_Combo.getItems().add(rs.getString("name"));
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

    public  void loadData_Global() throws SQLException {
       // delet_empty_bon();
        Connection cnn = conn.connect();
        try{

            data_2 = FXCollections.observableArrayList();
            rs = cnn.createStatement().executeQuery("SELECT * FROM demo.bon_table where personID= 12");
            while(rs.next()){

                data_2.add(new Bon_Fournisseur_Global(

                        rs.getInt(   "id"),
                        "Vent_"+rs.getString("id"),
                        rs.getDouble("total"),
                        rs.getString("date"),
                        rs.getInt(   "personID")
                ));

            }
        }
        catch(SQLException eX){
            eX.printStackTrace();
            System.out.println("error ! Not Connected to Db****");
        }


        finally {

            id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
            n_bon_column.setCellValueFactory(new PropertyValueFactory<>("n_bon"));
            valeur_column.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
            Bon_Client_Global_table.setItems(data_2);

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }

    }


    @FXML
    public void refresh(int ventID) throws SQLException {

        try {

            data = FXCollections.observableArrayList();

            ResultSet rs = conn.connect().createStatement().executeQuery("SELECT v.id , p.ref , p.des , v.nbr_pcs , p.prix_vent , v.value,d.personID,d.id,d.date FROM demo.vent_table v, demo.product_table p, demo.bon_table d where v.productID = p.id and v.ventID = d.id  and v.ventID="+ventID);
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

                vent_table.setItems(data);
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
    public void delete_vent() throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Vent vent = vent_table.getSelectionModel().getSelectedItem();
            // Now delete
            if(! vent_table.getSelectionModel().isEmpty() ) {
                int id = vent.getId();
                String product_Name = vent.getDes();
                int productID = utility.getProduct_ID(product_Name);
                double prix = vent.getPrix_vent();
                int ventID = vent.getVentID();
                int personID = vent.getClientID();
                try {

                    String query = "DELETE FROM  demo.vent_table WHERE id="+id;
                    preparesStatemnt = conn.connect().prepareStatement(query);
                    preparesStatemnt.executeUpdate();

                    refresh(VENT_ID);
                    preparesStatemnt.close();
                    conn.connect().close();
           ////////////////// // Update product:///////////////////////
                    double new_value;
                    int old_nbr_pcs = utility.get_Product_Nbr_pcs(productID);
                    int  new_nbr_pcs = old_nbr_pcs + vent.getNbr_pcs();
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
                    product_txt.clear();
                    quant_txt.clear();
                    utility.setTextFieldFocus(product_txt);
                    notification.show_Confirmation("Vent Deleted");


                    }
                catch (SQLException e) { e.printStackTrace(); }
                finally {

                    if (conn.connect() != null) {
                        conn.connect().close();
                    }
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
                    vent_table.setVisible(true);
                    delete_btn.setVisible(true);
                    edit_btn.setVisible(true);
                    save_btn.setVisible(true);
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




            }


        }

        else {
            // ... user chose CANCEL or closed the dialog

        }

    }

     @FXML
    public void show_vent_detail() throws SQLException {
         delet_empty_bon();
        Bon_Client_Global_table.setVisible(true);
        vent_table.setVisible(true);
        paiment_btn.setVisible(false);
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        paiment_btn.setVisible(false);
        save_btn.setVisible(false);
        paiment_txt.setVisible(false);
        quant_txt.setVisible(false);
        add_ventbtn.setVisible(false);
        show_table.setVisible(false);
        client_lb.setVisible(false);
        pane.setVisible(false);
         loadData_Global();



       }




    public void reglement_rapid() throws SQLException{

        if (!client_Combo.getValue().toString().isEmpty()){
             String client_NAME = client_Combo.getValue().toString();
            double amount = Double.parseDouble(paiment_txt.getText());
            int Reglement_id ;
            int  max_id  = utility.getMax_ID("demo.person_reglement_table","id") ;
            Reglement_id = max_id +1;   // 1) Reglement id
            String note = "/";  // 5) Note

            String mode = "Espece"; // 7) Mode
            String date = datePicker.getValue().toString(); // 8 payement date
            int clientID = utility.getFournisseur_ID(client_NAME); //9) clientID
            double old_Sold =  utility.get_Sold(clientID); // 3) Old Sold
            double new_sold = old_Sold - amount; // 4) new sold

            double old_total  = utility.get_caisse_total();
            double new_total = old_total + amount;


            if (!paiment_txt.getText().isEmpty()){

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
                notification.show_Confirmation("Paiment de : " + amount + " DZD"+ " received !");
                vent_table.setItems(null);
                pane.setVisible(false);
                paiment_btn.setVisible(false);
                paiment_txt.setVisible(false);
                client_Combo.setValue("Client_Vent");
                pane.setVisible(false);
                vent_table.setVisible(false);
                client_lb.setText(client_Combo.getValue().toString());
                paiment_txt.clear();

            }

        }


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
               vent_table.setVisible(true);

               delete_btn.setVisible(true);
               edit_btn.setVisible(true);
               save_btn.setVisible(true);
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
           ////////////////////////
           // Notification check

           double product_Quantity = utility.get_Product_quantity(productID);
           int alert = utility.get_Product_Alert(productID);
           utility.Product_Notification(product_Quantity,alert,product_Name);
           notification.show_Confirmation("Product updated");
           product_txt.clear();
           quant_txt.clear();
           utility.setTextFieldFocus(product_txt);

       }


   }
    @FXML
    public void save_bon() throws SQLException {
        String client_Name = client_Combo.getValue().toString();
        int personID = utility.getFournisseur_ID(client_Name);

        double total = get_Bon_Total(VENT_ID,personID);
        // Update client Sold
        double old_sold = utility.get_Sold(personID);
        utility.update_Fournisseur_Sold(total, old_sold, personID);
        paiment_txt.setVisible(true);
        paiment_btn.setVisible(true);
        add_ventbtn.setVisible(false);
        quant_txt.setVisible(false);

        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        save_btn.setVisible(false);
        utility.setTextFieldFocus(paiment_txt);
        notification.show_Confirmation("saved successfully");
       // closeButtonAction();
    }
   @FXML
   public void open_new_Bon() throws SQLException {
    // OPEN NEW BON

    int id_bon = utility.getMax_ID("demo.bon_table","id")+1;
    double total =0.0;
    String date = datePicker.getValue().toString();
    String client_Name = client_Combo.getValue().toString();
    int clientId = utility.getFournisseur_ID(client_Name);
       client_lb.setText("Vent_N : "+id_bon+"");
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
                else if (!paiment_txt.getText().isEmpty())
                {
                    reglement_rapid();

                }

                break;
        }
    }

    @FXML
    public void show_onClick(){

        Bon_Fournisseur_Global bon_fournisseur_global = Bon_Client_Global_table.getSelectionModel().getSelectedItem();
        int Vent_ID = bon_fournisseur_global.getId();
         VENT_ID = Vent_ID;
         try{
             refresh(VENT_ID);
            }
         catch (Exception s){ s.printStackTrace();}

        }


    public void delet_empty_bon() throws SQLException{

        String query = "Delete from demo.bon_table where total=0";
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.executeUpdate();
        preparesStatemnt.close();
        conn.connect().close();
    }

    @FXML
    private  void handel_toggle_Button_event()throws SQLException{

        if(toggleButton.isSelected())
        {
            toggleButton.setText("Hide Details");
            Bon_Client_Global_table.setVisible(true);
            vent_table.setVisible(true);
            loadData_Global();
            show_onClick();


        }
        else
        {
            Bon_Client_Global_table.setVisible(false);
            vent_table.setVisible(false);
            toggleButton.setText("Show Details");
        }

    }
    @Override
    public void initialize(URL location, ResourceBundle resources)   {
        /////// Value changed listener in the Table
        Bon_Client_Global_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {

                        show_onClick();

                    }
                }
        );




        Bon_Client_Global_table.setVisible(false);
        vent_table.setVisible(false);
        paiment_btn.setVisible(false);
        delete_btn.setVisible(false);
        edit_btn.setVisible(false);
        paiment_btn.setVisible(false);
        save_btn.setVisible(false);
        paiment_txt.setVisible(false);
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
