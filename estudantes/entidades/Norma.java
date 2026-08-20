package estudantes.entidades;
import professor.entidades.CodigoCurso;

public class Norma extends DocumentoAdministrativo{
    private int numero;
    private boolean valido;
    private String texto;

    public Norma(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto) {
        super(criador, codigoCurso, paginas);
        this.numero = numero;
        this.texto = texto;
        this.valido = valido;
    }
}
