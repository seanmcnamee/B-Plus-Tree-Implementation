package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import app.App;
import app.GUI.GUI;
import app.GUI.GUIPage;

public class AddDataMenuPage extends GUIPage {
    public AddDataMenuPage() {
        super();
        this.panel.setBackground(Color.GRAY);
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = 
        {
            new VariableComponent(new JLabel("Add a New:", SwingConstants.CENTER), .5, .1, 1, .2),
            new VariableComponent(new JButton("Supplier"), .32, .4, 1/3.0, 1/4.0),
            new VariableComponent(new JButton("Project"), .68, .4, 1/3.0, 1/4.0),

            new VariableComponent(new JButton("Item"), .15, .7, 1/3.0, 1/4.0),
            new VariableComponent(new JButton("Contract"), .5, .7, 1/3.0, 1/4.0),
            new VariableComponent(new JButton("Order"), .85, .7, 1/3.0, 1/4.0),

            new VariableComponent(new JButton("Back"), .1, .95, .2, .1)
        };
        this.setBackgroundAndTextOfComponentsInRange(components, 1, 5, Color.BLUE, Color.WHITE);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
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