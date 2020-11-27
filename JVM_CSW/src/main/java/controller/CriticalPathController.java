package controller;

import critical_path.ImplementKotlin;
import critpath.ImplementScala;
import javafx.scene.layout.AnchorPane;
import model.CriticalPathFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

import static utils.Constants.*;


/**
 * @author josed
 */

/**Class in charge of handling the Critical_Path_Window , it collects the information from the corresponding sources and displays them**/

public class CriticalPathController implements Initializable {

    @FXML
    private Button KotlinButton;

    @FXML
    private Button ScalaButton;

    @FXML
    private Label lblProjectCount;

    @FXML
    private Label lblDisplay;

    @FXML
    private VBox vTaskItems;
    @FXML
    private Button HomeButton1;

    @FXML
    private TextArea CriticalPathArea;

    @FXML
    private Button RefreshBtn;

    @FXML
    private Button ChangeProjectBtn;

    @FXML
    private AnchorPane AnchorPanel;


    private ObservableList<CriticalPathFactory> listOfTasks;

    @FXML
    private void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    private double xOffset = 0;
    private double yOffset = 0;



/** Corresponds to the Scala Implementation uses code extracted from the scala package**/
    @FXML
    public void LoadScala(MouseEvent mouseEvent) {

        ImplementScala implementScala = new ImplementScala();
        implementScala.criticalPath(CriticalPathArea);
    }


    /** Corresponds to the Kotlin Implementation uses code extracted from the kotlin package**/

    @FXML
    public void LoadKotlin(MouseEvent mouseEvent) {

        ImplementKotlin implementKotlin = new ImplementKotlin();
        implementKotlin.criticalPath(CriticalPathArea);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DisplayItems displayItems =  new DisplayItems();
        displayItems.Display(vTaskItems,lblProjectCount);

    }

    public void ReturnHome(MouseEvent event) {
        if (event.getSource() == HomeButton1) {

            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(event,FXML_HOME);

        }
    }

    @FXML
    public void RefreshPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == RefreshBtn) {

            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(mouseEvent,FXML_Critical_PATH);
        }
    }

    @FXML
    public void ChangeDirectorie(MouseEvent mouseEvent) {

        ChooseProject chooseProject = new ChooseProject();
        chooseProject.choose(AnchorPanel);

    }
}
