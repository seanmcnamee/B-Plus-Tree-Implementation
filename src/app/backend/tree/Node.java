package app.backend.tree;

/**
 * Note that internally, a node can store 1 extra than is displayed. This is so
 * that splitting can be done easier
 */
public abstract class Node {
    protected Object[] dataMembers;
    protected Node parent;
    protected Node childNode;
    protected Node nextSibling;
    protected int memberCount = 0;
    private final int maxCount, minCount;

    public Node(int dataSize) {
        // The array should store enough for itself full and a fuse.
        this.maxCount = dataSize; // size larger than this is too much
        this.minCount = (int) Math.ceil(dataSize / 2.0); // Size smaller than this is too little
        this.dataMembers = new Object[(int) (dataSize + minCount)];
    }

    public Node(int dataSize, Object... dataArr) {
        this(dataSize);
        insertIntoCurrent(dataArr);
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

    public int addData(Object data) {
        Object temp;
        int dataIndex = binarySearch(0, memberCount - 1, keyFromObject(data));
        memberCount++;
        for (int i = dataIndex; i < memberCount; i++) {
            temp = this.dataMembers[i];
            this.dataMembers[i] = data;
            data = temp;
        }
        return dataIndex;
    }

    public int removeData(String key) {
        int dataIndex = binarySearch(0, memberCount - 1, key);
        memberCount--;
        for (int i = dataIndex; i < memberCount; i++) {
            this.dataMembers[i] = this.dataMembers[i + 1];
        }
        this.dataMembers[memberCount] = null;
        return dataIndex;
    }

    /**
     * Get the index of the desired key.
     */
    public int getIndexOf(String key) {
        return binarySearch(0, memberCount - 1, key);
    }

    /**
     * Returns the index to add/delete the data.
     */
    private int binarySearch(int low, int high, String key) {
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

    public Object[] arrayPartition(int start, int end) {
        // System.out.println("Partitioning...");
        Object[] arr = getArray(start, end);
        for (int i = start; i <= end; i++) {
            arr[i - start] = getObject(this.dataMembers[i]);
        }

        return arr;
    }

    public void replaceData(Object[] data) {
        insertIntoCurrent(data);
    }

    public void appendData(Object optionalData, Object[] data) {
        if (optionalData == null) {
            appendToCurrent(data);
        } else {
            appendToCurrent(optionalData, data);
        }
    }

    private void insertIntoCurrent(Object[] data) {
        for (int i = 0; i < this.dataMembers.length; i++) {
            if (i < data.length) {
                this.dataMembers[i] = data[i];
            } else {
                this.dataMembers[i] = null;
            }
        }
        this.memberCount = Math.min(this.dataMembers.length, data.length);
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

        /*
         * for (int i = currentSize; i < this.dataMembers.length; i++) { if
         * (i-currentSize < data.length) { this.dataMembers[i] = data[i-currentSize]; }
         * } this.memberCount = currentSize + currentSize - 1;
         */
    }
}
