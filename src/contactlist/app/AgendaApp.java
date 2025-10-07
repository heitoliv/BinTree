package contactlist.app;
import java.util.Scanner;

import lib.BinTree;


public class AgendaApp {
    private static BinTree<Contato> arvore = new BinTree<>(null);
    public static void main(){

    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n===== AGENDA DE CONTATOS =====");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Pesquisar contato (chave padrão - nome completo)");
            System.out.println("3 - Pesquisar contato (usando Comparator por nome)");
            System.out.println("4 - Remover contato");
            System.out.println("5 - Listar todos (em ordem alfabética)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // isso limpa o buffer. JAMAIS REMOVER ESSA LINHA SE N VAI DAR BIGODE

            switch (opcao) {
                case 1 -> adicionarContato(sc); // TODO
                case 2 -> pesquisarContato(sc);// TODO
                case 3 -> pesquisarContatoPorNome(sc);// TODO
                case 4 -> removerContato(sc);// TODO
                case 5 -> listarContatos();// TODO
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
        sc.close();
    }
}