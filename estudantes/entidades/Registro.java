package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public abstract class Registro extends DocumentoAcademico {
    private String estudante;
    private long matricula;

    public Registro(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula){
        super(criador, codigoCurso, paginas, autenticacao);
        this.estudante = estudante;
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object obj){
        //fazer comparação por referencia
        //verificar se objeto é null ou de outra classe
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Registro objReg = (Registro) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return estudante.equals(objReg.estudante) && matricula == objReg.matricula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estudante, matricula);
    }
}
