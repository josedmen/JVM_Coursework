package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.CriticalPathFactory;
import utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Constants.FXML_ITEM_TASK;
import static utils.Constants.LIST_OF_TASKS;

/**
 * @author josed
 */
public class DisplayItems {

    public void Display(VBox vTaskItems, Label lblProjectCount){
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(fetchList);

        fetchList.setOnSucceeded((event) -> {

            LIST_OF_TASKS = FXCollections.observableArrayList(fetchList.getValue());
            int size = LIST_OF_TASKS.size();
            lblProjectCount.setText("Projects: (" + size + ")");

            try { //load task items to vbox
                Node[] nodes = new Node[size];
                for (int i = 0; i < nodes.length; i++) {
                    //load specific item
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_ITEM_TASK));
                    TaskItemController controller = new TaskItemController();
                    loader.setController(controller);
                    nodes[i] = loader.load();
                    vTaskItems.getChildren().add(nodes[i]);
                    controller.setTask(LIST_OF_TASKS.get(i));
                }

                //for errors
                for (int i = 0; i < nodes.length; i++) {
                    try {
                        nodes[i] = FXMLLoader.load(getClass().getResource(FXML_ITEM_TASK));

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
                System.out.println(url);
                list = new Gson().fromJson(url, new TypeToken<List<CriticalPathFactory>>() {
                }.getType());
                //System.out.println(list);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }


    };

}
