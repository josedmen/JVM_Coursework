package model

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString


@Getter
@Setter
@NoArgsConstructor
@ToString
class ProjectFactory(val name: String, val status: String, val email: String,val tlf: String
                     ,val teamLeader: String,val id : String,val Child: String,val children: List<String>,val Duration :Float)

