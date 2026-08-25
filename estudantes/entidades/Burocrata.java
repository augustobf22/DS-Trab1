package estudantes.entidades;

import professor.entidades.*;
import estudantes.entidades.*;
import estudantes.regras.*;
import java.util.LinkedHashMap;
import java.util.Map;

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
        //buscar processos, garantindo que são do tipo processo mesmo
        //buscar documentos
        //antes de adicionar um documento ao processo, realizar validações
        //logica inicial: pega um documento de um monte e um processo aberto; se passar em todas as validações, adiciona doc ao processo
        //ao final, devolver documentos não usados

        //logica de checar por novos processos
        //logica de checar por novos documentos nos montes

        Processo[] processosAtuais = mesa.getProcessos();
        Map<CodigoCurso, Documento[]> montes = new LinkedHashMap<>();

        /*
        //para doc dentro de um monte: tenta botar no primeiro processo; nao deu, tenta no proximo. se nao conseguiu em nenhum, devolve ao monte? como saber que o processo esta cheio?
        montes.forEach((curso, monte) -> {
            for (Documento doc : monte) {
                for (Processo processoAtual : processosAtuais) {
                    if (validarRegras(processoAtual, doc, regras)) {
                        processoAtual.adicionarDocumento(doc);
                        universidade.removerDocumentoDoMonteDoCurso(doc, curso);
                        break; //sai do processo atual, vai pro proximo doc
                    }
                }
            }
        });
        */

        
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
                /*// depois de adicionar os documentos
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
                System.out.println("Total de páginas: " + totalPaginas); */

                universidade.despachar(processoAtual); }

        }
    }
    
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