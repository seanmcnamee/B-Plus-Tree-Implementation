package app.GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import app.backend.tree.Tree;

/**
 * The mastermind class behind the quick creation of the project's pages.
 * A VariableComponent can hold and JComponent, and uses the gameScale to set its position
 * dynamically on the page.
 */
public abstract class GUIPage {
    protected Tree tree;
    protected JPanel panel;

    //After creation, access must be done through this array, which all GUIPages have access to.
    protected VariableComponent[] components; 

    public class VariableComponent {
        public JComponent component;
        public double x, y, width, height;

        /**
         * Note that x, y, width, and height are ALL percents (of the total GUI width or height)
         * @param component Can be ANY JComponent!
         * @param x
         * @param y
         * @param width
         * @param height
         */
        public VariableComponent(JComponent component, double x, double y, double width, double height) {
            this.component = component;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public GUIPage(Tree tree) {
        this.tree = tree;
        this.panel = new JPanel();
        panel.setLayout(null);
        this.components = createComponents();
        addComponentsToPanel();
    }

    //Notice that these methods define WHAT is on the page, and WHAT to do when they are interacted with.
    public abstract VariableComponent[] createComponents();

    public abstract void actionPerformed(Object obj, GUI main);

    public void setButtonListeners(GUI parent) {
        for (VariableComponent vB : this.components) {
            if (vB.component.getClass().equals(JButton.class)) {
                ((JButton) vB.component).addActionListener(parent);
            }
        }
    }

    /**
     * Set position and sizes of components based on percentages
     */
    public void setComponentSizeAndLocation(double pixelWidth, double pixelHeight, double xStart, double yStart) {
        // For each component
        for (VariableComponent vB : this.components) {
            double b1Width = vB.width * pixelWidth;
            double b1Height = vB.height * pixelHeight;
            double b1X = xStart + vB.x * pixelWidth - (b1Width / 2.0);
            double b1Y = yStart + vB.y * pixelHeight - (b1Height / 2.0);
            vB.component.setBounds((int) b1X, (int) b1Y, (int) b1Width, (int) b1Height);
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Used to quickly edit the background of the objects in the components array
     * WILL fail if start or end are out of bounds!
     */
    protected void setBackgroundAndTextOfComponentsInRange(VariableComponent[] components, int start, int end,
            Color backColor, Color textColor) {
        for (int i = start; i <= end; i++) {
            components[i].component.setBackground(backColor);
            components[i].component.setForeground(textColor);
        }
    }

    /**
     * Used to quickly edit the background of the objects in the components array
     * @param indices You can put as many or few indices here as you wish.
     * WILL fail if any index is out of bounds!
     */
    protected void setBackgroundAndTextOfComponentsAtIndices(VariableComponent[] components, Color backColor, Color textColor, int... indices) {
        for (int index : indices) {
            components[index].component.setBackground(backColor);
            components[index].component.setForeground(textColor);
        }
    }

    /**
     * @param indices Can include 1 or more indices (comma seperated or as an array). Will fail if those indices
     * are not JTextAreas of it those indices are out of bounds!
     * @return a String[] array containing the text within each of the JTextArea Components at the provided indices
     */
    protected String[] getStringsOfTextAreas(int... indices) {
        String[] values = new String[indices.length];
        int valueIndex = 0;
        for (int indexOfComponents : indices) {
            values[valueIndex] = ((JTextArea) this.components[indexOfComponents].component).getText();
            valueIndex++;
        }
        return values;
    }

    /**
     * Set the text of the JLabel at the provided index
     * @param text The String to set the JLabel's text to
     * @param indices Will fail if any index is out of bounds or if a JLabel is not at a provided location.
     */
    protected void setStringOfLabels(String text, int... indices) {
        for (int indexOfComponents : indices) {
            ((JLabel) this.components[indexOfComponents].component).setText(text);
        }
    }


    /**
     * Switch to the provided page (uses the array of GUIPages stored in GUI)
     */
    protected GUIPage prepareAndSwitchToPage(int page, GUI main) {
        this.panel.setVisible(false);
        clearAllJTextAreas();
        GUIPage newPage = main.switchToAndReturnPage(page);
        main.setComponentSizeAndLocation();
        return newPage;
    }

    private void addComponentsToPanel() {
        for (VariableComponent vB: this.components) {
            panel.add(vB.component);
        }
    }

    protected void clearAllJTextAreas() {
        for (VariableComponent vC : this.components) {
            if (vC.component.getClass().equals(JTextArea.class)) {
                ((JTextArea) vC.component).setText(null);
            }
        }
    }
}