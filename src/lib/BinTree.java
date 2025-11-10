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
    protected NodeTree<T> root;              // nó raiz da árvore
    protected Comparator<T> comparator;      // critério de comparação para ordenar os elementos

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
    public void adicionar(T newValue) {
        root = adicionarRecursivo(root, newValue);
    }

    /**
     * Método recursivo auxiliar para inserir elementos na árvore.
     *
     * @param node  nó atual em análise
     * @param value valor a ser inserido
     * @return o nó atualizado após a inserção
     */
    private NodeTree<T> adicionarRecursivo(NodeTree<T> node, T value) {
        if (node == null) {
            return new NodeTree<T>(value);
        }

        int comp = comparator.compare(value, node.getValue());
        if (comp < 0) {
            // insere à esquerda se o valor for menor
            node.setChildLeft(adicionarRecursivo(node.getChildLeft(), value));
        } else if (comp > 0) {
            // insere à direita se o valor for maior
            node.setChildRight(adicionarRecursivo(node.getChildRight(), value));
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
    public T pesquisar(T value) {
        return pesquisarRecursivo(root, value, comparator);
    }

    /**
     * Pesquisa um valor na árvore usando um comparator específico.
     *
     * @param value valor a ser pesquisado
     * @param comp  Comparator utilizado para a busca
     * @return o valor encontrado ou null se não existir
     */
    public T pesquisar(T value, Comparator<T> comp) {
        return pesquisarComparadorExt(this.root, value, comp);
    }

    private T pesquisarComparadorExt(NodeTree<T> r, T value, Comparator c){
        if (r == null)
            return null;
        else if (c.compare(value, r.getValue()) == 0)
            return r.getValue();
        else {
            T noEsq = pesquisarComparadorExt(r.getChildLeft(), value, c);
            T noDir = pesquisarComparadorExt(r.getChildRight(), value, c);

            if (noEsq != null)
                return noEsq;
            else
                return noDir;
        }
    }
    

    /**
     * Método recursivo auxiliar para pesquisa.
     *
     * @param node  nó atual em análise
     * @param value valor que estamos procurando
     * @param cmp   comparator usado para comparar os elementos
     * @return o valor encontrado ou null
     */
    private T pesquisarRecursivo(NodeTree<T> node, T value, Comparator<T> cmp) {
        if (node == null) return null;

        int comp = cmp.compare(value, node.getValue());
        if (comp == 0) {
            return node.getValue(); // valor encontrado
        } else if (comp < 0) {
            return pesquisarRecursivo(node.getChildLeft(), value, comparator);
        }
        return pesquisarRecursivo(node.getChildRight(), value, comparator);
    }

    /**
     * Remove um valor da árvore.
     *
     * @param value valor a ser removido
     * @return o valor removido ou null se não existir
     */
    public T remover(T value) {
        ResultRemove<T> result = removerRecursivo(root, value, comparator);
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
    private ResultRemove<T> removerRecursivo(NodeTree<T> node, T value, Comparator<T> cmp) {
        if (node == null) return new ResultRemove<>(null, null);

        int comp = cmp.compare(value, node.getValue());
        if (comp < 0) {
            // busca na subárvore esquerda
            ResultRemove<T> result = removerRecursivo(node.getChildLeft(), value, cmp);
            node.setChildLeft(result.newRoot);
            return new ResultRemove<>(node, result.removedValue);
        } else if (comp > 0) {
            // busca na subárvore direita
            ResultRemove<T> res = removerRecursivo(node.getChildRight(), value, cmp);
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
            ResultRemove<T> result = removerRecursivo(node.getChildRight(), succ.getValue(), cmp);
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
    public String caminharEmOrdem() {
        StringBuilder sb = new StringBuilder();
        buildEmOrdemString(root, sb);
        return sb.toString().trim();
    }

    /**
     * Método auxiliar que percorre a árvore em ordem (in-order) e
     * constrói uma string com os valores dos nós, separados por espaço.
     *
     * @param node nó atual em análise
     * @param sb   acumulador da string (StringBuilder)
     */
    private void buildEmOrdemString(NodeTree<T> node, StringBuilder sb) {
        if (node != null) {
            // Visita recursivamente a subárvore esquerda
            buildEmOrdemString(node.getChildLeft(), sb);

            // Adiciona o valor do nó atual
            sb.append(node.getValue()).append(" ");

            // Visita recursivamente a subárvore direita
            buildEmOrdemString(node.getChildRight(), sb);
        }
    }

    /**
     * Percorre a árvore em nível (Breadth-First pesquisar) e retorna uma string
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
    public int quantidadeNos() {
        return quantidadeNos(root);
    }

    private int quantidadeNos(NodeTree<T> node) {
        if (node == null) return 0;
        return 1 + quantidadeNos(node.getChildLeft()) + quantidadeNos(node.getChildRight());
    }

    /**
     * Calcula a altura da árvore.
     * @return altura (nível mais profundo da árvore)
     */
    public int altura() {
        if (root == null) {
            throw new IllegalStateException("Árvore vazia: não é possível calcular a altura.");
        }
        return altura(root);
    }

    private int altura(NodeTree<T> node) {
        if (node == null){
            return -1;
        }// Altura de árvore vazia é -1
        int left = altura(node.getChildLeft());
        int right = altura(node.getChildRight());
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