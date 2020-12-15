package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InputDetails {
    public void ToGo(ActionEvent actionEvent) throws IOException {
        Parent Home = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        Scene HomeScene = new Scene(Home);
        Stage Window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Window.setScene(HomeScene);
        if(win){
            Window.setFullScreen(true);
            Window.setFullScreenExitHint("");
        }

        Window.show();
    }

}
