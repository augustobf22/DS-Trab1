package professor.entidades;

import estudantes.entidades.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Classe que representa a secretaria que recebe e lida com os processos.
 * <br><br>
 * <strong>Não mexa aqui!!!</strong>
 * 
 * @author Jean Cheiran
 */
public class Secretaria {
    private int documentosDespachados = 0;
    private int documentosPerdidos = 0;
    private LinkedList<Processo> processosDespachadosCorretamente;
    private LinkedList<Processo> processosDespachadosComProblemas;
    
    protected Secretaria(){
        processosDespachadosCorretamente = new LinkedList<>();
        processosDespachadosComProblemas = new LinkedList<>();
    }
    
    private void registrarErro(String tipo, String motivo, Burocrata burocrata){
        System.out.println("[ERRO] Tipo: " + tipo + " | Motivo: " + motivo);
        burocrata.estressar();
    }

    protected void despachar(Processo processo, Burocrata burocrata){
        //perder documentos se exceder capacidade do processo
        if(processo.contarPaginas() > 250){
            System.out.println("[ERRO] Tipo: CAPACIDADE_EXCEDIDA | Motivo: processo tem " + processo.contarPaginas() + " páginas, excedendo o limite de 250.");
            documentosPerdidos += processo.contarDocumentos();
            burocrata.estressarMuito();
            return;
        }
        
        Documento[] copiaDoProcesso = processo.pegarCopiaDoProcesso();
        boolean processoComProblemas = false;
        
        //graduação X pós-graduação
        boolean graduacao = false, posgraduacao = false;
        for(Documento doc : copiaDoProcesso){
            if(doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_COMPUTACAO) || doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_ENGENHARIA_ELETRICA) || doc.getCodigoCurso().equals(CodigoCurso.POS_GRADUACAO_MICROELETRONICA)){
                posgraduacao = true;
            }else{
                graduacao = true;
            }
        }
        if(graduacao && posgraduacao){
            registrarErro("GRADUACAO_X_POS_GRADUACAO", "processo mistura documentos de graduação e pós-graduação.", burocrata);
            processoComProblemas = true;
        }
        
        //administrativos X acadêmicos
        boolean administrativos = false, academicos = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof DocumentoAdministrativo){
                administrativos = true;
            }
            if(doc instanceof DocumentoAcademico){
                academicos = true;
            }
        }
        if(administrativos && academicos){
            registrarErro("ADMINISTRATIVO_X_ACADEMICO", "processo mistura documentos administrativos e acadêmicos.", burocrata);
            processoComProblemas = true;
        }
        
        //processo só com atas
        boolean apenasAtas = true;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof DocumentoAdministrativo){
                apenasAtas = false;
            }
            if(doc instanceof DocumentoAcademico){
                apenasAtas = false;
            }
        }
        if(apenasAtas){
            registrarErro("SO_COM_ATAS", "processo contém apenas atas, o que não é permitido.", burocrata);
            processoComProblemas = true;
        }
        
        //portarias e editais substanciais
        boolean documentoSubstancialValido = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Edital || doc instanceof Portaria){
                Norma norma = (Norma) doc;
                if(norma.getPaginas() >= 100 && norma.isValido()){
                    documentoSubstancialValido = true;
                }
            }
        }
        if(documentoSubstancialValido && copiaDoProcesso.length > 1){
            registrarErro("PORTARIA_EDITAL_SUBSTANCIAL", "norma válida com 100+ páginas foi enviada junto com outros documentos.", burocrata);
            processoComProblemas = true;
        }
        
        //circulares e ofícios para mesmo destinatário
        HashMap<String, Integer> destinatarios = new HashMap<>();
        int contagemDeOficiosECirculares = 0;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Oficio){
                contagemDeOficiosECirculares++;
                Oficio oficio = (Oficio) doc;
                if(destinatarios.containsKey(oficio.getDestinatario())){
                    destinatarios.put(oficio.getDestinatario(), destinatarios.get(oficio.getDestinatario()) + 1);
                }else{
                    destinatarios.put(oficio.getDestinatario(), 1);
                }
            }
            if(doc instanceof Circular){
                contagemDeOficiosECirculares++;
                Circular circular = (Circular) doc;
                //se o mesmo destinatário aparecer mais de uma vez, não vai ser legal :(
                for(String destinatario : circular.getDestinatarios()){
                    if(destinatarios.containsKey(destinatario)){
                        destinatarios.put(destinatario, destinatarios.get(destinatario) + 1);
                    }else{
                        destinatarios.put(destinatario, 1);
                    }
                }
            }
        }
        if(contagemDeOficiosECirculares > 0){
            boolean existemCircularesEOficiosSemDestinatarioComum = true;
            for(int ocorrencias : destinatarios.values()){
                // >= é mais seguro por causa de possíveis repetições de destinatários no mesmo documento
                if(ocorrencias >= contagemDeOficiosECirculares){
                    existemCircularesEOficiosSemDestinatarioComum = false;
                }
            }
            if(existemCircularesEOficiosSemDestinatarioComum){
                HashMap<String, Integer> copie = new HashMap<>(destinatarios);
                String destinatariosProcesso = copie.keySet().toString();
                String destinatariosDocumento = "";
                for(Documento doc : copiaDoProcesso){
                    if(doc instanceof Oficio){
                        destinatariosDocumento += ((Oficio) doc).getDestinatario() + ", ";
                    }
                    if(doc instanceof Circular){
                        for(String destinatario : ((Circular) doc).getDestinatarios()){
                            destinatariosDocumento += destinatario + ", ";
                        }
                    }
                }
                if(destinatariosDocumento.endsWith(", ")){
                    destinatariosDocumento = destinatariosDocumento.substring(0, destinatariosDocumento.length() - 2);
                }

                System.out.println("[DEBUG] DESTINATARIOS_DIVERGENTES");
                System.out.println("[DEBUG] contagemDeOficiosECirculares = " + contagemDeOficiosECirculares);
                System.out.println("[DEBUG] destinatarios.values() = " + copie);
                System.out.println("[DEBUG] critério atual: for (int ocorrencias : destinatarios.values()) if (ocorrencias >= contagemDeOficiosECirculares) ...");
                for(String destinatario : copie.keySet()){
                    int ocorrencias = copie.get(destinatario);
                    System.out.println("[DEBUG] destinatario='" + destinatario + "' => ocorrencias=" + ocorrencias + " | comparacao=" + ocorrencias + " >= " + contagemDeOficiosECirculares + " => " + (ocorrencias >= contagemDeOficiosECirculares));
                }
                System.out.println("[DEBUG] destinatarios no processo = " + destinatariosProcesso);
                System.out.println("[DEBUG] destinatarios no documento = [" + destinatariosDocumento + "]");
                System.out.println("[DEBUG] documentos no processo = ");
                for(Documento doc : copiaDoProcesso){
                    if(doc instanceof Oficio){
                        System.out.println("  - Oficio(destinatario=" + ((Oficio) doc).getDestinatario() + ")");
                    } else if(doc instanceof Circular){
                        System.out.println("  - Circular(destinatarios=" + java.util.Arrays.toString(((Circular) doc).getDestinatarios()) + ")");
                    } else {
                        System.out.println("  - " + doc.getClass().getSimpleName());
                    }
                }
                System.out.println("[ERRO] Tipo: DESTINATARIOS_DIVERGENTES | Motivo: circulares e ofícios não compartilham um destinatário comum. | contagemDeOficiosECirculares=" + contagemDeOficiosECirculares + " | ocorrenciasPorDestinatario=" + copie + " | Destinatários no processo: " + destinatariosProcesso + " | Destinatários no documento: [" + destinatariosDocumento + "]");
                burocrata.estressar();
                processoComProblemas = true;
            }
        }
        
        //diplomas só com diplomas, certificados e atas
        boolean diplomas = false, documentosNaoDiplomasCertificadosAtas = false;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Diploma){
                diplomas = true;
            //não precisa testar Diploma, porque acabou de verificar acima
            }else if(!(doc instanceof Certificado) && !(doc instanceof Ata)){
                documentosNaoDiplomasCertificadosAtas = true;
            }
        }
        if(diplomas && documentosNaoDiplomasCertificadosAtas){
            registrarErro("DIPLOMA_MISTURADO", "processo contém diploma junto com documentos que não são diploma, certificado ou ata.", burocrata);
            processoComProblemas = true;
        }
        
        //atestados da mesma categoria
        boolean atestadosComCategoriasMisturadas = false;
        String categoriaDoPrimeiroAtestadoEncontrado = null;
        for(Documento doc : copiaDoProcesso){
            if(doc instanceof Atestado){
                Atestado atestado = (Atestado) doc;
                if(categoriaDoPrimeiroAtestadoEncontrado == null){
                    categoriaDoPrimeiroAtestadoEncontrado = atestado.getCategoria();
                }else if(!categoriaDoPrimeiroAtestadoEncontrado.equals(atestado.getCategoria())){
                    atestadosComCategoriasMisturadas = true;
                }
            }
        }
        if(atestadosComCategoriasMisturadas){
            registrarErro("ATESTADOS_CATEGORIAS_MISTURADAS", "atestados de categorias diferentes estão no mesmo processo.", burocrata);
            processoComProblemas = true;
        }
        
        documentosDespachados += processo.contarDocumentos();
        
        if(!processoComProblemas){
            processosDespachadosCorretamente.push(processo);
            System.out.println("[OK] Processo despachado corretamente. Documentos: " + processo.contarDocumentos() + ", páginas: " + processo.contarPaginas());
        }else{
            processosDespachadosComProblemas.push(processo);
            System.out.println("[AVISO] Processo despachado com problemas. Documentos: " + processo.contarDocumentos() + ", páginas: " + processo.contarPaginas());
        }
    }
    
    protected int contarProcessosDespachados(){
        return processosDespachadosCorretamente.size() + processosDespachadosComProblemas.size();
    }
    
    protected int contarDocumentosDespachados(){
        return documentosDespachados;
    }
    
    protected int contarDocumentosPerdidos(){
        return documentosPerdidos;
    }
}
