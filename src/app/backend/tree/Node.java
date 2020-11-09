package app.backend.tree;

public class Node {
    protected Node parent;
    protected Node childNode;
    protected Node nextSibling;

    public Node getParent() {
        return parent;
    }

    public Node getChildNode() {
        return childNode;
    }
    
    public Node getNextSibling() {
        return nextSibling;
    }


}
