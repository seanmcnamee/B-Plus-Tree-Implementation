package app;

import app.GUI.GUI;
import app.GUI.Pages.AddDataMenuPage;
import app.GUI.Pages.MenuPage;
import app.GUI.Pages.ViewDataMenuPage;
import app.backend.fileaccess.FileAccess;
import app.backend.fileaccess.KeyValue;
import app.backend.tree.LeafNode;
import app.backend.tree.Tree;

public class App {
    public static int MENU = 0, ADD_DATA = 1, VIEW_DATA = 2;
    public static void main(String[] args) throws Exception {
        testTree();
    }

    private static void startGUI() {
        GUI g = new GUI(new MenuPage(), new AddDataMenuPage(), new ViewDataMenuPage());
        System.out.println("Hello Java");
        g.start();
    }

    private static void testFileAccess() {
        FileAccess file = new FileAccess("src//app//res//partfile.txt");
        KeyValue pair = file.getNextPair();
        
        System.out.println(pair.getKey() + ", " + pair.getValue());
    }

    private static void testLeafNode() {
        LeafNode node = new LeafNode(5);
        System.out.println(node.toString());
        node.addData(new KeyValue("a", "fdaf"));
        System.out.println(node.toString());
        node.addData(new KeyValue("d", "fdaf"));
        System.out.println(node.toString());
        node.addData(new KeyValue("c", "fdaf"));
        System.out.println(node.toString());
        node.addData(new KeyValue("b", "fdaf"));
        System.out.println(node.toString());
        node.addData(new KeyValue("ba", "fdaf"));
        System.out.println(node.toString());
    }
//
    private static void testTree() {
        Tree tree = new Tree(5, 3);
        tree.insert("d");
        tree.printPreOrder();
        tree.insert("c");
        tree.printPreOrder();
        tree.insert("e");
        tree.printPreOrder();
        tree.insert("a");
        tree.printPreOrder();
    }
}