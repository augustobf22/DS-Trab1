package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Atestado extends Registro{
    private String descricao;
    private String categoria;

    public Atestado(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, String descricao, String categoria){
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public String getCategoria() { return this.categoria; }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Atestado objAtest = (Atestado) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(descricao, objAtest.descricao) && Objects.equals(categoria, objAtest.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descricao, categoria);
    }
}
