# Etapa 5: Relatório de Análise (BinTree vs. BinTreeAVL)

Este relatório analisa os dados de saída do `AppRelatorioAVL`, comparando o desempenho e a estrutura de uma Árvore Binária de Busca (`BinTree`) padrão e uma Árvore AVL (`BinTreeAVL`) quando ambas são alimentadas com dados ordenados, o que representa o pior caso de inserção.

## 1\. Dados de Saída do AppRelatorioAVL

A execução do aplicativo de teste gerou os seguintes resultados:

```
Árvore AVL Criada
Quantidade de Nós: 100 Altura: 6
Árvore Degenerada Criada
Quantidade de Nós: 100 Altura: 99
Árvore AVL Criada
Quantidade de Nós: 1000 Altura: 9
Árvore Degenerada Criada
Quantidade de Nós: 1000 Altura: 999
Árvore AVL Criada
Quantidade de Nós: 10000 Altura: 13
Árvore Degenerada Criada
Quantidade de Nós: 10000 Altura: 9999
```

-----

## 2\. Método de Geração Utilizado

Ambas as árvores (`BinTree` e `BinTreeAVL`) foram alimentadas usando o método `geraArvoreDegenerada`, que insere elementos com matrículas em ordem perfeitamente crescente (ex: 101, 102, 103...).

```java
public void geraArvoreDegenerada(int n, BinTreeInterface<Aluno> arv){
        int i,matricula= matriculaBase;
        String nome;
        for(i=1;i<=n;i++){
            matricula++;
            nome = geraNomeCompleto();
            //Aqui crio um aluno com os dados gerados e o adiciono na árvore.
            arv.adicionar(new Aluno(matricula,nome));
        }
    }
```

-----

## 3\. Análise: Comparação das Alturas das Árvores

**Pergunta:** *Por que as alturas das árvores degeneradas criadas com o AppRelatorioAVL é consideravelmente menor que as alturas das árvores degeneradas criadas na etapa anterior, se ambas foram alimentadas utilizando o método geraArvoreDegenerada?*

### 3.1. Correção da Premissa

Vamos analisar os dados com atenção. A sua pergunta parte de uma premissa que precisa ser ajustada:

  * **Árvores Degeneradas (Relatório Anterior):** A altura era $O(n)$. Para $N=1000$, a altura seria $\approx 999$.
  * **Árvores Degeneradas (AppRelatorioAVL):** A saída para a "Árvore Degenerada Criada" (sua `BinTree` normal) **também** mostra alturas de 99, 999 e 9999.

As alturas das árvores degeneradas (BST) **são exatamente as mesmas** em ambos os relatórios, o que prova que o método `geraArvoreDegenerada` está funcionando de forma consistente.

### 3.2. A Verdadeira Comparação

A sua saída do `AppRelatorioAVL` não está comparando duas árvores degeneradas. Ela está comparando duas árvores diferentes recebendo a *mesma entrada degenerada*:

1.  **`Árvore Degenerada Criada` (sua `BinTree`):** Uma BST padrão que **não se auto-balanceia**. Ao receber dados ordenados, ela corretamente degenera em uma lista encadeada, resultando em uma altura $O(n)$.
2.  **`Árvore AVL Criada` (sua `BinTreeAVL`):** Uma BST que **se auto-balanceia**. Mesmo recebendo os dados na pior ordem possível, o método `adicionar` sobrescrito detecta o desbalanceamento a cada inserção e aplica rotações (como a Rotação à Esquerda) para forçar a árvore a manter uma altura logarítmica.

A tabela a seguir esclarece o que seus dados demonstram:

| Tipo de Árvore | Entrada de Dados | $N=100$ | $N=1000$ | $N=10000$ | Complexidade de Altura |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `BinTree` (BST Padrão) | Ordenada (Pior Caso) | **Altura 99** | **Altura 999** | **Altura 9999** | **$O(n)$** |
| `BinTreeAVL` | Ordenada (Pior Caso) | **Altura 6** | **Altura 9** | **Altura 13** | **$O(\log n)$** |

**Conclusão:** A diferença drástica de altura (ex: 9999 vs. 13) não ocorre because o método de geração mudou, mas sim porque a `BinTreeAVL` está fazendo exatamente o que ela foi projetada para fazer: **impedir que a árvore degenere**, mesmo sob as piores condições de inserção.

-----

## 4\. Análise: Ordem de Complexidade da Busca na AVL

**Pergunta:** *Qual será a ordem de complexidade de buscas em árvores AVL criadas utilizando o método "geraArvoreDegenerada" do gerador de árvores? Explique.*

**Resposta:** A ordem de complexidade da busca é **$O(\log n)$**.

### 4.1. Explicação

1.  **Complexidade de Busca = $O(altura)$:** Em qualquer árvore binária de busca, a operação `pesquisar` percorre um caminho da raiz até um nó (ou folha). No pior caso, o número de nós visitados é igual à altura da árvore + 1. Portanto, a complexidade de tempo da busca é sempre ditada pela altura da árvore: $O(altura)$.

2.  **Altura da `BinTree` (BST):** Como visto no seu Relatório Anterior e na saída do `AppRelatorioAVL`, se uma `BinTree` normal recebe dados ordenados (via `geraArvoreDegenerada`), sua altura se torna $O(n)$. Consequentemente, a complexidade de busca nessa árvore degenerada também é **$O(n)$**.

3.  **Altura da `BinTreeAVL`:** O propósito fundamental de uma árvore AVL é **garantir** que, após qualquer operação de inserção (ou remoção), a altura da árvore permaneça **$O(\log n)$**. Ela faz isso aplicando rotações para rebalancear a árvore sempre que o fator de balanceamento de um nó é violado.

Como seus próprios testes de saída comprovam (para $N=10000$, a altura foi 13, e $\log_2(10000) \approx 13.28$), a `BinTreeAVL` manteve com sucesso sua altura logarítmica, mesmo quando alimentada com o pior caso de entrada.

Portanto, como a altura da árvore resultante é garantidamente $O(\log n)$, a complexidade da busca (que é $O(altura)$) também será **$O(\log n)$**.

-----

# Etapa 6: Implementações Baseadas em Árvores Binárias na Biblioteca Padrão de Java

## 1\. Introdução

A Biblioteca Padrão de Java, através do seu robusto *Java Collections Framework*, oferece um conjunto de estruturas de dados de alto desempenho para o uso cotidiano do desenvolvedor. Embora não exponha diretamente uma classe genérica como `BinarySearchTree`, a biblioteca utiliza implementações highly otimizadas e especializadas de árvores binárias para fornecer as funcionalidades de algumas de suas classes mais importantes.

O objetivo desta seção é investigar e apresentar as estruturas de dados da biblioteca padrão do Java que são fundamentadas em árvores binárias. Analisaremos a estrutura subjacente, o propósito de sua utilização e a complexidade computacional (em notação Big O) para as operações fundamentais de inserção, busca e remoção. As principais classes abordadas serão `TreeMap`, `TreeSet` e `PriorityQueue`.

-----

## 2\. `TreeMap` e `TreeSet`: O Poder das Árvores Rubro-Negras

As classes `TreeMap` e `TreeSet` são as implementações mais diretas de uma árvore de busca binária no Java Collections Framework. Ambas garantem que os elementos sejam mantidos em uma ordem definida e oferecem desempenho logarítmico para as operações básicas.

### 2.1. A Estrutura Subjacente: Árvore Rubro-Negra (Red-Black Tree)

Tanto `TreeMap` quanto `TreeSet` são implementados utilizando uma **Árvore Rubro-Negra**. Esta é uma variação de uma árvore de busca binária autobalanceada, projetada para garantir que a árvore permaneça "razoavelmente" balanceada durante inserções e remoções.

Uma árvore de busca binária (BST) simples pode, no pior caso, degenerar em uma estrutura semelhante a uma lista encadeada, levando a uma complexidade de `O(n)` para as operações. Para evitar isso, as árvores autobalanceadas, como a Rubro-Negra, realizam operações de rebalanceamento (rotações e recolorações) após modificações.

As principais garantias de uma Árvore Rubro-Negra são:

1.  Cada nó é vermelho ou preto.
2.  A raiz é sempre preta.
3.  Nenhum nó vermelho pode ter um filho vermelho.
4.  Todo caminho da raiz até uma folha (nó `null`) contém o mesmo número de nós pretos.

Essas regras garantem que o caminho mais longo da raiz até qualquer folha não seja mais do que o dobro do caminho mais curto. Isso, por sua vez, assegura que a altura da árvore seja sempre proporcional a **`O(log n)`**, onde *n* é o número de nós. Manter essa altura logarítmica é a chave para o desempenho eficiente da estrutura.

### 2.2. `TreeMap<K, V>`

O `TreeMap` é uma implementação da interface `Map` que armazena pares de chave-valor (`K`, `V`). Sua principal característica é manter as chaves em ordem crescente. A ordenação pode ser a "ordem natural" dos elementos (se a classe da chave implementar a interface `Comparable`) ou uma ordem customizada, definida por um `Comparator` passado no construtor do mapa.

  * **Inserção (`put(K key, V value)`):** A inserção envolve encontrar a posição correta para a nova chave na árvore, o que leva `O(log n)`. Após a inserção do novo nó, podem ser necessárias algumas operações de rebalanceamento (rotações e/ou recolorações) para manter as propriedades da árvore rubro-negra. Essas operações também têm um custo amortizado de `O(log n)`.
      * **Complexidade: `O(log n)`**
  * **Busca (`get(Object key)`, `containsKey(Object key)`):** A busca em uma árvore rubro-negra segue o algoritmo padrão de uma árvore de busca binária. Como a altura da árvore é garantidamente `O(log n)`, a busca também é.
      * **Complexidade: `O(log n)`**
  * **Remoção (`remove(Object key)`):** Similar à inserção, a remoção primeiro localiza o nó a ser removido (`O(log n)`). Após a remoção, a árvore pode violar as regras da árvore rubro-negra, exigindo operações de rebalanceamento para restaurar o equilíbrio. O custo dessas operações também é `O(log n)`.
      * **Complexidade: `O(log n)`**

### 2.3. `TreeSet<E>`

O `TreeSet` é uma implementação da interface `Set` que armazena elementos únicos e ordenados. Internamente, um `TreeSet` utiliza um `TreeMap<E, Object>`, onde os elementos do conjunto são as chaves do mapa, e o valor associado é um objeto constante e irrelevante. Devido a essa implementação, suas características de desempenho são idênticas às do `TreeMap`.

  * **Inserção (`add(E element)`):** Corresponde a uma chamada `map.put(element, DUMMY_VALUE)`.
      * **Complexidade: `O(log n)`**
  * **Busca (`contains(Object element)`):** Corresponde a uma chamada `map.containsKey(element)`.
      * **Complexidade: `O(log n)`**
  * **Remoção (`remove(Object element)`):** Corresponde a uma chamada `map.remove(element)`.
      * **Complexidade: `O(log n)`**

-----

## 3\. `PriorityQueue`: Uma Aplicação Especializada com Heaps Binários

Embora não seja uma árvore de busca, a `PriorityQueue` é outra estrutura de dados fundamental da biblioteca Java que é implementada usando uma forma especializada de árvore binária: o **Heap Binário**.

### 3.1. A Estrutura Subjacente: Heap Binário (Binary Heap)

Um Heap Binário é uma árvore binária "completa" que satisfaz a "propriedade do heap":

  * **Min-Heap (implementação padrão em Java):** O valor de qualquer nó é sempre menor ou igual aos valores de seus filhos. Isso garante que o menor elemento da coleção esteja sempre na raiz da árvore.
  * **Max-Heap:** O valor de qualquer nó é maior ou igual ao de seus filhos.

Embora conceitualmente seja uma árvore, um heap binário é eficientemente implementado usando um array, economizando a memória extra com ponteiros.

### 3.2. `PriorityQueue<E>`

A `PriorityQueue` é uma fila que ordena os elementos com base em sua prioridade, removendo sempre o elemento de "menor" valor.

  * **Inserção (`add(E e)`, `offer(E e)`):** Um novo elemento é adicionado ao final do heap e "sobe" na árvore (`siftUp`) até restaurar a propriedade do heap.
      * **Complexidade: `O(log n)`**
  * **Remoção do elemento de maior prioridade (`poll()`, `remove()`):** O elemento na raiz é removido, o último elemento do heap o substitui e então "desce" na árvore (`siftDown`) até restaurar a ordem.
      * **Complexidade: `O(log n)`**
  * **Busca (`contains(Object o)`) e Remoção de um elemento arbitrário (`remove(Object o)`):** Um heap **não** é otimizado para buscas arbitrárias, exigindo uma varredura linear.
      * **Complexidade: `O(n)`**

-----

## 4\. Tabela Comparativa e Conclusão

| Estrutura | Estrutura Subjacente | Característica Principal | Inserção | Busca (Específica) | Remoção (Específica) | Obter Min/Max |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **`TreeMap`** | Árvore Rubro-Negra | Pares chave-valor ordenados pela chave. | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| **`TreeSet`** | Árvtore Rubro-Negra | Elementos únicos e ordenados. | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| **`PriorityQueue`** | Heap Binário (Min-Heap) | Fila de prioridade; acesso rápido ao menor elemento. | `O(log n)` | **`O(n)`** | **`O(n)`** | **`O(1)`** (peek) |

**Conclusão:**

A biblioteca padrão do Java faz um uso inteligente de estruturas baseadas em árvores binárias. Para cenários que exigem uma coleção totalmente ordenada com operações rápidas sobre *qualquer* elemento, `TreeMap` e `TreeSet` (baseados em Árvores Rubro-Negras) são a escolha ideal. Por outro lado, quando o requisito é apenas obter e remover eficientemente o elemento de maior prioridade, a `PriorityQueue` (baseada em Heap Binário) é a ferramenta mais adequada e performática.
