package estudantes.entidades;

import professor.entidades.CodigoCurso;
import java.util.Arrays;
import java.util.Objects;

public class Historico extends Registro{
    private double coeficiente;
    private String[] componentes;

    public Historico(String criador, CodigoCurso codigoCurso, int paginas, long autenticacao, String estudante, long matricula, double coeficiente, String[] componentes){
        super(criador, codigoCurso, paginas, autenticacao, estudante, matricula);
        this.coeficiente = coeficiente;
        this.componentes = componentes;
    }

    @Override
    public boolean equals(Object obj){
        //verificar super classe
        if(!super.equals(obj)) return false;

        //casting para classe atual
        Historico objHist = (Historico) obj;

        //comparar atributos proprios (cuidado em usar == ou equals) (retorna true apenas se todos forem verdadeiros)
        //usar Double.compare() == 0 pois java pode dar erro de arredondamento
        return Double.compare(coeficiente, objHist.coeficiente) == 0 && Arrays.equals(componentes, objHist.componentes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coeficiente, Arrays.hashCode(componentes));
    }
}
