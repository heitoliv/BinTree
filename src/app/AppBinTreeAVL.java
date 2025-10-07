/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import lib.BinTree;
import lib.BinTreeAVL;
import lib.BinTreeInterface;

/**
 *
 * @author victoriocarvalho
 *
 * Classe principal do aplicativo a ser utilizado para fazer o relatório do trabalho
 * de árvore AVL
 */
public class AppBinTreeAVL {
    public static void main(String[] args) {

        GeradorDeArvores gerador = new GeradorDeArvores();
        ComparadorAlunoPorMatricula comparador = new ComparadorAlunoPorMatricula();
        BinTreeInterface<Aluno> arv;

        arv = new BinTreeAVL<>(comparador);
        gerador.geraArvoreDegenerada(100, arv);
        System.out.println("Árvore AVL Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());
        arv = new BinTree<>(comparador);
        gerador.geraArvoreDegenerada(100, arv);
        System.out.println("Árvore Degenerada Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());

        arv = new BinTreeAVL<>(comparador);
        gerador.geraArvoreDegenerada(1000, arv);
        System.out.println("Árvore AVL Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());
        arv = new BinTree<>(comparador);
        gerador.geraArvoreDegenerada(1000, arv);
        System.out.println("Árvore Degenerada Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());

        arv = new BinTreeAVL<>(comparador);
        gerador.geraArvoreDegenerada(10000, arv);
        System.out.println("Árvore AVL Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());
        arv = new BinTree<>(comparador);
        gerador.geraArvoreDegenerada(10000, arv);
        System.out.println("Árvore Degenerada Criada");
        System.out.println("Quantidade de Nós: " + arv.countNodes()+ " Altura: " + arv.height());


    }
}