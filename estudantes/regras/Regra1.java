package estudantes.regras;

import estudantes.entidades.Documento;
import professor.entidades.Processo;

//processo não pode ter docs de grad e pos simultaneamente
public class Regra1 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();

        //verificar se processo está vazio
        if (docsProcesso.length == 0) {
            return true;
        }

        boolean processoContemDocPos = false;
        for(Documento doc : docsProcesso){
            if(doc.isPosGrad()){
                processoContemDocPos = true;
                break;
            }
        }

        //se o processo contem docs de pos, aceitar apenas docs de pos
        //se não contem, aceitar apenas docs que não sao de pos
        //ou seja, precisam ser iguais
        return processoContemDocPos == documento.isPosGrad();
    }
}
