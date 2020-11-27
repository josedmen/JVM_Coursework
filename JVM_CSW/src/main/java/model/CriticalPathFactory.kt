package model
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString

/** Model for the data we which to store inside our files , each parameter corresponds to a textfield in the GUI**/

/**
@author josed
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
class CriticalPathFactory(val name: String, val status: String, val email: String,val tlf: String
                          ,val teamLeader: String,val id : String,val Child: String,val children: List<String>,val Duration :Float)