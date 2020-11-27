package controller;

import javafx.scene.control.TextArea;
import model.CriticalPathFactory;

import java.util.List;

/**
 * @author Charlie Mico
 */

/** Creates the visual representation of the CPA algorithm and displays in CriticalPathArea TextArea **/
public class DisplayCriticalPath {


    public void displayCriticalPath(String startPoint, List<CriticalPathFactory> path, float totalDuration, TextArea CriticalPathArea) {
        StringBuilder builder = new StringBuilder();
        final String NEW_LINE = "\n";
        final String TAB = "\t";
        if(path.size() < 1) {
            CriticalPathArea.setText("No Elements, so unable to calculate critical path. Please add some tasks");
            return;
        }

        builder
                .append("Critical Path from ")
                .append(startPoint)
                .append(" total duration ")
                .append(totalDuration)
                .append(":")
                .append(NEW_LINE);

        path.forEach(e ->
                builder .append(TAB)
                        .append("Name: ")
                        .append(e.getId())
                        .append(", Duration: ")
                        .append(e.getDuration())
                        .append(e.getChildren())
                        .append(NEW_LINE));

        System.out.println("Test");
        CriticalPathArea.setText(builder.toString());
    }
}
