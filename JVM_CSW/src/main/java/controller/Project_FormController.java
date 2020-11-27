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
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource(FXML_HOME)));
                stage.setScene(scene);
                stage.show();

                scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });

                scene.setOnMouseDragged((MouseEvent mouseEvent) -> {
                    stage.setX(mouseEvent.getScreenX() - xOffset);
                    stage.setY(mouseEvent.getScreenY() - yOffset);
                });


            } catch (IOException ex) {

                System.err.println(ex.getMessage());
            }

        }
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(fetchList);

        fetchList.setOnSucceeded((event) -> {

            listOfTasks = FXCollections.observableArrayList(fetchList.getValue());
            int size = listOfTasks.size();
            lblProjectCount.setText("Projects: (" + size + ")");


            try { //load task items to vbox
                Node[] nodes = new Node[size];
                for (int i = 0; i < nodes.length; i++) {
                    //load specific item
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.FXML_ITEM_TASK));
                    TaskItemController controller = new TaskItemController();
                    loader.setController(controller);
                    nodes[i] = loader.load();
                    vTaskItems.getChildren().add(nodes[i]);
                    controller.setTask(listOfTasks.get(i));
                }

                // Optional
                for (int i = 0; i < nodes.length; i++) {
                    try {
                        nodes[i] = FXMLLoader.load(getClass().getResource(Constants.FXML_ITEM_TASK));
                        //vTaskItemsupcoming.getChildren().add(nodes[i]);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
                System.err.println("Error Creating Tasks...");
                System.err.println(e.getMessage());
            }
        });
    }

    public final Task<List<CriticalPathFactory>> fetchList = new Task() {

        @Override
        protected List<CriticalPathFactory> call() throws Exception {
            List<CriticalPathFactory> list = null;
            try {

                BufferedReader url = new BufferedReader(new FileReader(Constants.PROJECTS_DATA));
                //System.out.println(url);
                list = new Gson().fromJson(url, new TypeToken<List<CriticalPathFactory>>() {
                }.getType());
                //System.out.println(list);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

    };

    private static String readUrl(String urlString) throws Exception {

        @Cleanup
        BufferedReader reader = null;

        URL url = new URL(urlString);
        reader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder buffer = new StringBuilder();
        int read;
        char[] chars = new char[1024];
        while ((read = reader.read(chars)) != -1) {
            buffer.append(chars, 0, read);
        }

        return buffer.toString();
    }

    @FXML
    public void RefreshPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == RefreshBtn) {
            try {
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource(FXML_PROJECT_FORM)));
                stage.setScene(scene);
                stage.show();

                scene.setOnMousePressed((MouseEvent Event) -> {
                    xOffset = Event.getSceneX();
                    yOffset = Event.getSceneY();
                });

                scene.setOnMouseDragged((MouseEvent Event) -> {
                    stage.setX(Event.getScreenX() - xOffset);
                    stage.setY(Event.getScreenY() - yOffset);
                });


            } catch (IOException ex) {

                System.err.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void ChangeDirectorie(MouseEvent mouseEvent) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ROOT_DIRECTORY));

        Stage stage = (Stage) AnchorPanel.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        String ChangedDirectories = file.getAbsolutePath();
        Constants.PROJECTS_DATA = ChangedDirectories;
        System.out.println(ChangedDirectories);
    }
}

