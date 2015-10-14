package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.arquitetura.dominio.PacienteAtendimento;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.PessoaServiceImpl;

@Component
@Scope("session")
public class AtendimentoController {

	private PessoaServiceImpl pessoaService;
	private List<Pessoa> listaEsperaPacientes;
    private Pessoa paciente;

	public AtendimentoController() {
		listaEsperaPacientes = new ArrayList<Pessoa>();
        pessoaService = new PessoaServiceImpl();
        paciente = new Pessoa();
	}
    
	/**
	 * Carrega o paciente do auto complete ajax e exibe suas informações
	 * @param event
	 */
	public void carregarPacienteNaFilaEspera(SelectEvent event){  
		paciente = ((Pessoa)event.getObject());  
    }
	
	public void adicionarPacienteNaFilaEspera(){  
   
		int ordemDoPaciente = listaEsperaPacientes.size();
		
		cadastrarPacienteAtendimento();
		paciente.setHorarioChegada(new Date());
		paciente.setOrdemChegada(ordemDoPaciente+1);

		if(paciente.getId()!=0){
			paciente.setAtivo(true);
			listaEsperaPacientes.add(paciente);
		}else{			
			paciente.setAtivo(false);
			listaEsperaPacientes.add(paciente); 
		}
		
        paciente = new Pessoa();
	}
	
	 private void cadastrarPacienteAtendimento(){
	    	
	    	PacienteAtendimento pacienteAtendimento = new PacienteAtendimento();
	    	
	    	pacienteAtendimento.setAtendido(false);
	    	pacienteAtendimento.setDataHorario(new Date());
			pacienteAtendimento.setNomePaciente(paciente.getNome());
	        pacienteAtendimento.setObservacao(paciente.getObservacao());
	        pacienteAtendimento.setDesistencia(false);
	      
	    	if(paciente.getId()==0){
	    		pacienteAtendimento.setPaciente(null);
	    	}else{
	    		pacienteAtendimento.setPaciente(paciente);
	    	}
	    	
	    }
	 
	 
	    public List<Pessoa> getListaEsperaPacientes() {
			return listaEsperaPacientes;
		}
	    public void setListaEsperaPacientes(List<Pessoa> listaEsperaPacientes) {
			this.listaEsperaPacientes = listaEsperaPacientes;
		}
	    public Pessoa getPaciente() {
			return paciente;
		}
	    public void setPaciente(Pessoa paciente) {
			this.paciente = paciente;
		}
	    
}
