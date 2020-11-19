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
            addDataOrSplit(childNode, pair);
        }
    }

    private void split(Node base) {
        System.out.println("Splitting");
        //Split the data into two partitions
        int mid = base.getMid();
        String midElement = base.keyAtIndex(mid);
        Object[] lower = base.arrayPartition(0, mid-1);

        Object[] higher; //= base.arrayPartition(mid, base.getSize()-1);

        //Seperate the base node into two nodes
        

        Node newNode; //= base.getDynamicNode(nodeSizes, higher);
        if (base.hasChildNode()) {
            higher = base.arrayPartition(mid+1, base.getSize()-1);
            newNode = new InternalNode(this.keysPerInternalNode, higher);
            
            Node childNode = base.getChildNode();
            
            for (int i = 0; i <= base.getSize(); i++) {
                if (i > mid) {
                    if (i == mid+1) {
                        newNode.setChildNode(childNode);
                    }
                    childNode.setParent(newNode);
                }
                childNode = childNode.getNextSibling();
            }
        } else {
            higher = base.arrayPartition(mid, base.getSize()-1);
            newNode = new LeafNode(this.keysPerLeafNode, higher);
        }

        base.replaceData(lower);
        newNode.setNextSibling(base.getNextSibling());
        base.setNextSibling(newNode);

        System.out.println("Base size: " + base.getSize());
        System.out.println("New size: " + newNode.getSize());
        

        //insert key to parent node
        Node parentNode = base.getParent();
        if (parentNode == null) {
            //Create new root internal node
            parentNode = new InternalNode(keysPerInternalNode);
            parentNode.setChildNode(base);
            base.setParent(parentNode);
            this.root = parentNode;
        }

        newNode.setParent(base.getParent());

        addDataOrSplit(parentNode, midElement);
    }

    private void addDataOrSplit(Node node, Object data) {
        if (node.isFull()) {
            node.addData(data);
            split(node);
        } else {
            node.addData(data);
        }
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
        if (current.hasNextSibling() && current.getParent().equals(current.getNextSibling().getParent())) {
            preOrder(current.getNextSibling(), tabCount);
        }
    }
}
