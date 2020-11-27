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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kotlin.Pair;
import lombok.Cleanup;
import model.ChildrenPairFactory;
import model.CriticalPathFactory;
import model.ProjectFactory;
import persistance.FilePersistence;
import utils.Constants;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Constants.FXML_HOME;
import static utils.Constants.FXML_PROJECT_FORM;
import static utils.Constants.ROOT_DIRECTORY;



public class Project_FormController implements Initializable {


    @FXML
    private TextField ProjectName;

    @FXML
    private TextField TeamLeader;

    @FXML
    private TextField Email;

    @FXML
    private TextField StatusTxt;

    @FXML
    private TextField PhoneNumberTxt;

    @FXML
    private Button SaveButton;

    @FXML
    private Button HomeButton;

    @FXML
    private Label lblProjectCount;


    @FXML
    private VBox vTaskItems;

    @FXML
    private Label lblGreeting;

    @FXML
    private TextField DurationTxt;

    @FXML
    private TextField ChildrenTxt;

    @FXML
    private TextField IdTxt;

    @FXML
    private Button RefreshBtn;

    @FXML
    private Button ChangeProject;

    @FXML
    private AnchorPane AnchorPanel;

    private ObservableList<CriticalPathFactory> listOfTasks;

    private double xOffset = 0;
    private double yOffset = 0;


    public Project_FormController() {
    }

    @FXML
    protected void SaveData(MouseEvent event) {
      /**Checks that the necessary Fields are filled , if not will display enter All Display for the User **/
        if (ProjectName.getText().isEmpty() || TeamLeader.getText().isEmpty() || Email.getText().isEmpty()
                || PhoneNumberTxt.getText().isEmpty() || DurationTxt.getText().isEmpty()) {
            lblGreeting.setTextFill(Color.TOMATO);
            lblGreeting.setText("Enter all details !!");
        } else {

            saveData();
        }

    }

    private void clearFields() {
        ProjectName.clear();
        TeamLeader.clear();
        PhoneNumberTxt.clear();
        Email.clear();
        StatusTxt.clear();
        ChildrenTxt.clear();
        DurationTxt.clear();
        IdTxt.clear();

    }

    private String saveData() {

        /** User Defined Directory Path,gives the user the ability of Creating a Main Project Folder and Add inside Child tasks **/

        String DIRECTORY_PATH = "./src/main/resources/Projects/" + ProjectName.getText() ;

        try {

            ChildrenPairFactory ch = new ChildrenPairFactory(this.ChildrenTxt);
            ProjectFactory factory = new ProjectFactory(ProjectName.getText()
                    , StatusTxt.getText(), Email.getText(), PhoneNumberTxt.getText()
                    , TeamLeader.getText(), IdTxt.getText(), ChildrenTxt.getText(), ch.childs(ChildrenTxt.getText()),
                    Float.parseFloat(DurationTxt.getText()));

            CriticalPathFactory criticalPathFactory = new CriticalPathFactory(ProjectName.getText()
                    , StatusTxt.getText(), Email.getText(), PhoneNumberTxt.getText()
                    , TeamLeader.getText(), IdTxt.getText(), ChildrenTxt.getText(), ch.childs(ChildrenTxt.getText()),
                    Float.parseFloat(DurationTxt.getText()));

            FilePersistence file = new FilePersistence();
            Pair<ProjectFactory, List<CriticalPathFactory>> load_pair = file.loadProject(DIRECTORY_PATH);

            if(load_pair.component1() != null) {

                List<CriticalPathFactory> list = new ArrayList<>();
                list.addAll(load_pair.component2());
                list.add(criticalPathFactory);
                file.saveProject(DIRECTORY_PATH, factory, list);

            } else {
                file.saveProject(DIRECTORY_PATH, factory, Arrays.asList(criticalPathFactory));
            }
            clearFields();
            return "Success";
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            lblGreeting.setTextFill(Color.TOMATO);
            lblGreeting.setText(ex.getMessage());
            return "Exception";
        }
    }

    public void ReturnHome(MouseEvent event) {
        if (event.getSource() == HomeButton) {
            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(event,FXML_HOME);

        }
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DisplayItems displayItems =  new DisplayItems();
        displayItems.Display(vTaskItems,lblProjectCount);
    }

    @FXML
    public void RefreshPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == RefreshBtn) {
            LoadWindow loadWindow = new LoadWindow();
            loadWindow.Load(mouseEvent,FXML_PROJECT_FORM);
        }
    }

    @FXML
    public void ChangeDirectorie(MouseEvent mouseEvent) {

        ChooseProject chooseProject = new ChooseProject();
        chooseProject.choose(AnchorPanel);
    }
}

