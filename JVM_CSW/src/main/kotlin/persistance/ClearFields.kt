package persistance

import javafx.scene.control.TextField

/**
 * @author josed
 */
class ClearFields {
    /**  Clears Textfields after data is saved  */
    fun clearFields(ProjectName: TextField, TeamLeader: TextField, PhoneNumberTxt: TextField,
                    Email: TextField, StatusTxt: TextField, ChildrenTxt: TextField, DurationTxt: TextField, IdTxt: TextField) {
        ProjectName.clear()
        TeamLeader.clear()
        PhoneNumberTxt.clear()
        Email.clear()
        StatusTxt.clear()
        ChildrenTxt.clear()
        DurationTxt.clear()
        IdTxt.clear()
    }
}