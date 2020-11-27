package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CriticalPathFactory;
import utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;


public class TaskItemController implements Initializable {

    @FXML
    private ImageView iconSelect;

    @FXML
    private Label lblTaskName;

    @FXML
    private Button btnInfo;

    @FXML
    private Label lblTeamLeader;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblTlf;

    @FXML
    private Label lblChildren;

    @FXML
    private Label lblDuration;

   @FXML
    private Label lblProjectName;

    @FXML Label lblId;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setTask(CriticalPathFactory model) {
        ContextMenu menu = new ContextMenu();
        System.out.println(model.toString());
        lblTaskName.setText("Project: " + model.getName());
        lblTeamLeader.setText("Team Leader: " + model.getTeamLeader());
        lblEmail.setText(model.getEmail());
        lblTlf.setText(model.getTlf());
        lblChildren.setText("Children: " + model.getChild());
        lblDuration.setText("Duration: " + model.getDuration());
        lblId.setText("ID: " + model.getId());
        btnInfo.setText(model.getStatus());



        if (model.getStatus().contains("Done")) {
            iconSelect.setImage(new Image(getClass().getResourceAsStream(Constants.ICON_CHECK_FILL)));
        } else {
            iconSelect.setImage(new Image(getClass().getResourceAsStream(Constants.ICON_CHECK_UNFILL)));

        }

        lblTaskName.setContextMenu(menu);
    }

}
