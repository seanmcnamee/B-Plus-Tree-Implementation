package app;

import app.GUI.GUI;
import app.GUI.Pages.AddPage;
import app.GUI.Pages.ClosingPage;
import app.GUI.Pages.DeletePage;
import app.GUI.Pages.MenuPage;
import app.GUI.Pages.ViewDataMenuPage;
import app.backend.fileaccess.FileAccess;
import app.backend.fileaccess.KeyValue;
import app.backend.tree.LeafNode;
import app.backend.tree.Tree;

public class App {

    //Used for switching between pages.
    public static int MENU = 0, ADD_DATA = 1, VIEW_DATA = 2, DELETE_DATA = 3, CLOSING_DATA = 4;

    public static void main(String[] args) throws Exception {
        //Create the tree. Holds max of 4 keys internally, and max of 16 KeyValues at the leaves.
        Tree tree = new Tree(4, 16);

        //Fill the tree from the file provided.
        FileAccess file = new FileAccess("src//app//res//partfile.txt");
        file.fillTreeFromFile(tree);

        //Create the GUI with the following pages
        GUI g = new GUI(new MenuPage(tree), new AddPage(tree), new ViewDataMenuPage(tree), new DeletePage(tree),
                new ClosingPage(tree, file));
        System.out.println("Hello Java");

        //Start the GUI
        g.start();
    }


    //All of the following classes were for our unit testing. Success was based on lack of errors/console output.
    private static void testFileAccess() {
        FileAccess file = new FileAccess("src//app//res//partfile.txt");
        Tree tree = new Tree(4, 16);

        while (file.hasNext()) { // Using .hasNext() closes the reader when you can't continue anymore
            KeyValue pair = file.getNextPair();
            tree.insert(pair);
            // System.out.println(pair.getKey() + ", " + pair.getValue());
        }

        // tree.printPreOrder();

        file.writeFromTree(tree);

        System.out.println("Done.");
    }

    private static void testLeafNode() {
        LeafNode node = new LeafNode(5);
        KeyValue d1 = new KeyValue("a", "fdaf");
        KeyValue d2 = new KeyValue("d", "fdaf");
        KeyValue d3 = new KeyValue("c", "fdaf");
        KeyValue d4 = new KeyValue("b", "fdaf");
        KeyValue d5 = new KeyValue("ba", "fdaf");
        KeyValue[] arr = { d1, d2, d3, d4, d5 };

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
        Tree tree = new Tree(3, 3);

        KeyValue d1 = new KeyValue("bd", "fdaf");
        KeyValue d2 = new KeyValue("c", "fdaf");
        KeyValue d3 = new KeyValue("e", "fdaf");
        KeyValue d4 = new KeyValue("a", "fdaf");
        KeyValue d5 = new KeyValue("ba", "fdaf");
        KeyValue d6 = new KeyValue("d", "fdaf");
        KeyValue d7 = new KeyValue("ad", "fdaf");
        KeyValue d8 = new KeyValue("af", "fdaf");
        KeyValue[] arr = { d1, d2, d3, d4, d5, d6, d7, d8 };

        tree.printPreOrder();
        System.out.println("Inserting...");
        for (KeyValue k : arr) {
            System.out.println("\tInsert " + k.getKey());
            tree.insert(k);
            tree.printPreOrder();

        }

        System.out.println("Deleting...");
        for (KeyValue k : arr) {
            System.out.println("\tDelete " + k.getKey());
            tree.delete(k.getKey());
            tree.printPreOrder();

        }

    }

    private static void testAppend() {
        KeyValue d1 = new KeyValue("d", "fdaf");
        KeyValue d2 = new KeyValue("c", "fdaf");
        KeyValue d3 = new KeyValue("e", "fdaf");
        KeyValue d4 = new KeyValue("a", "fdaf");
        KeyValue d5 = new KeyValue("ba", "fdaf");
        LeafNode node = new LeafNode(5);
        node.addData(d1);
        node.addData(d2);
        node.addData(d3);
        System.out.println(node.toString());

        Object[] arr = { d4, d5 };
        node.appendData(null, arr);
        System.out.println(node.toString());
    }
}