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
import br.arquitetura.dominio.Consulta;
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
import br.arquitetura.service.ConsultaServiceImpl;
import br.arquitetura.service.DenteArcadaDentariaServiceImpl;
import br.arquitetura.service.DenteServiceImpl;
import br.arquitetura.service.PessoaServiceImpl;
import br.arquitetura.service.TratamentoServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("session")
public class ConsultaController {
	
	private Consulta consulta;
	List<Consulta> listaConsultaOperacoes;
	private ConsultaServiceImpl consultaService;
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
	    if(consulta == null){
	    	resetConsulta();
	    }
		consultaService = new ConsultaServiceImpl();
		denteArcadaDentariaService = new  DenteArcadaDentariaServiceImpl();
	    consultaGeralService = new ConsultaGeralServiceImpl();
	    pessoaService = new PessoaServiceImpl();
	    arcadaDentariaService = new ArcadaDentariaServiceImpl();
	    denteService = new DenteServiceImpl();
	    
		listagemTratamento = new ArrayList<Tratamento>();
		tratamentoService = new TratamentoServiceImpl();
		listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
		pacienteAvulso = new PacienteAtendimento();
		listaConsultaOperacoes = new ArrayList<Consulta>();
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
			consulta.setPessoa(pacienteDaConsulta);
		}
		carregarListagemDentes();
		return PaginasUtil.CONSULTA_PASSO_2;
	}
	
	public String iniciarPasso3(){
		
		//se o paciente for avulso, finalizar a consulta diretamente.
		if(pacienteDaConsulta.getId()==0){
		
			pacienteAvulso.setNomePaciente(pacienteDaConsulta.getNome());
			pacienteAvulso.setAtendido(true);
			pacienteAvulso.setDataHorario(new Date());
			return PaginasUtil.CONSULTA_PASSO_3;
		}
		
		double valorTotalConsulta = 0.0;
		//Pacientes que não passaram pela realização de procedimentos
		if(!listaConsultaOperacoes.isEmpty()){
			for (Consulta consulta : listaConsultaOperacoes) {
				  valorTotalConsulta += consulta.getValor();
			}
		}
		
		consulta.setConsultaGeral(new ConsultaGeral());
		consulta.getConsultaGeral().setAgendamento(agendamentoDaConsulta);
		consulta.getConsultaGeral().setDataConsulta(new Date());
		consulta.getConsultaGeral().setNumeroProtocolo(getNumeroConsultaGeral());
		consulta.getConsultaGeral().setValorTotal(valorTotalConsulta);
		consulta.getConsultaGeral().setValorPago(valorTotalConsulta);
		consulta.setPessoa(pacienteDaConsulta);
		
		return PaginasUtil.CONSULTA_PASSO_3;
	}
	
	@Transactional
	public String finalizarConsulta() {

		if (pacienteDaConsulta.getId() == 0) {
			return cadastrarPacientesAvulsos();
		}

		cadastrarAvulsosAposProcedimento();
		consultaGeralService.cadastrar(consulta.getConsultaGeral());

		if (!listaConsultaOperacoes.isEmpty()) {
			registrarConsultaPacientesAposProcedimento();

		} else {
			registrarConsultaPaciente();
		}

		pacienteDaConsulta = new Pessoa();
		listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
		listaConsultaOperacoes = new ArrayList<Consulta>();
		resetConsulta();

		exibirMensagemSucesso("finalizada");
		return PaginasUtil.PAGINA_INICIAL;
	}


    /**
     * Salva a consulta do paciente que finalizou a consulta de forma direta, sem passar pela
     * realização de procedimentos.
     */
	private void registrarConsultaPaciente() {
		//Para finalização de consulta sem procedimento.
		 consulta.setValor(consulta.getConsultaGeral().getValorTotal());
		 consulta.setNumero(getNumeroProcedimento());
		 consulta.setTratamento(null);
		 consulta.setPessoa(pacienteDaConsulta);
		 consulta.setDenteArcadaDentaria(null);
		 
		 consultaService.cadastrar(consulta);
		 cadastrarAtendimentoPaciente();
	}


	/**
	 * salva a consulta dos paciente que estão cadastrandos na base de pacientes após
	 * realização de um procedimento.
	 */
	private void registrarConsultaPacientesAposProcedimento() {
		for (Consulta consulta : listaConsultaOperacoes) {
			    consulta.setConsultaGeral(new ConsultaGeral());
			    consulta.setConsultaGeral(this.consulta.getConsultaGeral());
			    consulta.setPessoa(pacienteDaConsulta);
			    consultaService.cadastrar(consulta);	
		}
		
		 cadastrarAtendimentoPaciente();
	}


   /**
    * Faz o cadastro dos pacientes avulsos após a realização do procedimento, a partir da finalização da consulta
    * esse paciente vai pertencer a tabela de pacientes.
    */
	private void cadastrarAvulsosAposProcedimento() {
		//Para pacientes cadastrados no momento da consulta (PACIENTES AVULSOS).
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
		if(consulta.getTratamento().getId()>0){
			consulta.setTratamento(tratamentoService.listarPorId("Tratamento", consulta.getTratamento().getId()));	    
			consulta.setNumero(getNumeroProcedimento());
			consulta.setDenteArcadaDentaria(dente);
			consulta.getDenteArcadaDentaria().setEmTratamento(false);
			consulta.getDenteArcadaDentaria().setSituacao(1);
			
			listaConsultaOperacoes.add(consulta);
			
			consulta = new Consulta();
			consulta.setPessoa(pacienteDaConsulta);
			consulta.setDenteArcadaDentaria(new DenteArcadaDentaria());
			consulta.setTratamento(new Tratamento());
			
			exibirMensagemSucesso("Adicionado");
		}else{
			exibirMensagemErro("Informe o tratamento para o dente selecionado.");
		}
	}
	
	
	/**
	 * Realiza um pré-cadastro para o paciente avulso, ele só será salvo na finalização da primeira consulta.
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
		for (Consulta item : listaConsultaOperacoes) {
			if(consulta.getNumero()==item.getNumero()){
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
        consulta.setPessoa((Pessoa)event.getObject());  
        pacienteDaConsulta = consulta.getPessoa();
	
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
	
	public Consulta getConsulta() {
		return consulta;
	}
	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}
	public List<Consulta> getListaConsultaOperacoes() {
		return listaConsultaOperacoes;
	}
	public void setListaConsultaOperacoes(List<Consulta> listaConsultaOperacoes) {
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
		 List<Consulta> lista = consultaService.listarTodos("Consulta");
		 int proximaNumeracao = lista.size()+listaConsultaOperacoes.size()+1; 
		 return proximaNumeracao;
	}
	
	public int getNumeroConsultaGeral(){
		 List<ConsultaGeral> lista = consultaGeralService.listarTodos("ConsultaGeral");
		 int proximaNumeracao = lista.size()+1; 
		 return proximaNumeracao;
	}
	
	public String getTempoAtendimento(){
		
		Date dataInicio = consulta.getPessoa().getHorarioChegada();
		Date dataFim = new Date();
		
		long diff = dataFim.getTime() - dataInicio.getTime();

		long diffMinutes = diff / (60 * 1000) % 60;

		return diffMinutes+" Minutos.";
	   
	}
	
	public void resetConsulta(){
		consulta = new Consulta();
		consulta.setConsultaGeral(new ConsultaGeral());
		consulta.setDenteArcadaDentaria(new DenteArcadaDentaria());
		consulta.setPessoa(new Pessoa());
		consulta.setTratamento(new Tratamento());	
	}

}
