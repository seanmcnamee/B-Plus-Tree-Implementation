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

public class AddPage extends GUIPage {
    public AddPage() {
        super();
        this.panel.setBackground(Color.BLACK);
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = 
        {
            new VariableComponent(new JLabel("Add a New Part", SwingConstants.CENTER), .5, .1, 1, .2),
            new VariableComponent(new JLabel("<HTML><U>Enter Part Number:</U></HTML>"), .13, .3, .23, .10),
            new VariableComponent(new JTextArea(), .38, .3, .23, .05),
            new VariableComponent(new JLabel("<HTML><U>Add Description:</U></HTML>"), .13, .5, .23, .10),
            new VariableComponent(new JTextArea(), .38, .5, .23, .15),
            new VariableComponent(new JButton("ADD"), .6, .3, .10, .05),
            new VariableComponent(new JButton("Back to Menu Page"), .88, .93, .23, .10) 
        };
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 1, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 6, 6, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 2, 2, Color.WHITE, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 3, 3, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 4, 4, Color.WHITE, Color.BLACK);
        this.setBackgroundAndTextOfComponentsInRange(components, 5, 5, Color.GREEN, Color.WHITE);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JLabel) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 15));
        ((JLabel) components[3].component).setFont(new Font("Verdana", Font.PLAIN, 15));
        //((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        //vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        if (obj.equals(this.components[this.components.length-1].component)) {
            System.out.println("Back to menu page");
            prepareAndSwitchToPage(App.MENU, main);
        }
    }
}