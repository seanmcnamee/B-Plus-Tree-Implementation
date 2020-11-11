package app.backend.tree;

/**
 * InternalNode
 */
public class InternalNode extends Node {

    public InternalNode(int keyCount) {
        super(keyCount);
    }

    public InternalNode(int keyCount, String firstKey) {
        super(keyCount);
        addData(firstKey);
    }

    @Override
    public String keyFromObject(Object o) {
        return ((String) o);
    }
}