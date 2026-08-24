package estudantes.regras;

import estudantes.entidades.Ata;
import estudantes.entidades.Documento;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.Processo;

//processo não pode misturar docs admin e academicos, ata vai com qualquer coisa
public class Regra2 extends Regra{
    public Regra2(Processo processo, Documento documento){
        super(processo, documento);
    }

    public boolean validate(){
        //atas vão com qualquer coisa
        if(documento instanceof Ata) {return true;}

        boolean processoContemDocAdmin = false;
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();

        for(Documento doc : docsProcesso){
            if(doc instanceof DocumentoAdministrativo){
                processoContemDocAdmin = true;
                break;
            }
        }

        //se o processo contem docs administrativos, aceitar apenas docs admin
        //se não contem, aceitar apenas docs academicos
        //ou seja, precisam ser iguais
        return processoContemDocAdmin == documento instanceof DocumentoAdministrativo;
    }
}
