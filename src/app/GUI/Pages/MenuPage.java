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

public class MenuPage extends GUIPage {

    // This is a test comment. Will it get deleted?
    public MenuPage(Tree tree) {
        super(tree);
        this.panel.setBackground(Color.BLACK);// sets the color of the backgroud in the GUI
    }

    public BufferedImage loadImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("res//logo.png")); //src//app//
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
            ViewDataMenuPage viewPage = (ViewDataMenuPage) prepareAndSwitchToPage(App.VIEW_DATA, main);
            viewPage.reloadLabels();

        }   else if(obj.equals(this.components[2].component)) {
            //To the Adding Page. (Resets Labels)
            AddPage addPage = (AddPage)(prepareAndSwitchToPage(App.ADD_DATA, main));
            addPage.reloadLabels();
        }   else if(obj.equals(this.components[3].component)) {
            //To the Deleting Page. (Resets Labels)
            DeletePage deletePage = (DeletePage)(prepareAndSwitchToPage(App.DELETE_DATA, main));
            deletePage.reloadLabels();
        } else if(obj.equals(this.components[4].component)) {
            //To the Closing Page. (Resets Labels so that counts are the newest)
            ClosingPage closingPage = (ClosingPage)(prepareAndSwitchToPage(App.CLOSING_DATA, main));
            closingPage.reloadLabels();
           
        }
    }

}