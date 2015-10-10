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
import br.arquitetura.dominio.Consulta;
import br.arquitetura.dominio.ConsultaGeral;
import br.arquitetura.dominio.Dente;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.Tratamento;
import br.arquitetura.service.ConsultaGeralServiceImpl;
import br.arquitetura.service.ConsultaServiceImpl;
import br.arquitetura.service.DenteArcadaDentariaServiceImpl;
import br.arquitetura.service.DenteServiceImpl;
import br.arquitetura.service.TratamentoServiceImpl;

@Component
@Scope("session")
public class ConsultaController {
	
	private Consulta consulta;
	List<Consulta> listaConsultaOperacoes;
	private ConsultaServiceImpl consultaService;
	private ConsultaGeralServiceImpl consultaGeralService;
	private DenteArcadaDentariaServiceImpl denteArcadaDentariaService;
	private TratamentoServiceImpl tratamentoService;
	private List<Tratamento> listagemTratamento;
	private Dente dente;
	private DenteServiceImpl denteService;
	private List<DenteArcadaDentaria> listagemDentesPaciente;
	private Agendamento agendamentoDaConsulta;
	private Pessoa pacienteDaConsulta;
	
	
	public ConsultaController() {
	    if(consulta == null){
		consulta = new Consulta();
		consulta.setConsultaGeral(new ConsultaGeral());
		consulta.setDenteArcadaDentaria(new DenteArcadaDentaria());
		consulta.setPessoa(new Pessoa());
		consulta.setTratamento(new Tratamento());
	    }
		consultaService = new ConsultaServiceImpl();
		denteArcadaDentariaService = new  DenteArcadaDentariaServiceImpl();
	    consultaGeralService = new ConsultaGeralServiceImpl();
	    
		listagemTratamento = new ArrayList<Tratamento>();
		tratamentoService = new TratamentoServiceImpl();
		listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
	}

	
	
	public String iniciarPasso1(){
		return PaginasUtil.CONSULTA_PASSO_1;
	}
	
	public String iniciarPasso2(){
		
		carregarListagemTratamento();
		dente = new Dente();
		carregarListagemDentes();
		consulta.setPessoa(pacienteDaConsulta);
		
		listaConsultaOperacoes = new ArrayList<Consulta>();

		return PaginasUtil.CONSULTA_PASSO_2;
	}
	
	public String iniciarPasso3(){
		
		double valorTotalConsulta = 0.0;
		
		for (Consulta consulta : listaConsultaOperacoes) {
			  valorTotalConsulta += consulta.getValor();
		}
		consulta.setConsultaGeral(new ConsultaGeral());
		consulta.getConsultaGeral().setAgendamento(agendamentoDaConsulta);
		consulta.getConsultaGeral().setDataConsulta(new Date());
		consulta.getConsultaGeral().setNumeroProtocolo(21321);
		//consulta.getConsultaGeral().setTempoAtendimento();
		consulta.getConsultaGeral().setValorTotal(valorTotalConsulta);
		
		return PaginasUtil.CONSULTA_PASSO_3;
	}
	
	@Transactional
	public String finalizarConsulta(){
		  
		  consultaGeralService.cadastrar(consulta.getConsultaGeral());
		for (Consulta consulta : listaConsultaOperacoes) {
			    consulta.setConsultaGeral(new ConsultaGeral());
			    consulta.setConsultaGeral(this.consulta.getConsultaGeral());
				consultaService.cadastrar(consulta);			
		}
		
		exibirMensagemSucesso("finalizada");
		return PaginasUtil.PAGINA_INICIAL;
	}

	
	public void adicionarConsultaOperacao(){
		FacesContext fc = FacesContext.getCurrentInstance();
		int numeroDente = Integer.parseInt(getidDenteParam(fc));
		DenteArcadaDentaria dente = null;
		for (DenteArcadaDentaria d : listagemDentesPaciente){		
			//if(d.getNumero()==idDente){
				dente = d;
				break;
			//}
				
		}
		consulta.setTratamento(tratamentoService.listarPorId("Tratamento", consulta.getTratamento().getId()));
	    
		
		
		consulta.setDenteArcadaDentaria(dente);
		consulta.getDenteArcadaDentaria().setEmTratamento(false);
		consulta.getDenteArcadaDentaria().setSituacao(1);
		
		listaConsultaOperacoes.add(consulta);
		
		consulta = new Consulta();
		consulta.setPessoa(pacienteDaConsulta);
		consulta.setDenteArcadaDentaria(new DenteArcadaDentaria());
		consulta.setTratamento(new Tratamento());
		
		exibirMensagemSucesso("Adicionado");
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
		listagemDentesPaciente = denteArcadaDentariaService.findByPaciente(pacienteDaConsulta);
		if(listagemDentesPaciente == null){
			
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
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
	
}
