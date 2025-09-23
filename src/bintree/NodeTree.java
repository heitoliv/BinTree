package bintree;

public class NodeTree<T> {
    private T value;
    private NodeTree<T> ChildLeft;
    private NodeTree<T> ChildRight;

    public NodeTree(T value){
        this.value = value;
        this.ChildLeft = null;
        this.ChildRight = null;
    }

    /**
     * @return the valor
     */
    public T getValor() {
        return value;
    }

    /**
     * @param value the valor to set
     */
    public void setValor(T value) {
        this.value = value;
    }

    /**
     * @return the ChildRight
     */
    public NodeTree<T> getChildRight() {
        return ChildRight;
    }

    /**
     * @param ChildRight the ChildRight to set
     */
    public void setChildRight(NodeTree<T> ChildRight) {
        this.ChildRight = ChildRight;
    }

    /**
     * @return the filhoEsquerda
     */
    public NodeTree<T> getChildLeft() {
        return ChildLeft;
    }

    /**
     * @param ChildLeft the ChildLeft to set
     */
    public void setChildLeft(NodeTree<T> ChildLeft) {
        this.ChildLeft = ChildLeft;
    }


}

