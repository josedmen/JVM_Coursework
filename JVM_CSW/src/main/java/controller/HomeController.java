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
import utils.Constants;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static utils.Constants.ROOT_DIRECTORY;

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
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource(Constants.FXML_PROJECT_FORM)));
                stage.setScene(scene);
                stage.show();

                // Grab your root here
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
    public void loadCriticalPathWindow(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == CriticalPathButton) {
            try {
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource(Constants.FXML_Critical_PATH)));
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

                //for errors
                for (int i = 0; i < nodes.length; i++) {
                    try {
                        nodes[i] = FXMLLoader.load(getClass().getResource(Constants.FXML_ITEM_TASK));

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
    public void ChangeDirectorie(MouseEvent mouseEvent) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(ROOT_DIRECTORY));

        Stage stage = (Stage) AnchorPanel.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);
        String ChangetoDirectory = file.getAbsolutePath();
        Constants.PROJECTS_DATA = ChangetoDirectory;
        System.out.println(ChangetoDirectory);

    }

    @FXML
    public void RefreshPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == RefreshBtn) {
            try {
                Node node = (Node) mouseEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource(Constants.FXML_HOME)));
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
}
