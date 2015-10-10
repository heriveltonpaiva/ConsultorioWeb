package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.arquitetura.dominio.Consulta;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.ConsultaServiceImpl;

@Component
public class HistoricoConsultaController {
 
	private Pessoa paciente;
	private ConsultaServiceImpl consultaService;
	private List<Consulta> listHistoricoConsultas;
	
	public HistoricoConsultaController() {
		paciente = new Pessoa();
		consultaService = new ConsultaServiceImpl();
		listHistoricoConsultas = new ArrayList<Consulta>();
	}
	
	public void exibirHistoricoPaciente(){
	     listHistoricoConsultas = consultaService.findHistoricoByPacienteAndDente(paciente, null);
	}
	
	public List<Consulta> getListHistoricoConsultas() {
		return listHistoricoConsultas;
	}
	public void setListHistoricoConsultas(List<Consulta> listHistoricoConsultas) {
		this.listHistoricoConsultas = listHistoricoConsultas;
	}
	public Pessoa getPaciente() {
		return paciente;
	}
	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}
}
