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

    public DeletePage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    @Override
    public VariableComponent[] createComponents() {
        String newLine = ("Display the Next \n 10 Parts");
        VariableComponent[] components = {
                new VariableComponent(new JLabel("Deleting Part", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JLabel("<HTML><U>Enter Part Number:</U></HTML>"), .13, .3, .23, .10), // Underlines
                                                                                                                // the
                                                                                                                // text
                new VariableComponent(new JTextArea(), .38, .3, .23, .05),
                new VariableComponent(new JButton("Delete"), .6, .3, .10, .05),
                new VariableComponent(new JButton("Back to Menu Page"), .88, .93, .23, .10) };

        /*
         * VariableComponent[] menuExitButton = { new VariableComponent(new
         * JButton("Exit ->"), .88, .93, .23, .10) };
         */
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 1, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 4, 4, Color.BLUE, Color.WHITE);
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
        /*
         * if (obj.equals(this.components[1].component)) {
         * prepareAndSwitchToPage(App.ADD_DATA, main); } // else
         * if(obj.equals(this.components[2].component)) {
         * prepareAndSwitchToPage(App.VIEW_DATA, main);
         */
        if (obj.equals(this.components[this.components.length - 1].component)) {// function that performs the actions
                                                                                // when button is clicked
            System.out.println("Back to menu page");
            prepareAndSwitchToPage(App.MENU, main);
        } else if (obj.equals(this.components[3].component)) {

            // TODO what if it doesn't match??

            // Get the key from the textarea
            String part = ((JTextArea) this.components[2].component).getText();

            // Delete the part
            tree.delete(part);

            // Clear the textarea to show completion
            ((JTextArea) this.components[2].component).setText("");
        }

    }
}
