package app;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;


import app.GUI.GUI;
import app.GUI.Pages.AddDataMenuPage;
import app.GUI.Pages.MenuPage;
import app.GUI.Pages.ViewDataMenuPage;
import app.backend.FileAccess;
import app.backend.KeyValue;

public class App {
    public static int MENU = 0, ADD_DATA = 1, VIEW_DATA = 2;
    public static void main(String[] args) throws Exception {
        //GUI g = new GUI(new MenuPage(), new AddDataMenuPage(), new ViewDataMenuPage());
        //System.out.println("Hello Java");
        //g.start();

        FileAccess file = new FileAccess("src//app//res//partfile.txt");
        KeyValue pair = file.getNextPair();
        
        System.out.println(pair.getKey() + ", " + pair.getValue());
    }
}