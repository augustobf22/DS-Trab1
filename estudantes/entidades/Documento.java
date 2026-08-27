package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

/**
 * Classe que representa um documento genérico.
 * <br><br>
 * <strong>Seu trabalho começa aqui...</strong>
 * 
 * @author coloque os nomes dos autores aqui
 */
public abstract class Documento {
    private String criador;
    private CodigoCurso codigoCurso;
    private int paginas;

    public Documento(String criador, CodigoCurso codigoCurso, int paginas) {
        this.criador = criador;
        this.codigoCurso = codigoCurso;
        this.paginas = paginas;
    }

    public int getPaginas() { return this.paginas; }

    public boolean isPosGrad() {
        String cod = this.codigoCurso.toString();

        return cod.contains("POS");
    }

    public CodigoCurso getCodigoCurso() { return this.codigoCurso; }

    @Override
    public boolean equals(Object obj){
        //fazer comparação por referencia
        if(this == obj) return true;

        //verificar se objeto é null ou de outra classe
        if(obj == null || obj.getClass() != this.getClass()) return false;

        //casting para classe atual
        Documento objDoc = (Documento) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        //usar Objects.equals para comparar strings melhor do que string1.equals(string2)
        return paginas == objDoc.paginas && codigoCurso == objDoc.codigoCurso && Objects.equals(criador, objDoc.criador);
    }

    @Override
    public int hashCode(){
        return Objects.hash(criador, codigoCurso, paginas);
    }
}