package bintree;

import java.util.Comparator;

public class BinTree<T> {
    private NodeTree<T> root;
    private Comparator<T> comparator;

    public BinTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    private T addRecursive(NodeTree<T> node, T value){
        if (node == null) {
            return new NodeTree<>(value);
        }
    }
}
