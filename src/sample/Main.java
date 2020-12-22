package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
                AnchorPane rootElement = (AnchorPane) loader.load();
                Scene scene = new Scene(rootElement);
                primaryStage.setTitle("JavaFX meets OpenCV");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main (String[]args) throws Exception{
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            launch(args);
        }
    }



//        AnchorPane root = FXMLLoader.load(getClass().getResource("FXML/sample.fxml"));
//        primaryStage.initStyle(StageStyle.UNDECORATED);
//        Scene scene = new  Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }
//
//    public static void main(String[] args) throws Exception {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        launch(args);
//    }
//}


//import javafx.geometry.Rectangle2D;
//import javafx.scene.Parent;
//import javafx.scene.control.Alert;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCodeCombination;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Screen;


//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            AnchorPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//            Scene scene = new Scene(root);
//
////            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
////            primaryStage.getIcons().add(new Image("logo.png"));
////            primaryStage.setTitle("e x o V i s i x | Smart & Intelligent Computer Vision Solution ");
//
////            DB obj = new DB();
//
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
    //for getting the screen size
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getVisualBounds();

//        primaryStage.setX(bounds.getMinX());//
//        primaryStage.setY(bounds.getMinY());
//        primaryStage.setWidth(bounds.getWidth());
//        primaryStage.setHeight(bounds.getHeight());//

//        KeyCombination keyCombinationWin1 = new KeyCodeCombination(KeyCode.TAB, KeyCombination.ALT_ANY);
//        KeyCombination keyCombinationWin2 = new KeyCodeCombination(KeyCode.WINDOWS, KeyCombination.ALT_ANY);

//        root.addEventHandler

    //        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (keyCombinationWin1.match(event) || keyCombinationWin2.match(event) ||
//                    event.getCode() == KeyCode.WINDOWS || event.getCode() == KeyCode.ALT ) {
//
//            }
//        });
