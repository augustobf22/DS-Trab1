package estudantes.regras;

import estudantes.entidades.Documento;
import professor.entidades.Processo;

//processo não pode ter docs de grad e pos simultaneamente
public class Regra1 extends Regra{
    public Regra1(Processo processo, Documento documento){
        super(processo, documento);
    }

    public boolean validate(){
        boolean processoContemDocPos = false;
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();

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
