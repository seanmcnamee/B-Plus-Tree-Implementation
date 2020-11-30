package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import app.App;
import app.GUI.GUI;
import app.GUI.GUIPage;
import app.backend.tree.Tree;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane; // for the save/exit part 

public class MenuPage extends GUIPage {

    // This is a test comment. Will it get deleted?
    public MenuPage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    public BufferedImage loadImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src//app//res//logo.png"));
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        return img;
    }
    @Override
    public VariableComponent[] createComponents() {
        String newLine = ("Search by Part Number");
  
        ImageIcon icon = new ImageIcon(loadImage()); 
        JLabel thumb = new JLabel();
        thumb.setIcon(icon);

        VariableComponent[] components = {
                new VariableComponent(new JLabel("Welcome to the System", SwingConstants.CENTER), .5, .1, 1, .2),
                new VariableComponent(new JButton("<html>" + newLine.replaceAll("\\n", "<br>")+ "</html>"), .13, .6, .25, .15), // displays text onto new line
                new VariableComponent(new JButton("Add a Part"), .50, .6, .25, .15),
                new VariableComponent(new JButton("Delete a Part"), .87, .6, .25, .15),
                new VariableComponent(new JButton("Exit Program ->"), .87, .93, .23, .10),
                new VariableComponent(thumb, .5, .3, .15, .2) }; //icon 


        /*VariableComponent[] menuExitButton = {
            new VariableComponent(new JButton("Exit ->"), .88, .93, .23, .10) 
        };
        */
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 3, Color.BLUE, Color.WHITE); //changes color of text and backColor
        this.setBackgroundAndTextOfComponentsInRange(components, 4, 4, Color.RED, Color.WHITE);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JButton) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 15)); //Changes font + size of text 
        ((JButton) components[2].component).setFont(new Font("Verdana", Font.PLAIN, 15));
        ((JButton) components[3].component).setFont(new Font("Verdana", Font.PLAIN, 15));
        
        // ((JLabel) components[0]).setVerticalTextPosition(AbstractButton.CENTER);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        
        if (obj.equals(this.components[1].component)) { // function that performs the actions when button is clicked
            prepareAndSwitchToPage(App.VIEW_DATA, main);
        }   else if(obj.equals(this.components[2].component)) {
            prepareAndSwitchToPage(App.ADD_DATA, main);
        }   else if(obj.equals(this.components[3].component)) {
            prepareAndSwitchToPage(App.DELETE_DATA, main);
        } else if(obj.equals(this.components[4].component)) {
            ClosingPage deletePage = (ClosingPage)(prepareAndSwitchToPage(App.CLOSING_DATA, main));
            deletePage.reloadCounts();
           
        }
    }

}