package Utilities_Package;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import static org.controlsfx.control.Notifications.create;

public class Notification {

    public void show_Information(String s){
        Notifications note = create()
                .text(s)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle();

        note.showInformation();
    }
    public void show_Warrning(String s){

        Notifications note = create()
                .text(s)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT) .darkStyle();
               note.showWarning();

    }
    public void show_Confirmation(String s){

        Notifications note = create()
                .text(s)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT) .darkStyle();
                note.showConfirm();

    }
    public void show_Error(String s){

        Notifications note = create()
                .text(s).position(Pos.TOP_LEFT).darkStyle()
                .hideAfter(Duration.seconds(3));
                note.showError();

    }

}
