package Caisse_Package;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

    public class Caisse_Controller implements Initializable {

        @FXML
        public TableView<Caisse>  caisse_tableView;
        public TableColumn<Caisse,Integer> Id_column    ;
        public TableColumn<Caisse, String> name_column ;
        public TableColumn<Caisse, String> operationType_column ;
        public TableColumn<Caisse, String>   date_column ;
        public TableColumn<Caisse,Double>   amount_column ;
        public TableColumn<Caisse,Double>   old_soldcolumn;
        public TableColumn<Caisse,Double>   sold_column ;
        public TableColumn<Caisse,String>   note_column ;
        public TableColumn<Caisse,Double>   old_total_column ;
        public TableColumn<Caisse,Double>   total_column ;
        @FXML
        public Label total_lb;
        @FXML
        public Button closeButton;
        @FXML
        public TextField search_txt;
        public ObservableList<Caisse> data;
        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet rs = null;
        Utility utility = new Utility();
        /////////////////////////////////

        @FXML
        public void SearchThread() throws SQLException{


            name_column.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
          date_column.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            operationType_column.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Caisse> filteredData = new FilteredList<>(data, p -> true);
            // 2. Set the filter Predicate whenever the filter changes.
            search_txt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(caisse -> {
                    // If filter text is empty, display all persons.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (caisse.getPersonName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches Ref

                    } else if (caisse.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches Des.
                    }
                    else if (caisse.getOperationType().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches Des.
                    }


                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList.
            SortedList<Caisse> sortedData = new SortedList<>(filteredData);
            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(caisse_tableView.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            caisse_tableView.setItems(sortedData);
        }



        public void load(){
            int personType_int=1;
            try{

                data = FXCollections.observableArrayList();
                rs = conn.connect().createStatement().executeQuery("SELECT R.id,P.name,R.date,R.amount,R.old_sold,R.sold,R.note,P.PersonType,P.id ,R.old_total_caisse,R.total_caisse FROM demo.person_reglement_table R, demo.person_table P where R.personID=P.id and R.id>0 ;");
                while(rs.next()){
                     int     id     =  rs.getInt(   1);
                    String  Name   =  rs.getString(2);
                     personType_int = rs.getInt(8);
                     String date =  rs.getString(3);
                    double amount = rs.getDouble(4);
                    double old_sold = rs.getDouble(5);
                    double sold = rs.getDouble(6);
                    String note =  rs.getString(7);
                    double old_total = rs.getDouble(10);
                    double total = rs.getDouble(11);
                    String OperationType=null;

                    if (personType_int==2)    {OperationType="Verssement [-]";}
                    else if(personType_int==1){OperationType="Paiment     [+]";}

                    data.add(new Caisse(id,Name, OperationType,date,amount,old_sold,sold,note,rs.getInt(9),old_total,total));
                }

            }
            catch(SQLException eX){
                eX.printStackTrace();
                System.out.println("error ! Not Connected to Db****");
            }
            finally{
                Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
                name_column.setCellValueFactory(new PropertyValueFactory<>("personName"));
                operationType_column.setCellValueFactory(new PropertyValueFactory<>("operationType"));
                date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
                amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
                old_soldcolumn.setCellValueFactory(new PropertyValueFactory<>("oldsold"));
                sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
                note_column.setCellValueFactory(new PropertyValueFactory<>("note"));
                old_total_column.setCellValueFactory(new PropertyValueFactory<>("old_total"));
                total_column.setCellValueFactory(new PropertyValueFactory<>("total"));
                caisse_tableView.setItems(data);

            }


        }

    @FXML
    public Button goHome_btn;
    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
    }
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
              break;

        }
    }
        @FXML
        private void closeButtonAction(){
            // get a handle to the stage
            Stage stage = (Stage) closeButton.getScene().getWindow();
            // do what you have to do

            stage.close();
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();

        try {
            double total = utility.get_caisse_total();

            total_lb.setText("TOTAL : "+String.format("%,.2f", total)+" DZD");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Utility().Button_request_focus(goHome_btn);
    }
}
