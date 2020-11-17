package app.backend.tree;

import app.backend.fileaccess.KeyValue;

public class Tree {
    private Node root;
    private final int keysPerInternalNode, keysPerLeafNode;

    public Tree(int keysPerInternalNode, int keysPerLeafNode) {
        this.keysPerInternalNode = keysPerInternalNode;
        this.keysPerLeafNode = keysPerLeafNode;
    }

    public void insert(String s) {
        insert(new KeyValue(s, ""));
    }

    public void insert(KeyValue pair) {
        //When empty, create an internalNode and leafNode
        if (root == null) {
            this.root = createInternalNode(pair);
            return;
        }

        recursiveInsert(root, pair);
    }

    private InternalNode createInternalNode(KeyValue pair) {
        InternalNode newInternal = new InternalNode(keysPerInternalNode, pair.getKey());
        LeafNode leafLeft = new LeafNode(keysPerLeafNode);
        LeafNode leafRight = new LeafNode(keysPerLeafNode, pair);
        leafLeft.setParent(newInternal);
        leafRight.setParent(newInternal);
        newInternal.setChildNode(leafLeft);
        leafLeft.setNextSibling(leafRight);
        return newInternal;
    }

    private void recursiveInsert(Node current, KeyValue pair) {
        Node childNode = current.getChildNode();
        int childNum = 0;
        while (childNum < current.getSize() && current.keyAtIndex(childNum).compareTo(pair.getKey()) <= 0) {
            childNum++;
            childNode = childNode.getNextSibling();
        }
        if (childNode.hasChildNode()) {
            recursiveInsert(childNode, pair);
        }   else {
            //At the leaf node
            if (childNode.isFull()) {
                childNode.addData(pair); //This can NOT extracted before the IF because it has to be full BEFORE knowing we have to split
                split(childNode);
            }   else {
                childNode.addData(pair);
            }
        }
    }

    private void split(Node base) {
        System.out.println("Splitting");
        //Split the data into two partitions
        int mid = base.getMid();
        Object[] lower = base.arrayPartition(0, mid-1);
        Object[] higher = base.arrayPartition(mid, base.getSize()-1);

        //Seperate the base node into two nodes
        base.replaceData(lower);
        Node newNode = new LeafNode(this.keysPerLeafNode, higher);
        newNode.setNextSibling(base.getNextSibling());
        base.setNextSibling(newNode);
        newNode.setParent(base.getParent());

        //insert key to parent node to distinguish the new from the old
        base.getParent().addData(newNode.keyAtIndex(0));

        //Must check if parent even exists
        //Must make sure if works for internal nodes


        //MOVE EM AROUND
    }

    public void delete(String key) {

    }

    public String search(String key) {
        return null;
    }



    private void fuse(Node base) {

    }

    private void connect(Node parent, Node firstchild){
        parent.setChildNode(firstchild);
        firstchild.setParent(parent);
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
        if (current.hasNextSibling()) {
            preOrder(current.getNextSibling(), tabCount);
        }
    }
}
