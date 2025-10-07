package lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementação genérica de uma Árvore Binária de Busca (BST).
 *
 * @param <T> tipo dos elementos armazenados na árvore
 */
public class BinTree<T> implements BinTreeInterface<T> {
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


    /**
     * Retorna a travessia in-order como uma string (útil para testes ou exibição).
     * @return String com os elementos em ordem
     */
    public String inOrderToString() {
        StringBuilder sb = new StringBuilder();
        buildInOrderString(root, sb);
        return sb.toString().trim();
    }

    /**
     * Método auxiliar que percorre a árvore em ordem (in-order) e
     * constrói uma string com os valores dos nós, separados por espaço.
     *
     * @param node nó atual em análise
     * @param sb   acumulador da string (StringBuilder)
     */
    private void buildInOrderString(NodeTree<T> node, StringBuilder sb) {
        if (node != null) {
            // Visita recursivamente a subárvore esquerda
            buildInOrderString(node.getChildLeft(), sb);

            // Adiciona o valor do nó atual
            sb.append(node.getValue()).append(" ");

            // Visita recursivamente a subárvore direita
            buildInOrderString(node.getChildRight(), sb);
        }
    }

    /**
     * Percorre a árvore em nível (Breadth-First Search) e retorna uma string
     * com os valores de cada nó separados por " \n ", iniciando com "[" e terminando com "]".
     *
     * @return string formatada com os valores da árvore em ordem de nível
     */
    public String caminharEmNivel() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        if (root != null) {
            Queue<NodeTree<T>> queue = new LinkedList<>(); // Fila para BFS
            queue.add(root);

            while (!queue.isEmpty()) {
                // Remove o próximo nó da fila
                NodeTree<T> current = queue.poll();

                // Adiciona seu valor à string com separador " \n "
                sb.append(current.getValue().toString()).append(" \n ");

                // Adiciona os filhos à fila (esquerdo depois direito)
                if (current.getChildLeft() != null) {
                    queue.add(current.getChildLeft());
                }
                if (current.getChildRight() != null) {
                    queue.add(current.getChildRight());
                }
            }

            // Remove o último " \n " extra
            if (sb.length() >= 3) {
                sb.setLength(sb.length() - 3);
            }
        }

        sb.append("]");
        return sb.toString();
    }



    /**
     * Conta o número total de nós na árvore.
     * @return número total de nós
     */
    public int countNodes() {
        return countNodes(root);
    }

    private int countNodes(NodeTree<T> node) {
        if (node == null) return 0;
        return 1 + countNodes(node.getChildLeft()) + countNodes(node.getChildRight());
    }

    /**
     * Calcula a altura da árvore.
     * @return altura (nível mais profundo da árvore)
     */
    public int height() {
        if (root == null) {
            throw new IllegalStateException("Árvore vazia: não é possível calcular a altura.");
        }
        return height(root);
    }

    private int height(NodeTree<T> node) {
        if (node == null){
            return -1;
        }// Altura de árvore vazia é -1
        int left = height(node.getChildLeft());
        int right = height(node.getChildRight());
        return 1 + Math.max(left, right);
    }
}

//    // Método para percorrer a árvore em ordem
//    public void inOrderTraversal() {
//        inOrderTraversal(root);
//    }
//
//    /**
//     * Classe auxiliar que armazena o resultado de uma remoção.
//     *
//     * @param node nó que irá representar o ponto de partida ao percorrer a árvore (in-ordem)
//     */
//    private void inOrderTraversal(NodeTree<T> node) {
//        if (node != null) {
//            inOrderTraversal(node.getChildLeft());          // Visita subárvore esquerda
//            System.out.print(node.getValue() + " ");        // Visita nó atual
//            inOrderTraversal(node.getChildRight());         // Visita subárvore direita
//        }
//    }