package Home_Package;


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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class Home_Controller extends Notification implements Initializable {

    /// Notification Table
    @FXML
    public TableView<Notifications> note_table;
    @FXML
    public TableColumn<Notifications,Integer> id_Column;
    public TableColumn<Notifications,String> note_Column;
    public TableColumn<Notifications, String> date_Column;
    @FXML
    public Label notification_lb;
    @FXML
    public Pane notificationn_pane;
    @FXML
    public TextField notification_search_txt;

    @FXML
    public TitledPane notification_titlePane;
    public ObservableList<Notifications> data;
    Db_Connection conn = new Db_Connection();
    PreparedStatement preparesStatemnt = null;
    ResultSet rs = null;
    Utility utility = new Utility();


    @FXML
    public void Notifications_SearchThread() throws SQLException{


        note_Column.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
        date_Column.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Notifications> filteredData = new FilteredList<>(data, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        notification_search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(note -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (note.getNote().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Ref
                } else if (note.getDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Des.
                }


                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Notifications> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(note_table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        note_table.setItems(sortedData);
    }


  public void load_notifications() throws SQLException {
      Connection cnn = conn.connect();
      try{

          data = FXCollections.observableArrayList();
           rs = cnn.createStatement().executeQuery("SELECT * FROM demo.notification_table where status = 'unread'");
          while(rs.next()){
              data.add(new Notifications(

                      rs.getInt( 1),
                      rs.getString(2),
                      rs.getString(3),
                      rs.getString(4)

                     ));
          }
      }
      catch(SQLException eX){
          eX.printStackTrace();
          System.out.println("error ! Not Connected to Db****");
      }
     finally {
          int size =data.size();
          if (size!=0){
              notification_lb.setVisible(true);
              notification_lb.setText(size+"");
          }
          else {
              notification_lb.setVisible(false);
          }


          id_Column.setCellValueFactory(new PropertyValueFactory<>("id"));
          note_Column.setCellValueFactory(new PropertyValueFactory<>("note"));
          date_Column.setCellValueFactory(new PropertyValueFactory<>("date"));
          note_table.setItems(data);

          rs.close();
          conn.connect().close();
      }


  }

        @FXML
        public void remove_notification() throws SQLException
         {
             if (!note_table.getSelectionModel().isEmpty()){
                 Notifications notification = note_table.getSelectionModel().getSelectedItem();
                 int id = notification.getId();
                 String query = "DELETE FROM  demo.notification_table WHERE id="+id;
                 preparesStatemnt = conn.connect().prepareStatement(query);
                 preparesStatemnt.executeUpdate();
                 load_notifications();
                 int size =data.size();
                 if (size!=0){
                     notification_lb.setText(size+"");
                 }
                 else {
                     notification_lb.setVisible(false);
                 }

                 show_Confirmation("Notification has been removed ---- > size is " + size);


                 preparesStatemnt.close();
                 conn.connect().close();
             }


         }



  @FXML
  public void show_notification_vbox(){
      int size =data.size();
      if (size>0)
      {
          notification_titlePane.setExpanded(true);
          notification_titlePane.setVisible(true);
      }

  }

    @FXML
    public void hide_notification_vbox(){
        notification_titlePane.setExpanded(false);
        notification_titlePane.setVisible(false);
    }


   // product
    @FXML
    public void Open_Product_Window(Event event) throws IOException {

        new Utility().go_Pruduct(event);

    }

    // Stock
    @FXML
    public void Open_Stock_Window(Event event) throws IOException {
        new Utility().go_Stock(event);
    }
    // Client
    @FXML
    public void Open_Client_Window(Event event) throws IOException {
        new Utility().go_Client(event);
    }
    // Fournisseur
    @FXML
    public void Open_Fournisseur_Window(Event event) throws IOException {

       new Utility().go_Fournisseur(event);
    }

    // Caisse

    @FXML
    public void Open_Caisse_Window(Event event) throws IOException {
        new Utility().go_Caisse(event);
    }


    @FXML
    public void manage_Users()throws IOException {
      new Utility().show_Manage_Users();

    }
    // Open_Bon_Command_Window
    @FXML
    public void Open_Bon_Command_Window(Event event) throws IOException {
        new Utility().go_Bon_Command(event);
    }

    // Go Contoire
    @FXML
    public void Open_Contoire_Window(Event event)throws IOException  {
        new Utility().go_Contoir(event);
    }



    // Logout
    @FXML
    public void log_Out_Function(Event event) throws IOException {
        new Utility().log_Out(event);
    }

    // Event Handler
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
                Open_Fournisseur_Window(event);  break;
            case P:
                Open_Product_Window(event);break;
            case C:
                Open_Client_Window(event);break;
            case S:
                Open_Stock_Window(event);break;
            case ALT_GRAPH:
                Open_Caisse_Window(event);break;

        }
    }

    public void change_Sytle() {

        note_table.setStyle("-fx-selection-bar: #52BE80; -fx-font-weight: Bold ");

    }


    @FXML
    public void on_Click()
       {



        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        /////// Value changed listener in the Table
        note_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        change_Sytle();
                    }
                }
        );




        try {
            load_notifications();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size =data.size();
       if (size!=0){
           notification_lb.setVisible(true);
           notification_lb.setText(size+"");
           }
       else {
           notification_lb.setVisible(false);
            }





    }
}
