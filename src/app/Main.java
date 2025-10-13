package app;

import lib.BinTree;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        // Cria árvore binária de contatos ordenada por nome
        BinTree<Contact> contatos = new BinTree<>(new ContactNameComparator());

        // === 1️⃣ ADICIONAR CONTATOS ===
        System.out.println("=== ADICIONANDO CONTATOS ===");
        contatos.add(new Contact("Caio Cesar", "9999-1111", "caio@email.com"));
        contatos.add(new Contact("Heitor", "9888-2222", "heitor@email.com"));
        contatos.add(new Contact("Bruno", "9777-3333", "bruno@email.com"));
        contatos.add(new Contact("Mariano", "9666-4444", "mariano@email.com"));
        System.out.println("Contatos adicionados com sucesso!\n");

        // === 2️⃣ LISTAR (em ordem) ===
        System.out.println("=== LISTA DE CONTATOS (IN-ORDER) ===");
        System.out.println(contatos.inOrderToString());
        System.out.println();

        // === 3️⃣ PESQUISAR POR NOME (usando o comparador padrão) ===
        System.out.println("=== PESQUISA POR NOME ===");
        Contact resultadoNome = contatos.search(new Contact("Bruno", "", ""), new ContactNameComparator());
        System.out.println(resultadoNome != null ? "Encontrado: " + resultadoNome : "Contato não encontrado.");
        System.out.println();

        // === 4️⃣ PESQUISAR POR TELEFONE (usando comparador alternativo) ===
        System.out.println("=== PESQUISA POR TELEFONE ===");
        Comparator<Contact> compTelefone = new ContactNameComparator();
        Contact resultadoTelefone = contatos.search(new Contact("", "9666-4444", ""), compTelefone);
        System.out.println(resultadoTelefone != null ? "Encontrado: " + resultadoTelefone : "Contato não encontrado.");
        System.out.println();

        // === 5️⃣ REMOVER CONTATO ===
        System.out.println("=== REMOVENDO CONTATO 'Carlos' ===");
        Contact removido = contatos.remove(new Contact("Carlos", "", ""));
        System.out.println(removido != null ? "Removido: " + removido : "Contato não encontrado para remoção.");
        System.out.println();

        // === 6️⃣ LISTAR NOVAMENTE (após remoção) ===
        System.out.println("=== LISTA DE CONTATOS (ATUALIZADA) ===");
        System.out.println(contatos.inOrderToString());
        System.out.println();

        // === 7️⃣ MOSTRAR CAMINHAMENTO EM NÍVEL ===
        System.out.println("=== CAMINHAMENTO EM NÍVEL ===");
        System.out.println(contatos.caminharEmNivel());
    }
}