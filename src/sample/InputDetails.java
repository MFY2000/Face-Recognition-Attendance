package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InputDetails {
    public AnchorPane Error;
    public TextField fname;
    public TextField code;
    public TextField lname;
    public TextField reg;
    public TextField age;
    public ComboBox Section;
    public ImageView mainImage;

    public void ToGo(ActionEvent actionEvent) throws IOException {
        Parent Home = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
        Scene HomeScene = new Scene(Home);
        Stage Window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Window.setScene(HomeScene);
        if (Window.isFullScreen()) {
            Window.setFullScreen(true);
            Window.setFullScreenExitHint("");
        }

        Window.show();
    }


    public void initialize() throws Exception {
        UserHolder holder = UserHolder.getInstance();
        UserDetails u = holder.getUser();

        code.setText("189");// here the unique come db;
        code.setDisable(true);

//        Section.set("9 to 5");// here a function come which dropdown
        Section.setDisable(true);
        InputStream stream = new FileInputStream(u.getAddress());
        Image image = new Image(stream);
        mainImage.setImage(image);
    }


    public void validity(InputMethodEvent inputMethodEvent) {
    }

    void sendData(){

    }

    public void UploadData(ActionEvent actionEvent) {
        if ((fname.getText()).length() != 0) {
            if ((lname.getText()).length() != 0) {
                if ((age.getText()).length() != 0) {
                    if ((reg.getText()).length() != 0) {
                        sendData();
                    } else {
                        Error.setDisable(false);
                        Error.setOpacity(100);
                        // label
                    }
                } else {
                    Error.setDisable(false);
                    Error.setOpacity(100);
                    // label
                }
            } else {
                Error.setDisable(false);
                Error.setOpacity(100);
                // label
            }
        } else {
            Error.setDisable(false);
            Error.setOpacity(100);
            // label
        }
    }
}


