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
import app.backend.fileaccess.KeyValue;
import app.backend.tree.LeafNode;
import app.backend.tree.Node;

public class ViewDataMenuPage extends GUIPage {

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

            // TODO what if it doesn't match????
            KeyValue pair = getFirstPair();
            // Print the value to the user
            ((JLabel) this.components[5].component).setText(pair.toString());

            // Clear their textarea input
            ((JTextArea) this.components[2].component).setText("");

        } else if (obj.equals(this.components[4].component)) {
            String part = ((JTextArea) this.components[2].component).getText();
            // Find it in the tree
            LeafNode node = (LeafNode) tree.search(part);
            // Find it in the node
            int indexOfKey = node.getIndexOf(part);
            KeyValue pair = (KeyValue) (node.ObjectAtIndex(indexOfKey));

            String output = "";
            int numDisplayed = 0;
            output += ((KeyValue) (node.ObjectAtIndex(indexOfKey + numDisplayed))).toString() + "\n\n";
            // Keep a count of how many pairs you display! (only need 10)
            // Loop through the rest of your node.
            // If you need to display more, go to the next node
            // Repeat until you've reached 10 (OR there is no next node)
            do {
                numDisplayed++;
                if (indexOfKey + numDisplayed >= node.getSize()) {
                    node = (LeafNode) node.getNextSibling();
                    indexOfKey = -numDisplayed;
                }
                output += ((KeyValue) (node.ObjectAtIndex(indexOfKey + numDisplayed))).toString() + "\n";
            } while (numDisplayed < 10);

            // Print the value to the user
            ((JLabel) this.components[5].component).setText("<html>" + output.replaceAll("\\n", "<br>") + "</html>");

            // Clear their textarea input
            ((JTextArea) this.components[2].component).setText("");

            /*
             * for (int i = 0; i < 10; i++) { tree.printPreOrder(); }
             */
        }
    }

    private KeyValue getFirstPair() {
        // Get the key
        String part = ((JTextArea) this.components[2].component).getText();
        // Find it in the tree
        LeafNode node = (LeafNode) tree.search(part);
        // Find it in the node
        int indexOfKey = node.getIndexOf(part);
        KeyValue pair = (KeyValue) (node.ObjectAtIndex(indexOfKey));
        return pair;
    }
}