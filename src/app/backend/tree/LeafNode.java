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

    @Override
    public String keyFromObject(Object o) {
        if (o == null) return null;
        return ((KeyValue) o).getKey();
    }

}
