package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Arrays;
import java.util.Objects;

public class Plano extends DocumentoAcademico{
    private String responsavel;
    private String[] planejamento;

    public Plano(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String responsavel, String[] planejamento){
        super(criador, codigoCurso, paginas, autenticacao);
        this.responsavel = responsavel;
        this.planejamento = planejamento;
    }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Plano objPlano = (Plano) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(responsavel, objPlano.responsavel) && Arrays.equals(planejamento, objPlano.planejamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), responsavel, Arrays.hashCode(planejamento));
    }
}
