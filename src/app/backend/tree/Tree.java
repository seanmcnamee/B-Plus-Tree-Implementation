package app.backend.tree;

import java.util.ArrayList;

import app.backend.fileaccess.KeyValue;

/**
 * The B+Tree utilizes search, insert, and delete. 
 * Can also get the counts for all the splits and get the nextX values when searching.
 */
public class Tree {
    public Node root;
    private final int keysPerInternalNode, keysPerLeafNode;
    private int totalSplits, totalFuses, totalParentSplits, totalParentFuses;

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
     * @param current For recursive finding. The current node whose keys we're
     *                evaluating
     * @param pair    the KeyValue pair we're searching for
     * @return The node that should contain pair
     */
    private Node search(Node current, String key) {
        if (!current.hasChildNode()) {
            return current; // If at a leaf, then this is the key
        } else {
            Node childNode = current.getChildNode();
            int childNum = 0;
            // Which child should I follow to get to this key?
            while (childNum < current.getSize() && current.keyAtIndex(childNum).compareTo(key) <= 0) {
                childNum++;
                childNode = childNode.getNextSibling();
            }
            // Search based on that child
            return search(childNode, key);
        }
    }

    /**
     * @param key the string we're searching for
     * @param count the number of values to include after the key
     * @return The node that should contain that string as a key
     */
    public ArrayList<String> searchAndNextX(String key, int count) {
        ArrayList<String> nextX = new ArrayList<String>();
        // Find it in the tree
        LeafNode node = (LeafNode) search(key);
        // Find it in the node
        int indexOfKey = node.getIndexOf(key);

        if (indexOfKey >= 0) {
            int numDisplayed = 0;
            // Keep a count of how many pairs you display! (only need 10)
            // Loop through the rest of your node.
            // If you need to display more, go to the next node
            // Repeat until you've reached count (OR there is no next nodnumDisplayed++;e)
            while (numDisplayed < count+1){ //Include the searching one, but make sure there's the next 'count'
                nextX.add(((KeyValue) (node.ObjectAtIndex(indexOfKey + numDisplayed))).toString());
                numDisplayed++;
                if (indexOfKey + numDisplayed >= node.getSize()) {
                    node = (LeafNode) node.getNextSibling();
                    indexOfKey = -numDisplayed;
                }
            }
        }
        return nextX;
    }

    /**
     * Will add the pair to the array. Note that to prevent duplication,
     * a check must be done before calling this
     * @param pair The KeyValue to add into the tree
     */
    public void insert(KeyValue pair) {
        Node nodeToAddTo = search(pair.getKey());
        if (nodeToAddTo == null) { // The only case that this happens is if there is no root
            this.root = new LeafNode(keysPerLeafNode, pair);// createStartingRootNode(pair);
        } else {
            // Otherwise, Add the data to the node that was found
            addDataOrSplit(nodeToAddTo, pair, false);
        }
    }

    /**
     * Add the data to the given node, and split if needed.
     * 
     * @param node the node to add the data to
     * @param data the KeyValue pair or String to be added
     * @param isParentSplit whether totalParentSplits should be incremented
     */
    private void addDataOrSplit(Node node, Object data, boolean isParentSplit) {
        node.addData(data);
        if (node.isOverFilled()) {
            split(node, isParentSplit);
        }
    }

    /**
     * The base node gets split into itself, and a new node to its right
     */
    private void split(Node base, boolean isParentSplit) {
        if (isParentSplit) {
            this.totalParentSplits++;
        }
        this.totalSplits++;

        // First off, we need to know the key that will be pushed up to the higher layer
        int mid = base.getMid();
        String midElement = base.keyAtIndex(mid);

        // The overflowing Node has to be split in half.
        Object[] lower = base.arrayPartition(0, mid - 1);
        Object[] higher; // For a leaf, the mid is included. For an internal, it is not

        // We need to create a new node to hold the higher half of data that was
        // overflowing.
        Node newNode;
        if (base.hasChildNode()) {
            // For an internal node, including the mid would be repetitive.
            higher = base.arrayPartition(mid + 1, base.getSize() - 1);
            newNode = new InternalNode(this.keysPerInternalNode, higher);
            restructureChildPointers(base, newNode, mid);
        } else {
            // For a leaf node, we can't lose any data, so we include mid
            higher = base.arrayPartition(mid, base.getSize() - 1);
            newNode = new LeafNode(this.keysPerLeafNode, higher);
        }

        // Now, lets update the base to only include the lower set of keys
        // and set the connections
        base.replaceData(lower);

        // If you don't have a parent, you are the root and a new root must be created
        Node parentNode = base.getParent();
        if (parentNode == null) {
            // Create new root internal node
            parentNode = addAndReturnNewRootNode(base);
        }

        base.insertNextSibling(newNode);
        // insert key to parent node
        addDataOrSplit(parentNode, midElement, true);
    }

    /**
     * Sets all the children's parent pointers in the larger half of base to point
     * to the newNode. It as well sets newnode's child pointer to be the first child
     * whose pointer is changed.
     * 
     * @param oldEntireParent
     * @param newJustHigherParent
     * @param mid
     */
    private void restructureChildPointers(Node oldEntireParent, Node newJustHigherParent, int mid) {
        // All of the connections are still pointing to the base node (lower half).
        // Let's go through and make it so the higher half is pointer to this new node.
        Node childNode = oldEntireParent.getChildNode();
        for (int i = 0; i <= oldEntireParent.getSize(); i++) { // Loop through all the children
            if (i > mid) {
                if (i == mid + 1) { // The first one in the higher half is the child
                    newJustHigherParent.setChildNode(childNode);
                }
                childNode.setParent(newJustHigherParent); // All the children in the higher half have the new node as a
                                                          // parent
            }
            childNode = childNode.getNextSibling();
        }
    }

    // Creates a new root node to be the parent of the given base node.
    private Node addAndReturnNewRootNode(Node base) {
        Node parentNode = new InternalNode(keysPerInternalNode);
        parentNode.setChildNode(base);
        base.setParent(parentNode);
        this.root = parentNode;
        return parentNode;
    }

    /**
     * Will delete the KeyValue pair in the LeafNode with the provided key
     * @param key allows for searching the KeyValue to delete.
     * @return whether this method was successful. True it successful. False otherwise.
     */
    public boolean delete(String key) {
        Node nodeToAddTo = search(key);
        if (nodeToAddTo == null || nodeToAddTo.getIndexOf(key) == -1) { // The only case that this happens is if there is no root
            return false;
        } else {
            // Otherwise, remove the data to the node that was found
            removeDataOrFuse(nodeToAddTo, key, false);
            return true;
        }
    }

    
    /**
     * Remove the data to the given node, and fuse if needed.
     * 
     * @param node the node to remove the data from
     * @param key allows for searching the KeyValue to delete.
     * @param isParentFuse whether totalParentFuses should be incremented
     */
    private void removeDataOrFuse(Node node, String key, boolean isParentFuse) {
        System.out.println("Removing " + key + " from " + node);
        node.removeData(key);
        if (node.isUnderFilled()) {
            fuse(node, isParentFuse);
        }
    }

    /**
     * The base node gets added to the node on its left.
     */
    private void fuse(Node base, boolean isParentFuse) {
        if (isParentFuse) {
            this.totalParentFuses++;
        }
        this.totalFuses++;

        Node parent = base.getParent();
        if (parent == null) {
            // This is the root node. It has no sibling
            // If its the last element in it, have its first child take its place.
            if (base.getSize() == 0) {
                if (base.hasChildNode()) {
                    base.getChildNode().setParent(null);
                }
                this.root = base.getChildNode();
            }
        } else {
            // This is NOT the root node.

            // Get the previous sibling
            Node leftSibling = getPreviousSibling(base);
            if (leftSibling == null) {
                // If there is no left sibling, fuse your right sibling to you.
                fuse(base.getNextSibling(), isParentFuse);
            } else {

                // Remove that key that the parent has for us
                String possibleParentKey = getKeyForNode(base);
                removeDataOrFuse(parent, possibleParentKey, true); // Fuse if needed.

                /// Load all your values into the previous sibling
                if (!base.hasChildNode()) {
                    // For Leaves, we don't
                    leftSibling.appendData(null, base.arrayPartition(0, base.getSize() - 1));
                } else {
                    // For internal nodes, we need to pull down the parent key.
                    leftSibling.appendData(possibleParentKey, base.arrayPartition(0, base.getSize() - 1));
                }

                // Make your sibling forget about you (point to your next sibling)
                leftSibling.setNextSibling(base.getNextSibling());

                // Split if needed.
                if (leftSibling.isOverFilled()) {
                    split(leftSibling, false);
                }
            }

        }

    }

    /**
     * Loop using the parent to get the previous sibling.
     * @param base the current sibling
     * @return the sibling to base's left. Null if it has no smaller sibling.
     * Note that this will return null even if there is a smaller cousin.
     */
    private Node getPreviousSibling(Node base) {
        Node parent = base.getParent();
        Node currentChild = parent.getChildNode();
        while (currentChild != null && currentChild.getNextSibling() != base) {
            currentChild = currentChild.getNextSibling();
        }
        return currentChild;
    }

    /**
     * Get the key that the parent holds that helps searching get to this node.
     * Note that calling this for the root will return null
     * @param base the current nodes
     * @return the key
     */
    private String getKeyForNode(Node base) {
        Node parent = base.getParent();
        Node currentChild = parent.getChildNode();
        int i = 0;
        while (currentChild != null && currentChild.getNextSibling() != base) {
            currentChild = currentChild.getNextSibling();
            i++;
        }
        if (currentChild == null) {
            return null;
        } else {
            return parent.keyAtIndex(i);
        }
    }

    public int getSplits() {
        return this.totalSplits;
    }

    public int getFuses() {
        return this.totalFuses;
    }

    public int getParentSplits() {
        return this.totalParentSplits;
    }

    public int getParentFuses() {
        return this.totalParentFuses;
    }

    public Node getFirst() {
        return getFirst(this.root);
    }

    /**
     * Continuously follows the firstChild until it reaches a leaf.
     * @param current used for recursion. Should be initially called with the root.
     * @return The smallest (leftmost) LeafNode in the tree.
     */
    private Node getFirst(Node current) {
        if (current.hasChildNode()) {
            return getFirst(current.getChildNode());
        }
        return current;
    }

    public void printPreOrder() {
        System.out.println();
        if (root != null) {
            preOrder(root, 0);
        } else {
            System.out.println("Tree is empty");
        }

    }

    private void preOrder(Node current, int tabCount) {
        String tabs = "";
        for (int i = 0; i < tabCount; i++) {
            tabs += "\t";
        }
        System.out.println(tabs + current.toString());
        if (current.hasChildNode()) {
            preOrder(current.getChildNode(), tabCount + 1);
        }
        if (current.hasNextSibling() && current.getParent().equals(current.getNextSibling().getParent())) {
            preOrder(current.getNextSibling(), tabCount);
        }
    }
}
