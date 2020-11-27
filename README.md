# JVM_Coursework
*****Important******
Java jdk-8 must be used to run the project.
Task Manager
Home Page:

Create Project Button:
Prompts the Create Project Window

Change Project Button:
When pressed the windows filechooser will be opened , in the directory the projects are stored at,
in order to change the project the desired Parent Project must be selected , inside the tasks.json must be selected as it contains
the information of all child projects, after pressing open , the refresh button must be present again in order to see the changes.

Critical Path Button:
Prompts the Critical Path Window

Refresh Button:
Refreshes the Windows, must be pressed to see updates.

Vbox: 
Present in all windows, shows all tasks linked to a Main Project and their information.



Create Project Window:

Our Task Manager permits creation of a Parent Project , which child projects will be added to, we perform this task by 
Using a Project Form , which will be Prompted when pressing Create Project button, the form has the following fields:


Project Name:Parent Project
Project ID : Name of child project you are creating 
Team Leader:Leader of this project
Team Leader Email
Team Leader Phone Number 
Status : Set the initial status of the task or urgency
Duration: The stimated duration of the current child Project 
Children: Sucessor tasks.

All information will be displayed in the VBox at the right of the Screen.
In order for the VBox to update the refresh button must be pressed 

Save Button: 
Saves the information to the correspondent tasks.json file , within the Folder inidicated by the input in the Project Name Field 
If the Project Name is not a folder , a new folder and tasks.json will be generated.

Home Button:
Return to Home Page.

Change Project Button: 
When pressed the windows filechooser will be opened , in the directory the projects are stored at,
in order to change the project the desired Parent Project must be selected , inside the tasks.json must be selected as it contains
the information of all child projects, after pressing open , the refresh button must be present again in order to see the changes.

Refresh Button:
Refreshes the Windows, must be pressed to see updates.

Critical Path Window:

Kotlin Button: 
Will activate CAP kotlin implementation and display its output to the Text Area.

Scala Button:
Will activate CAP Scala implementation and display its output to the Text Area.

Home Button:
Return To the Home Page 

Change Project Button:
Select a new Project, the contents of the project selected will be displayed in the Vbox and now the CAP for this 
project can be calculated.

Refresh Button:
Refreshes the Windows, must be pressed to see updates. 








References:

Amos Chepchieng, 2019, JavaFX Task Management Sample,Viewed 10 November 2020  ,https://github.com/k33ptoo/javafx-tasks Template Used for the GUI construction.
Amos Chepchieng,2019 ,javafx ui - task management sample ui #1 - free code,Viewed 10 November 2020,https://www.youtube.com/watch?v=5T_9K3ZkCf8&t=2993s


