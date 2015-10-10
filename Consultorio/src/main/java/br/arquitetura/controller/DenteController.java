package br.arquitetura.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.Dente;
import br.arquitetura.service.DenteServiceImpl;

@Component
@Scope("session")
public class DenteController {

	private DenteServiceImpl denteService;
    private Dente dente;
	private List<Dente> listagem;
	
	public DenteController() {
		denteService = new DenteServiceImpl();
		listagem = new ArrayList<Dente>();
		listagem = denteService.listarTodos("Dente");

		dente = new Dente();
		dente.setArquivo(new Arquivo());
	}
	
	public void uploadListener(FileUploadEvent evento) throws IOException{
		UploadedFile arq = evento.getFile();
		dente.getArquivo().setDescricao(arq.getFileName());
		dente.getArquivo().setContentType(arq.getContentType());
		dente.getArquivo().setTamanho(arq.getSize());
		dente.getArquivo().setConteudo(arq.getContents());
		
	}
	
	@Transactional
	public String salvar(){
		  try {
				denteService.cadastrar(dente);
				exibirMensagemSucesso("Inserido");
				//carregarListagem();
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return null;
	}
	
	@Transactional
	public String editar(){
	   try{
		   denteService.alterar(dente);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			denteService.remover(dente);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	
	@Transactional
	public String carregarListagem(){
		listagem = denteService.listarTodos("Dente");
		return PaginasUtil.LISTAR_DENTES;
	}
	
	public String iniciarCadastro(){
		return PaginasUtil.CADASTRAR_DENTE;
	}
	
	public Dente getDente() {
		return dente;
	}
	public void setDente(Dente dente) {
		this.dente = dente;
	}
	
	public List<Dente> getListagem() {
		return listagem;
	}
	public void setListagem(List<Dente> listagem) {
		this.listagem = listagem;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}

	
}
