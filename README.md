
# BinTree — Relatório Completo e README

Este documento consolida, em um único arquivo **.md**, toda a documentação técnica e o relatório de análise do projeto de árvores binárias: **árvore degenerada**, **árvore perfeitamente balanceada**, **comparação de complexidades**, **AVL vs BST**, e um panorama das **estruturas baseadas em árvores na biblioteca padrão do Java**. 

> Repositório: https://github.com/heitoliv/BinTree

---

## Sumário
1. [Topologia da Árvore Gerada pelo Método `geraArvoreDegenerada`](#1-topologia-da-árvore-gerada-pelo-método-geraarvoredegenerada)
2. [Pior caso para 100, 200 e 1000 elementos (árvore degenerada)](#2-e-3-qual-é-o-pior-caso-para-100-200-e-1000-elementos)
3. [Topologia da Árvore Gerada pelo Método `geraArvorePerfeitamenteBalanceada`](#4-topologia-da-árvore-gerada-pelo-método-geraarvoreperfeitamentebalanceada)
4. [Pior caso para 100, 200 e 1000 elementos (árvore balanceada)](#5-e-6-qual-é-o-pior-caso-para-100-200-e-1000-elementos)
5. [Ordem de complexidade da busca em árvores perfeitamente balanceadas](#qual-a-ordem-de-complexidade-de-buscas-pesquisart-valor-em-árvores-geradas-por-geraarvoreperfeitamentebalanceada)
6. [Busca usando comparador por nome em árvore indexada por matrícula](#7-ordem-de-complexidade-da-busca-comparado-por-matricula)
7. [Complexidade dos métodos geradores de árvore](#8-complexidade-dos-métodos-geradores-de-árvore)
8. [Por que ocorre `StackOverflowError` em árvores degeneradas e como mitigar](#9-por-que-ocorre-o-stack-overflow)
9. [Relatório (Etapa 5): BinTree vs. BinTreeAVL](#etapa-5-relatório-de-análise-bintree-vs-bintreeavl)
10. [Complexidade de buscas em AVL gerada com dados degenerados](#4-análise-ordem-de-complexidade-da-busca-na-avl)
11. [Etapa 6: Estruturas baseadas em árvores no Java (TreeMap, TreeSet, PriorityQueue)](#etapa-6-implementações-baseadas-em-árvores-binárias-na-biblioteca-padrão-de-java)
12. [Comparativo: BinTreeAVL vs. TreeMap/TreeSet](#5-compartivo-entre-as-bibliotecas-padrão-java-vs-avl)
13. [Exemplo prático em Java (TreeMap + Comparator + busca por atributo não-chave)](#exemplo-prático-de-utilização-treemap)
14. [Planilha/Créditos de desenvolvimento](#planilha-de-desenvolvimento)

---

## 1. Topologia da Árvore Gerada pelo Método `geraArvoreDegenerada`

- O método cria `n` objetos do tipo `Aluno`.
- A cada iteração, a matrícula é incrementada (`matricula++`).
- O nome é gerado de forma aleatória.
- Cada novo aluno é adicionado à árvore **em ordem crescente de matrícula**.

Sendo a estrutura uma **BST (Binary Search Tree)**, com inserção baseada na matrícula:

- O primeiro aluno vira a **raiz**.
- Cada novo aluno possui **matrícula maior** que o anterior.
- Assim, todos os novos nós são inseridos **à direita** do nó anterior.

**Resultado:** uma **árvore degenerada**, que se comporta como uma **lista encadeada**.

Topologia (linear):

```
Raiz
 └── Nó
      └── Nó
           └── Nó
                └── ...
```

Cada nó possui **apenas um filho direito** (ou esquerdo, conforme a ordem de inserção).

**Exemplo** (com `matriculaBase = 100` e `n = 5`):

| Ordem | Matrícula | Nome (exemplo) |
| :---- | :-------- | :------------- |
| 1     | 101       | Ana Costa      |
| 2     | 102       | Bruno Lima     |
| 3     | 103       | Carla Souza    |
| 4     | 104       | Diego Torres   |
| 5     | 105       | Elisa Rocha    |

Árvore resultante:

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

## 2 e 3: Qual é o pior caso para 100, 200 e 1000 elementos?

Como a inserção é estritamente crescente, a árvore degenere e a operação de busca `pesquisar` tem pior caso **O(n)** (alvo na última posição/maior matrícula).

| Número de Alunos | Nº de nós percorridos no pior caso |
| :---- | :-------- |
| 100   | 100       |
| 200   | 200       |
| 1000  | 1000      |

---

## 4. Topologia da Árvore Gerada pelo Método `geraArvorePerfeitamenteBalanceada`

- Recebe intervalo de matrículas (`min`, `max`) e uma árvore vazia.
- Calcula a **média entre `min` e `max`** e cria um aluno com a matrícula correspondente.
- Nome aleatório.
- Insere a matrícula média como **raiz** e aplica recursão para:
  - valores menores → **subárvore esquerda**;
  - valores maiores → **subárvore direita**.

**Resultado:** árvore **perfeitamente balanceada**, com preenchimento simétrico.

Esquema:

```
          Raiz
         /    \
     SubE      SubD
     /  \      /  \
   ...  ...  ...  ...
```

Exemplo com `matriculaBase = 100`, `min = 1` e `max = 7`:

| Ordem de Inserção | Intervalo (min, max) | Média | Matrícula | Nome (exemplo) |
| :---------------- | :------------------- | :---- | :-------- | :------------- |
| 1                 | (1, 7)               | 4     | 104       | Ana Costa      |
| 2                 | (1, 3)               | 2     | 102       | Bruno Lima     |
| 3                 | (1, 1)               | 1     | 101       | Carla Souza    |
| 4                 | (3, 3)               | 3     | 103       | Diego Torres   |
| 5                 | (5, 7)               | 6     | 106       | Elisa Rocha    |
| 6                 | (5, 5)               | 5     | 105       | Felipe Martins |
| 7                 | (7, 7)               | 7     | 107       | Gabriela Nunes |

Estrutura:

```
          (104, Ana Costa)
         /               \
 (102, Bruno Lima)       (106, Elisa Rocha)
     /       \              /           \
(101, Carla) (103, Diego) (105, Felipe) (107, Gabriela)
```

---

## 5 e 6: Qual é o pior caso para 100, 200 e 1000 elementos?

Para uma árvore perfeitamente balanceada com `n` nós, a altura ≈ `⌊log2 n⌋`. Assim, o número de nós visitados no pior caso de busca (da raiz até a folha mais profunda) é `⌊log2 n⌋ + 1` (ou `⌈log2 (n+1)⌉`).

| Número de Alunos | Nós percorridos (pior caso) |
| :---- | :-------- |
| 100   | ⌊log₂ 100⌋ + 1 = 6 + 1 = **7** |
| 200   | ⌊log₂ 200⌋ + 1 = 7 + 1 = **8** |
| 1000  | ⌊log₂ 1000⌋ + 1 = 9 + 1 = **10** |

**Conclusão:** busca com complexidade **O(log n)**.

---

### Qual a ordem de complexidade de buscas (`pesquisar(T valor)`) em árvores geradas por `geraArvorePerfeitamenteBalanceada`?

**O(log n)** — a altura cresce logaritmicamente em relação a `n`; a busca percorre no máximo a altura.

---

## 7. Ordem de complexidade da busca comparado por matricula

Cenário: a árvore está **indexada por matrícula** (comparador por matrícula), mas a busca é realizada passando um **`Comparator` por nome**.

- A estrutura física da árvore **permanece ordenada pela matrícula**.
- O número de nós percorridos **depende da altura da árvore**, não do campo usado pelo `Comparator` passado à busca.
- Em uma árvore perfeitamente balanceada, percorre-se **O(log n)** níveis.

> **Observação importante:** para que a busca binária seja **correta**, o **comparador usado para navegar** precisa ser **consistente** com o que ordenou a árvore. Caso contrário, a busca binária pode falhar e, para garantir correção, deve-se recorrer a **varredura linear (O(n))**. Se sua implementação garante consistência entre comparadores, mantém-se **O(log n)**.

**Resumo:**

| Questão | Resposta |
| --- | --- |
| Nº de nós percorridos (pior caso) | **O(log n)** (em árvore balanceada e comparadores consistentes) |
| Ordem de complexidade da busca | **O(log n)** |

---

## 8. Complexidade dos métodos geradores de árvore

### `geraArvorePerfeitamenteBalanceada`
- Cria árvore balanceada por divisão recursiva do intervalo e inserção da mediana.
- Cada inserção custa **O(log n)**; com `n` nós, custo total **O(n log n)**.

### `geraArvoreDegenerada`
- Insere chaves em ordem crescente, degenerando a BST.
- A k-ésima inserção custa **O(k)** → somatório 1..n ⇒ **O(n²)**.

**Conclusão:** gerar árvore balanceada é assintoticamente **mais eficiente** que gerar uma degenerada.

---

## 9. Por que ocorre o Stack OverFlow

Em árvores **degeneradas**, a inserção recursiva percorre uma cadeia longa de nós (altura ≈ `n`). Para `n` muito grandes (ex.: 50.000), a profundidade recursiva pode exceder o limite da pilha, resultando em **`StackOverflowError`**.

**Mitigações:**
1. **Inserção iterativa** (substitui chamadas recursivas por laços).
2. **Balanceamento** (usar AVL/Red-Black) para limitar a altura a `O(log n)`.
3. **Tail recursion** e/ou **ajuste de stack** (quando possível) — ainda assim não resolve a degeneração estrutural.

---

## Etapa 5: Relatório de Análise (BinTree vs. BinTreeAVL)

### 1. Dados de Saída do `AppRelatorioAVL`

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

### 2. Método de Geração Utilizado

Ambas as árvores receberam entradas via `geraArvoreDegenerada` (matrículas estritamente crescentes, como 101, 102, 103, ...):

```java
public void geraArvoreDegenerada(int n, BinTreeInterface<Aluno> arv){
    int i, matricula = matriculaBase;
    String nome;
    for (i = 1; i <= n; i++){
        matricula++;
        nome = geraNomeCompleto();
        // Cria o aluno e adiciona na árvore
        arv.adicionar(new Aluno(matricula, nome));
    }
}
```

### 3. Análise: Comparação das Alturas das Árvores

- **`BinTree` (BST padrão, sem balanceamento):** com dados ordenados, degenere e sua altura é **O(n)** (ex.: 9999 para `n=10000`).
- **`BinTreeAVL` (BST auto-balanceada):** aplica rotações a cada inserção para manter altura **O(log n)** (ex.: 13 para `n=10000`).

| Tipo de Árvore | Entrada | N=100 | N=1000 | N=10000 | Complexidade da altura |
| :--- | :--- | :---: | :---: | :---: | :---: |
| `BinTree` (BST) | Ordenada | 99 | 999 | 9999 | **O(n)** |
| `BinTreeAVL` | Ordenada | 6 | 9 | 13 | **O(log n)** |

**Conclusão:** a diferença decorre do **balanceamento** da AVL, não do método de geração.

---

## 4. Análise: Ordem de Complexidade da Busca na AVL

Em qualquer BST, a busca custa **O(altura)**. Como a AVL mantém altura **O(log n)**, a busca permanece **O(log n)**, mesmo com entrada degenerada.

---

## Etapa 6: Implementações Baseadas em Árvores Binárias na Biblioteca Padrão de Java

### 1. `TreeMap` e `TreeSet` — Árvores Rubro-Negras

- **Estrutura:** Red-Black Tree (autobalanceada)
- **Operações principais:** inserção, busca e remoção em **O(log n)**
- **Ordenação:** ordem natural da chave ou `Comparator` customizado

#### `TreeMap<K,V>`
- Pares (chave, valor); chaves em ordem.
- `put`, `get`, `remove`, `containsKey` → **O(log n)**.

#### `TreeSet<E>`
- Conjunto ordenado; internamente usa `TreeMap<E,Object>`.
- `add`, `contains`, `remove` → **O(log n)**.

### 2. `PriorityQueue<E>` — Heap Binário (Min-Heap)

- **Estrutura:** heap binário completo (geralmente implementado como array).
- `offer/add` e remoção da raiz (`poll/remove`) → **O(log n)**.
- Busca arbitrária/remoção de elemento específico → **O(n)**.
- `peek` → **O(1)** para obter o mínimo.

---

## 5. Comparativo entre as bibliotecas (Padrão Java vs AVL)

### 1) Estrutura e balanceamento
- **Sua `BinTreeAVL`:** AVL (balanceamento estrito). Busca levemente mais rápida; inserções/remoções exigem mais rotações.
- **`TreeMap`/`TreeSet`:** Red-Black (balanceamento relaxado). Menos rotações em média; API rica.

### 2) Cobertura de métodos
- **`BinTreeAVL`:** foco em inserir/buscar/remover e balancear.
- **Java Collections:** além do básico, fornece navegação (`first/last/lower/higher/floor/ceiling`), *views* (`subSet/headSet/tailSet`), iteradores ordenados e integração com `SortedMap/SortedSet/Navigable*`.

### 3) Busca por atributo não-chave
- Em estruturas ordenadas por um atributo, buscar eficientemente por **outro** atributo exige **outro índice**. Caso contrário, a busca é linear **O(n)**.

---

## Exemplo Prático de Utilização (TreeMap)

### 1. Classe `Produto`

```java
// Produto.java
public class Produto implements Comparable<Produto> {
    private int id;
    private String nome;
    private double preco;

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }

    // Ordem natural por ID
    @Override
    public int compareTo(Produto outro) {
        return Integer.compare(this.id, outro.id);
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome='" + nome + '\'' + ", preco=" + preco + '}';
    }
}
```

### 2. Classe Principal com Exemplos

```java
// Main.java
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        // TreeMap ordenado por ID (chave Integer)
        Map<Integer, Produto> produtosPorId = new TreeMap<>();
        produtosPorId.put(102, new Produto(102, "Mouse Gamer", 150.00));
        produtosPorId.put(35,  new Produto(35,  "Teclado Mecânico", 350.50));
        produtosPorId.put(1,   new Produto(1,   "Monitor 4K", 2200.00));

        System.out.println("--- Produtos ordenados por ID (chave natural) ---");
        for (Map.Entry<Integer, Produto> entry : produtosPorId.entrySet()) {
            System.out.println("Chave: " + entry.getKey() + ", Valor: " + entry.getValue());
        }
        System.out.println();

        // TreeMap alternativo ordenado por Nome (chave String)
        Map<String, Produto> produtosPorNome = new TreeMap<>();
        produtosPorNome.put("Mouse Gamer",      new Produto(102, "Mouse Gamer", 150.00));
        produtosPorNome.put("Teclado Mecânico", new Produto(35,  "Teclado Mecânico", 350.50));
        produtosPorNome.put("Monitor 4K",       new Produto(1,   "Monitor 4K", 2200.00));

        System.out.println("--- Produtos ordenados por Nome (chave natural da String) ---");
        produtosPorNome.forEach((chave, valor) -> System.out.println("Chave: " + chave + ", Valor: " + valor));
        System.out.println();

        // Busca por atributo não-chave (preço) -> varredura O(n)
        System.out.println("--- Buscando produto com preço 150.00 (Busca Linear O(n)) ---");
        double precoBuscado = 150.00;
        Optional<Produto> produtoEncontrado = produtosPorId.values().stream()
                .filter(produto -> produto.getPreco() == precoBuscado)
                .findFirst();

        if (produtoEncontrado.isPresent()) {
            System.out.println("Produto encontrado: " + produtoEncontrado.get());
        } else {
            System.out.println("Nenhum produto encontrado com o preço " + precoBuscado);
        }
    }
}
```

### 3. Saída Esperada

```
--- Produtos ordenados por ID (chave natural) ---
Chave: 1, Valor: Produto{id=1, nome='Monitor 4K', preco=2200.0}
Chave: 35, Valor: Produto{id=35, nome='Teclado Mecânico', preco=350.5}
Chave: 102, Valor: Produto{id=102, nome='Mouse Gamer', preco=150.0}

--- Produtos ordenados por Nome (chave natural da String) ---
Chave: Monitor 4K, Valor: Produto{id=1, nome='Monitor 4K', preco=2200.0}
Chave: Mouse Gamer, Valor: Produto{id=102, nome='Mouse Gamer', preco=150.0}
Chave: Teclado Mecânico, Valor: Produto{id=35, nome='Teclado Mecânico', preco=350.5}

--- Buscando produto com preço 150.00 (Busca Linear O(n)) ---
Produto encontrado: Produto{id=102, nome='Mouse Gamer', preco=150.0}
```

---

## Planilha de Desenvolvimento

| Componente | Responsável |
| --- | --- |
| Desenvolvimento da biblioteca (árvores) | Heitor Oliveira |
| Desenvolvimento do aplicativo de contatos | Caio Coronel |
| Relatório e perguntas | Caio Coronel e Heitor Oliveira |

---

## Observações Finais
- **BST degenerada vs balanceada:** `O(n)` vs `O(log n)` em altura e buscas.
- **AVL** evita degeneração via rotações, mantendo garantias assintóticas.
- **TreeMap/TreeSet** (Red-Black) oferecem API rica com custo logarítmico.
- **Múltiplos índices** podem ser necessários para buscas eficientes por atributos distintos.

> Para dúvidas ou melhorias, abra uma *issue* no repositório.
