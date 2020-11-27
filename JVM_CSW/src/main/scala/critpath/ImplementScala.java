package critpath;

import controller.DisplayCriticalPath;
import critpath.CriticalPath;
import critpath.DAG;
import javafx.scene.control.TextArea;
import model.CriticalPathFactory;
import persistance.FilePersistence;
import scala.Tuple2;
import scala.collection.immutable.Set;
import utils.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author josed
 */
public class ImplementScala {

    public void criticalPath(TextArea CriticalPathArea){

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
}
