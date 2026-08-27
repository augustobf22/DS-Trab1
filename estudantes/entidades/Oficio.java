package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Oficio extends Deliberacao{
    private String destinatario;

    public Oficio(String criador, CodigoCurso codigoCurso, int paginas, String texto, String destinatario){
        super(criador, codigoCurso, paginas, texto);
        this.destinatario = destinatario;
    }

    public String getDestinatario() { return this.destinatario; }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Oficio objOfic = (Oficio) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        return Objects.equals(destinatario, objOfic.destinatario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinatario);
    }
}