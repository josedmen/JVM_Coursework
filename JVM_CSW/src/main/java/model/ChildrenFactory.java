package model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;


public class ChildrenFactory {

    @FXML
    private TextField ChildrenTxt;

    private  String[] Children;

    public ChildrenFactory(){

        While(ChildrenTxt.getText().isEmpty() == false);{

            Children[0] = ChildrenTxt.getText();



        }

    }

}
