package sample;

        import javafx.beans.InvalidationListener;
        import javafx.beans.property.BooleanProperty;
        import javafx.beans.property.SimpleBooleanProperty;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;
        import java.io.IOException;



public class CameraInput {
    public TextField ID;
    public TextField temp;
    public Button logged;
    public RadioButton show;
    public PasswordField pass;
    public Label log;
    public Button close;
    public Button full;
    public Button minimize;
    public Hyperlink web;
    public boolean win = false;




    // TextFieldwork//
    @FXML
    private void GetOnPassword(KeyEvent key1) {
        if (key1.getCode() == KeyCode.ENTER) {
            pass.requestFocus();
        }
        if (key1.getCode() == KeyCode.F11){
            Stage stage = (Stage) ((Node) key1.getSource()).getScene().getWindow();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            win = true;
        }
    }
    //end//


    //RadioButton//

    public void ShowPass(ActionEvent actionEvent) {


        BooleanProperty showPassword = new SimpleBooleanProperty() {
            @Override
            protected void invalidated() {
                String txt;


                if (show.isSelected()) {
                    txt = pass.getText();
                    pass.setVisible(false);
                    temp.setText(txt);
                    temp.setVisible(true);
                    temp.setOpacity(100);
                    temp.setEditable(true);
                } else {
                    txt = temp.getText();
                    temp.setVisible(false);
                    pass.setText(txt);
                    pass.setVisible(true);
                }


            }
        };

        showPassword.bind(show.selectedProperty());
    }

    public void Full(KeyEvent key3) {
        if(show.isSelected()){
            if ((key3.getCode() == KeyCode.F11)){
                Stage stage = (Stage) ((Node) key3.getSource()).getScene().getWindow();
                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);
                win = true;
            }
        }

        else {
            if (key3.getCode() == KeyCode.F11){
                Stage stage = (Stage) ((Node) key3.getSource()).getScene().getWindow();
                stage.setFullScreenExitHint("");
                stage.setFullScreen(true);
                win = true;
            }
        }
    }
//end//

    //PasswordField//

    @FXML
    private void LogIn(KeyEvent key2) {
        if (key2.getCode() == KeyCode.ENTER) {
            logged.requestFocus();
            logged.isPressed();
        }
        if (key2.getCode() == KeyCode.F11){
            Stage stage = (Stage) ((Node) key2.getSource()).getScene().getWindow();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            win = true;
        }
    }
//end//


    //closeButton//
    public void CloseWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();

    }
//

    //Full Screen button//
    public void FullScreen(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        win = true;


    }

    public void full2(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.F11){
            Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();
            stage.setFullScreenExitHint("");
            stage.setFullScreen(true);
            win = true;
        }
    }
//end//


    //MaximizeButton//
    public void MinimizeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    //end//

    //Hyperlink
    public void GetOnSignUp(ActionEvent actionEvent)  throws IOException{

        Parent Signup = FXMLLoader.load(getClass().getResource("SIGNUP.fxml"));
        Scene SignupScene = new Scene(Signup);
        Stage Window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Window.setScene(SignupScene);
        if(win){
            Window.setFullScreen(true);
            Window.setFullScreenExitHint("");
        }

        Window.show();

    }
    //end//

    //LogIn//
    public void LoggedIn(ActionEvent actionEvent) throws IOException {

    }

    //end//

    public void ToGo(ActionEvent actionEvent) throws IOException {
        Parent Home = FXMLLoader.load(getClass().getResource("FXML/UserDetails.fxml"));
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
