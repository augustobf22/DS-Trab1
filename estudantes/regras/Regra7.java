package estudantes.regras;

import estudantes.entidades.Atestado;
import estudantes.entidades.Documento;
import professor.entidades.Processo;

//atestados só podem estar com atestados da mesma categoria
public class Regra7 implements Regra {
    public boolean validate(Processo processo, Documento documento){
        //se doc não for do tipo Atestado, validação não se aplica
        if(!(documento instanceof Atestado)) {return true;}

        String categoriaAtual = ((Atestado) documento).getCategoria();

        //devolver false apenas quando processo contem Atestado com categoria diferente
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        for(Documento doc : docsProcesso){
            if(doc instanceof Atestado){
                return categoriaAtual.equals(((Atestado) doc).getCategoria());
            }
        }

        return true; //se passou pelo for sem dar retorno, Processo nao contem Atestado
    }
}
