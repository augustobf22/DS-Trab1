package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Objects;

public class Portaria extends Norma{
    private int anoInicio;

    public Portaria(String criador, CodigoCurso codigoCurso, int paginas, int numero, boolean valido, String texto, int anoInicio){
        super(criador, codigoCurso, paginas, numero, valido, texto);
        this.anoInicio = anoInicio;
    }

    @Override
    public boolean equals(Object obj){
        if(!super.equals(obj)) return false;

        Portaria objPor = (Portaria) obj;
        return anoInicio == objPor.anoInicio;
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), anoInicio);
    }
}
