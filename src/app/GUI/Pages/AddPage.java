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
import app.backend.fileaccess.KeyValue;
import app.backend.tree.LeafNode;
import app.backend.tree.Tree;

public class AddPage extends GUIPage {
    private String ALREADY_EXISTS = "That part already exists!";

    public AddPage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = {
                new VariableComponent(new JLabel("Adding Part", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JLabel("<HTML><U>Enter Part Number:</U></HTML>"), .13, .3, .23, .10), // Underlines text
                new VariableComponent(new JTextArea(), .38, .3, .23, .05),
                new VariableComponent(new JLabel("<HTML><U>Add Description:</U></HTML>"), .13, .5, .23, .10),
                new VariableComponent(new JTextArea(), .38, .5, .23, .15),
                new VariableComponent(new JButton("Add"), .6, .3, .10, .05),
                new VariableComponent(new JLabel(), .7, .5, .3, .05),
                new VariableComponent(new JButton("Back to Menu Page"), .88, .93, .23, .10) };
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 1, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 7, 7, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 2, 2, Color.WHITE, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 3, 3, Color.BLUE, Color.WHITE);// changes color of text
                                                                                                // and backColor
        this.setBackgroundAndTextOfComponentsInRange(components, 4, 4, Color.WHITE, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 5, 5, Color.GREEN, Color.BLACK);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JLabel) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 15));// Changes font + size of text
        ((JLabel) components[3].component).setFont(new Font("Verdana", Font.PLAIN, 15));
        // ((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        if (obj.equals(this.components[this.components.length - 1].component)) { // function that performs the actions
                                                                                 // when button is clicked
            System.out.println("Back to menu page");
            prepareAndSwitchToPage(App.MENU, main);
        } else if (obj.equals(this.components[5].component)) {
            // Grab part number and description
            String part = ((JTextArea) this.components[2].component).getText();
            String desc = ((JTextArea) this.components[4].component).getText();

            LeafNode node = (LeafNode) tree.search(part);
            // Find it in the node
            int indexOfKey = node.getIndexOf(part);
            if (indexOfKey < 0) {
                //Doesn't exist, so add it
                KeyValue pair = new KeyValue(part, desc);

                // Save in tree
                tree.insert(pair);
    
                // Empty textareas to show that it was accepted
                ((JTextArea) this.components[2].component).setText("");
                ((JTextArea) this.components[4].component).setText("");
            } else {
                ((JLabel) this.components[6].component).setText(ALREADY_EXISTS);
            }
        }
    }

    public void reloadLabels() {
        ((JLabel) this.components[6].component).setText("");
    }
}