package critical_path

import controller.DisplayCriticalPath
import javafx.scene.control.TextArea
import model.CriticalPathFactory
import persistance.FilePersistence
import utils.Constants
import kotlin.io.path.ExperimentalPathApi

/**
 * @author josed
 */
class ImplementKotlin {
    @ExperimentalPathApi
    fun criticalPath(CriticalPathArea: TextArea?) {
        val taskList = FilePersistence().loadTasks(Constants.PROJECTS_DATA)
        val graph = TaskDAG(taskList)
        if (CriticalPathArea != null) {
            CriticalPathArea.text= "Kotlin Implementation"
        }
        val startPoint = "task_1"
        graph.toList().stream().map { e: CriticalPathFactory -> e.id + ":" + graph.calcTotalDuration(e.id) }.forEach { x: String? -> println(x) }
        val d = DisplayCriticalPath()
        d.displayCriticalPath(startPoint, graph.findCriticalPath(startPoint), graph.calcTotalDuration(startPoint), CriticalPathArea,"Kotlin Implementation")
    }
}