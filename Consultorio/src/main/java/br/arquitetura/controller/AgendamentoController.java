package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Agendamento;
import br.arquitetura.dominio.Expediente;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.TipoPessoa;
import br.arquitetura.service.AgendamentoServiceImpl;
import br.arquitetura.service.ExpedienteServiceImpl;
import br.arquitetura.service.PessoaServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("session")
public class AgendamentoController {

	private AgendamentoServiceImpl agendamentoService;
	private ExpedienteServiceImpl expedienteService;
	private PessoaServiceImpl pessoaService;
    private Agendamento agendamento;
    private int idPessoa;
    private int idExpediente;
	private List<Agendamento> listagem;
    private List<Pessoa> listagemPacientes;
	private List<Expediente> listagemExpedientes;
    private Date dataInicio;
    private Date dataFinal;
	
	public AgendamentoController() {
		agendamentoService = new AgendamentoServiceImpl();
		agendamento = new Agendamento();
		carregarListagem();
		agendamento.setNumero(getNumeroAgendamento());
		agendamento.setPessoa(new Pessoa());
		agendamento.setExpediente(new Expediente());
		pessoaService = new PessoaServiceImpl();
		expedienteService = new ExpedienteServiceImpl();
		
		listagem = new ArrayList<Agendamento>();
		listagem = agendamentoService.listarTodos("Agendamento");

		listagemPacientes = new ArrayList<Pessoa>();
		listagemPacientes = pessoaService.listarTodos("Pessoa");

		listagemExpedientes = new ArrayList<Expediente>();
		listagemExpedientes = expedienteService.listarTodos("Expediente");

		}
	
	@Transactional
	public String salvar(){
		  try {
			  	agendamento.getPessoa().setId(idPessoa);
			  	agendamento.getExpediente().setId(idExpediente);
				agendamentoService.cadastrar(agendamento);
				exibirMensagemSucesso("Inserido");
				agendamento = new Agendamento();
				agendamento.setNumero(getNumeroAgendamento());
				agendamento.setPessoa(new Pessoa());
				agendamento.setExpediente(new Expediente());
				
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return PaginasUtil.CADASTRAR_AGENDAMENTO;
	}
	
	@Transactional
	public String editar(){
	   try{
		   agendamentoService.alterar(agendamento);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			agendamentoService.remover(agendamento);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	@Transactional
	public String carregarListagem(){
		listagem = agendamentoService.findAllAgendamentoByPeriodo(dataInicio, dataFinal);

		return PaginasUtil.LISTAR_AGENDAMENTOS;
	}
	
	@Transactional
	public void carregarListagemPacientes(){
		listagemPacientes = pessoaService.findByTipoPessoa(TipoPessoa.PACIENTE);
	}
	@Transactional
	public void carregarListagemExpedientes(){
		listagemExpedientes = expedienteService.listarTodos("Expediente");
	}
	
	public String iniciarCadastro(){
		agendamentoService = new AgendamentoServiceImpl();
		agendamento = new Agendamento();
        agendamento.setPessoa(new Pessoa());
        agendamento.setExpediente(new Expediente());
        agendamento.setNumero(getNumeroAgendamento());
        
        carregarListagemExpedientes();
        carregarListagemPacientes();
		
		return PaginasUtil.CADASTRAR_AGENDAMENTO;
	}
	
    public Agendamento getAgendamento() {
		return agendamento;
	}
    public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
	
    public List<Expediente> getListagemExpedientes() {
		return listagemExpedientes;
	}
    public void setListagemExpedientes(List<Expediente> listagemExpedientes) {
		this.listagemExpedientes = listagemExpedientes;
	}
    public void setListagemPacientes(List<Pessoa> listagemPacientes) {
		this.listagemPacientes = listagemPacientes;
	}
    public List<Pessoa> getListagemPacientes() {
		return listagemPacientes;
    }
    public void setListagem(List<Agendamento> listagem) {
		this.listagem = listagem;
	}
    public List<Agendamento> getListagem() {
		return listagem;
	}
    
    public int getIdPessoa() {
		return idPessoa;
	}
    public void setIdPessoa(int idPessoa) {
		this.idPessoa = idPessoa;
	}
    public void setIdExpediente(int idExpediente) {
		this.idExpediente = idExpediente;
	}
    public int getIdExpediente() {
		return idExpediente;
	}
    public Date getDataFinal() {
		return dataFinal;
	}
    public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
    public Date getDataInicio() {
		return dataInicio;
	}
    public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
    
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
	
	public int getNumeroAgendamento(){
		 int proximaNumeracao = listagem.size()+1; 
		 return proximaNumeracao;
	}
}
