package app.backend.tree;

import app.backend.fileaccess.KeyValue;

public class Tree {
    private Node root;
    private final int keysPerInternalNode, keysPerLeafNode;
    private int totalSplits, totalFuses;

    public Tree(int keysPerInternalNode, int keysPerLeafNode) {
        this.keysPerInternalNode = keysPerInternalNode;
        this.keysPerLeafNode = keysPerLeafNode;
        this.totalSplits = this.totalFuses = 0;
    }

    /**
     * @param key the string we're searching for
     * @return The node that should contain that string as a key
     */
    public Node search(String key) {
        if (root == null) {
            return null;
        } else {
            return search(root, key);
        }
    }

    /**
     * @param current For recursive finding. The current node whose keys we're evaluating
     * @param pair the KeyValue pair we're searching for
     * @return The node that should contain pair
     */
    private Node search(Node current, String key) {
        Node childNode = current.getChildNode();
        int childNum = 0;
        //Which child should I follow to get to this key?
        while (childNum < current.getSize() && current.keyAtIndex(childNum).compareTo(key) <= 0) {
            childNum++;
            childNode = childNode.getNextSibling();
        }
        //Am I at the leaf or not?
        if (childNode.hasChildNode()) {
            return search(childNode, key);
        }   else {
            //At the leaf node
            return childNode;
        }
    }

    //TODO remove this because we shouldn't default add empty data with a key.
    //This is for testing only
    public void insert(String s) {
        System.out.println("Adding " + s);
        insert(new KeyValue(s, ""));
    }

    public void insert(KeyValue pair) {
        Node nodeToAddTo = search(pair.getKey());
        if (nodeToAddTo == null) { //The only case that this happens is if there is no root
            this.root = createStartingRootNode(pair);
        } else {
            //Otherwise, Add the data to the node that was found
            addDataOrSplit(nodeToAddTo, pair);
        }
    }

    /**
     * Adds a root node and 2 leafnodes and set the connections
     * @param pair What to add to the right leaf node
     * @return the internalNode that should be the new root of the tree.
     */
    private InternalNode createStartingRootNode(KeyValue pair) {
        InternalNode newInternal = new InternalNode(keysPerInternalNode, pair.getKey());
        LeafNode leafLeft = new LeafNode(keysPerLeafNode);
        LeafNode leafRight = new LeafNode(keysPerLeafNode, pair);
        //Set connections between parent and children
        leafLeft.setParent(newInternal);
        leafRight.setParent(newInternal);
        newInternal.setChildNode(leafLeft);
        leafLeft.setNextSibling(leafRight);
        return newInternal;
    }

    /**
     * Add the data to the given node, and split if needed.
     * @param node the node to add the data to
     * @param data the KeyValue pair or String to be added
     */
    private void addDataOrSplit(Node node, Object data) {
        if (node.isFull()) {
            node.addData(data);
            split(node);
        } else {
            node.addData(data);
        }
    }
    
    private void split(Node base) {
        this.totalSplits++;
        
        //First off, we need to know the key that will be pushed up to the higher layer
        int mid = base.getMid();
        String midElement = base.keyAtIndex(mid);
        
        //The overflowing Node has to be split in half.
        Object[] lower = base.arrayPartition(0, mid-1);
        Object[] higher; //For a leaf, the mid is included. For an internal, it is not
        

        //We need to create a new node to hold the higher half of data that was overflowing.
        Node newNode;
        if (base.hasChildNode()) {
            //For an internal node, including the mid would be repetitive.
            higher = base.arrayPartition(mid+1, base.getSize()-1);
            newNode = new InternalNode(this.keysPerInternalNode, higher);
            restructureChildPointers(base, newNode, mid);
        } else {
            //For a leaf node, we can't lose any data, so we include mid
            higher = base.arrayPartition(mid, base.getSize()-1);
            newNode = new LeafNode(this.keysPerLeafNode, higher);
        }

        //Now, lets update the base to only include the lower set of keys
        //and set the connections
        base.replaceData(lower);
        
        //If you don't have a parent, you are the root and a new root must be created
        Node parentNode = base.getParent();
        if (parentNode == null) { 
            //Create new root internal node
            parentNode = addAndReturnNewRootNode(base);
        }

        base.insertNextSibling(newNode);
        //insert key to parent node
        addDataOrSplit(parentNode, midElement);
    }

    /**
     * Sets all the children's parent pointers in the larger half of base to point to the newNode.
     * It as well sets newnode's child pointer to be the first child whose pointer is changed.
     * @param oldEntirePArent
     * @param newJustHigherParent
     * @param mid
     */
    private void restructureChildPointers(Node oldEntirePArent, Node newJustHigherParent, int mid) {
        //All of the connections are still pointing to the base node (lower half).
        //Let's go through and make it so the higher half is pointer to this new node.
        Node childNode = oldEntirePArent.getChildNode();
        for (int i = 0; i <= oldEntirePArent.getSize(); i++) { //Loop through all the children
            if (i > mid) {
                if (i == mid+1) { //The first one in the higher half is the child
                    newJustHigherParent.setChildNode(childNode);
                }
                childNode.setParent(newJustHigherParent); //All the children in the higher half have the new node as a parent
            }
            childNode = childNode.getNextSibling();
        }
    }

    //Creates a new root node to be the parent of the given base node.
    private Node addAndReturnNewRootNode(Node base) {
        Node parentNode = new InternalNode(keysPerInternalNode);
        parentNode.setChildNode(base);
        base.setParent(parentNode);
        this.root = parentNode;
        return parentNode;
    }

    public void delete(String key) {
        /*
        Node nodeToAddTo = search(pair.getKey());
        if (nodeToAddTo == null) { //The only case that this happens is if there is no root
            this.root = createStartingRootNode(pair);
        } else {
            //Otherwise, Add the data to the node that was found
            addDataOrSplit(nodeToAddTo, pair);
        }
        */
    }

    private void fuse(Node base) {

    }

    public int getSplits() {
        return this.totalSplits;
    }

    public int getFuses() {
        return this.totalFuses;
    }

    public void printPreOrder() {
        System.out.println();
        preOrder(root, 0);
    }

    private void preOrder(Node current, int tabCount) {
        String tabs = "";
        for (int i = 0; i < tabCount; i++) {
            tabs += "\t";
        }
        System.out.println(tabs + current.toString());
        if (current.hasChildNode()) {
            preOrder(current.getChildNode(), tabCount+1);
        }
        if (current.hasNextSibling() && current.getParent().equals(current.getNextSibling().getParent())) {
            preOrder(current.getNextSibling(), tabCount);
        }
    }
}
