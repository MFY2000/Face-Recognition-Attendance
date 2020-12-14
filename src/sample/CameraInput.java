package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CameraInput {


    public void ToGo(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fibonaccipriceretracement/FibonacciPriceRetracementDialog.fxml"));
        Parent content = (Parent) loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(content));
        stage.show();
    }
}
