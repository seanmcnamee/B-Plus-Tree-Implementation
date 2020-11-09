package app.backend.tree;

public class LeafNode extends Node {
    private String[] value;

    public LeafNode(int numOfValues) {
        this.value = new String[numOfValues];
    }
}
