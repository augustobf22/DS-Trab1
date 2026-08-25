package estudantes.regras;

import estudantes.entidades.Documento;
import professor.entidades.Processo;

//processo pode ter no max 250 paginas
public class RegraPaginas implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //contarPaginas() é um metodo protegido do Processo, por isso a repetição de codigo aqui
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        int paginas = 0;

        for(Documento doc : docsProcesso){
            paginas += doc.getPaginas();
        }

        return paginas + documento.getPaginas() <= 250;
    }
}
