package Utilities_Package;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

    public class Utility {

    private String view_Window, view_title;

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
        App_Stage.show();

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