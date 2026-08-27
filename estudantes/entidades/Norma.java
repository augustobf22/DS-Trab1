package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

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

    public boolean isValido() { return this.valido; }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Norma objNorma = (Norma) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return numero == objNorma.numero && valido == objNorma.valido && Objects.equals(texto, objNorma.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numero, valido, texto);
    }
}
