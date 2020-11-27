package controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import persistance.SaveData;

import java.net.URL;
import java.util.ResourceBundle;

import static utils.Constants.FXML_HOME;
import static utils.Constants.FXML_PROJECT_FORM;

/** Controls the Create_Project_Window **/



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


    @FXML

    protected void SaveData(MouseEvent event) {
      /**Checks that the necessary Fields are filled , if not will display enter All Display for the User **/
        if (ProjectName.getText().isEmpty() || TeamLeader.getText().isEmpty() || Email.getText().isEmpty()
                || PhoneNumberTxt.getText().isEmpty() || DurationTxt.getText().isEmpty()) {
            lblGreeting.setTextFill(Color.TOMATO);
            lblGreeting.setText("Enter all details !!");
        } else {

            SaveData saveData = new SaveData();
            saveData.saveData(ProjectName,StatusTxt,Email,PhoneNumberTxt,TeamLeader,IdTxt,ChildrenTxt,DurationTxt,lblGreeting);

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

