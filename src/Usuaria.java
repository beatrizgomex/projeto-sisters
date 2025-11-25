import java.util.*;

public class Usuaria {
    protected int idusuaria;
    protected String nome;
    protected String email;
    protected String senha;
    protected String papel;
    protected String bioCurta;
    protected ArrayList<String> interesses;
    protected ArrayList<String> habilidades;
    protected String preferenciasPrivacidade;
    protected ArrayList<Usuaria> listaAmigas;

    public Usuaria() {
        this.idusuaria = 0;
        this.nome = "";
        this.email = "";
        this.senha = "";
        this.papel = "";
        this.bioCurta = "";
        this.interesses = new ArrayList<>();
        this.habilidades = new ArrayList<>();
        this.preferenciasPrivacidade = "";
        this.listaAmigas = new ArrayList<>();
    }

    public Usuaria(int idUsuaria, String nome, String email, String senha, String papel, String bioCurta, ArrayList<String> interesses, ArrayList<String> habilidades, String preferenciasPrivacidade, ArrayList<Usuaria> listaAmigas) {
        this.idusuaria = idUsuaria;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
        this.bioCurta = bioCurta;
        this.interesses = interesses;
        this.habilidades = habilidades;
        this.preferenciasPrivacidade = preferenciasPrivacidade;
        this.listaAmigas = listaAmigas;
    }

    public void Cadastrar(Usuaria usuaria) {
        this.idusuaria = usuaria.idusuaria;
    }

    public void removerInteresse(String valorRemover) {
        this.interesses.remove(interesses.get(interesses.indexOf(valorRemover)));
    }


    public void removerHabilidade(String valorRemover) {
        this.habilidades.remove(habilidades.get(habilidades.indexOf(valorRemover)));
    }

    public void editarListaAmigas(int opcao, Usuaria usuaria2){
        // opcao 1 - adicionar usuaria
        // opcao 2 - remover usuaria
        switch(opcao){
            case 1 :
                this.listaAmigas.add(usuaria2);
            case 2 :
                this.listaAmigas.remove(usuaria2);
        }
    }


    public void editarPerfil(Usuaria usuaria, int opcao, String novoValor) {
        //opcao 6 - add novo interesse
        //opcao 7 - add nova habilidade
        switch (opcao) {
            case 1 -> usuaria.nome = novoValor;
            case 2 -> usuaria.email = novoValor;
            case 3 -> usuaria.senha = novoValor;
            case 4 -> usuaria.papel = novoValor;
            case 5 -> usuaria.bioCurta = novoValor;
            case 6 -> usuaria.interesses.add(novoValor);
            case 7 -> usuaria.habilidades.add(novoValor);
            case 8 -> usuaria.preferenciasPrivacidade = novoValor;
            default -> System.out.println("Opção inválida.");
        }
    }
}


