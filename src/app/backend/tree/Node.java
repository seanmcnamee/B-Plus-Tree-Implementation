package app.backend.tree;

public abstract class Node {
    protected Object[] dataMembers;
    protected Node parent;
    protected Node childNode;
    protected Node nextSibling;
    protected int topIndex = -1;

    public Node(int dataSize) {
        this.dataMembers = new Object[dataSize];
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
        for (int i = binarySearch(0, topIndex, data); i <= topIndex+1; i++) {
            temp = this.dataMembers[i];
            this.dataMembers[i] = data;
            data = temp;
        }
        topIndex++;
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
            if (keyFromObject(this.dataMembers[low]).compareTo(keyFromObject(data)) > 0) {
                return low;
            } else {
                return low+1;
            }
        }

        //Otherwise, split in half and keep honing in
        int mid = (high+low)/2;
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

    public String toString() {
        String overall = "";
        for (Object data : dataMembers) {
            String key = keyFromObject(data);
            overall += key + " ";
        }
        return overall;
    }

    public int getSize() {
        return topIndex+1;
    }


}
