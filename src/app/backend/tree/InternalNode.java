package app.backend.tree;

/**
 * InternalNode
 */
public class InternalNode extends Node {
    private String[] keys;

    //Call this with 4 to specifiy 4 keys, and a max of 5 children
    public InternalNode(int keyCount) {
        this.keys = new String[keyCount];
    }
    
}