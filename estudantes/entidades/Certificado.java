package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Certificado extends Registro{
    private String descricao;

    public Certificado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao){
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Certificado objCert = (Certificado) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(descricao, objCert.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descricao);
    }
}
