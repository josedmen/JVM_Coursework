package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Constants;

import java.net.URL;

/**
 * @author josed
 */
public class Main extends Application {

    // Define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {





        URL path = getClass().getResource(Constants.FXML_HOME);
        if (path != null) {
            Parent root = FXMLLoader.load(path);
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(Constants.APP_TITLE);
            stage.initStyle(StageStyle.TRANSPARENT);

            // Grab your Window
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

           // Move around here
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            stage.show();
        } else {
            System.exit(-1);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }



}
