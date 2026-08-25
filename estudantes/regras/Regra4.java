package estudantes.regras;

import estudantes.entidades.Documento;
import estudantes.entidades.Norma;
import professor.entidades.Processo;

//portarias e editais VALIDOS não podem ter mais de 100 paginas
//se devolver falso nessa validação, ir para o proximo processo vazio e despachar so com esse documento
public class Regra4 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //se doc não for portaria ou edital(= Norma), não precisa passar pela validação
        if ( !(documento instanceof Norma) ) {
            return true;
        } else if ( !(((Norma) documento).isValido()) ) { //se doc não for valido, retorna true (cast deveria ser desnecessário pela logica)
            return true;
        }

        int paginas = documento.getPaginas();
        return (paginas < 100); //retorna true apenas para docs com menos de 100 paginas
    }
}
