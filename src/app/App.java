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
        //testLeafNode();
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
        KeyValue d1 = new KeyValue("a", "fdaf");
        KeyValue d2 =new KeyValue("d", "fdaf");
        KeyValue d3 =new KeyValue("c", "fdaf");
        KeyValue d4 =new KeyValue("b", "fdaf");
        KeyValue d5 =new KeyValue("ba", "fdaf");
        KeyValue[] arr = {d1, d2, d3, d4, d5};

        System.out.println("Inserting...");
        for (KeyValue k : arr) {
            System.out.println("\t" + k.getKey());
            node.addData(k);
            System.out.println(node.toString());
            
        }
        
        System.out.println("Deleting...");
        for (KeyValue k : arr) {
            System.out.println("\t" + k.getKey());
            System.out.println(node.removeData(k.getKey()));
            System.out.println(node.toString());
            
        }
    }

    private static void testTree() {
        Tree tree = new Tree(3, 2);
        tree.insert("d");
        tree.printPreOrder();
        tree.insert("c");
        tree.printPreOrder();
        tree.insert("e");
        tree.printPreOrder();
        tree.insert("a");
        tree.printPreOrder();
        tree.insert("ba");
        tree.printPreOrder();
        tree.insert("bd");
        tree.printPreOrder();
        tree.insert("ad");
        tree.printPreOrder();
        tree.insert("af");
        tree.printPreOrder();

        tree.delete("bd");
        tree.printPreOrder();
    }
}