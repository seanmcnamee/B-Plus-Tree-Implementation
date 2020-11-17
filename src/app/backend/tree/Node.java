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

    public void addData(Object data) {
        Object temp;
        for (int i = binarySearch(0, memberCount-1, data); i <= memberCount; i++) {
            temp = this.dataMembers[i];
            this.dataMembers[i] = data;
            data = temp;
        }
        memberCount++;
    }

    /**
     * Returns the index to add the new key.
     */
    private int binarySearch(int low, int high, Object data) {
        if (high < low) {
            return low;
        }
        //When you've honed into 1 value, give the index
        if (high-low == 0) {
            System.out.println("Comparing " + keyFromObject(this.dataMembers[low]) + " to " + keyFromObject(data));
            if (keyFromObject(this.dataMembers[low]).compareTo(keyFromObject(data)) > 0) {
                System.out.println("\t\t\tinserting below");
                return low;
            } else {
                System.out.println("\t\t\tinserting above");
                return low+1;
            }
        }

        //Otherwise, split in half and keep honing in
        int mid = (high+low)/2;
        System.out.println("Comparing " + keyFromObject(this.dataMembers[mid]) + " to " + keyFromObject(data));
        if (keyFromObject(this.dataMembers[mid]).compareTo(keyFromObject(data)) > 0) {
            return binarySearch(low, mid, data);
        } else {
            return binarySearch(mid+1, high, data);
        }
    }

    public String keyAtIndex(int index) {
        return keyFromObject(this.dataMembers[index]);
    }

    public abstract String keyFromObject(Object o);
    public abstract Object getObject(Object o);
    public abstract Object[] getArray(int start, int end);
    public abstract Node getDynamicNode(int[] sizes, Object[] newData); //0 = leaf, 1 = internal

    public String toString() {
        String overall = "";
        for (int i = 0; i < this.dataMembers.length-1; i++) {
            Object data = this.dataMembers[i];
            String key = keyFromObject(data);
            overall += key + ((i==this.dataMembers.length-1)? "    <": "") + " ";
        }
        return overall;
    }

    public int getSize() {
        return memberCount;
    }

    public boolean isFull() {
        return getSize() >= dataMembers.length-1;
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
