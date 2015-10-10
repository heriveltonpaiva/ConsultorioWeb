package br.arquitetura.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dao.GenericDaoImpl;
import br.arquitetura.dominio.ArcadaDentaria;
import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.Contato;
import br.arquitetura.dominio.Dente;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Endereco;
import br.arquitetura.dominio.Estado;
import br.arquitetura.dominio.Medicacao;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.TipoAcarda;
import br.arquitetura.dominio.TipoPessoa;
import br.arquitetura.service.ArcadaDentariaServiceImpl;
import br.arquitetura.service.DenteArcadaDentariaServiceImpl;
import br.arquitetura.service.DenteServiceImpl;
import br.arquitetura.service.PessoaServiceImpl;

@Component
@Scope("session")
public class PacienteController {
    
	private PessoaServiceImpl pessoaService;
	private ArcadaDentariaServiceImpl arcadaDentariaService;
	private DenteArcadaDentariaServiceImpl denteArcadaDentariaService;
	private DenteServiceImpl denteService;
    private UploadedFile file;
    private List<String> listaSexo;
    private List<String> listaEstadoCivil;
    private List<Estado> listaEstados;
	private List<Medicacao> listaMedicacoes;
    private Medicacao medicacao;
	private List<Pessoa> listaEsperaPacientes;
    
    private Pessoa paciente;
	private List<Pessoa> listagem;
	private int idFoto;

	public PacienteController() {
		pessoaService = new PessoaServiceImpl();
		denteService = new DenteServiceImpl();
		arcadaDentariaService = new ArcadaDentariaServiceImpl();
		denteArcadaDentariaService = new DenteArcadaDentariaServiceImpl();
		medicacao = new Medicacao();
		
		listaEsperaPacientes = new ArrayList<Pessoa>();
		listaMedicacoes = new ArrayList<Medicacao>();
		listagem = new ArrayList<Pessoa>();
		listagem = pessoaService.listarTodos("Pessoa");
		carregarListagensCombo();
		
		paciente = new Pessoa();
		paciente.setArquivo(new Arquivo());
		paciente.setEndereco(new Endereco());
		paciente.setContato(new Contato());
	}
	
	public void salvarArquivo() throws IOException{
		UploadedFile arq = file;
		if(arq != null){
			paciente.getArquivo().setDescricao(arq.getFileName());
			paciente.getArquivo().setContentType(arq.getContentType());
			paciente.getArquivo().setTamanho(arq.getSize());
			paciente.getArquivo().setConteudo(arq.getContents());
		}
	}
	
	@Transactional
	public String salvar(){
		  try {
			    paciente.setTipoPessoa(TipoPessoa.PACIENTE);
			    salvarArquivo();
				pessoaService.cadastrar(paciente);
		        criarArcadaDentaria(paciente);
		        salvarMedicacaoPaciente();
		        
				exibirMensagemSucesso("Inserido");
				//carregarListagem();
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return null;
	}
	
	@Transactional
	public String editar(){
	   try{
		   pessoaService.alterar(paciente);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			pessoaService.remover(paciente);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	
	@Transactional
	public String carregarListagem(){
		listagem = pessoaService.listarTodos("Pessoa");
		
		//Adicionando uma lista de medicações tomada pelo paciente.
		List<Pessoa> listagemAtualizada = new ArrayList<Pessoa>();
		for (Pessoa paciente : listagem) {
			 paciente.setMedicacoes(pessoaService.findMedicacaoByPaciente(paciente));
			 listagemAtualizada.add(paciente);
		}
		
		listagem = new ArrayList<Pessoa>();
		listagem.addAll(listagemAtualizada);
		
		
		carregarFotosPacientes();

		return PaginasUtil.LISTAR_PACIENTES;
	}
	
	 public List<String> completeText(String query) {
	        List<String> results = new ArrayList<String>();
	        for(int i = 0; i < 10; i++) {
	            results.add(query + i);
	        }
	         
	        return results;
	    }
	public List<String> autoComplete(String query){
		List<String> results = new ArrayList<String>();		
		for (Pessoa pessoa : listagem) {
			results.add(pessoa.getNome());
		}
        return results;
	}
	
	public List<Pessoa> completePaciente(String query) {
        return listagem;
    }
	
	/**
	 * Carrega o paciente do auto complete ajax e exibe suas informações
	 * @param event
	 */
	public void carregarPacienteNaFilaEspera(SelectEvent event){  
		paciente = ((Pessoa)event.getObject());  
    }
	
	public void adicionarPacienteNaFilaEspera(){  
   
		if(paciente.getId()!=0){
			listaEsperaPacientes.add(paciente);
		}else{			
			paciente.setAtivo(false);
			listaEsperaPacientes.add(paciente); 
		}
        paciente = new Pessoa();
	}
	
	public String iniciarCadastro(){
		return PaginasUtil.CADASTRAR_PACIENTES;
	}
	
	public List<Pessoa> getListagem() {
		return listagem;
	}
	public void setListagem(List<Pessoa> listagem) {
		this.listagem = listagem;
	}
	public Pessoa getPaciente() {
		return paciente;
	}
	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}
	public int getIdFoto() {
		return idFoto;
	}
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
	
	public void exibirMensagemAlerta(String mensagem){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"",""+mensagem));
	}
	
	
	@Transactional
	public void criarArcadaDentaria(Pessoa paciente){
		
		ArcadaDentaria arcada = new ArcadaDentaria();
		
		arcada.setPaciente(paciente);
		arcada.setTipo(TipoAcarda.PERMANENTE);
		arcadaDentariaService.cadastrar(arcada);
		
		List<Dente> listaDentes = denteService.listarTodos("Dente");
		
		if (listaDentes != null) {
			for (Dente dente : listaDentes) {

				DenteArcadaDentaria denteArcada = new DenteArcadaDentaria();

				denteArcada.setAcardaDentaria(arcada);
				denteArcada.setEmTratamento(false);
				denteArcada.setSituacao(1);
				denteArcada.setDente(dente);
				denteArcadaDentariaService.cadastrar(denteArcada);
			}
		}else{
			exibirMensagemAlerta("Não há dentes cadastrados para formação da arcada dentária");
		}
		
		
	}
	
	public void carregarFotosPacientes(){
		try {
			for (Pessoa pessoa : listagem) {
				if(pessoa.getArquivo()!=null){
				String content[] = pessoa.getArquivo().getContentType().split("/");			
				String nomeArquivo = pessoa.getArquivo().getId() + "."+content[1];
				String arquivo = "C:\\Users\\Herivelton\\workspace_eclipse_mars\\Consultorio\\WebContent\\resourcers\\images\\" + File.separator + nomeArquivo;

				criarArquivo(pessoa.getArquivo().getConteudo(), arquivo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    private void criarArquivo(byte[] bytes, String arquivo){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public void carregarListagensCombo(){
    	
      listaSexo = new ArrayList<String>();
      
      listaSexo.add("Masculino");
      listaSexo.add("Feminino");
      listaSexo.add("Outro");
    
      listaEstadoCivil = new ArrayList<String>();
      
      listaEstadoCivil.add("Solteiro(a)");
      listaEstadoCivil.add("Casado(a)");
      listaEstadoCivil.add("Divorciado(a)");
      listaEstadoCivil.add("Viúvo(a)");
      listaEstadoCivil.add("Separado(a)");
      listaEstadoCivil.add("Companheiro(a)");
      
      listaEstados = new ArrayList<Estado>();
      
      listaEstados.add(Estado.RN);
      listaEstados.add(Estado.AC);
      listaEstados.add(Estado.AL);
      listaEstados.add(Estado.AP);
      listaEstados.add(Estado.AM);
      listaEstados.add(Estado.BA);
      listaEstados.add(Estado.CE);
      listaEstados.add(Estado.DT);
      listaEstados.add(Estado.ES);
      listaEstados.add(Estado.GO);
      listaEstados.add(Estado.MA);
      listaEstados.add(Estado.MT);
      listaEstados.add(Estado.MS);
      listaEstados.add(Estado.MG);
      listaEstados.add(Estado.PA);
      listaEstados.add(Estado.PB);
      listaEstados.add(Estado.PR);
      listaEstados.add(Estado.PE);
      listaEstados.add(Estado.PI);
      listaEstados.add(Estado.RJ);
      listaEstados.add(Estado.RS);
      listaEstados.add(Estado.RO);
      listaEstados.add(Estado.RR);
      listaEstados.add(Estado.SC);
      listaEstados.add(Estado.SP);
      listaEstados.add(Estado.SE);
      listaEstados.add(Estado.TO);
    
    }
    public List<String> getListaEstadoCivil() {
		return listaEstadoCivil;
	}
    public List<Estado> getListaEstados() {
		return listaEstados;
	}
    public List<String> getListaSexo() {
		return listaSexo;
	}
    
    public void adicionarMedicacao(){
    	if(medicacao.getDescricao().equals("")){
    		exibirMensagemAlerta("Informe a descrição para o medicamento.");
    	}else{
	    	listaMedicacoes.add(medicacao);
	    	medicacao = new Medicacao();
    	}
    }
    
    public void removerMedicacao(){
       listaMedicacoes.remove(medicacao);
    }
    
	@Transactional
    public void salvarMedicacaoPaciente(){
    	if(listaMedicacoes.size()>0){    	
        	for (Medicacao medicacao : listaMedicacoes){
        		medicacao.setPaciente(paciente);
        		GenericDaoImpl dao = new GenericDaoImpl();
        		medicacao.setId(dao.nextSequence("MEDICACAO_ID_SEQ"));
        		dao.cadastrar(medicacao);
			}
        
        }
    }
    
    public Medicacao getMedicacao() {
		return medicacao;
	}
    public void setMedicacao(Medicacao medicacao) {
		this.medicacao = medicacao;
	}
    public List<Medicacao> getListaMedicacoes() {
		return listaMedicacoes;
	}
    public void setListaMedicacoes(List<Medicacao> listaMedicacoes) {
		this.listaMedicacoes = listaMedicacoes;
	}
    public List<Pessoa> getListaEsperaPacientes() {
		return listaEsperaPacientes;
	}
    public void setListaEsperaPacientes(List<Pessoa> listaEsperaPacientes) {
		this.listaEsperaPacientes = listaEsperaPacientes;
	}
}
