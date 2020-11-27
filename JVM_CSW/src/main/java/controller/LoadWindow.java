package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static utils.Constants.*;

/**
 * @author josed
 */
public class LoadWindow {

    public void Load(MouseEvent event, String path){
        try {
            /** Create and Load your new Window**/
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(path)));
            stage.setScene(scene);
            stage.show();


              /** Make your window movable**/
            scene.setOnMousePressed((
                    MouseEvent Event) -> {
                X_OFFSET = Event.getSceneX();
                Y_OFFSET = Event.getSceneY();
            });

            scene.setOnMouseDragged((MouseEvent Event) -> {
                stage.setX(Event.getScreenX() - X_OFFSET);
                stage.setY(Event.getScreenY() - Y_OFFSET);
            });




        } catch (IOException ex) {

            System.err.println(ex.getMessage());
        }

    }
}
