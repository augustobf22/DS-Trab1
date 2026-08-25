package estudantes.regras;

import estudantes.entidades.Documento;
import professor.entidades.Processo;

public interface Regra {
    public boolean validate(Processo processo, Documento documento) ;
}
