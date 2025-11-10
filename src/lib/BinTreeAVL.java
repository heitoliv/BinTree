package lib;

import java.util.Comparator;

/**
 * Implementação de uma Árvore Binária de Busca AVL (Auto-Balanceada).
 * Esta classe herda de BinTree e sobrescreve o método de adição
 * para garantir que a árvore permaneça balanceada após cada inserção.
 *
 * @param <T> tipo dos elementos armazenados na árvore
 */
public class BinTreeAVL<T> extends BinTree<T> {

    /**
     * Construtor da árvore AVL.
     *
     * @param comparator objeto Comparator responsável por definir a ordem dos elementos
     */
    public BinTreeAVL(Comparator<T> comparator) {
        super(comparator);
    }

    //-----------------------------------------------------------------
    // Métodos Auxiliares de Balanceamento
    //-----------------------------------------------------------------

    /**
     * Calcula a altura de um nó específico.
     * Este método é necessário pois o 'altura(NodeTree<T> node)' da classe pai é privado.
     *
     * @param node O nó cuja altura será calculada.
     * @return A altura do nó (-1 se o nó for nulo).
     */
    private int alturaNode(NodeTree<T> node) {
        if (node == null) {
            return -1; // Altura de uma árvore/nó nulo é -1
        }
        
        // Recalcula a altura de forma recursiva (ineficiente, mas funcional sem
        // um campo 'altura' no NodeTree)
        int left = alturaNode(node.getChildLeft());
        int right = alturaNode(node.getChildRight());
        
        return 1 + Math.max(left, right);
    }

    /**
     * Calcula o fator de balanceamento de um nó.
     * O fator é (altura da subárvore esquerda) - (altura da subárvore direita).
     *
     * @param node O nó para verificar o balanceamento.
     * @return O fator de balanceamento (0 se o nó for nulo).
     */
    private int getFatorBalanceamento(NodeTree<T> node) {
        if (node == null) {
            return 0;
        }
        // Usa nosso método 'alturaNode' reimplementado
        return alturaNode(node.getChildLeft()) - alturaNode(node.getChildRight());
    }

    //-----------------------------------------------------------------
    // Métodos de Rotação
    //-----------------------------------------------------------------

    /**
     * Executa uma rotação simples à direita (Caso Esquerda-Esquerda).
     *
     * @param y Nó desbalanceado (raiz da subárvore a ser rotacionada).
     * @return A nova raiz da subárvore após a rotação.
     */
    private NodeTree<T> rotacaoDireita(NodeTree<T> y) {
        
        NodeTree<T> x = y.getChildLeft();
        NodeTree<T> T2 = x.getChildRight();

        // Realiza a rotação
        x.setChildRight(y);
        y.setChildLeft(T2);

        // Retorna a nova raiz
        return x;
    }

    /**
     * Executa uma rotação simples à esquerda (Caso Direita-Direita).
     *
     * @param x Nó desbalanceado (raiz da subárvore a ser rotacionada).
     * @return A nova raiz da subárvore após a rotação.
     */
    private NodeTree<T> rotacaoEsquerda(NodeTree<T> x) {
        
        NodeTree<T> y = x.getChildRight();
        NodeTree<T> T2 = y.getChildLeft();

        // Realiza a rotação
        y.setChildLeft(x);
        x.setChildRight(T2);

        // Retorna a nova raiz
        return y;
    }

    //-----------------------------------------------------------------
    // Sobrescrita do Método Adicionar
    //-----------------------------------------------------------------

    /**
     * Insere um novo valor na árvore e, em seguida, executa o
     * balanceamento AVL. (Sobrescreve o método da classe BinTree).
     *
     * @param newValue valor a ser adicionado
     */
    @Override
    public void adicionar(T newValue) {
        // Acessamos 'root' e 'comparator' da classe pai (que devem ser 'protected')
        this.root = adicionarEBalancear(this.root, newValue);
    }

    /**
     * Método recursivo auxiliar para inserir E balancear a árvore.
     * Este método substitui o 'adicionarRecursivo' da classe pai.
     *
     * @param node  Nó atual da recursão.
     * @param value Valor a ser inserido.
     * @return O nó raiz da subárvore (potencialmente) modificada e balanceada.
     */
    private NodeTree<T> adicionarEBalancear(NodeTree<T> node, T value) {
        
        // --- 1. Inserção Padrão de BST ---
        if (node == null) {
            return new NodeTree<>(value);
        }

        // Usamos o 'comparator' (que deve ser 'protected') da classe pai
        int comp = comparator.compare(value, node.getValue());

        if (comp < 0) {
            node.setChildLeft(adicionarEBalancear(node.getChildLeft(), value));
        } else if (comp > 0) {
            node.setChildRight(adicionarEBalancear(node.getChildRight(), value));
        } else {
            // Valores duplicados não são inseridos
            return node;
        }

        // --- 2. Obter Fator de Balanceamento ---
        int balance = getFatorBalanceamento(node);

        // --- 3. Casos de Rotação ---

        // Caso 1: Esquerda-Esquerda (LL)
        if (balance > 1 && comparator.compare(value, node.getChildLeft().getValue()) < 0) {
            return rotacaoDireita(node);
        }

        // Caso 2: Direita-Direita (RR)
        if (balance < -1 && comparator.compare(value, node.getChildRight().getValue()) > 0) {
            return rotacaoEsquerda(node);
        }

        // Caso 3: Esquerda-Direita (LR)
        
        if (balance > 1 && comparator.compare(value, node.getChildLeft().getValue()) > 0) {
            node.setChildLeft(rotacaoEsquerda(node.getChildLeft()));
            return rotacaoDireita(node);
        }

        // Caso 4: Direita-Esquerda (RL)
        
        if (balance < -1 && comparator.compare(value, node.getChildRight().getValue()) < 0) {
            node.setChildRight(rotacaoDireita(node.getChildRight()));
            return rotacaoEsquerda(node);
        }

        // --- 4. Retornar o nó (sem mudanças de balanceamento) ---
        return node;
    }
}