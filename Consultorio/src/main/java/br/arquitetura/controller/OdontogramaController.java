package br.arquitetura.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import br.arquitetura.dominio.Consulta;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.ConsultaServiceImpl;
/**
 * Controlador responsável pelas operações de histórico do Odontograma
 * @author Herivelton
 *
 */
@Component
public class OdontogramaController {
    
	private ConsultaServiceImpl consultaService;
	private List<Consulta> listHistoricoDentario;
	private Pessoa paciente;
	private DenteArcadaDentaria dentePaciente;
	
	public OdontogramaController() {
		
	   dentePaciente = new DenteArcadaDentaria();
	   consultaService = new ConsultaServiceImpl();
	   paciente= new Pessoa();
	   
	}
	
	/**
	 * Exibe o histórico dos procedimentos realizado no dente por paciente. 
	 */
	public void exibirHistorico(){
		listHistoricoDentario = consultaService.findHistoricoByPacienteAndDente(paciente, dentePaciente);
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
	public List<Consulta> getListHistoricoDentario() {
		return listHistoricoDentario;
	}
	public void setListHistoricoDentario(List<Consulta> listHistoricoDentario) {
		this.listHistoricoDentario = listHistoricoDentario;
	}
}
