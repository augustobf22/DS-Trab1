package estudantes.regras;

import estudantes.entidades.Ata;
import estudantes.entidades.Documento;
import estudantes.entidades.DocumentoAcademico;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.Processo;

//processo não pode ser despachado apenas com atas
public class Regra3 extends Regra{
    public Regra3(Processo processo, Documento documento){
        super(processo, documento);
    }

    public boolean validate(){
        //se não for do tipo ata, não precisa passar pela validaçao
        if(!(documento instanceof Ata)) {return true;}

        boolean contemOutro = false; //se continuar false, não pode ser despachado
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();

        for(Documento doc : docsProcesso){
            if(doc instanceof DocumentoAdministrativo || doc instanceof DocumentoAcademico){
                contemOutro = true;
                break;
            }
        }

        return contemOutro;
    }
}
