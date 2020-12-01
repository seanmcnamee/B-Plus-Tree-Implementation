package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import app.App;
import app.GUI.GUI;
import app.GUI.GUIPage;
import app.backend.tree.Tree;

public class DeletePage extends GUIPage {
    private String NOT_FOUND = "That part can't be found!";

    public DeletePage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = {
                new VariableComponent(new JLabel("Deleting Part", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JLabel("<HTML><U>Enter Part Number:</U></HTML>"), .13, .3, .23, .10), // Underlines text
                new VariableComponent(new JTextArea(), .38, .3, .23, .05),
                new VariableComponent(new JButton("Delete"), .6, .3, .10, .05),
                new VariableComponent(new JLabel(), .38, .5, .23, .05),
                new VariableComponent(new JButton("Back to Menu Page"), .88, .93, .23, .10) };

        /*
         * VariableComponent[] menuExitButton = { new VariableComponent(new
         * JButton("Exit ->"), .88, .93, .23, .10) };
         */
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 1, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 5, 5, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 2, 2, Color.WHITE, Color.BLACK);// changes color of
                                                                                                 // text and backColor
        this.setBackgroundAndTextOfComponentsInRange(components, 3, 3, Color.RED, Color.BLACK);
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

            // Get the key from the textarea
            //Since there is only 1 element, we grab it from the returned array instantly.
            String part = getStringsOfTextAreas(2)[0];

            // Delete the part
            boolean success = tree.delete(part);

            if (success) {
                // Clear the textarea to show completion
                clearAllJTextAreas();
            } else {
                setStringOfLabels(NOT_FOUND, 4);
            }
            
        }

    }

    public void reloadLabels() {
        setStringOfLabels("", 4);
    }
}
