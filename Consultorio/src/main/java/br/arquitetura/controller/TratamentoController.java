package br.arquitetura.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.CategoriaTratamento;
import br.arquitetura.dominio.Tratamento;
import br.arquitetura.service.CategoriaTratamentoServiceImpl;
import br.arquitetura.service.TratamentoServiceImpl;

@Component
@Scope("session")
public class TratamentoController {

	private CategoriaTratamentoServiceImpl categoriaTratamentoService;
	private TratamentoServiceImpl tratamentoService;
    private Tratamento tratamento;
	private List<Tratamento> listagem;
	private List<CategoriaTratamento> listagemCategoria;

	public TratamentoController() {
		categoriaTratamentoService = new CategoriaTratamentoServiceImpl();
		tratamentoService = new TratamentoServiceImpl();
		tratamento = new Tratamento();
		tratamento.setCategoriaTratamento(new CategoriaTratamento());
		listagem = new ArrayList<Tratamento>();
		listagemCategoria = new ArrayList<CategoriaTratamento>();
		carregarListagemCategoria();
		}
	
	@Transactional
	public String salvar(){
		  try {
				tratamentoService.cadastrar(tratamento);
				exibirMensagemSucesso("Inserido");
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return PaginasUtil.CADASTRAR_TRATAMENTO;
	}
	
	@Transactional
	public String editar(){
	   try{
		   tratamentoService.alterar(tratamento);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			tratamentoService.remover(tratamento);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	@Transactional
	public String carregarListagem(){
		listagem = tratamentoService.listarTodos("Tratamento");
		return PaginasUtil.LISTAR_TRATAMENTO;
	}
	
	@Transactional
	public void carregarListagemCategoria(){
		listagemCategoria = categoriaTratamentoService.listarTodos("CategoriaTratamento");
	}
	
	
	public String iniciarCadastro(){
		tratamentoService = new TratamentoServiceImpl();
		tratamento = new Tratamento();
		tratamento.setCategoriaTratamento(new CategoriaTratamento());
		
		carregarListagemCategoria();
		
		return PaginasUtil.CADASTRAR_TRATAMENTO;
	}
	
	public TratamentoServiceImpl getTratamentoService() {
		return tratamentoService;
	}

	public void setTratamentoService(TratamentoServiceImpl tratamentoService) {
		this.tratamentoService = tratamentoService;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public List<Tratamento> getListagem() {
		return listagem;
	}

	public void setListagem(List<Tratamento> listagem) {
		this.listagem = listagem;
	}
	public List<CategoriaTratamento> getListagemCategoria() {
		return listagemCategoria;
	}
	public void setListagemCategoria(List<CategoriaTratamento> listagemCategoria) {
		this.listagemCategoria = listagemCategoria;
	}

	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
}
