package persistance

import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import model.ChildrenPairFactory
import model.CriticalPathFactory
import model.ProjectFactory
import java.util.*
import kotlin.io.path.ExperimentalPathApi

/**
 * @author josed
 */
class SaveData {
    /** Reads the user input and saves the information to the Project Folder specified by the user
     * , Information will be saved to tasks.json inside this folder  */
    @ExperimentalPathApi
    fun saveData(ProjectName: TextField, StatusTxt: TextField, Email: TextField,
                 PhoneNumberTxt: TextField, TeamLeader: TextField, IdTxt: TextField,
                 ChildrenTxt: TextField, DurationTxt: TextField, lblGreeting: Label): String {
        /** User Defined Directory Path,gives the user the ability of Creating a Main Project Folder and Add inside Child tasks  */
        val PATH = "./src/main/resources/Projects/" + ProjectName.text
        return try {
            val ch = ChildrenPairFactory(ChildrenTxt)
            val factory = ProjectFactory(ProjectName.text, StatusTxt.text, Email.text, PhoneNumberTxt.text, TeamLeader.text, IdTxt.text, ChildrenTxt.text, ch.childs(ChildrenTxt.text), DurationTxt.text.toFloat())
            val criticalPathFactory = CriticalPathFactory(ProjectName.text, StatusTxt.text, Email.text, PhoneNumberTxt.text, TeamLeader.text, IdTxt.text, ChildrenTxt.text, ch.childs(ChildrenTxt.text), DurationTxt.text.toFloat())
            val file = FilePersistence()
            val loadPair = file.loadProject(PATH)
            if (loadPair.component1() != null) {
                val list: MutableList<CriticalPathFactory> = ArrayList()
                list.addAll(loadPair.component2())
                list.add(criticalPathFactory)
                file.saveProject(PATH, factory, list)
            } else {
                file.saveProject(PATH, factory, listOf(criticalPathFactory))
            }
            val clearFields = ClearFields()
            clearFields.clearFields(ProjectName, TeamLeader, PhoneNumberTxt, Email, StatusTxt, ChildrenTxt, DurationTxt, IdTxt)
            "Success"
        } catch (ex: Exception) {
            println(ex.message)
            lblGreeting.textFill = Color.TOMATO
            lblGreeting.text = ex.message
            "Exception"
        }
    }
}