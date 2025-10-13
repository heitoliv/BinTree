package contactlist.app;

public class Contato {
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    
    public Contato(String nome, String sobrenome, String telefone, String email){
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome(){
        return this.nome;
    }

    public String getSobrenome(){
        return this.sobrenome;
    }
    @Override
    public String toString(){
        return sobrenome + ", " + nome + " - " + telefone + " (" + email + ")";
    }

    
}
