package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.arquitetura.dominio.Procedimento;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.ProcedimentoServiceImpl;
import br.arquitetura.service.DenteArcadaDentariaServiceImpl;
/**
 * Controlador responsável pelas operações de histórico do Odontograma
 * @author Herivelton
 *
 */
@Component
@Scope("session")
public class OdontogramaController {
    
	private ProcedimentoServiceImpl consultaService;
	private List<Procedimento> listHistoricoDentario;
	private Pessoa paciente;
	private DenteArcadaDentaria dentePaciente;
	private List<DenteArcadaDentaria> listagemDentesPaciente;
	private DenteArcadaDentariaServiceImpl denteArcadaDentariaService;

	
	public OdontogramaController() {
		
	   dentePaciente = new DenteArcadaDentaria();
	   consultaService = new ProcedimentoServiceImpl();
	   paciente= new Pessoa();
	   listagemDentesPaciente = new ArrayList<DenteArcadaDentaria>();
	   denteArcadaDentariaService = new DenteArcadaDentariaServiceImpl();
	}
	
	/**
	 * Exibe o histórico dos procedimentos realizado no dente por paciente. 
	 */
	public void exibirHistorico(){
		FacesContext fc = FacesContext.getCurrentInstance();
		if(getidDenteParam(fc)!=null){
		int numeroDente = Integer.parseInt(getidDenteParam(fc));
		
		//Recupera o Id do paciente do Passo 2 do procedimento para exibição do histórico do dente.
		if(paciente.getId() == 0){
			paciente.setId(Integer.parseInt(getidPacienteParam(fc)));
		}
		
			listagemDentesPaciente = denteArcadaDentariaService.findByPaciente(paciente);
	
			for (DenteArcadaDentaria d : listagemDentesPaciente){		
				if(d.getDente().getNumero()==numeroDente){
					dentePaciente = d;
					break;
				}
					
			}
		}
		
		listHistoricoDentario = consultaService.findHistoricoByPacienteAndDente(paciente, dentePaciente);
		
		if(!listHistoricoDentario.isEmpty()){
			if(dentePaciente.getId()==0){
				exibirMensagemAlerta(dentePaciente.getDente().getNumero());
			}
			
		}else{
			if(dentePaciente.getId()>0){
				exibirMensagemAlerta(dentePaciente.getDente().getNumero());
			}else{
				exibirMensagemAlertaPadrao();
			}
		}
		   dentePaciente = new DenteArcadaDentaria();

	}
	
	public Pessoa getPaciente() {
		return paciente;
	}
	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}
	public DenteArcadaDentaria getDentePaciente() {
		return dentePaciente;
	}
	public void setDentePaciente(DenteArcadaDentaria dentePaciente) {
		this.dentePaciente = dentePaciente;
	}
	public List<Procedimento> getListHistoricoDentario() {
		return listHistoricoDentario;
	}
	public void setListHistoricoDentario(List<Procedimento> listHistoricoDentario) {
		this.listHistoricoDentario = listHistoricoDentario;
	}
	
	public String getidDenteParam(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		if(params.get("idDente")==null){
			return null;
		}
		return params.get("idDente");
		
	}
	
	public String getidPacienteParam(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		if(params.get("idPaciente")==null){
			return null;
		}
		return params.get("idPaciente");
	}
	
	public List<DenteArcadaDentaria> getListagemDentesPaciente() {
		return listagemDentesPaciente;
	}
	public void setListagemDentesPaciente(List<DenteArcadaDentaria> listagemDentesPaciente) {
		this.listagemDentesPaciente = listagemDentesPaciente;
	}
	
	public void exibirMensagemAlerta(int idDente){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum procedimento realizado para o dente de N° "+idDente+".", "" ));		  	
	}
	
	public void exibirMensagemAlertaPadrao(){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhum procedimento realizado para esse paciente.", "" ));		  	
	}
	
	
}
