package Utilities_Package;

import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


          Home_page_Scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
              @Override
              public void handle(KeyEvent event)  {
                  switch (event.getCode()) {
                      case BACK_SPACE: try{
                          Parent parent = FXMLLoader.load(getClass().getResource("/Home_Package/Home_View.fxml"));
                          Scene scene = new Scene(parent);
                          Stage stage = new Stage();
                          stage.setScene(scene);
                          stage.setTitle("Home Page");
                          stage.setFullScreen(false);
                          stage.setResizable(false);
                          stage.show();

                      } catch (Exception e){


                      } break;

                      case SHIFT:
                          System.out.println("Key Down");
                          showAlert("Event Handler");
                          break;
                  }
              }
          });
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

        // Showing Alert Message
        public void showAlert(String s){

                Notifications note = Notifications.create()
                        .text(s)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.BOTTOM_RIGHT)
                        .darkStyle();
                note.showConfirm();

        }



}