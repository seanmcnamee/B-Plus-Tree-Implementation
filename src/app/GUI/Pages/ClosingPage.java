package app.GUI.Pages;

import app.GUI.GUI;
import app.GUI.GUIPage;
import app.backend.fileaccess.FileAccess;
import app.backend.tree.Tree;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.WindowEvent;

public class ClosingPage extends GUIPage {
    private FileAccess file;

    public ClosingPage(Tree tree, FileAccess file) {
        super(tree);
        this.file = file;
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = {

                new VariableComponent(new JLabel("Have a Good Day!!", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JButton("Save & Exit"), .20, .6, .25, .15),
                new VariableComponent(new JButton("Exit"), .80, .6, .25, .15),
                new VariableComponent(new JLabel(), .5, .75, .25, .15),
                new VariableComponent(new JLabel(), .5, .8, .25, .15),
                new VariableComponent(new JLabel(), .5, .85, .25, .15),
                new VariableComponent(new JLabel(), .5, .9, .25, .15),
                new VariableComponent(new JLabel(), .5, .95, .25, .15) };

        this.setBackgroundAndTextOfComponentsInRange(components, 0, 0, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 1, 1, Color.GREEN, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 2, 2, Color.RED, Color.BLACK);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JButton) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 15));// Changes font + size of text
        ((JButton) components[2].component).setFont(new Font("Verdana", Font.PLAIN, 15));

        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {

        if (obj.equals(this.components[1].component)) { // function that performs the actions when button is clicked
            // Save the tree to the file
            file.writeFromTree(this.tree);
            main.frame.dispatchEvent(new WindowEvent(main.frame, WindowEvent.WINDOW_CLOSING));
        } else if (obj.equals(this.components[2].component)) {
            main.frame.dispatchEvent(new WindowEvent(main.frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void reloadLabels() {
        setStringOfLabels("Splits: " + tree.getSplits(), 3);
        setStringOfLabels("Parent Splits: " + tree.getParentSplits(), 4);
        setStringOfLabels("Fusions: " + tree.getFuses(), 5);
        setStringOfLabels("Parent Fusions: " + tree.getParentFuses(), 6);
        setStringOfLabels("Tree Depth: " + tree.getDepth(), 7);
    }

}
