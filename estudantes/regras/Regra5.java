package estudantes.regras;

import estudantes.entidades.Circular;
import estudantes.entidades.Deliberacao;
import estudantes.entidades.Documento;
import estudantes.entidades.Oficio;
import professor.entidades.Processo;

import java.util.Arrays;
import java.util.stream.Stream;


//circulares e oficios precisam ter destinatario em comum
public class Regra5 implements Regra{
    public boolean validate(Processo processo, Documento documento){
        //validação so se aplica para docs do tipo Deliberação (circulares e oficios)
        if( !(documento instanceof Deliberacao) ) return true;

        //filtrar o processo por documentos do tipo Deliberação e construir lista de destinatarios
        Documento[] docsProcesso = processo.pegarCopiaDoProcesso();
        String[] listaDestinatarios = Arrays.stream(docsProcesso)
                .filter(doc -> doc instanceof Deliberacao) //deixar apenas doc do tipo certo
                .flatMap(doc -> { //itera sobre cada doc e busca destinatario ou destinatarios, dependendo do tipo
                    //se for oficio, buscar destinatario
                    if(doc instanceof Oficio oficio) {
                        return Stream.of(oficio.getDestinatario());}
                    //se for circular, buscar destinatarios
                    else {
                        Circular circular = (Circular) doc;
                        return Arrays.stream(circular.getDestinatarios());
                    }
                })
                .toArray(String[]::new); //array criado do tipo Documento[]

        //se for vazia, não precisa fazer match dos destinatarios -> retorna true
        if(listaDestinatarios.length == 0) return true;

        //verificar se destinatario do doc atual esta na lista
        if(documento instanceof Oficio){
            String destinatarioAtual = ((Oficio) documento).getDestinatario();

            return Arrays.asList(listaDestinatarios).contains(destinatarioAtual); //retorna verdadeiro se o destinatario atual esta na lista
        } else {
            String[] destinatariosAtuais = ((Circular) documento).getDestinatarios();

            for(String destinatarioAtual : destinatariosAtuais){
                if(Arrays.asList(listaDestinatarios).contains(destinatarioAtual)){
                    return true;
                }
            }

            return false; //se não retornou true dentro do for, lista não contem o destinatario atual
        }
    }
}
