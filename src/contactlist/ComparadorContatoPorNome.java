package contactlist.app;

import java.util.Comparator;

public class ComparadorContatoPorNome implements Comparator<Contato> {

    @Override
    public int compare(Contato c1, Contato c2) {
        int cmpSobrenome = c1.getSobrenome().compareToIgnoreCase(c2.getSobrenome());
        if (cmpSobrenome != 0) {
            return cmpSobrenome;
        }
        return c1.getNome().compareToIgnoreCase(c2.getNome());
    }
}