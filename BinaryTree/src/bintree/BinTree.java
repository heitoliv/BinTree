package bintree;

import java.util.Comparator;

/**
 * Implementação genérica de uma Árvore Binária de Busca (BST).
 *
 * @param <T> tipo dos elementos armazenados na árvore
 */
public class BinTree<T> {
    private NodeTree<T> root;              // nó raiz da árvore
    private Comparator<T> comparator;      // critério de comparação para ordenar os elementos

    /**
     * Construtor da árvore binária, recebe um Comparator para comparar os elementos.
     *
     * @param comparator objeto Comparator responsável por definir a ordem dos elementos
     */
    public BinTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    /**
     * Insere um novo valor na árvore.
     *
     * @param newValue valor a ser adicionado
     */
    public void add(T newValue) {
        root = addRecursive(root, newValue);
    }

    /**
     * Método recursivo auxiliar para inserir elementos na árvore.
     *
     * @param node  nó atual em análise
     * @param value valor a ser inserido
     * @return o nó atualizado após a inserção
     */
    private NodeTree<T> addRecursive(NodeTree<T> node, T value) {
        if (node == null) {
            return new NodeTree<T>(value);
        }

        int comp = comparator.compare(value, node.getValue());
        if (comp < 0) {
            // insere à esquerda se o valor for menor
            node.setChildLeft(addRecursive(node.getChildLeft(), value));
        } else if (comp > 0) {
            // insere à direita se o valor for maior
            node.setChildRight(addRecursive(node.getChildRight(), value));
        }
        // se for igual, não faz nada (evita duplicatas)
        return node;
    }

    /**
     * Pesquisa um valor na árvore usando o comparator da instância.
     *
     * @param value valor a ser pesquisado
     * @return o valor encontrado ou null se não existir
     */
    public T search(T value) {
        return search(value, comparator);
    }

    /**
     * Pesquisa um valor na árvore usando um comparator específico.
     *
     * @param value valor a ser pesquisado
     * @param comp  Comparator utilizado para a busca
     * @return o valor encontrado ou null se não existir
     */
    public T search(T value, Comparator<T> comp) {
        return searchRecursive(root, value, comp);
    }

    /**
     * Método recursivo auxiliar para pesquisa.
     *
     * @param node  nó atual em análise
     * @param value valor que estamos procurando
     * @param cmp   comparator usado para comparar os elementos
     * @return o valor encontrado ou null
     */
    private T searchRecursive(NodeTree<T> node, T value, Comparator<T> cmp) {
        if (node == null) return null;

        int comp = cmp.compare(value, node.getValue());
        if (comp == 0) {
            return node.getValue(); // valor encontrado
        } else if (comp < 0) {
            return searchRecursive(node.getChildLeft(), value, comparator);
        }
        return searchRecursive(node.getChildRight(), value, comparator);
    }

    /**
     * Remove um valor da árvore.
     *
     * @param value valor a ser removido
     * @return o valor removido ou null se não existir
     */
    public T remove(T value) {
        ResultRemove<T> result = removeRecursive(root, value, comparator);
        root = result.newRoot; // atualiza a raiz caso tenha sido alterada
        return result.removedValue;
    }

    /**
     * Método recursivo auxiliar para remoção de nós.
     *
     * @param node  nó atual em análise
     * @param value valor a ser removido
     * @param cmp   comparator usado para comparar os elementos
     * @return objeto ResultRemove contendo o novo nó raiz e o valor removido
     */
    private ResultRemove<T> removeRecursive(NodeTree<T> node, T value, Comparator<T> cmp) {
        if (node == null) return new ResultRemove<>(null, null);

        int comp = cmp.compare(value, node.getValue());
        if (comp < 0) {
            // busca na subárvore esquerda
            ResultRemove<T> result = removeRecursive(node.getChildLeft(), value, cmp);
            node.setChildLeft(result.newRoot);
            return new ResultRemove<>(node, result.removedValue);
        } else if (comp > 0) {
            // busca na subárvore direita
            ResultRemove<T> res = removeRecursive(node.getChildRight(), value, cmp);
            node.setChildRight(res.newRoot);
            return new ResultRemove<>(node, res.removedValue);
        } else {
            // Nó encontrado
            T removed = node.getValue();

            // caso 1: sem filho à esquerda
            if (node.getChildLeft() == null) return new ResultRemove<>(node.getChildRight(), removed);

            // caso 2: sem filho à direita
            if (node.getChildRight() == null) return new ResultRemove<>(node.getChildLeft(), removed);

            // caso 3: dois filhos (substitui pelo sucessor (menor da subárvore direita))
            NodeTree<T> succ = findMin(node.getChildRight());
            node.setValue(succ.getValue()); // copia valor do sucessor
            ResultRemove<T> result = removeRecursive(node.getChildRight(), succ.getValue(), cmp);
            node.setChildRight(result.newRoot);
            return new ResultRemove<>(node, removed);
        }
    }

    /**
     * Classe auxiliar que armazena o resultado de uma remoção.
     *
     * @param <T> tipo do valor armazenado na árvore
     */
    private static class ResultRemove<T> {
        NodeTree<T> newRoot;   // nova raiz (subárvore ajustada após remoção)
        T removedValue;        // valor que foi efetivamente removido

        ResultRemove(NodeTree<T> root, T value) {
            this.newRoot = root;
            this.removedValue = value;
        }
    }

    /**
     * Encontra o nó de menor valor em uma subárvore.
     *
     * @param node nó inicial da subárvore
     * @return o nó com o menor valor
     */
    private NodeTree<T> findMin(NodeTree<T> node) {
        NodeTree<T> current = node;
        while (current.getChildLeft() != null) {
            current = current.getChildLeft();
        }
        return current;
    }

    // Método para percorrer a árvore em ordem
    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    /**
     * Classe auxiliar que armazena o resultado de uma remoção.
     *
     * @param node bó que irá representar o ponto de partida ao percorrer a árvore (in-ordem)
     */
    private void inOrderTraversal(NodeTree<T> node) {
        if (node != null) {
            inOrderTraversal(node.getChildLeft());          // Visita subárvore esquerda
            System.out.print(node.getValue() + " ");        // Visita nó atual
            inOrderTraversal(node.getChildRight());         // Visita subárvore direita
        }
    }
}
