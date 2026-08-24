package estudantes.regras;

import estudantes.entidades.Documento;
import professor.entidades.Processo;

public abstract class Regra {
    protected Processo processo;
    protected Documento documento;

    public Regra(Processo processo, Documento documento) {
        this.processo = processo;
        this.documento = documento;
    }
}
