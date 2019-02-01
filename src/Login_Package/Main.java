package Login_Package;

import javafx.application.Application;

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


        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:    System.out.println("Key Up"); break;
                    case ALT:  System.out.println("Key Down"); break;

                }
            }
        });




    }

    @Override
    public void handle(KeyEvent event){
    System.out.println("Hello world");

    }



    public static void main(String[] args) {
        launch(args);
    }


}
