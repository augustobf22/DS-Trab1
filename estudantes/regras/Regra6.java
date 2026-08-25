package estudantes.regras;

import estudantes.entidades.Ata;
import estudantes.entidades.Certificado;
import estudantes.entidades.Diploma;
import estudantes.entidades.Documento;
import professor.entidades.Processo;

//Diplomas só podem ir em processo contendo outros diplomas, certificados ou atas
public class Regra6 implements Regra {
    public boolean validate(Processo processo, Documento documento){
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();

        //validar se processo ja contem diploma; nesse caso, aceita apenas certficado ou ata
        boolean contemDiploma = false;
        boolean contemOutros = false;
        for(Documento doc : docsProcesso){
            if((doc instanceof Diploma)){
                contemDiploma = true;
                break;
            } else if (!(doc instanceof Certificado || doc instanceof Ata)){
                contemOutros = true;
                break;
            }
        }

        //se for diploma, retorna true apenas se não tiver outros
        if(documento instanceof Diploma){
            return !contemOutros;
        } else if(documento instanceof Certificado || documento instanceof Ata){ //nessa regra, certificados e atas sempre podem entrar no processo
            return true;
        } else { //se não for diploma, retorna true apenas se nao tiver diplomas no processo
            return !contemDiploma;
        }
    }
}
