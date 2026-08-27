package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public abstract class DocumentoAcademico extends Documento {
    private long autenticacao;

    public DocumentoAcademico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao) {
        super(criador, codigoCurso, paginas);
        this.autenticacao = autenticacao;
    }

    @Override
    public boolean equals(Object obj){
        //fazer comparação por referencia
        //verificar se objeto é null ou de outra classe
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        DocumentoAcademico objDocAcad = (DocumentoAcademico) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return autenticacao == objDocAcad.autenticacao;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), autenticacao);
    }
}