package app.GUI.Pages;

import java.awt.Color;
import app.GUI.GUI;
import app.GUI.GUIPage;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainPage extends GUIPage {

    public MainPage() {
        super();
        this.panel.setBackground(Color.WHITE);
    }

    @Override
    public VariableComponent[] createComponents() {
        VariableComponent[] components = {
            new VariableComponent(new JLabel("Welcome to the Database", SwingConstants.CENTER), .5, .1, 1, .2),
            new VariableComponent(new JButton("Add Data"), .3, .6, 1 / 3.0, 2 / 3.0),
            new VariableComponent(new JButton("View Data"), .7, .6, 1 / 3.0, 2 / 3.0) };
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 2, Color.BLUE, Color.WHITE);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        // ((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        // TODO Auto-generated method stub

    }
    
}