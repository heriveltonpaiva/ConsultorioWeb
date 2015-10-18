package br.arquitetura.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.Contato;
import br.arquitetura.dominio.Endereco;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.TipoPessoa;
import br.arquitetura.dominio.Users;
import br.arquitetura.service.PessoaServiceImpl;

@Component
@Scope("session")
public class PessoaController {

	private PessoaServiceImpl pessoaService;
	private Pessoa pessoa;
    private UploadedFile file;
	private List<Pessoa> listagem;
	private int idFoto;

	public PessoaController() {
		pessoaService = new PessoaServiceImpl();
		reset();
	}
	
	public void salvarArquivo() throws IOException{
		UploadedFile arq = file;
		if(arq != null){
			pessoa.getArquivo().setDescricao(arq.getFileName());
			pessoa.getArquivo().setContentType(arq.getContentType());
			pessoa.getArquivo().setTamanho(arq.getSize());
			pessoa.getArquivo().setConteudo(arq.getContents());
		}
	}
	
	@Transactional
	public String salvar(){
		  try {
			    salvarArquivo();
			    
			    if(pessoa.getUsuario().getAutorizacao().equals("ROLE_DENTISTA")){
			    	pessoa.setTipoPessoa(TipoPessoa.DENTISTA);
			    }else if(pessoa.getUsuario().getAutorizacao().equals("ROLE_ATENDENTE")){
			    	pessoa.setTipoPessoa(TipoPessoa.ATENDENTE);
			    }
			    
				pessoaService.cadastrar(pessoa);
				exibirMensagemSucesso("Inserido");
				reset();
				carregarListagem();
		  	}catch(Exception e){
		  		exibirMensagemErro(e);
		  	}
		return null;
	}
	
	
	@Transactional
	public String editar(){
	   try{
		   pessoaService.alterar(pessoa);
		   exibirMensagemSucesso("Alterado" );
	   }catch(Exception e){
		   exibirMensagemErro(e);
	   }
		return carregarListagem();
	}
	
	@Transactional
	public String remover(){
		try{
			pessoaService.remover(pessoa);
			exibirMensagemSucesso("Removido");
		}catch(Exception e){
			exibirMensagemErro(e);
		}
		return carregarListagem();
	}
	
	@Transactional
	public String carregarListagem(){
		listagem = pessoaService.findByTipoPessoa(TipoPessoa.DENTISTA);
		listagem.addAll(pessoaService.findByTipoPessoa(TipoPessoa.ATENDENTE));
		
		return PaginasUtil.LISTAR_PESSOAS;
	}
	
	public String iniciarCadastro(){
		return PaginasUtil.CADASTRAR_PESSOAS;
	}
	
	public void reset(){
		
		listagem = new ArrayList<Pessoa>();
		listagem = pessoaService.listarTodos("Pessoa");	
		pessoa = new Pessoa();
		pessoa.setArquivo(new Arquivo());
		pessoa.setEndereco(new Endereco());
		pessoa.setContato(new Contato());
		pessoa.setUsuario(new Users());
	}
	
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public List<Pessoa> getListagem() {
		return listagem;
	}
	public void setListagem(List<Pessoa> listagem) {
		this.listagem = listagem;
	}
	public int getIdFoto() {
		return idFoto;
	}
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public void exibirMensagemErro(Exception e){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "" + e.getMessage()));		  	
	}
	
}
