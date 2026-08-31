package estudantes.regras;

import estudantes.entidades.Ata;
import estudantes.entidades.Documento;
import estudantes.entidades.DocumentoAcademico;
import estudantes.entidades.DocumentoAdministrativo;
import professor.entidades.Processo;

//processo não pode misturar docs admin e academicos, ata vai com qualquer coisa
public class Regra2 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //protecao contra docs nulos
        if(documento == null) return false;

        //atas vão com qualquer coisa
        if(documento instanceof Ata) {return true;}

        boolean contemDocAdmin = false;
        boolean contemDocAcad = false;

        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        for(Documento doc : docsProcesso){
            if(doc instanceof DocumentoAdministrativo){
                contemDocAdmin = true;
            } else if(doc instanceof DocumentoAcademico){
                contemDocAcad = true;
            }
        }

        //se não contem docs de nenhum tipo, aceita qualquer doc
        if(!contemDocAcad && !contemDocAdmin) return true;

        //retorna true apenas quando doc recebido é do mesmo tipo dos docs já contidos no processo
        return (contemDocAdmin && documento instanceof DocumentoAdministrativo) || (contemDocAcad && documento instanceof DocumentoAcademico);
    }
}
