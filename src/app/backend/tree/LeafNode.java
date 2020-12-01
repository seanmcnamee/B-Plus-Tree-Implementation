package app.backend.tree;

import app.backend.fileaccess.KeyValue;
/**
 * This repreasents the deepest nodes of the tree. They store KeyValue objects in their array.
 */
public class LeafNode extends Node {
    

    public LeafNode(int numOfValues) {
        super(numOfValues);
    }

    public LeafNode(int numOfValues, KeyValue firstPair) {
        super(numOfValues);
        addData(firstPair);
    }

    public LeafNode(int dataSize, Object... dataArr) {
        this(dataSize);
        replaceData(dataArr);
    }

    @Override
    public String keyFromObject(Object o) {
        if (o == null) return null;
        return ((KeyValue) o).getKey();
    }

    @Override
    public Object[] getArray(int start, int end) {
        return new KeyValue[end-start+1];
    }

    @Override
    public Object getObject(Object o) {
        return ((KeyValue) o);
    }

}
