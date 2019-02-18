package Login_Package;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
public class Main extends Application implements EventHandler<KeyEvent> {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("Login_View.fxml"));

        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.getScene().setOnKeyPressed(this);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public void handle(KeyEvent event){
    System.out.println("Hello world");

    }



    public static void main(String[] args) {
        launch(args);
    }


}
