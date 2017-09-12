package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Expediente;
import br.arquitetura.service.ExpedienteServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("request")
public class ExpedienteController {

	private ExpedienteServiceImpl expedienteService;
    private Expediente expediente;
	private List<Expediente> listagem;
	
	public ExpedienteController() {
		expedienteService = new ExpedienteServiceImpl();
		listagem = new ArrayList<Expediente>();
		expediente = new Expediente();
		listagem = expedienteService.listarTodos("Expediente");

	}
	
	@Transactional
	public String salvar(){
		  try {
				expedienteService.cadastrar(expediente);
				exibirMensagemSucesso("Inserido");
				carregarListagem();
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return null;
	}
	
	@Transactional
	public String editar(){
	   try{
		   expedienteService.alterar(expediente);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			expedienteService.remover(expediente);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	
	@Transactional
	public String carregarListagem(){
		listagem = expedienteService.listarTodos("Expediente");
		return PaginasUtil.LISTAR_EXPEDIENTE;
	}
	
	public String iniciarCadastro(){
		return PaginasUtil.CADASTRAR_EXPEDIENTE;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public List<Expediente> getListagem() {
		return listagem;
	}
	public void setListagem(List<Expediente> listagem) {
		this.listagem = listagem;
	}

	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
}
