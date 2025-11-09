# 1. Topologia da Árvore Gerada pelo Método `geraArvoreDegenerada`

* O método cria `n` objetos do tipo `Aluno`.
* A cada iteração, a matrícula é incrementada (`matricula++`).
* O nome é gerado de forma aleatória.
* Cada novo aluno é adicionado à árvore **em ordem crescente de matrícula**.

Sendo a árvore uma **árvore binária de busca (BST)** que insere nós com base no valor da matrícula:

* O primeiro aluno vira a **raiz**.
* Cada novo aluno possui **matrícula maior** que o anterior.
* Assim, todos os novos nós são inseridos **à direita** do nó anterior.

 Isso resulta em uma **árvore degenerada**, ou seja, uma árvore que se comporta como uma **lista encadeada**.


A topologia de uma **árvore degenerada** é **linear**, semelhante a isto:

```
Raiz
 └── Nó
      └── Nó
           └── Nó
                └── ...
```

Cada nó possui **apenas um filho direito** (ou esquerdo, dependendo da ordem de inserção).

Se `matriculaBase = 100` e `n = 5`, o método irá gerar os seguintes alunos:

| Ordem | Matrícula | Nome (exemplo) |
| :---- | :-------- | :------------- |
| 1     | 101       | Ana Costa      |
| 2     | 102       | Bruno Lima     |
| 3     | 103       | Carla Souza    |
| 4     | 104       | Diego Torres   |
| 5     | 105       | Elisa Rocha    |

### Estrutura da Árvore Gerada

```
(101, Ana Costa)
       \
       (102, Bruno Lima)
              \
              (103, Carla Souza)
                     \
                     (104, Diego Torres)
                            \
                            (105, Elisa Rocha)
```

---

# 2 e 3: Qual é o pior caso para 100, 200 e 1000 elementos?

Como visto anteriormente, a árvore aumenta o numero da matricula cada vez que é iterada uma adição de elemento. Sendo assim, o pior caso ao realizar a busca com o método pesquisar é o elemento referente ao valor estar na ultima posição da árvore (ter a maior matricula). Sendo assim, a complexidade é O(n) para essa árvore degenerada em específico.

De certa forma, no pior caso 

| Numero de Alunos | Numero de nós percorridos no pior caso |
| :---- | :-------- |
| 100     | 100       |
| 200     | 200       |
| 1000    | 1000      |

# 4 Topologia da Árvore Gerada pelo Método `geraArvorePerfeitamenteBalanceada`

* O método recebe um intervalo de matrículas (`min` e `max`) e uma árvore vazia.
* Ele calcula a **média entre `min` e `max`** e cria um aluno com a matrícula correspondente.
* O nome é gerado de forma aleatória.
* O aluno com a matrícula média é adicionado à árvore.
* O método é então chamado **recursivamente** para:

  * Inserir os valores **menores que a média** na **subárvore esquerda**.
  * Inserir os valores **maiores que a média** na **subárvore direita**.

Sendo a árvore uma **árvore binária de busca (BST)** que insere nós com base no valor da matrícula:

* O nó com matrícula média torna-se a **raiz**.
* Os valores menores ficam à **esquerda**.
* Os valores maiores ficam à **direita**.
* O processo recursivo garante que a árvore seja **simetricamente preenchida**.

Isso resulta em uma **árvore perfeitamente balanceada**, onde as subárvores esquerda e direita de cada nó têm **aproximadamente o mesmo número de elementos**.

A topologia de uma **árvore perfeitamente balanceada** é **simétrica**, semelhante a isto:

```
          Raiz
         /    \
     SubE      SubD
     /  \      /  \
   ...  ...  ...  ...
```

Cada nó possui **até dois filhos**, e a diferença de altura entre as subárvores é **no máximo 1**.

Se `matriculaBase = 100`, `min = 1` e `max = 7`, o método irá gerar os seguintes alunos:

| Ordem de Inserção | Intervalo (min, max) | Média | Matrícula | Nome (exemplo) |
| :---------------- | :------------------- | :---- | :-------- | :------------- |
| 1                 | (1, 7)               | 4     | 104       | Ana Costa      |
| 2                 | (1, 3)               | 2     | 102       | Bruno Lima     |
| 3                 | (1, 1)               | 1     | 101       | Carla Souza    |
| 4                 | (3, 3)               | 3     | 103       | Diego Torres   |
| 5                 | (5, 7)               | 6     | 106       | Elisa Rocha    |
| 6                 | (5, 5)               | 5     | 105       | Felipe Martins |
| 7                 | (7, 7)               | 7     | 107       | Gabriela Nunes |

### Estrutura da Árvore Gerada

```
          (104, Ana Costa)
         /               \
 (102, Bruno Lima)       (106, Elisa Rocha)
     /       \              /           \
(101, Carla) (103, Diego) (105, Felipe) (107, Gabriela)
```

# 5 e 6: Qual é o pior caso para 100, 200 e 1000 elementos?

Considere uma árvore perfeitamente balanceada com n nós. Nessa árvore a altura (número máximo de arestas da raiz até a folha mais profunda) é aproximadamente ⌊log2 n⌋. O número de nós percorridos em uma busca no pior caso corresponde ao número de níveis visitados da raiz até a folha mais profunda, ou seja, ⌊log2 n⌋ + 1 (que também pode ser escrito como ⌈log2 (n+1)⌉).

Aplicando aos casos pedidos:

| Numero de Alunos | Numero de nós percorridos no pior caso |
| :---- | :-------- |
| 100   | ⌊log2 100⌋ + 1 = 6 + 1 = 7      |
| 200   | ⌊log2 200⌋ + 1 = 7 + 1 = 8      |
| 1000  | ⌊log2 1000⌋ + 1 = 9 + 1 = 10    |

**Como cheguei a essas conclusões?**  
Porque em uma árvore binária perfeitamente balanceada os nós estão distribuídos de forma a preencher níveis de maneira uniforme. O número máximo de nós em uma árvore de altura h é 2^{h+1}-1; invertendo essa relação obtemos que a altura mínima necesaria para n nós é ≈ ⌊log2 n⌋, e o número de níveis (nós visitados numa busca da raiz até a folha) é esse valor mais 1.

---

**Qual a ordem de complexidade de buscas (`pesquisar(T valor)`) em árvores geradas por `geraArvorePerfeitamenteBalanceada`?**

A ordem de complexidade no pior caso é **O(log n)**.

**Explicação:**  
Em uma árvore perfeitamente balanceada a altura cresce proporcionalmente ao logaritmo do número de nós (h = Θ(log n)). Uma operação de busca percorre da raiz até um nó (ou folha) — em termos assintóticos, percorre no máximo um número de níveis proporcional à altura. Portanto, a busca tem complexidade de tempo O(log n), muito melhor que o pior caso da árvore degenerada (O(n)).

# 7. Ordem de complexidade da busca comparado por matricula

Para entender o número de nós percorridos e a ordem de complexidade da busca no pior caso nesse cenário, é importante levar em consideração o seguinte:

### 1. **Árvore binária de busca indexada por matrícula:**

* A árvore foi instanciada com o `ComparadorAlunoPorMatricula`, o que significa que os nós da árvore estão organizados com base na matrícula do aluno.
* Quando realizamos a busca por nome (`busca = arv.pesquisar(new Aluno(0,"Pedro"), compPorNome)`), a árvore continua seguindo a organização por matrícula, **mas a comparação para o percurso da árvore será feita com base no nome**. Isso significa que, mesmo utilizando o `Comparator` por nome, a árvore ainda está fisicamente organizada pela matrícula.

### 2. **Busca por nome utilizando o comparador de nome:**

* **O pior caso para a busca por nome ocorrerá quando a árvore tiver uma estrutura completamente balanceada**. Nesse caso, a busca por nome será semelhante a uma busca em qualquer árvore binária de busca (BST) tradicional, mas com um detalhe: o valor utilizado para a comparação durante o percurso será o nome, **não a matrícula**.
* O **número de nós percorridos no pior caso** será o mesmo número de níveis que seria percorrido em uma busca normal pela **matrícula**. Ou seja, **o número de nós percorridos não é afetado pelo comparador** utilizado para a busca — ele é afetado apenas pela estrutura da árvore, que depende da quantidade de nós inseridos.

### 3. **Analisando o número de nós percorridos:**

* Como a árvore foi criada com o método `geraArvorePerfeitamenteBalanceada` e está organizada pela **matrícula**, a estrutura será uma árvore **perfeitamente balanceada**. A altura de uma árvore balanceada é dada por **O(log n)**, onde `n` é o número de nós.
* No pior caso, a busca percorrerá todos os níveis da árvore da **raiz até a folha mais profunda**. Portanto, o número de nós percorridos será **O(log n)**, onde `n` é o número total de alunos.

### 4. **Ordem de complexidade da busca:**

* A **ordem de complexidade** para a busca continua sendo **O(log n)**, mesmo quando usamos um `Comparator` diferente (como `compPorNome`). O motivo disso é que a estrutura da árvore (como ela está balanceada) e o número de comparações realizadas são ambos **logarítmicos** em relação ao número de nós.
* Embora o comparador de nome possa alterar a ordem de comparação para determinar a posição do nó a ser visitado, o número de nós percorridos e a complexidade de tempo no pior caso não mudam.


### Resposta final:

| Questão                                    | Resposta                                                           |
| ------------------------------------------ | ------------------------------------------------------------------ |
| **Número de nós percorridos no pior caso** | **O(log n)**, onde `n` é o número de elementos na árvore (alunos). |
| **Ordem de complexidade da busca**         | **O(log n)**.                                                      |

**Explicação:**
Mesmo que a árvore seja indexada por matrícula, a busca por nome será realizada pela árvore utilizando a comparação dos nomes, mas **a estrutura da árvore não muda**. Portanto, no pior caso, a busca percorre um número de nós proporcional à altura da árvore, que é **O(log n)** para uma árvore perfeitamente balanceada.

# 8. Complexidade dos métodos geradores de árvore

### Comparação das ordens de complexidade dos métodos

Vamos analisar as ordens de complexidade de ambos os métodos e explicar qual tende a gerar mais rapidamente uma árvore de tamanho `n`.

---

### 1. **Método `geraArvorePerfeitamenteBalanceada`**

**Descrição:**

* Este método cria uma árvore binária **perfeitamente balanceada**. Ele faz isso dividindo recursivamente o intervalo de matrículas entre `min` e `max`, sempre inserindo o valor médio como o nó da árvore.
* A cada recursão, ele chama `geraArvorePerfeitamenteBalanceada` para os intervalos menores e maiores que a média.

**Análise da complexidade:**

* **Divisão do intervalo:** Cada chamada recursiva divide o intervalo pela metade, o que implica que a árvore será balanceada.
* **Altura da árvore:** Como a árvore é balanceada, a altura será aproximadamente **O(log n)**, já que a árvore irá se dividir em duas partes a cada nível.
* **Número de chamadas:** O número de chamadas recursivas será proporcional à altura da árvore, ou seja, **O(log n)**.
* **Custo da inserção:** Cada inserção de nó na árvore também tem custo **O(log n)**, porque a árvore está balanceada.

**Complexidade total:**
Para cada nó, há uma operação de inserção que leva **O(log n)**. Como há `n` nós, a complexidade total é:

[
\text{Complexidade de } \text{geraArvorePerfeitamenteBalanceada} = O(n \log n)
]

---

### 2. **Método `geraArvoreDegenerada`**

**Descrição:**

* Este método cria uma árvore **degenerada**, onde os nós são inseridos em ordem crescente de matrícula.
* A árvore gerada não é balanceada e cada nó é inserido sempre à direita do anterior, criando uma estrutura semelhante a uma lista encadeada.

**Análise da complexidade:**

* **Inserção em árvore degenerada:** Como a árvore está degenerada, a inserção de cada nó exige percorrer a árvore até a posição correta, o que leva **O(n)** para o último nó inserido (a árvore é essencialmente uma lista).
* **Número de inserções:** Existem `n` inserções a serem feitas.

**Complexidade total:**
Cada inserção leva **O(n)**, e há `n` inserções. Portanto, a complexidade total é:

[
\text{Complexidade de } \text{geraArvoreDegenerada} = O(n^2)
]

---

### **Conclusão: Qual método gera a árvore mais rapidamente?**

* **`geraArvorePerfeitamenteBalanceada`:** O método tem complexidade **O(n log n)**, o que significa que ele cresce de forma mais eficiente conforme o número de elementos aumenta.
* **`geraArvoreDegenerada`:** O método tem complexidade **O(n^2)**, o que significa que ele é menos eficiente à medida que o número de elementos cresce.

**Portanto, o método `geraArvorePerfeitamenteBalanceada` tende a gerar árvores mais rapidamente, especialmente quando o número de elementos `n` é grande**, já que sua complexidade é mais baixa em comparação com a árvore degenerada, cuja complexidade é quadrática.

---

### Resumo das Complexidades:

| Método                              | Complexidade   |
| ----------------------------------- | -------------- |
| `geraArvorePerfeitamenteBalanceada` | **O(n log n)** |
| `geraArvoreDegenerada`              | **O(n²)**      |

Com base nisso, **o método `geraArvorePerfeitamenteBalanceada` é mais eficiente** e tende a gerar a árvore de forma mais rápida à medida que o número de elementos `n` aumenta.

# 9. Por que ocorre o Stack OverFlow

Quando a árvore é **degenerada** (em que todos os nós são inseridos de forma linear, cada novo nó é inserido à direita do nó anterior), a árvore acaba se tornando uma **lista encadeada**. A inserção, portanto, fica limitada a uma linha reta de nós, e cada nova inserção exige a busca pela posição correta, que leva até o final da árvore.

**Especificamente, o erro ocorre porque:**

* O método `adicionarRecursivo` provavelmente não está lidando corretamente com árvores de grande altura, como no caso de 50.000 elementos, gerando um **loop infinito** ou **excesso de chamadas recursivas**.
* Em árvores degeneradas, o método recursivo acaba se chamando indefinidamente, pois sempre tenta inserir o próximo nó à direita do anterior, causando um **estouro da pilha de chamadas recursivas** (o famoso `StackOverflowError`).

### **Momento do erro:**

O erro ocorre durante a execução do segundo bloco de código:

```java
arv = new BinTree<>(compPorMatricula);
gerador.geraArvoreDegenerada(50000, arv);
```

* **`geraArvoreDegenerada`** tenta inserir 50.000 elementos de maneira recursiva, mas como a árvore degenerada é essencialmente uma lista encadeada, a recursão não encontra a condição de parada de maneira eficiente, pois o método de inserção recursivo percorre a árvore até o final a cada vez que um novo nó é adicionado.

### **Solução:**

Para evitar esse erro, existem duas abordagens possíveis:

1. **Transformar a inserção recursiva em iterativa:** Isso evitaria o risco de estouro de pilha, pois a inserção seria feita sem recorrer à recursão. A ideia é inserir os elementos de forma iterativa, percorrendo a árvore até o ponto correto sem usar chamadas recursivas.

2. **Limitar a profundidade da recursão:** Se você deseja manter a recursão, uma abordagem seria ajustar o limite máximo de recursões ou alterar a implementação para impedir que a profundidade da árvore cresça demais sem controle. Em alguns casos, pode-se aplicar **recursão otimizada** (por exemplo, utilizando a técnica de **tail recursion**), mas no caso de árvores degeneradas, a recursão não é a abordagem ideal devido à falta de balanceamento.

### **Conclusão:**

O **`StackOverflowError`** ocorre devido à **profunda recursão** criada pela estrutura da árvore degenerada, onde a recursão não encontra um ponto de interrupção eficiente, resultando em um número excessivo de chamadas. A solução seria usar uma abordagem **iterativa** ou otimizar o código recursivo para evitar esse erro.

# 10, 11 e 12. Contact Manager:

### 1. **Requisitos Funcionais e Funcionamento do Aplicativo**

O aplicativo desenvolvido permite o gerenciamento de contatos, com as seguintes funcionalidades principais:

* **Adicionar Contato**: O usuário pode adicionar um novo contato, informando o nome, telefone e e-mail.
* **Listar Contatos**: O aplicativo exibe os contatos armazenados na árvore em ordem crescente de nome (utilizando a travessia em ordem da árvore).
* **Pesquisar por Nome**: O usuário pode pesquisar um contato pelo nome, e o aplicativo retorna o contato correspondente, se encontrado.
* **Pesquisar por Telefone**: O aplicativo também permite buscar um contato através do número de telefone, utilizando um comparador específico para isso.
* **Remover Contato**: O usuário pode remover um contato, informando o nome do contato a ser removido.

### 2. **Arquitetura das Classes e Organização**

O aplicativo segue uma arquitetura baseada em uma **árvore binária de busca (BST)**, onde os contatos são armazenados e manipulados de acordo com o critério de comparação definido por **Comparadores** (por nome ou telefone).

* **Classe `Contact`**: Representa um contato com atributos de nome, telefone e e-mail. Ela possui métodos para acessar essas informações, um método `equals()` para comparar contatos por nome e um método `toString()` para exibir os dados do contato.
* **Classe `ContactNameComparator`**: Implementa a interface `Comparator<Contact>` e é usada para comparar contatos pelo nome, o que é útil para organizar os contatos na árvore e realizar buscas.
* **Classe `ContactPhoneComparator`**: Implementa a interface `Comparator<Contact>` e é usada para comparar contatos pelo telefone, permitindo a pesquisa dos contatos pelo número de telefone.
* **Classe `Main`**: Contém a lógica de interação com o usuário por meio de um menu. A classe gerencia as opções de adicionar, listar, pesquisar e remover contatos, utilizando a árvore binária (`BinTree<Contact>`), que é inicialmente organizada por nome e permite a pesquisa e remoção de contatos.

### 3. **Planilha de Desenvolvimento**

Aqui está um exemplo de como a planilha poderia ser organizada:

| **Componente**                      | **Responsável**  |                                                                                                                                           |
| ----------------------------------- | ---------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Desenvolvimento da Biblioteca            | Heitor Oliveira 
| esenvolvimento do Aplicativo de Contatos           | Caio Coronel
| Relatório e Perguntas          | Caio e Heitor

# 13. Seção do Relatório: Implementações Baseadas em Árvores Binárias na Biblioteca Padrão de Java

### 1. Introdução

A Biblioteca Padrão de Java, através do seu robusto *Java Collections Framework*, oferece um conjunto de estruturas de dados de alto desempenho para o uso cotidiano do desenvolvedor. Embora não exponha diretamente uma classe genérica como `BinarySearchTree`, a biblioteca utiliza implementações altamente otimizadas e especializadas de árvores binárias para fornecer as funcionalidades de algumas de suas classes mais importantes.

O objetivo desta seção é investigar e apresentar as estruturas de dados da biblioteca padrão do Java que são fundamentadas em árvores binárias. Analisaremos a estrutura subjacente, o propósito de sua utilização e a complexidade computacional (em notação Big O) para as operações fundamentais de inserção, busca e remoção. As principais classes abordadas serão `TreeMap`, `TreeSet` e `PriorityQueue`.

### 2. `TreeMap` e `TreeSet`: O Poder das Árvores Rubro-Negras

As classes `TreeMap` e `TreeSet` são as implementações mais diretas de uma árvore de busca binária no Java Collections Framework. Ambas garantem que os elementos sejam mantidos em uma ordem definida e oferecem desempenho logarítmico para as operações básicas.

##### 2.1. A Estrutura Subjacente: Árvore Rubro-Negra (Red-Black Tree)

Tanto `TreeMap` quanto `TreeSet` são implementados utilizando uma **Árvore Rubro-Negra**. Esta é uma variação de uma árvore de busca binária autobalanceada, projetada para garantir que a árvore permaneça "razoavelmente" balanceada durante inserções e remoções.

Uma árvore de busca binária (BST) simples pode, no pior caso, degenerar em uma estrutura semelhante a uma lista encadeada, levando a uma complexidade de `O(n)` para as operações. Para evitar isso, as árvores autobalanceadas, como a Rubro-Negra, realizam operações de rebalanceamento (rotações e recolorações) após modificações.

As principais garantias de uma Árvore Rubro-Negra são:
1. Cada nó é vermelho ou preto.
2. A raiz é sempre preta.
3. Nenhum nó vermelho pode ter um filho vermelho.
4. Todo caminho da raiz até uma folha (nó `null`) contém o mesmo número de nós pretos.

Essas regras garantem que o caminho mais longo da raiz até qualquer folha não seja mais do que o dobro do caminho mais curto. Isso, por sua vez, assegura que a altura da árvore seja sempre proporcional a **`O(log n)`**, onde *n* é o número de nós. Manter essa altura logarítmica é a chave para o desempenho eficiente da estrutura.

##### 2.2. `TreeMap<K, V>`

O `TreeMap` é uma implementação da interface `Map` que armazena pares de chave-valor (`K`, `V`). Sua principal característica é manter as chaves em ordem crescente. A ordenação pode ser a "ordem natural" dos elementos (se a classe da chave implementar a interface `Comparable`) ou uma ordem customizada, definida por um `Comparator` passado no construtor do mapa.

*   **Inserção (`put(K key, V value)`):** A inserção envolve encontrar a posição correta para a nova chave na árvore, o que leva `O(log n)`. Após a inserção do novo nó, podem ser necessárias algumas operações de rebalanceamento (rotações e/ou recolorações) para manter as propriedades da árvore rubro-negra. Essas operações também têm um custo amortizado de `O(log n)`.
    *   **Complexidade: `O(log n)`**

*   **Busca (`get(Object key)`, `containsKey(Object key)`):** A busca em uma árvore rubro-negra segue o algoritmo padrão de uma árvore de busca binária. Como a altura da árvore é garantidamente `O(log n)`, a busca também é.
    *   **Complexidade: `O(log n)`**

*   **Remoção (`remove(Object key)`):** Similar à inserção, a remoção primeiro localiza o nó a ser removido (`O(log n)`). Após a remoção, a árvore pode violar as regras da árvore rubro-negra, exigindo operações de rebalanceamento para restaurar o equilíbrio. O custo dessas operações também é `O(log n)`.
    *   **Complexidade: `O(log n)`**

##### 2.3. `TreeSet<E>`

O `TreeSet` é uma implementação da interface `Set` que armazena elementos únicos e ordenados. Internamente, um `TreeSet` utiliza um `TreeMap<E, Object>`, onde os elementos do conjunto são as chaves do mapa, e o valor associado é um objeto constante e irrelevante. Devido a essa implementação, suas características de desempenho são idênticas às do `TreeMap`.

*   **Inserção (`add(E element)`):** Corresponde a uma chamada `map.put(element, DUMMY_VALUE)`.
    *   **Complexidade: `O(log n)`**

*   **Busca (`contains(Object element)`):** Corresponde a uma chamada `map.containsKey(element)`.
    *   **Complexidade: `O(log n)`**

*   **Remoção (`remove(Object element)`):** Corresponde a uma chamada `map.remove(element)`.
    *   **Complexidade: `O(log n)`**

### 3. `PriorityQueue`: Uma Aplicação Especializada com Heaps Binários

Embora não seja uma árvore de busca, a `PriorityQueue` é outra estrutura de dados fundamental da biblioteca Java que é implementada usando uma forma especializada de árvore binária: o **Heap Binário**.

##### 3.1. A Estrutura Subjacente: Heap Binário (Binary Heap)

Um Heap Binário é uma árvore binária "completa" que satisfaz a "propriedade do heap":
*   **Min-Heap (implementação padrão em Java):** O valor de qualquer nó é sempre menor ou igual aos valores de seus filhos. Isso garante que o menor elemento da coleção esteja sempre na raiz da árvore.
*   **Max-Heap:** O valor de qualquer nó é maior ou igual ao de seus filhos.

Embora conceitualmente seja uma árvore, um heap binário é eficientemente implementado usando um array, economizando a memória extra com ponteiros.

##### 3.2. `PriorityQueue<E>`

A `PriorityQueue` é uma fila que ordena os elementos com base em sua prioridade, removendo sempre o elemento de "menor" valor.

*   **Inserção (`add(E e)`, `offer(E e)`):** Um novo elemento é adicionado ao final do heap e "sobe" na árvore (`siftUp`) até restaurar a propriedade do heap.
    *   **Complexidade: `O(log n)`**

*   **Remoção do elemento de maior prioridade (`poll()`, `remove()`):** O elemento na raiz é removido, o último elemento do heap o substitui e então "desce" na árvore (`siftDown`) até restaurar a ordem.
    *   **Complexidade: `O(log n)`**

*   **Busca (`contains(Object o)`) e Remoção de um elemento arbitrário (`remove(Object o)`):** Um heap **não** é otimizado para buscas arbitrárias, exigindo uma varredura linear.
    *   **Complexidade: `O(n)`**

### 4. Tabela Comparativa e Conclusão

| Estrutura | Estrutura Subjacente | Característica Principal | Inserção | Busca (Específica) | Remoção (Específica) | Obter Min/Max |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **`TreeMap`** | Árvore Rubro-Negra | Pares chave-valor ordenados pela chave. | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| **`TreeSet`** | Árvore Rubro-Negra | Elementos únicos e ordenados. | `O(log n)` | `O(log n)` | `O(log n)` | `O(log n)` |
| **`PriorityQueue`** | Heap Binário (Min-Heap) | Fila de prioridade; acesso rápido ao menor elemento. | `O(log n)` | **`O(n)`** | **`O(n)`** | **`O(1)`** (peek) |

**Conclusão:**

A biblioteca padrão do Java faz um uso inteligente de estruturas baseadas em árvores binárias. Para cenários que exigem uma coleção totalmente ordenada com operações rápidas sobre *qualquer* elemento, `TreeMap` e `TreeSet` (baseados em Árvores Rubro-Negras) são a escolha ideal. Por outro lado, quando o requisito é apenas obter e remover eficientemente o elemento de maior prioridade, a `PriorityQueue` (baseada em Heap Binário) é a ferramenta mais adequada e performática.

### Link para o repositório: https://github.com/heitoliv/BinTree 