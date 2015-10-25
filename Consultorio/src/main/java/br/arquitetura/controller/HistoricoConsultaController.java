package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.arquitetura.dominio.Procedimento;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.ProcedimentoServiceImpl;

@Component
public class HistoricoConsultaController {
 
	private Pessoa paciente;
	private ProcedimentoServiceImpl consultaService;
	private List<Procedimento> listHistoricoConsultas;
	
	public HistoricoConsultaController() {
		paciente = new Pessoa();
		consultaService = new ProcedimentoServiceImpl();
		listHistoricoConsultas = new ArrayList<Procedimento>();
	}
	
	public void exibirHistoricoPaciente(){
	     listHistoricoConsultas = consultaService.findHistoricoByPacienteAndDente(paciente, null);
	}
	
	public List<Procedimento> getListHistoricoConsultas() {
		return listHistoricoConsultas;
	}
	public void setListHistoricoConsultas(List<Procedimento> listHistoricoConsultas) {
		this.listHistoricoConsultas = listHistoricoConsultas;
	}
	public Pessoa getPaciente() {
		return paciente;
	}
	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}
}
