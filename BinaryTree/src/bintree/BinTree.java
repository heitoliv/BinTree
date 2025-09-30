package bintree;

import java.util.Comparator;

public class BinTree<T> {
    private NodeTree<T> root;
    private Comparator<T> comparator;

    public BinTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    public void add(T newValue){
        root = addRecursive(root,newValue);
    }

    private NodeTree<T> addRecursive(NodeTree<T> node, T value) {
        if (node == null) {
            return new NodeTree<T>(value);
        }

        int comp = comparator.compare(value, node.getValue());
        if (comp < 0){
            node.setChildLeft(addRecursive(node.getChildLeft(), value));
        }
        else if (comp > 0){
            node.setChildRight(addRecursive(node.getChildRight(),value));
        }
        return node;
    }

    public T search(T value){
        return search(value, comparator);

    }

    public T search(T value, Comparator comparator){
        return searchRecursive(root,value,comparator);
    }

    private T searchRecursive(NodeTree<T> node, T value, Comparator comparator){
        if (node == null) return null;

        int comp = comparator.compare(value, node.getValue());
        if (comp == 0) {
            return node.getValue();
        }
        else if (comp < 0){
            return searchRecursive(node.getChildLeft(),value,comparator);
        }
        return searchRecursive(node.getChildRight(),value,comparator);
    }

    private NodeTree<T>[] removeRec(NodeTree<T> current, T value){
    }

}
