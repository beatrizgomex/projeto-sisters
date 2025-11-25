import java.util.ArrayList;

public class Aluna extends Usuaria{
    private String curso;
    private int semestre;

    public Aluna(int idUsuaria, String nome, String email, String senha, String papel, String bioCurta, ArrayList<String> interesses, ArrayList<String> habilidades, String preferenciasPrivacidade, ArrayList<Usuaria> listaAmigas, String curso, int semestre){
        super(idUsuaria,nome,email, senha, papel,bioCurta, interesses, habilidades, preferenciasPrivacidade, listaAmigas);
        this.curso = curso;
        this.semestre = semestre;
    }
}
