package estudantes.regras;

import estudantes.entidades.Circular;
import estudantes.entidades.Deliberacao;
import estudantes.entidades.Documento;
import estudantes.entidades.Oficio;
import professor.entidades.Processo;

import java.util.*;


//circulares e oficios precisam ter destinatario em comum
public class Regra5 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //validação so se aplica para docs do tipo Deliberação (circulares e oficios)
        if( !(documento instanceof Deliberacao) ) return true;

        //verifica se existe interseção entre os destinatarios dos docs no processo
        Set<String> intersecao = buscaIntersecao(processo);

        //se não existe nenhum destinatario em comum aqui, ja retorna falso
        if (intersecao == null || intersecao.isEmpty()) {
            return false;
        }

        //buscar os destinatarios do documento
        Set<String> destinatariosDoc = buscarDestinatarios(documento);

        //proteção contra docs criados com erro/null no destinatarios
        if(destinatariosDoc.isEmpty()) return true;

        //confere se existem elementos em comum entre os destinatarios do processo (intersecao) e os destinatarios do documento
        intersecao.retainAll(destinatariosDoc);

        //devolve verdadeiro caso existam elementos em comum (intersecao não é vazia)
        return !intersecao.isEmpty();
    }

    //usando set/hashset para armazenar pois tempo de busca é menor
    //metodo auxiliar para buscar destinatarios (oficio sempre tem um, circular mais de um)
    private Set<String> buscarDestinatarios(Documento doc){
        if (doc instanceof Oficio oficio) {
            return Collections.singleton(oficio.getDestinatario());
        } else if (doc instanceof Circular) {
            return new HashSet<>(Arrays.asList(((Circular) doc).getDestinatarios()));
        }

        //se não entrou em nenhum if, devolver conjunto vazio
        return Collections.emptySet();
    }

    //recebe um processo e devolve set com destinatarios em comum entre os docs
    private Set<String> buscaIntersecao(Processo processo){
        Set<String> intersecao = null;

        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        for(Documento doc : docsProcesso){
            if (!(doc instanceof Deliberacao)) {
                continue;
            }

            Set<String> destinatarios = buscarDestinatarios(doc);
            if (destinatarios.isEmpty()) {
                continue;
            }

            if (intersecao == null) { //na primeira passagem, inicia o conjunto
                intersecao = new HashSet<>(destinatarios);
            } else {
                intersecao.retainAll(destinatarios); //retainAll: metodo que compara os valores do conjunto e mantém apenas os que são iguais => interseção
            }

            //se intersecao estiver vazia dentro do for, ja pode quebrar o laco e dar retorno
            if (intersecao.isEmpty()) {
                return intersecao;
            }
        }

        return intersecao;
    }
}
