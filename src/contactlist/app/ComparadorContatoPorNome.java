package contactlist.app;

import java.util.Comparator;

public class ComparadorContatoPorNome implements Comparator<Contato> {

    @Override
    public int compare(Contato c1, Contato c2) {
        // Primeiro compara por sobrenome
        int cmpSobrenome = c1.getSobrenome().compareToIgnoreCase(c2.getSobrenome());
        if (cmpSobrenome != 0) {
            return cmpSobrenome;
        }
        // Se o sobrenome for igual, compara por nome
        return c1.getNome().compareToIgnoreCase(c2.getNome());
    }
}