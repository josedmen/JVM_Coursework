package controller;

import critical_path.TaskDAG;
import critpath.CriticalPath;
import critpath.DAG;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import model.CriticalPathFactory;
import persistance.FilePersistence;
import scala.Tuple2;
import scala.collection.immutable.Set;
import utils.Constants;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

        System.out.println("Scala Demo Start");
        CriticalPathArea.clear();
        CriticalPathArea.setText("Scala Demo Start");
        List<CriticalPathFactory> task_list = new FilePersistence().loadTasks(Constants.PROJECTS_DATA);


        Map<String, CriticalPathFactory> task_map = new HashMap<>();
        Map<String, List<String>> task_relation = new HashMap<>();
        task_list.forEach(task -> {
            task_map.put(task.getId(), task);
            task_relation.put(task.getId(),
                    task.getChildren().stream().filter(childID -> !childID.equals("")).collect(Collectors.toList()));
        });

        DAG<String> dag = CriticalPath.makeDAG(task_relation);

        List<Tuple2<String, Set<String>>> criticalPath = CriticalPath.findCriticalPath("task_1",
                dag,
                task_map::get,
                CriticalPathFactory::getDuration);

        List<CriticalPathFactory> path = new ArrayList<>();
        path.add(task_map.get(criticalPath.get(0)._1()));
        criticalPath.get(0)._2().foreach(e -> path.add(task_map.get(e)));
        float total_duration = CriticalPath.getCriticalPathDuration(path, (e) -> ((CriticalPathFactory)e).getDuration());


        DisplayCriticalPath d = new DisplayCriticalPath();
        d.displayCriticalPath("task_1", path, total_duration,CriticalPathArea);
    }


    /** Corresponds to the Kotlin Implementation uses code extracted from the kotlin package**/

    @FXML
    public void LoadKotlin(MouseEvent mouseEvent) {

        List<CriticalPathFactory> task_list = new FilePersistence().loadTasks(Constants.PROJECTS_DATA);

        TaskDAG graph = new TaskDAG(task_list);

        final String start_point = "task_1";
        graph.toList().stream().map(e -> e.getId() + ":" + graph.calcTotalDuration(e.getId())).forEach(System.out::println);

        DisplayCriticalPath d = new DisplayCriticalPath();
        d.displayCriticalPath(start_point, graph.findCriticalPath(start_point), graph.calcTotalDuration(start_point),CriticalPathArea);
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
