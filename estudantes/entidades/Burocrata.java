package estudantes.entidades;

import professor.entidades.*;
import estudantes.regras.*;
import java.util.*;

/**
 * Classe que traz a lógica do algoritmo de organização e despacho de processos.
 * <br><br>
 * Você pode incluir novos atributos e métodos nessa classe para criar
 * lógicas mais complexas para o gerenciamento da organização e despacho de 
 * processos, mas eles não serão invocados diretamente pelo simulador e devem
 * respeitar propriedades de encapsulamento e coesão.
 * 
 * @author coloque os nomes dos autores aqui
 */
public class Burocrata {
    private int estresse = 0;
    private Mesa mesa;
    private Universidade universidade;

    //variaveis de controle, precisam se manter entre os ciclos de trabalhar()
    private static final int NUMERO_PASSAGENS = 10; //quantas passagens o processo vai fazer pela lista de docs sem adicionar nenhum doc antes de ser despachado
    private final int[] passagensSemAdicionarDoc = new int[5]; //guarda, para cada processo, quantas passagens ele já deu sem adicionar um doc. quando chegar em NUMERO_PASSAGENS, processo é despachado

    /**
     * Construtor de Burocrata.
     * 
     * @param m mesa com os processos
     * @param u universidade com os montes dos cursos e a secretaria
     */
    public Burocrata(Mesa m, Universidade u){
        this.mesa = m;
        this.universidade = u;
    }

    //instanciando as regras e criando metodo de validação universal
    Regra[] regras = new Regra[]{new Regra1(), new Regra2(), new Regra3(), new Regra4(), new Regra5(), new Regra6(), new Regra7(), new RegraPaginas()};
    private boolean validarRegras(Processo processo, Documento documento, Regra[] regras) {
        for (Regra regra : regras) {
            if (!regra.validate(processo, documento)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Executa a lógica de criação e despacho dos processos.
     * <br><br>
     * Esse método é o único método de controle invocado durante a simulação 
     * da universidade.
     * <br><br>
     * Aqui podem ser feitas todas as verificações sobre os documentos nos 
     * montes dos cursos e dos processos abertos na mesa do Burocrata. A partir 
     * dessas informações, você pode colocar documentos nos processos abertos
     * e despachar os processos para a secretaria acadêmica.
     * <br><br>
     * Cuidado com a complexidade do seu algoritmo, porque se ele demorar muito
     * serão criados menos documentos na sua execução e sua produtividade geral
     * vai cair.
     * <br><br>
     * Esse método será chamado a cada 50 milissegundos pelo simulador da
     * universidade.
     * <br><br>
     * <strong>O burocrata não pode manter documentos com ele</strong> depois
     * que o método trabalhar terminar de executar, ou seja, você deve devolver
     * para os montes dos cursos todos os documentos que você removeu dos montes
     * dos cursos.
     * 
     * @see professor.entidades.Universidade#despachar(Processo)
     * @see professor.entidades.Universidade#removerDocumentoDoMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso)
     * @see professor.entidades.Universidade#devolverDocumentoParaMonteDoCurso(estudantes.entidades.Documento, professor.entidades.CodigoCurso) 
     */
    public void trabalhar() {
        //criar lista com todos os docs
        //percorrer a lista alocando cada documento em um dos 5 processos, de acordo com as regras de alocação
        //depois de percorrer todos os documentos, verificar se existem novos documentos e continuar tentando fazer a alocação
        //quando um processo não alocou nenhum documento adicional após um numero x de passagens (testar diferentes valores aqui), ele é despachado

        Processo[] processosAtuais = mesa.getProcessos();

        //buscar docs nos montes dos cursos e montar lista com todos os docs
        List<Documento> listaTodosDocumentos = new ArrayList<>(); //arraylist não precisa especificar tamanho inicial, facilita remoção
        for(CodigoCurso cod : CodigoCurso.values()) {
            Documento[] monte = universidade.pegarCopiaDoMonteDoCurso(cod);
            listaTodosDocumentos.addAll(Arrays.asList(monte));
        }

        //cada processo tem um indicador booleano com indice igual ao seu
        boolean[] processoAdicionouDoc = new boolean[processosAtuais.length];

        //alocação dos docs nos processos
        for(int indiceDoc = 0; indiceDoc < listaTodosDocumentos.size(); indiceDoc++){ //usando for tradicional pois preciso de controle de indices
            Documento doc = listaTodosDocumentos.get(indiceDoc);

            for (int indiceProcesso = 0; indiceProcesso < processosAtuais.length; indiceProcesso++) {
                Processo processoAtual = processosAtuais[indiceProcesso];

                if (processoAtual != null && validarRegras(processoAtual, doc, regras)) { //valida as regras e verifica se processo não é nulo, evitando NullPointerException
                    processoAtual.adicionarDocumento(doc);
                    universidade.removerDocumentoDoMonteDoCurso(doc, doc.getCodigoCurso());

                    listaTodosDocumentos.remove(doc);
                    indiceDoc--; //sem essa linha, pularia um indice
                    processoAdicionouDoc[indiceProcesso] = true;
                    break; //vai pro proximo doc
                }
            }
        }

        //controle de passagens, decidir se processo vai ser despachado ou vai tentar adicionar mais docs
        for (int indiceProcesso = 0; indiceProcesso < processosAtuais.length; indiceProcesso++) {
            Processo processoAtual = processosAtuais[indiceProcesso];

            //se processo é nulo, resetar contagem e ir pro proximo processo
            if(processoAtual == null){
                passagensSemAdicionarDoc[indiceProcesso] = 0;
                continue;
            }

            //se recebeu docs nessa passagem, atualizar variavel de controle
            //se processo continua vazio, ignorar essa passagem (evita despachar processos vazios)
            //se não recebeu, aumentar variavel de controle e fazer verificação se chegou ao limite
            if(processoAdicionouDoc[indiceProcesso] || processoAtual.contarDocumentos() == 0){
                passagensSemAdicionarDoc[indiceProcesso] = 0;
            } else {
                    passagensSemAdicionarDoc[indiceProcesso]++;

                    //quando chegar no limite de passagens sem docs adicionados, despachar processo e atualizar variavel de controle
                    if(passagensSemAdicionarDoc[indiceProcesso] >= NUMERO_PASSAGENS){
                        universidade.despachar(processoAtual);
                        passagensSemAdicionarDoc[indiceProcesso] = 0;
                    }
            }
        }
    }

/*
        //outro metodo: abre um processo e bota o maximo de docs nele; um doc não deu, vai pro proximo; nao deu nesse monte, vai pro proximo ate acabarem os montes
        //criar lista com todos os docs? ao inves de forEach nos montes
        //como saber se processo esta cheio? deu mais uma volta e não adicionou mais documentos
        for (Processo processoAtual : processosAtuais) {
            //verfica se processo ja foi criado/não é null
            if (processoAtual == null) {
                continue; // vai pro proximo
            }

            //atualiza monte a cada processo
            for (CodigoCurso cod : CodigoCurso.values()) {
                Documento[] monte = universidade.pegarCopiaDoMonteDoCurso(cod);
                montes.put(cod, monte);
            }

            //criar uma lista com todos os docs e ir removendo eles
            //tentar inserir docs no processo até que nenhum seja inserido apos uma execução do loop

            //tenta alocar docs no processo
            montes.forEach((curso, monte) -> {
                for (Documento doc : monte) {
                    if (validarRegras(processoAtual, doc, regras)) {
                        processoAtual.adicionarDocumento(doc);
                        universidade.removerDocumentoDoMonteDoCurso(doc, curso);
                    }
                }
            });

            //testes
            if(processoAtual.contarDocumentos() != 0){
                // depois de adicionar os documentos
                System.out.println("\n=== Processo sendo despachado ===");

                Documento[] documentos = processoAtual.pegarCopiaDoProcesso();
                int totalPaginas = 0;

                for (Documento documento : documentos) {
                    totalPaginas += documento.getPaginas();

                    System.out.println(
                           "Tipo: " + documento.getClass().getSimpleName()
                                    + " | Curso: " + documento.getCodigoCurso()
                                    + " | Páginas: " + documento.getPaginas()
                    );
                }

                System.out.println("Total de documentos: " + documentos.length);
                System.out.println("Total de páginas: " + totalPaginas);

                universidade.despachar(processoAtual); }

        }

        //tentar fazer uma lista com todos os docs e percorrer ate que nenhum doc seja adicionado no processo

 */
    
    /**
     * Retorna o valor atual de estresse do burocrata.
     * @return estresse atual
     */
    public int getEstresse(){
        return this.estresse;
    }
    
    /**
     * Aumenta o estresse do burocrata em uma unidade.
     * 
     * <strong>VOCÊ NÃO DEVERIA INVOCAR ESSE MÉTODO!!!</strong>
     */
    public void estressar(){
        this.estresse++;
    }
    
    /**
     * Aumenta o estresse do burocrata em 10 unidades.
     * 
     * <strong>VOCÊ NÃO DEVERIA INVOCAR ESSE MÉTODO!!!</strong>
     */
    public void estressarMuito(){
        this.estresse += 10;
    }
}