package app.backend.tree;

/**
 * An abstract class because it will either be a LeafNode or InternalNode.
 * 
 * Note that internally, a node can store minCount extra than is displayed. 
 * This is so that fuse/insert can still order the array, even when there's 
 * supposed to be an overflow
 */
public abstract class Node {

    //Both will have to store some type of data (either keys for internal nodes, or KeyValues for leaf nodes)
    //the size of dataMembers is large enough so that there's never an indexOutOfBoundsException.
    //The memberCount acts as the size of the used portion of the array.
    //The max and min counts are needed for recognizing when a fuse or split is needed.
    protected Object[] dataMembers;
    protected int memberCount = 0; //Must be updated whenever dataMembers is updated
    private final int maxCount, minCount;

    //Pointers to the other nodes in the tree. Note that
    //parent is only null when the node is the root node.
    //childNode is only null when the node is a LeafNode.
    //nextSibling is only null when this node holds the largest values at its depth.
    protected Node parent;
    protected Node childNode;
    protected Node nextSibling;
    
    /**
     * @param dataSize The maxCount of values to be stored in this Node.
     * An InternalNode with 5 keys will have dataSize = 5.
     * A LeafNode with 30 values will have dataSize = 30.
     */
    public Node(int dataSize) {
        // The array should store enough for itself full and a fuse.
        this.maxCount = dataSize; // Whenever the array has memberCount larger than this its too much
        this.minCount = (int) Math.ceil(dataSize / 2.0); // Whenever the array has memberCount smaller than this is too little
        this.dataMembers = new Object[(int) (dataSize + minCount)];
    }

    public Node getParent() {
        return parent;
    }

    public Node getChildNode() {
        return childNode;
    }

    public boolean hasChildNode() {
        return childNode != null;
    }

    public Node getNextSibling() {
        return nextSibling;
    }

    public boolean hasNextSibling() {
        return nextSibling != null;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildNode(Node childNode) {
        this.childNode = childNode;
    }

    public void setNextSibling(Node nextSibling) {
        this.nextSibling = nextSibling;
    }

    /**
     * Pass this node's current next sibling to the new one. Siblings have the same
     * parent
     */
    public void insertNextSibling(Node newNextSibling) {
        newNextSibling.setNextSibling(this.nextSibling);
        setNextSibling(newNextSibling);
        nextSibling.setParent(this.parent);
    }

    /**
     * Puts the data into the proper spot in the array
     * @param data
     * @return
     */
    public int addData(Object data) {
        Object temp;
        int dataIndex = binarySearchInsert(0, memberCount - 1, keyFromObject(data));
        memberCount++;
        for (int i = dataIndex; i < memberCount; i++) {
            temp = this.dataMembers[i];
            this.dataMembers[i] = data;
            data = temp;
        }
        return dataIndex;
    }

    /**
     * Removed the data from the array.
     * @param key
     * @return the index that the data was removed from. -1 if not removed/not found
     */
    public int removeData(String key) {
        int dataIndex = binarySearch(0, memberCount - 1, key);
        if (dataIndex >= 0) {
            memberCount--;
            for (int i = dataIndex; i < memberCount; i++) {
                this.dataMembers[i] = this.dataMembers[i + 1];
            }
            this.dataMembers[memberCount] = null;
        }
        return dataIndex;
        
    }

    /**
     * Get the index of the desired key.
     */
    public int getIndexOf(String key) {
        return binarySearch(0, memberCount - 1, key);
    }

    /**
     * Returns the index to add the data properly. Will always be >= 0
     */
    private int binarySearchInsert(int low, int high, String key) {
        // System.out.println("Binary search: " + low + " - " + high);
        if (high < low) {
            return low;
        }
        // When you've honed into 1 value, give the index
        if (high - low == 0) {
            if (keyAtIndex(low).compareTo(key) >= 0) {
                // System.out.println("\t\t\tsmaller/equal");
                return low;
            } else {
                // System.out.println("\t\t\tlarger");
                return low + 1;
            }
        }

        // Otherwise, split in half and keep honing in
        int mid = (high + low) / 2;
        // System.out.println("Comparing " + keyAtIndex(mid) + " to " + key);
        if (keyAtIndex(mid).compareTo(key) >= 0) {
            return binarySearchInsert(low, mid, key);
        } else {
            return binarySearchInsert(mid + 1, high, key);
        }
    }

    /**
     * Returns the index of currently existing data. -1 if not found.
     */
    private int binarySearch(int low, int high, String key) {
        // System.out.println("Binary search: " + low + " - " + high);
        if (high < low) {
            return low;
        }
        // When you've honed into 1 value, give the index
        if (high - low == 0) {
            if (keyAtIndex(low).compareTo(key) == 0) {
                // System.out.println("\t\t\tsmaller/equal");
                return low;
            } else {
                return -1;
            }
        }

        // Otherwise, split in half and keep honing in
        int mid = (high + low) / 2;
        // System.out.println("Comparing " + keyAtIndex(mid) + " to " + key);
        if (keyAtIndex(mid).compareTo(key) >= 0) {
            return binarySearch(low, mid, key);
        } else {
            return binarySearch(mid + 1, high, key);
        }
    }

    public String keyAtIndex(int index) {
        return keyFromObject(this.dataMembers[index]);
    }

    public Object ObjectAtIndex(int index) {
        return getObject(this.dataMembers[index]);
    }

    public abstract String keyFromObject(Object o);

    public abstract Object getObject(Object o);

    public abstract Object[] getArray(int start, int end);

    public String toString() {
        String overall = "";
        for (int i = 0; i < this.maxCount; i++) {
            Object data = this.dataMembers[i];
            String key = keyFromObject(data);
            overall += key + " ";
        }
        return overall;
    }

    public int getSize() {
        return memberCount;
    }

    public boolean isOverFilled() {
        return getSize() > this.maxCount;
    }

    public boolean isUnderFilled() {
        return getSize() < this.minCount;
    }

    public int getMid() {
        return getSize() / 2;
    }

    /**
     * return an array with only the values in the provided range
     */
    public Object[] arrayPartition(int start, int end) {
        // System.out.println("Partitioning...");
        Object[] arr = getArray(start, end);
        for (int i = start; i <= end; i++) {
            arr[i - start] = getObject(this.dataMembers[i]);
        }

        return arr;
    }

    /**
     * Replace all the values in dataMembers with the values in data
     */
    public void replaceData(Object[] data) {
        for (int i = 0; i < this.dataMembers.length; i++) {
            if (i < data.length) {
                this.dataMembers[i] = data[i];
            } else {
                this.dataMembers[i] = null;
            }
        }
        this.memberCount = Math.min(this.dataMembers.length, data.length);
    }

    /**
     * Append all the values in data to dataMembers
     */
    public void appendData(Object optionalData, Object[] data) {
        if (optionalData == null) {
            appendToCurrent(data);
        } else {
            appendToCurrent(optionalData, data);
        }
    }

    private void appendToCurrent(Object... data) {
        int currentSize = getSize();
        int index = 0;
        while (this.dataMembers[index] != null) {
            index++;
        }
        for (int i = 0; i < data.length; i++) {
            this.dataMembers[index + i] = data[i];
        }
        this.memberCount = index + data.length;
    }
}
