package Utilities_Package;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.controlsfx.control.Notifications.*;

public class Utility {

    private String view_Window, view_title;
    Db_Connection conn = new Db_Connection()  ;
    PreparedStatement preparesStatemnt = null;
    ResultSet resultSet = null;

    public Utility(){}
     //Custom Constructor --> 1
      public Utility(String view, String title  ){
        view_Window = view;
        view_title = title;

    }
       // Custom Constructor --> 2
     public Utility (String actualWindow,String title, Event event  ) throws IOException {
        switchScene(actualWindow,title,event);
    }
    // Starting new Stage Function
       public void openNewStage(String View, String title) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(View));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
    }
         // Switching Scenes
      public void switchScene_Home(String Actual_Window,String title, Event event) throws IOException {

        Parent Home_page_Parent = FXMLLoader.load(getClass().getResource(Actual_Window));
        Scene  Home_page_Scene  = new Scene(Home_page_Parent);
        Stage App_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        App_Stage.hide();
        App_Stage.setScene(Home_page_Scene);
        App_Stage.setTitle(title);
        App_Stage.setFullScreen(true);
        App_Stage.setResizable(true);
        App_Stage.show();

    }
    // Switching Scenes
    public void switchScene(String Actual_Window,String title, Event event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(Actual_Window));
        Scene scene = new Scene(root);
        Window existingWindow = ((Node) event.getSource()).getScene().getWindow();
        // create a new stage:
        Stage stage = new Stage();
        // make it modal:
        stage.initModality(Modality.APPLICATION_MODAL);
        // make its owner the existing window:
        stage.initOwner(existingWindow);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle(title);
        stage.setResizable(true);

        stage.show();

    }
      // go Home
         public void go_Home(Event event)  throws IOException{
             switchScene_Home("/Home_Package/Home_View.fxml","Home Page", event);
     }
      // go Pruduct
      public void go_Pruduct(Event event)  throws IOException{
    switchScene("/Product_Package/Product_View.fxml","Product Page", event);
        }
      // Go Stock
          public void go_Stock(Event event)  throws IOException{
    switchScene("/Stock_Package/Stock_View.fxml","Stock Page", event);
      }
       // go Client
        public void go_Client(Event event)  throws IOException{
    switchScene("/Client_Package/Client_View.fxml","Client Page", event);
        }
       // Go Fournisseur
        public void go_Fournisseur(Event event)  throws IOException{
            switchScene("/Fournisseur_Package/Fournisseur_View.fxml","Fournisseur Page", event);
        }
        // Go Caisse
        public void go_Caisse(Event event)  throws IOException{
            switchScene("/Caisse_Package/Caisse_View.fxml","Caisse Page", event);
        }
        // Go Bon Command Window
        public void go_Bon_Command(Event event)  throws IOException{
            switchScene("/Bon_Command_Package/Bon_Command_Client_View.fxml","Bon Command Page", event);
        }
        // Go Contoire
      public void go_Contoir(Event event) throws IOException{

          switchScene("/Contoire_Package/Contoire_View.fxml","Contoire Page", event);


             }
       // ShowAndWait a New Window
        // Add new Product Window
        public void show_New_Product_Window(Event e) throws IOException {

        openNewStage("/Product_Package/New_Product_View.fxml","Ajouter Nouveau Produit Form");

        }
        // Add new Fournisseur Window
        public void show_New_Fournisseur_Window(Event e) throws IOException {

            openNewStage("/Fournisseur_Package/New_Fournisseur_View.fxml","Ajouter Nouveau Fournisseur Form");

        }
         // Edit Product
        public void show_Edit_Product_Window() throws IOException {

            openNewStage("/Product_Package/Product_Edit_View.fxml","Edit Product Window");
        }
        // Add new Client Window
        public void show_New_Client_Window(Event e) throws IOException {

            openNewStage("/Client_Package/New_Client_View.fxml","Ajouter Nouveau Client Form");
        }
       // Manage Users
        public void show_Manage_Users() throws IOException {
            openNewStage("/Login_Package/Manage_Users_View.fxml","Ajouter Nouveau User Form");

        }
        // Add new Reglement Window
        public void show_Reglement_Window(String person,Event e) throws IOException {

            openNewStage("/Reglement_Package/Reglement_View.fxml","Reglement de " + person);
        }
        // Update Client
         public void show_update_Client_Window() throws IOException {


             openNewStage("/Client_Package/New_Client_View.fxml","Modifier Client " );

             }
        // Bon Fournisseur
          public void show_Bon_Fournisseur_Window(String person)throws IOException{
          openNewStage("/Bon_Command_Package/Bon_Command_Fournisseur_View.fxml","Bon Command de Fournisseur " + person);


          }

          // Bon Fournisseur Global
          public void show_Bon_Fournisseur_Global_Window(String person)throws IOException{
              openNewStage("/Bon_Command_Package/Bon_Fournisseur_Global_View.fxml","Bon Command de Fournisseur " + person);
          }
            // Bon Client Global
          public void show_Bon_Client_Global_Window(String person)throws IOException{
            openNewStage("/Bon_Command_Package/Bon_Client_Global_View.fxml","Bon Command de Client " + person);
         }

        // Log in
        public void log_In(String person ,Event event) throws IOException {

            switchScene_Home("/Home_Package/Home_View.fxml","Home Page*" + person, event);
        }
        // Login out
        public void log_Out(Event event) throws IOException {

            switchScene_Home("/Login_Package/Login_View.fxml","Login Page", event);



        }
        // Find Max
        public int getMax_ID(String tableName,String colName) throws SQLException {

           // String query = "select max(id) from " + tableName;
            String query = "SELECT max("+colName+") FROM "+tableName;
            int idmax= 0;
            Connection cnn = conn.connect();

            try{

                preparesStatemnt = cnn.prepareStatement(query);
                resultSet = preparesStatemnt.executeQuery();
                if(resultSet.next()){
                    idmax = resultSet.getInt(1);
                }

                cnn.close();
                resultSet.close();
                preparesStatemnt.close();

              }

            catch (Exception ex){ex.printStackTrace();}

            finally {
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}
                if (resultSet != null) {resultSet.close();}
               }

            return  idmax;

              }
        // Showing Alert Message
        public void showAlert(String s){

                Notifications note = create()
                        .text(s)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle();
                note.showConfirm();

        }
        // String Date Converter
         public LocalDate stringToDateConverter(String stringDate){

        String[] date_Sperator = stringDate.split("-");
             LocalDate myDate = LocalDate.of(Integer.parseInt(date_Sperator[2])
                ,Integer.parseInt(date_Sperator[1])
                ,Integer.parseInt(date_Sperator[0]));
        return myDate;
        }
        public void change_Sytle(TextField textField) {
        String style = "-fx-text-fill: Red ; -fx-font-size: 12px;" +
                " -fx-background-radius: 20; -fx-alignment : Center;" +
                " -fx-font-weight: Bold;";
        textField.setStyle(style);

             }
        //Textfield request Focus
        public void setTextFieldFocus(TextField textField){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textField.requestFocus();
                }
            });
        }
        // Button request focus
        public void Button_request_focus(Button btn){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    btn.requestFocus();
                }
            });

        }
        public void update_Fournisseur_Sold(double amount, double old_sold, int id) throws SQLException {

           Db_Connection conn = new Db_Connection();

            try{

                double new_Sold = (old_sold + amount);
                String query    = "UPDATE demo.person_table SET sold =? Where id="+id;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setDouble(1, new_Sold);
                preparesStatemnt.executeUpdate();
                conn.connect().close();
            }

            catch (Exception ex){ex.printStackTrace();}

            finally {
                if (conn.connect()   != null) {conn.connect().close();}
                if (preparesStatemnt != null) {preparesStatemnt.close();}

            }

       }
       // Update CLIENT Sold
        public void update_Client_Sold(double amount, double old_sold, int id) throws SQLException {

        Db_Connection conn = new Db_Connection();


        try{

            double new_Sold = (old_sold + amount);
            String query    = "UPDATE demo.person_table SET sold =? Where id="+id + " and PersonType="+PersonType.Active_Client;

            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setDouble(1, new_Sold);
            preparesStatemnt.executeUpdate();
            conn.connect().close();
        }

        catch (Exception ex){ex.printStackTrace();}

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}

        }


    }
        // update stock
          public void update_stock(int productID,int new_quant) throws SQLException {

              Db_Connection conn = new Db_Connection();

              String query    = "UPDATE demo.product_table SET quan =? Where id="+productID;
              preparesStatemnt = conn.connect().prepareStatement(query);
              preparesStatemnt.setInt(1, new_quant);
              preparesStatemnt.executeUpdate();
              conn.connect().close();


          }
         // get old Quantity
       public int get_Product_quantity(int productID) throws SQLException {

        int product_old_quantity = 0;
        String query = "SELECT quan from demo.product_table  where id = '"+productID+"'";

        Connection cnn = conn.connect();
          try{

              preparesStatemnt = cnn.prepareStatement(query);
              resultSet = preparesStatemnt.executeQuery();
              if(resultSet.next()){
                  product_old_quantity = resultSet.getInt(1);
              }
              cnn.close();
              preparesStatemnt.close();
              resultSet.close();
          }

          catch (Exception ex){ex.printStackTrace();}

          finally {
              if (conn.connect()   != null) {conn.connect().close();}
              if (preparesStatemnt != null) {preparesStatemnt.close();}
              if (resultSet != null) {resultSet.close();}
          }

        return product_old_quantity;


          }
          // get product detail -- Strings
          public Object get_Product_detail_S(int productID ,String colum) throws SQLException {

              Object value = new Object();
              String query = "SELECT * from demo.product_table  where id ="+productID;
              Connection cnn = conn.connect();
              try{
                  preparesStatemnt = cnn.prepareStatement(query);
                  resultSet = preparesStatemnt.executeQuery();
                  if(resultSet.next()){
                      value = resultSet.getObject(colum);
                  }

                  cnn.close();
                  preparesStatemnt.close();
                  resultSet.close();
              }

              catch (Exception ex){ex.printStackTrace();}

              finally {
                  if (conn.connect()   != null) {conn.connect().close();}
                  if (preparesStatemnt != null) {preparesStatemnt.close();}
                  if (resultSet != null) {resultSet.close();}
              }

              return value;
          }
        // get Product Nbr_pcs_crt
          public int get_Product_Nbr_pcs_crt(int productID) throws SQLException {

              int product_Nbr_pcs_crt = 0;
              String query = "SELECT nbr_pcs_crt from demo.product_table  where id = '"+productID+"'";

              Connection cnn = conn.connect();
              try{

                  preparesStatemnt = cnn.prepareStatement(query);
                  resultSet = preparesStatemnt.executeQuery();
                  if(resultSet.next()){
                      product_Nbr_pcs_crt = resultSet.getInt(1);
                  }
                  cnn.close();
                  preparesStatemnt.close();
                  resultSet.close();
              }

              catch (Exception ex){ex.printStackTrace();}

              finally {
                  if (conn.connect()   != null) {conn.connect().close();}
                  if (preparesStatemnt != null) {preparesStatemnt.close();}
                  if (resultSet != null) {resultSet.close();}
              }

              return product_Nbr_pcs_crt;
          }
       public double get_Sold(int fournisseurID) throws SQLException {

        double updatedsold = 0.025;
        String query = "SELECT sold from demo.person_table  where id ="+fournisseurID;

        Connection cnn = conn.connect();

        try{
            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery();
            if(resultSet.next()){
                updatedsold = resultSet.getDouble(1);
            }
            cnn.close();
            preparesStatemnt.close();
            resultSet.close();
           }

        catch (Exception ex){ex.printStackTrace();}

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (resultSet != null) {resultSet.close();}
             }

        return updatedsold;
    }
    public double get_Client_Sold(int clientID) throws SQLException {

        double updatedsold = 0.025;
        String query = "SELECT sold from demo.person_table  where id ="+clientID +" and PersonType="+PersonType.Active_Client;
        Connection cnn = conn.connect();
        try{

            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery();
            if(resultSet.next()){
                updatedsold = resultSet.getDouble(1);
            }

            cnn.close();
            resultSet.close();
            preparesStatemnt.close();

        }

        catch (Exception ex){ex.printStackTrace();}

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (resultSet != null) {resultSet.close();}
           }

        return updatedsold;
           }
     // Get Fournisseur ID
     public int getFournisseur_ID(String  fournisseurName) throws SQLException {

              int fournisseurID = 0;
              String query = "SELECT id from demo.person_table  where name ='"+fournisseurName+"' and PersonType="+PersonType.Active_Fournisseur;
              Connection cnn = conn.connect();

         try{
             preparesStatemnt = cnn.prepareStatement(query);
             resultSet = preparesStatemnt.executeQuery();
             if(resultSet.next()){
                 fournisseurID = resultSet.getInt(1);
             }
             cnn.close();
             preparesStatemnt.close();
             resultSet.close();
             }

         catch (Exception ex){ex.printStackTrace();}
         finally {
             if (conn.connect()   != null) {conn.connect().close();}
             if (preparesStatemnt != null) {preparesStatemnt.close();}
             if (resultSet != null) {resultSet.close();}
         }

               return fournisseurID;


          }
          // get Person Name
    public String get_Person_Name(int PersonID) throws SQLException {
        String personName ="";

        String query = "SELECT name from demo.person_table  where id ="+PersonID;
        Connection cnn = conn.connect();

        try{
            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery();

            if(resultSet.next()){
                personName = resultSet.getString(1);
            }
            cnn.close();
            preparesStatemnt.close();
            resultSet.close();
        }

        catch (Exception ex){ex.printStackTrace();}

        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (resultSet != null) {resultSet.close();}
        }


        return personName;
    }
    // get Product price
     public double get_Product_price(int productID) throws SQLException {

         double price = 0.0;
         String query = "SELECT prix_vent from demo.product_table  where id ="+productID;
         try{

             preparesStatemnt = conn.connect().prepareStatement(query);
             resultSet = preparesStatemnt.executeQuery();
             if(resultSet.next()){
                 price = resultSet.getDouble(1);
             }
             conn.connect().close();
             resultSet.close();
             preparesStatemnt.close();
             }

          catch (Exception ex){ex.printStackTrace();}
          finally {
             if (conn.connect()   != null) {conn.connect().close();}
             if (preparesStatemnt != null) {preparesStatemnt.close();}
             if (resultSet != null) {resultSet.close();}
            }
        return price;
     }
    // get product ID
    public int getProduct_ID(String  ProductName) throws SQLException {
        int ProductID = 0;

        String query = "SELECT id from demo.product_table  where des = '"+ProductName+"'";
        Connection cnn = conn.connect();

        try{

            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery();
            if(resultSet.next()){
                ProductID = resultSet.getInt(1);
            }
            cnn.close();
            resultSet.close();
            preparesStatemnt.close();

             }

        catch (Exception ex){ex.printStackTrace();}
        finally {
            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (resultSet != null) {resultSet.close();}
                }

        return ProductID;
    }
    public double total_sum_calculator(int bonId,int personID, Label label) throws SQLException {
        ResultSet   rs = null;
        double sum_amount=0.25;
        try{

            String Query =" SELECT sum(value)  FROM demo.bon_detail_table where bonID="+bonId   ;
               rs = conn.connect().createStatement().executeQuery(Query);
            if (rs.next()){
                sum_amount = rs.getDouble(1);
            }
            label.setText("TOTAL : "+String.format("%,.2f", sum_amount)+" DZD");
        }

        catch (Exception ex){ex.printStackTrace();}

        finally {

            if (conn.connect()   != null) {conn.connect().close();}
            if (preparesStatemnt != null) {preparesStatemnt.close();}
            if (rs != null) {rs.close();}
        }

        return sum_amount;











    }
    public void show_TrayNotification(String message){

        String title = "Congratulations sir";
        NotificationType notification = NotificationType.SUCCESS;
        TrayNotification tray = new TrayNotification();
        tray.setTitle(title);
        tray.setMessage(message);
        tray.setAnimationType(AnimationType.POPUP);
        tray.setNotificationType(notification);
        tray.showAndDismiss( Duration.millis( 2500 ) );

       // tray.showAndWait();

    }





}