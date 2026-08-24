package estudantes.regras;

import estudantes.entidades.Ata;
import estudantes.entidades.Certificado;
import estudantes.entidades.Diploma;
import estudantes.entidades.Documento;
import professor.entidades.Processo;

//Diplomas só podem ir em processo contendo outros diplomas, certificados ou atas
public class Regra6 extends Regra {
    public Regra6(Processo processo, Documento documento){
        super(processo, documento);
    }

    public boolean validate(){
        //validação so se aplica para docs do tipo Diploma
        if( !(documento instanceof Diploma) ) return true;

        //percorrer processo, se encontrar doc de tipo diferente de Diploma, Certificado e Ata devolver falso
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        for(Documento doc : docsProcesso){
            if( !(doc instanceof Certificado || doc instanceof Ata)){
                return false;
            }
        }

        return true;
    }
}
