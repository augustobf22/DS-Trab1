package estudantes.entidades;

import professor.entidades.CodigoCurso;

import java.util.Arrays;
import java.util.Objects;

public abstract class Deliberacao extends DocumentoAdministrativo{
    private String texto;

    public Deliberacao(String criador, CodigoCurso codigoCurso, int paginas, String texto){
        super(criador, codigoCurso, paginas);
        this.texto = texto;
    }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Deliberacao objDel = (Deliberacao) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(texto, objDel.texto);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), texto);
    }
}
