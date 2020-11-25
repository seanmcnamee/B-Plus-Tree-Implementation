package app;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

import app.GUI.GUI;
import app.GUI.Pages.AddPage;
import app.GUI.Pages.ClosingPage;
import app.GUI.Pages.DeletePage;
import app.GUI.Pages.MenuPage;
import app.GUI.Pages.ViewDataMenuPage;
import app.backend.fileaccess.FileAccess;
import app.backend.fileaccess.KeyValue;

public class App {

    public static int MENU = 0, ADD_DATA = 1, VIEW_DATA = 2, DELETE_DATA = 3, CLOSING_DATA = 4;
    
    public static void main(String[] args) throws Exception {
        GUI g = new GUI(new MenuPage(), new AddPage(), new ViewDataMenuPage(), new DeletePage(), new ClosingPage());
        System.out.println("Hello Java");
        g.start();

        //FileAccess file = new FileAccess("src//app//res//partfile.txt");
        //KeyValue pair = file.getNextPair();
        
        //System.out.println(pair.getKey() + ", " + pair.getValue());
    }
}