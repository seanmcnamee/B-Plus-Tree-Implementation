package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import app.App;
import app.GUI.GUI;
import app.GUI.GUIPage;

public class MenuPage extends GUIPage {

    public MenuPage() {
        super();
        this.panel.setBackground(Color.BLACK);
    }

    @Override
    public VariableComponent[] createComponents() {
        String newLine = ("Display the Next \n 10 Parts");
        VariableComponent[] components = {
                new VariableComponent(new JLabel("Welcome to the System", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JButton("Search by Part Number"), .13, .8, .23, .10), 
                new VariableComponent(new JButton("Add a Part"), .38, .8, .23, .10),
                new VariableComponent(new JButton("Delete a Part"), .63, .8, .23, .10),
                new VariableComponent(new JButton("<html>" + newLine.replaceAll("\\n", "<br>") + "</html>"), .88, .8, .23, .10), // if you could make it so the text wraps inside the button, that would be great. -Louie
                new VariableComponent(new JButton("Exit ->"), .88, .93, .23, .10) };


        /*VariableComponent[] menuExitButton = {
            new VariableComponent(new JButton("Exit ->"), .88, .93, .23, .10) 
        };
        */
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 4, Color.BLUE, Color.WHITE);
        this.setBackgroundAndTextOfComponentsInRange(components, 5, 5, Color.RED, Color.WHITE);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        
        // ((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        if (obj.equals(this.components[1].component)) {
            prepareAndSwitchToPage(App.ADD_DATA, main);
        }   else if(obj.equals(this.components[2].component)) {
            prepareAndSwitchToPage(App.VIEW_DATA, main);
        }
    }

}