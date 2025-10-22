package app;

import lib.BinTree;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BinTree<Contact> arvore = new BinTree<>(new ContactNameComparator());
        ContactPhoneComparator phoneComparator = new ContactPhoneComparator();

        int opcao;

        do {
            System.out.println("\n=== MENU DE CONTATOS ===");
            System.out.println("1 - Adicionar contato");
            System.out.println("2 - Listar contatos (em ordem)");
            System.out.println("3 - Pesquisar por nome");
            System.out.println("4 - Pesquisar por telefone");
            System.out.println("5 - Remover contato");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    arvore.adicionar(new Contact(nome, telefone, email));
                    System.out.println("Contato adicionado com sucesso!");
                }

                case 2 -> {
                    System.out.println("\n=== LISTA DE CONTATOS (IN-ORDER) ===");
                    System.out.println(arvore.caminharEmOrdem());
                }

                case 3 -> {
                    System.out.print("Digite o nome do contato: ");
                    String nomeBusca = sc.nextLine();
                    Contact resultado = arvore.pesquisar(new Contact(nomeBusca, "", ""));
                    if (resultado != null)
                        System.out.println("Encontrado: " + resultado);
                    else
                        System.out.println("Contato não encontrado.");
                }

                case 4 -> {
                    System.out.print("Digite o telefone do contato: ");
                    String telefoneBusca = sc.nextLine();
                    Contact resultado = arvore.pesquisar(new Contact("", telefoneBusca, ""), phoneComparator);
                    if (resultado != null)
                        System.out.println("Encontrado: " + resultado);
                    else
                        System.out.println("Contato não encontrado.");
                }

                case 5 -> {
                    System.out.print("Digite o nome do contato a remover: ");
                    String nomeRemover = sc.nextLine();
                    Contact removido = arvore.remover(new Contact(nomeRemover, "", ""));
                    if (removido != null)
                        System.out.println("Removido: " + removido);
                    else
                        System.out.println("Contato não encontrado para remoção.");
                }

                case 0 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}
