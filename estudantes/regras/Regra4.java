package estudantes.regras;

import estudantes.entidades.Documento;
import estudantes.entidades.Norma;
import professor.entidades.Processo;

//portarias e editais VALIDOS não podem ter mais de 100 paginas
//se devolver falso nessa validação, ir para o proximo processo vazio e despachar so com esse documento
public class Regra4 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //se processo contem uma norma valida com mais de 100 paginas, nao pode adicionar mais nenhum documento
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        for(Documento doc : docsProcesso){
            if ( doc instanceof Norma && ((Norma) doc).isValido() && doc.getPaginas() >= 100) {
                return false;
            }
        }

        //se doc não for portaria ou edital(= Norma), não precisa passar pela validação
        if ( !(documento instanceof Norma) ) {
            return true;
        } else if ( !(((Norma) documento).isValido()) ) { //se doc não for valido, retorna true (cast deveria ser desnecessário pela logica)
            return true;
        }

        //normas validas com >= 100 paginas so podem ser despachadas sozinhas
        //se tem mais de 100 paginas, so pode ir em processo vazio
        int paginas = documento.getPaginas();
        if(paginas < 100){
            return true;
        } else if(processo.contarDocumentos() == 0){
            return true;
        }

        return false;
    }
}
