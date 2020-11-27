package controller;

import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;

import static utils.Constants.ROOT_DIRECTORY;
import static utils.Constants.PROJECTS_DATA;

/**
 * @author josed
 */
public class ChooseProject {

    public void choose (AnchorPane AnchorPanel){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ROOT_DIRECTORY));

        Stage stage = (Stage) AnchorPanel.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        String ChangedDirectories = file.getAbsolutePath();
        PROJECTS_DATA = ChangedDirectories;
        System.out.println(ChangedDirectories);
    }
}
