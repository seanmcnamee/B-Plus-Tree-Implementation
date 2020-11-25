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

    public InternalNode(int dataSize, Object... dataArr) {
        super(dataSize, dataArr);
    }

    @Override
    public String keyFromObject(Object o) {
        return ((String) o);
    }

    @Override
    public Object[] getArray(int start, int end) {
        return new String[end-start+1];
    }

    @Override
    public Object getObject(Object o) {
        return ((String) o);
    }

}