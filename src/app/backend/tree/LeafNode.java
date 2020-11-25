package app.backend.tree;

import app.backend.fileaccess.KeyValue;
public class LeafNode extends Node {
    

    public LeafNode(int numOfValues) {
        super(numOfValues);
    }

    public LeafNode(int numOfValues, KeyValue firstPair) {
        super(numOfValues);
        addData(firstPair);
    }

    public LeafNode(int dataSize, Object... dataArr) {
        super(dataSize, dataArr);
    }


    @Override
    public String keyFromObject(Object o) {
        if (o == null) return null;
        return ((KeyValue) o).getKey();
    }

    public Object[] getArray(int start, int end) {
        return new KeyValue[end-start+1];
    }

    @Override
    public Object getObject(Object o) {
        return ((KeyValue) o);
    }

}
