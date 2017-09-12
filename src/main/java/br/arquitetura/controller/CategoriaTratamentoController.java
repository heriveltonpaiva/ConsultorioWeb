package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.CategoriaTratamento;
import br.arquitetura.service.CategoriaTratamentoServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("session")
public class CategoriaTratamentoController{

	private CategoriaTratamentoServiceImpl categoriaTratamentoService;
    private CategoriaTratamento categoriaTratamento;
	private List<CategoriaTratamento> listagem;
	
	public CategoriaTratamentoController() {
		categoriaTratamentoService = new CategoriaTratamentoServiceImpl();
		listagem = new ArrayList<CategoriaTratamento>();
		listagem = categoriaTratamentoService.listarTodos("CategoriaTratamento");
		categoriaTratamento = new CategoriaTratamento();
	}
	
	@Transactional
	public String salvar(){
		  try {
				categoriaTratamentoService.cadastrar(categoriaTratamento);
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
		   categoriaTratamentoService.alterar(categoriaTratamento);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			categoriaTratamentoService.remover(categoriaTratamento);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	
	@Transactional
	public String carregarListagem(){
		listagem = categoriaTratamentoService.listarTodos("CategoriaTratamento");
		return PaginasUtil.LISTAR_CATEGORIA_TRATAMENTO;
	}
	
	public String iniciarCadastro(){
		return PaginasUtil.CADASTRAR_CATEGORIA_TRATAMENTO;
	}
	
	public CategoriaTratamento getCategoriaTratamento() {
		return categoriaTratamento;
	}
	public void setCategoriaTratamento(CategoriaTratamento categoriaTratamento) {
		this.categoriaTratamento = categoriaTratamento;
	}
	public List<CategoriaTratamento> getListagem() {
		return listagem;
	}
	public void setListagem(List<CategoriaTratamento> listagem) {
		this.listagem = listagem;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
}
