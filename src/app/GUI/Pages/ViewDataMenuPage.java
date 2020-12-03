package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import app.App;
import app.GUI.GUI;
import app.GUI.GUIPage;
import app.backend.tree.Tree;

public class ViewDataMenuPage extends GUIPage {
    private String NOT_FOUND = "That part can't be found!";

    public ViewDataMenuPage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK); // sets the color of the backgroud in the GUI
    }

    @Override
    public VariableComponent[] createComponents() {
        String newLine = ("Display the Next \n 10 Parts");
        VariableComponent[] components = {
                new VariableComponent(new JLabel("Part Search", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JLabel("<HTML><U>Enter Part Number:</U></HTML>"), .13, .3, .23, .10), // Underlines
                                                                                                                // the
                                                                                                                // text
                new VariableComponent(new JTextArea(), .38, .3, .23, .05),
                new VariableComponent(new JButton("Search"), .6, .3, .12, .05),
                new VariableComponent(new JButton("<html>" + newLine.replaceAll("\\n", "<br>") + "</html>"), .6, .4,
                        .17, .07), // displays text onto new line
                new VariableComponent(new JLabel(), .25, .6, .4, .4),
                new VariableComponent(new JButton("Back to Menu Page"), .88, .93, .23, .10) };
        this.setBackgroundAndTextOfComponentsInRange(components, 6, 6, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 1, Color.BLUE, Color.WHITE);// changes color of text
                                                                                                // and backColor
        this.setBackgroundAndTextOfComponentsInRange(components, 3, 3, Color.GREEN, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 4, 4, Color.RED, Color.BLACK);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JLabel) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 15));// Changes font + size of text
        // ((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        if (obj.equals(this.components[this.components.length - 1].component)) {// function that performs the actions
                                                                                // when button is clicked
            System.out.println("Back to menu page");
            prepareAndSwitchToPage(App.MENU, main);
        } else if (obj.equals(this.components[3].component)) {

            //Print out only this item
            printOutOrError(0);

        } else if (obj.equals(this.components[4].component)) {
            
            //Print out this and the next 10 items
            printOutOrError(10);
        }
    }

    private void printOutOrError(int xAfter) {
        //Get key
        //Since there is only 1 element, we grab it from the returned array instantly.
        String key = getStringsOfTextAreas(2)[0];

        //Get the pair at the current location (null if not found)
        ArrayList<String> keyAndNextX = tree.searchAndNextX(key, xAfter);
        
        if (keyAndNextX.size() > 0) {
            String output = "";
            for (String s : keyAndNextX) {
                String newLines = "\n";
                if (output.equals("")) {
                    newLines += "\n";
                }
                output += s + newLines;
            }

            // Print the value to the user
            setStringOfLabels("<html>" + output.replaceAll("\\n", "<br>") + "</html>", 5);

            // Clear their textarea input
            clearAllJTextAreas();
        } else {
            //Tell the user there's an error
            setStringOfLabels(NOT_FOUND, 5);
        }
    }

    public void reloadLabels() {
        System.out.println("Setting view to blank");
        setStringOfLabels("", 5);
    }

}