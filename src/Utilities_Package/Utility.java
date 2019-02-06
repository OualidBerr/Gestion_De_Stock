package Utilities_Package;

import Client_Package.New_Client_Controller;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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
      public void switchScene(String Actual_Window,String title, Event event) throws IOException {

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

      // go Home
         public void go_Home(Event event)  throws IOException{
       switchScene("/Home_Package/Home_View.fxml","Home Page", event);
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
            switchScene("/Bon_Command_Package/Bon_Command_View.fxml","Bon Command Page", event);
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


        // Log in
        public void log_In(String person ,Event event) throws IOException {

            switchScene("/Home_Package/Home_View.fxml","Home Page*" + person, event);
        }
        // Login out
        public void log_Out(Event event) throws IOException {

            switchScene("/Login_Package/Login_View.fxml","Login Page", event);



        }
        // Find Max
        public int getMax_ID(String tableName,String colName) throws SQLException {

           // String query = "select max(id) from " + tableName;
            String query = "SELECT max("+colName+") from "+tableName;

            int idmax= 0;
            Connection cnn = conn.connect();
            preparesStatemnt = cnn.prepareStatement(query);
            resultSet = preparesStatemnt.executeQuery();
            if(resultSet.next()){
                idmax = resultSet.getInt(1);
                }
            cnn.close();
            return  idmax;

              }

    // Find Sum
    public double get_Sum_Amount(int fournisseurID) throws SQLException {

        String query = "SELECT SUM(amount) FROM demo.fournisseur_reglement_table WHERE fournisseurID ="+fournisseurID;

        double Sum_amount = 0.25;
        Connection cnn = conn.connect();
        preparesStatemnt = cnn.prepareStatement(query);
        resultSet = preparesStatemnt.executeQuery();

        if(resultSet.next()){
            Sum_amount = resultSet.getDouble(1);
        }

        cnn.close();
        return  Sum_amount;

    }


        // Showing Alert Message
        public void showAlert(String s){

                Notifications note = Notifications.create()
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

    // String Date Converter
    public LocalDate stringToDateConverter_Reversed(String stringDate){

        String[] date_Sperator = stringDate.split("-");
        LocalDate myDate = LocalDate.of(Integer.parseInt(date_Sperator[0])
                ,Integer.parseInt(date_Sperator[1])
                ,Integer.parseInt(date_Sperator[2]));
        return myDate;
    }



        // Date Formatter
        public String DateToFormatedString(Date L_date){
              Format formatter;
              formatter = new SimpleDateFormat("yyyy-MM-dd");
              String stringDate = formatter.format(L_date);
              return stringDate;
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
        public void verssementFun(double amount, double old_sold, int id) throws SQLException {

           Db_Connection conn = new Db_Connection();
           ResultSet resultSet = null;
           PreparedStatement  preparesStatemnt = null;

           double new_Sold = (old_sold - amount);
           String query    = "UPDATE demo.fournisseur_table SET sold =? Where id="+id;
           preparesStatemnt = conn.connect().prepareStatement(query);
           preparesStatemnt.setDouble(1, new_Sold);
           preparesStatemnt.executeUpdate();
           conn.connect().close();

       }
        public void retreive_reglement(double amount, double old_sold, int fournisseurID) throws SQLException {

        Db_Connection conn = new Db_Connection();
        ResultSet resultSet = null;
        PreparedStatement  preparesStatemnt = null;
        double new_Sold = (old_sold + amount );
        String query    = "UPDATE demo.fournisseur_table SET sold =? Where id="+fournisseurID;
        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.setDouble(1, new_Sold);
        preparesStatemnt.executeUpdate();
        conn.connect().close();

    }
        public double get_Sold(int fournisseurID) throws SQLException {

             double updatedsold = 0.025;
             String query = "SELECT sold from demo.fournisseur_table  where id ="+fournisseurID;

                 Connection cnn = conn.connect();
                 preparesStatemnt = cnn.prepareStatement(query);
                 resultSet = preparesStatemnt.executeQuery();
              if(resultSet.next()){
               updatedsold = resultSet.getDouble(1);
           }
           cnn.close();

             return updatedsold;
          }
          // Get Fournisseur ID
           public int getFournisseur_ID(String  fournisseurName) throws SQLException {
              int fournisseurID = 0;
              String query = "SELECT id from demo.fournisseur_table  where name = '"+fournisseurName+"'";
              Connection cnn = conn.connect();
              preparesStatemnt = cnn.prepareStatement(query);
              resultSet = preparesStatemnt.executeQuery();
              if(resultSet.next()){
                  fournisseurID = resultSet.getInt(1);
                   }
                cnn.close();
               return fournisseurID;
          }

        public void update_sold(int fournisseurID) throws SQLException {

           double total_amount = get_Sum_Amount(fournisseurID);
           double new_sold = get_Sold(fournisseurID) - total_amount;

            Db_Connection conn = new Db_Connection();
            ResultSet resultSet = null;
            PreparedStatement  preparesStatemnt = null;

            String query    = "UPDATE demo.fournisseur_table SET sold =? Where id="+fournisseurID;
            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setDouble(1, new_sold);
            preparesStatemnt.executeUpdate();
            conn.connect().close();

           }













}