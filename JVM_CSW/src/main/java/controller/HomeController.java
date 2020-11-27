package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Cleanup;
import model.CriticalPathFactory;
import static utils.Constants.*;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HomeController implements Initializable {

    @FXML
    private Button CreateProjectBtn;


    @FXML
    private Label lblProjectCount;


    @FXML
    private VBox vTaskItems;

    @FXML
    private Button CriticalPathButton;

    @FXML
    private AnchorPane AnchorPanel;

    @FXML
    private Button RefreshBtn;

    private ObservableList<CriticalPathFactory> listOfTasks;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == CreateProjectBtn) {

            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(event,FXML_PROJECT_FORM);

        }
    }

    @FXML
    public void loadCriticalPathWindow(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == CriticalPathButton) {

            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(mouseEvent,FXML_Critical_PATH);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DisplayItems displayItems =  new DisplayItems();
        displayItems.Display(vTaskItems,lblProjectCount);
    }

    @FXML
    public void ChangeDirectorie(MouseEvent mouseEvent) {

        ChooseProject chooseProject = new ChooseProject();
        chooseProject.choose(AnchorPanel);

    }

    @FXML
    public void RefreshPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == RefreshBtn) {
            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(mouseEvent,FXML_HOME);
        }
    }
}
