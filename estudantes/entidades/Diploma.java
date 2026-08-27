package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Diploma extends Certificado{
    private String habilitacao;

    public Diploma(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao, String habilitacao){
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula, habilitacao);
        this.habilitacao = habilitacao;
    }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Diploma objDip = (Diploma) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(habilitacao, objDip.habilitacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), habilitacao);
    }
}
