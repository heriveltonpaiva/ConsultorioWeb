package br.arquitetura.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.arquitetura.dominio.PacienteAtendimento;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.service.PessoaServiceImpl;

@Component
@Scope("session")
public class AtendimentoController implements Serializable{

	private static final long serialVersionUID = 1L;
	private PessoaServiceImpl pessoaService;
	private List<Pessoa> listaEsperaPacientes;
    private Pessoa paciente;

	public AtendimentoController() {
		listaEsperaPacientes = new ArrayList<Pessoa>();
        pessoaService = new PessoaServiceImpl();
        paciente = new Pessoa();
        paciente.setAtendimento(new PacienteAtendimento());
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
		
		if(!listaEsperaPacientes.contains(paciente)){
		
			cadastrarPacienteAtendimento();
			
			paciente.setHorarioChegada(new Date());
			paciente.setOrdemChegada(ordemDoPaciente+1);
	
			if(paciente.getId()!=0){
				paciente.setAtivo(true);
				paciente.setAtendimento(new PacienteAtendimento());
				paciente.getAtendimento().setDataHorario(new Date());
				paciente.getAtendimento().setOrdemChegada(ordemDoPaciente+1);
				paciente.getAtendimento().setDesistencia(false);
				paciente.getAtendimento().setAtendido(false);
				paciente.setMedicacoes(pessoaService.findMedicacaoByPaciente(paciente));

				listaEsperaPacientes.add(paciente);
			}else{			
				
				//Para pacientes cadastrados na base de dados.
				paciente.setAtivo(false);
				paciente.setAtendimento(new PacienteAtendimento());
				paciente.getAtendimento().setDataHorario(new Date());
				paciente.getAtendimento().setOrdemChegada(ordemDoPaciente+1);
				paciente.getAtendimento().setDesistencia(false);
				paciente.getAtendimento().setAtendido(false);
				
				listaEsperaPacientes.add(paciente); 
			}
		}else{
			exibirMensagemErro("O Paciente "+paciente.getNome()+" já encontra-se na fila de atendimento.");
		}
		
        paciente = new Pessoa();
        paciente.setAtendimento(new PacienteAtendimento());
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
	 
	 
	 public void confirmarDesistenciaPaciente(){
		 PacienteAtendimento atendimento = paciente.getAtendimento();
		 atendimento.setPaciente(paciente);
		 atendimento.setNomePaciente(paciente.getNome());
		 atendimento.setObservacao(paciente.getObservacao());
		 atendimento.setAtendido(false);
		 atendimento.setDesistencia(true);
		 pessoaService.salvarPacienteAtendimento(atendimento);
		 
		 paciente = new Pessoa();
	     paciente.setAtendimento(new PacienteAtendimento());
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
	    
		public void exibirMensagemSucesso(String operacao){
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
		}
		public void exibirMensagemErro(String mensagem){
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""+mensagem));		  	
		}
		
}
