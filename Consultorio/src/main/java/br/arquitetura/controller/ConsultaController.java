package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Agendamento;
import br.arquitetura.dominio.ArcadaDentaria;
import br.arquitetura.dominio.Procedimento;
import br.arquitetura.dominio.ConsultaGeral;
import br.arquitetura.dominio.Dente;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.PacienteAtendimento;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.TipoAcarda;
import br.arquitetura.dominio.TipoPessoa;
import br.arquitetura.dominio.Tratamento;
import br.arquitetura.service.ArcadaDentariaServiceImpl;
import br.arquitetura.service.ConsultaGeralServiceImpl;
import br.arquitetura.service.ProcedimentoServiceImpl;
import br.arquitetura.service.DenteArcadaDentariaServiceImpl;
import br.arquitetura.service.DenteServiceImpl;
import br.arquitetura.service.PessoaServiceImpl;
import br.arquitetura.service.TratamentoServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("session")
public class ConsultaController {
	
	private Procedimento procedimento;
	List<Procedimento> listaConsultaOperacoes;
	private ProcedimentoServiceImpl consultaService;
	private ConsultaGeralServiceImpl consultaGeralService;
	private DenteArcadaDentariaServiceImpl denteArcadaDentariaService;
	private ArcadaDentariaServiceImpl arcadaDentariaService;
	private TratamentoServiceImpl tratamentoService;
	private List<Tratamento> listagemTratamento;
	private Dente dente;
	private DenteServiceImpl denteService;
	private List<DenteArcadaDentaria> listagemDentesPaciente;
	private Agendamento agendamentoDaConsulta;
	private Pessoa pacienteDaConsulta;
	private PessoaServiceImpl pessoaService;
	private PacienteAtendimento pacienteAvulso;
	
	public ConsultaController() {
	    if(procedimento == null){
	    	resetConsulta();
	    }
		consultaService = new ProcedimentoServiceImpl();
		denteArcadaDentariaService = new  DenteArcadaDentariaServiceImpl();
	    consultaGeralService = new ConsultaGeralServiceImpl();
	    pessoaService = new PessoaServiceImpl();
	    arcadaDentariaService = new ArcadaDentariaServiceImpl();
	    denteService = new DenteServiceImpl();
	    
		listagemTratamento = new ArrayList<Tratamento>();
		tratamentoService = new TratamentoServiceImpl();
		listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
		pacienteAvulso = new PacienteAtendimento();
		listaConsultaOperacoes = new ArrayList<Procedimento>();
	}

	
	
	public String iniciarPasso1(){
		return PaginasUtil.CONSULTA_PASSO_1;
	}
	
	/**
	 * Inicia a realização de procedimentos
	 * @return
	 */
	public String iniciarPasso2(){
		carregarListagemTratamento();
		dente = new Dente();
		
		//verifica se o paciente está cadastrado, se for paciente avulso, inicia o processo de cadastro e geração da arcada. 
		if(!pacienteDaConsulta.isAtivo()){
			pacienteDaConsulta = cadastrarPacientePreAtendimento();
			procedimento.setPessoa(pacienteDaConsulta);
		}
		carregarListagemDentes();
		return PaginasUtil.CONSULTA_PASSO_2;
	}
	
	public String iniciarPasso3(){
		
		//se o paciente for avulso, finalizar a procedimento diretamente.
		if(pacienteDaConsulta.getId()==0){
		
			pacienteAvulso.setNomePaciente(pacienteDaConsulta.getNome());
			pacienteAvulso.setAtendido(true);
			pacienteAvulso.setDataHorario(new Date());
			return PaginasUtil.CONSULTA_PASSO_3;
		}
		
		double valorTotalConsulta = 0.0;
		//Pacientes que não passaram pela realização de procedimentos
		if(!listaConsultaOperacoes.isEmpty()){
			for (Procedimento procedimento : listaConsultaOperacoes) {
				  valorTotalConsulta += procedimento.getValor();
			}
		}
		
		procedimento.setConsultaGeral(new ConsultaGeral());
		procedimento.getConsultaGeral().setAgendamento(agendamentoDaConsulta);
		procedimento.getConsultaGeral().setDataConsulta(new Date());
		procedimento.getConsultaGeral().setNumeroProtocolo(getNumeroConsultaGeral());
		procedimento.getConsultaGeral().setValorTotal(valorTotalConsulta);
		procedimento.getConsultaGeral().setValorPago(valorTotalConsulta);
		procedimento.setPessoa(pacienteDaConsulta);
		
		return PaginasUtil.CONSULTA_PASSO_3;
	}
	
	@Transactional
	public String finalizarConsulta() {

		if (pacienteDaConsulta.getId() == 0) {
			return cadastrarPacientesAvulsos();
		}

		cadastrarAvulsosAposProcedimento();
		consultaGeralService.cadastrar(procedimento.getConsultaGeral());

		if (!listaConsultaOperacoes.isEmpty()) {
			registrarConsultaPacientesAposProcedimento();

		} else {
			registrarConsultaPaciente();
		}

		pacienteDaConsulta = new Pessoa();
		listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
		listaConsultaOperacoes = new ArrayList<Procedimento>();
		resetConsulta();

		exibirMensagemSucesso("finalizada");
		return PaginasUtil.PAGINA_INICIAL;
	}


    /**
     * Salva a procedimento do paciente que finalizou a procedimento de forma direta, sem passar pela
     * realização de procedimentos.
     */
	private void registrarConsultaPaciente() {
		//Para finalização de procedimento sem procedimento.
		 procedimento.setValor(procedimento.getConsultaGeral().getValorTotal());
		 procedimento.setNumero(getNumeroProcedimento());
		 procedimento.setTratamento(null);
		 procedimento.setPessoa(pacienteDaConsulta);
		 procedimento.setDenteArcadaDentaria(null);
		 
		 consultaService.cadastrar(procedimento);
		 cadastrarAtendimentoPaciente();
	}


	/**
	 * salva a procedimento dos paciente que estão cadastrandos na base de pacientes após
	 * realização de um procedimento.
	 */
	private void registrarConsultaPacientesAposProcedimento() {
		for (Procedimento procedimento : listaConsultaOperacoes) {
			    procedimento.setConsultaGeral(new ConsultaGeral());
			    procedimento.setConsultaGeral(this.procedimento.getConsultaGeral());
			    procedimento.setPessoa(pacienteDaConsulta);
			    consultaService.cadastrar(procedimento);	
		}
		
		 cadastrarAtendimentoPaciente();
	}


   /**
    * Faz o cadastro dos pacientes avulsos após a realização do procedimento, a partir da finalização da procedimento
    * esse paciente vai pertencer a tabela de pacientes.
    */
	private void cadastrarAvulsosAposProcedimento() {
		//Para pacientes cadastrados no momento da procedimento (PACIENTES AVULSOS).
		Pessoa pacienteExiste = pessoaService.listarPorId("Pessoa", pacienteDaConsulta.getId());
		if(pacienteExiste == null){
			pacienteDaConsulta.setAtivo(true);
			pessoaService.salvarPacientePreAtendimento(pacienteDaConsulta);
			arcadaDentariaService.salvarArcadaPreAtendimento(listagemDentesPaciente.get(0).getArcadaDentaria());
			denteArcadaDentariaService.salvarDentesPreAtendimento(listagemDentesPaciente);
			
		    cadastrarAtendimentoPaciente();
		}
	}
    
	/**
	 * Cadastra os pacientes que não serão salvos na tabela de pacientes. e sim na tabela de
	 * pacienteAtendimento, os mesmos não possui prontuário e não realizaram nenhum procedimento.
	 * @return
	 */
	private String cadastrarPacientesAvulsos() {
		pacienteAvulso.setAtendido(true);
		pessoaService.salvarPacienteAtendimento(pacienteAvulso);            
		pacienteAvulso = new PacienteAtendimento();
		resetConsulta();
		exibirMensagemSucesso("finalizada");
		return PaginasUtil.PAGINA_INICIAL;
	}

	/**
	 * Cadastra o atendimento do paciente, seja ele avulso, após procedimento ou de forma direta.
	 */
	private void cadastrarAtendimentoPaciente() {
		PacienteAtendimento atendimento = pacienteDaConsulta.getAtendimento();
		 atendimento.setPaciente(pacienteDaConsulta);
		 atendimento.setAtendido(true);
		 pessoaService.salvarPacienteAtendimento(atendimento);
	}
	
	/**
	 * Adiciona um procedimento a listagem de operações, após clicar no dente no passo 2
	 */
	public void adicionarConsultaOperacao(){
		FacesContext fc = FacesContext.getCurrentInstance();
		int numeroDente = Integer.parseInt(getidDenteParam(fc));
		DenteArcadaDentaria dente = null;
		
		for (DenteArcadaDentaria d : listagemDentesPaciente){		
			if(d.getDente().getNumero()==numeroDente){
				dente = d;
				break;
			}
				
		}
		if(procedimento.getTratamento().getId()>0){
			procedimento.setTratamento(tratamentoService.listarPorId("Tratamento", procedimento.getTratamento().getId()));	    
			procedimento.setNumero(getNumeroProcedimento());
			procedimento.setDenteArcadaDentaria(dente);
			procedimento.getDenteArcadaDentaria().setEmTratamento(false);
			procedimento.getDenteArcadaDentaria().setSituacao(1);
			
			listaConsultaOperacoes.add(procedimento);
			
			procedimento = new Procedimento();
			procedimento.setPessoa(pacienteDaConsulta);
			procedimento.setDenteArcadaDentaria(new DenteArcadaDentaria());
			procedimento.setTratamento(new Tratamento());
			
			exibirMensagemSucesso("Adicionado");
		}else{
			exibirMensagemErro("Informe o tratamento para o dente selecionado.");
		}
	}
	
	
	/**
	 * Realiza um pré-cadastro para o paciente avulso, ele só será salvo na finalização da primeira procedimento.
	 * @return
	 */
	@Transactional
	public Pessoa cadastrarPacientePreAtendimento(){				
			pacienteDaConsulta.setTipoPessoa(TipoPessoa.PACIENTE);
		    pacienteDaConsulta.setDataNascimento(new Date());
			pessoaService.cadastrar(pacienteDaConsulta);
			criarArcadaDentaria(pacienteDaConsulta);
			return pacienteDaConsulta;
	}
	
	public void removerConsultaOperacao(){
		for (Procedimento item : listaConsultaOperacoes) {
			if(procedimento.getNumero()==item.getNumero()){
				listaConsultaOperacoes.remove(item);
				break;
			}
		}
		exibirMensagemSucesso("Removido");
	}
	
	public String getidDenteParam(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("idDente");
		
	}
	
	/**
	 * Carrega o paciente do auto complete ajax e exibe suas informações
	 * @param event
	 */
	public void carregaPaciente(SelectEvent event){  
        procedimento.setPessoa((Pessoa)event.getObject());  
        pacienteDaConsulta = procedimento.getPessoa();
	
    }
	
	@Transactional
	public void carregarListagemTratamento(){
		listagemTratamento = tratamentoService.listarTodos("Tratamento");
	}
	
	public void carregarListagemDentes(){
		if(listagemDentesPaciente.isEmpty()){
			listagemDentesPaciente = denteArcadaDentariaService.findByPaciente(pacienteDaConsulta);
		}
		
	}
	
	public List<Tratamento> getListagemTratamento() {
		return listagemTratamento;
	}
	public void setListagemTratamento(List<Tratamento> listagemTratamento) {
		this.listagemTratamento = listagemTratamento;
	}
	
	public List<DenteArcadaDentaria> getListagemDentesPaciente() {
		return listagemDentesPaciente;
	}
	
	public void setListagemDentesPaciente(List<DenteArcadaDentaria> listagemDentesPaciente) {
		this.listagemDentesPaciente = listagemDentesPaciente;
	}
	
	public Procedimento getConsulta() {
		return procedimento;
	}
	public void setConsulta(Procedimento procedimento) {
		this.procedimento = procedimento;
	}
	public List<Procedimento> getListaConsultaOperacoes() {
		return listaConsultaOperacoes;
	}
	public void setListaConsultaOperacoes(List<Procedimento> listaConsultaOperacoes) {
		this.listaConsultaOperacoes = listaConsultaOperacoes;
	}
	public Dente getDente() {
		return dente;
	}
	public void setDente(Dente dente) {
		this.dente = dente;
	}
	public Agendamento getAgendamentoDaConsulta() {
		return agendamentoDaConsulta;
	}
	public void setAgendamentoDaConsulta(Agendamento agendamentoDaConsulta) {
		this.agendamentoDaConsulta = agendamentoDaConsulta;
	}
	public Pessoa getPacienteDaConsulta() {
		return pacienteDaConsulta;
	}
	public void setPacienteDaConsulta(Pessoa pacienteDaConsulta) {
		this.pacienteDaConsulta = pacienteDaConsulta;
	}
	public PacienteAtendimento getPacienteAvulso() {
		return pacienteAvulso;
	}
	public void setPacienteAvulso(PacienteAtendimento pacienteAvulso) {
		this.pacienteAvulso = pacienteAvulso;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(String msg){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "" +msg));		  	
	}
	
	@Transactional
	public void criarArcadaDentaria(Pessoa paciente){
		
		ArcadaDentaria arcada = new ArcadaDentaria();
		
		arcada.setPaciente(paciente);
		arcada.setTipo(TipoAcarda.PERMANENTE);
		arcadaDentariaService.cadastrar(arcada);
		
		List<Dente> listaDentes = denteService.listarTodos("Dente");
		
		denteArcadaDentariaService.setListaDentes(new ArrayList<DenteArcadaDentaria>());
		
		if (listaDentes != null) {
			for (Dente dente : listaDentes) {

				DenteArcadaDentaria denteArcada = new DenteArcadaDentaria();

				denteArcada.setAcardaDentaria(arcada);
				denteArcada.setEmTratamento(false);
				denteArcada.setSituacao(1);
				denteArcada.setDente(dente);
				denteArcadaDentariaService.cadastrar(denteArcada);
			}
		}
		//caso o paciente seja do tipo avulso terá que busca a lista temporária do seus dentes.
		listagemDentesPaciente = denteArcadaDentariaService.getListaDentes();
	}
	
	
	public int getNumeroProcedimento(){
		 List<Procedimento> lista = consultaService.listarTodos("Procedimento");
		 int proximaNumeracao = lista.size()+listaConsultaOperacoes.size()+1; 
		 return proximaNumeracao;
	}
	
	public int getNumeroConsultaGeral(){
		 List<ConsultaGeral> lista = consultaGeralService.listarTodos("ConsultaGeral");
		 int proximaNumeracao = lista.size()+1; 
		 return proximaNumeracao;
	}
	
	public String getTempoAtendimento(){
		
		Date dataInicio = procedimento.getPessoa().getHorarioChegada();
		Date dataFim = new Date();
		
		long diff = dataFim.getTime() - dataInicio.getTime();

		long diffMinutes = diff / (60 * 1000) % 60;

		return diffMinutes+" Minutos.";
	   
	}
	
	public void resetConsulta(){
		procedimento = new Procedimento();
		procedimento.setConsultaGeral(new ConsultaGeral());
		procedimento.setDenteArcadaDentaria(new DenteArcadaDentaria());
		procedimento.setPessoa(new Pessoa());
		procedimento.setTratamento(new Tratamento());	
	}

}
