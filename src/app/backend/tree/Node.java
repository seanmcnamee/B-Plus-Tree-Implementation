package app.backend.tree;

/**
 * Note that internally, a node can store 1 extra than is displayed.
 * This is so that splitting can be done easier
 */
public abstract class Node {
    protected Object[] dataMembers;
    protected Node parent;
    protected Node childNode;
    protected Node nextSibling;
    protected int memberCount = 0;

    public Node(int dataSize) {
        this.dataMembers = new Object[dataSize+1];
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
     * Pass this node's current next sibling to the new one.
     * Siblings have the same parent
     */
    public void insertNextSibling(Node newNextSibling) {
        newNextSibling.setNextSibling(this.nextSibling);
        setNextSibling(newNextSibling);
        nextSibling.setParent(this.parent);
    }

    public int addData(Object data) {
        Object temp;
        int dataIndex = binarySearch(0, memberCount-1, keyFromObject(data));
        memberCount++;
        for (int i = dataIndex; i < memberCount; i++) {
            temp = this.dataMembers[i];
            this.dataMembers[i] = data;
            data = temp;
        }
        return dataIndex;
    }

    public int removeData(String key) {
        int dataIndex = binarySearch(0, memberCount-1, key);
        memberCount--;
        for (int i = dataIndex; i < memberCount; i++) {
            this.dataMembers[i] = this.dataMembers[i+1];
        }
        this.dataMembers[memberCount] = null;
        return dataIndex;
    }

    /**
     * Returns the index to add/delete the data.
     */
    private int binarySearch(int low, int high, String key) {
        System.out.println("Binary search: " + low + " - " + high);
        if (high < low) {
            return low;
        }
        //When you've honed into 1 value, give the index
        if (high-low == 0) {
            if (keyAtIndex(low).compareTo(key) >= 0) {
                System.out.println("\t\t\tsmaller/equal");
                return low;
            } else {
                System.out.println("\t\t\tlarger");
                return low+1;
            }
        }

        //Otherwise, split in half and keep honing in
        int mid = (high+low)/2;
        System.out.println("Comparing " + keyAtIndex(mid) + " to " + key);
        if (keyAtIndex(mid).compareTo(key) >= 0) {
            return binarySearch(low, mid, key);
        } else {
            return binarySearch(mid+1, high, key);
        }
    }

    public String keyAtIndex(int index) {
        return keyFromObject(this.dataMembers[index]);
    }

    public abstract String keyFromObject(Object o);
    public abstract Object getObject(Object o);
    public abstract Object[] getArray(int start, int end);

    public String toString() {
        String overall = "";
        for (int i = 0; i < this.dataMembers.length-1; i++) {
            Object data = this.dataMembers[i];
            String key = keyFromObject(data);
            overall += key + ((i==this.dataMembers.length)? "    <": "") + " ";
        }
        return overall;
    }

    public int getSize() {
        return memberCount;
    }

    public boolean isFull() {
        return getSize() >= dataMembers.length-1;
    }

    public boolean hasTooFew() {
        return getSize() <= (dataMembers.length-1)/2;
    }

    public int getMid() {
        return getSize()/2;
    }

    public Object[] arrayPartition(int start, int end) {
        System.out.println("Partitioning...");
        Object[] arr = getArray(start, end);
        for (int i = start; i <= end; i++) {
            arr[i-start] = getObject(this.dataMembers[i]);
        }
        
        return arr;
    }

    public void replaceData(Object[] data) {
        insertIntoCurrent(data);
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


}
