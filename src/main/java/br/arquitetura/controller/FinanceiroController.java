package br.arquitetura.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.EntradaFinanceiro;
import br.arquitetura.dominio.SaidaFinanceiro;
import br.arquitetura.service.ConsultaGeralServiceImpl;
import br.arquitetura.utils.PaginasUtil;

@Component
@Scope("session")
public class FinanceiroController {

	private ConsultaGeralServiceImpl consultaGeralService;
	private SaidaFinanceiro saidaFinanceiro;
	private List<SaidaFinanceiro> listaSaidaFinanceiro;
	private List<EntradaFinanceiro> listaEntradaFinanceiro;
    private UploadedFile file;
    private StreamedContent fileDownload;
    private double totalValor;
    private double totalSaida;
    private double totalValorPago;
    private double totalPendencia; 
    
    private Date dataInicio;
    private Date dataFinal;
    
	public FinanceiroController() {
	  saidaFinanceiro = new SaidaFinanceiro();
	  listaSaidaFinanceiro = new ArrayList<SaidaFinanceiro>();
	  listaEntradaFinanceiro = new ArrayList<EntradaFinanceiro>();
	  consultaGeralService = new ConsultaGeralServiceImpl();
	  carregarListaEntrada();
	  carregarListaSaida();
	  getListaEntradaFinanceiro();
	  getListaSaidaFinanceiro();
	  
	}
	
	@Transactional
	public String cadastrarSaida(){
		saidaFinanceiro.setDataCadastro(new Date());
		saidaFinanceiro.setDataSaida(new Date());
	   	consultaGeralService.cadastrarSaidaFinanceiro(saidaFinanceiro);
	    exibirMensagemSucesso("Inserido");
	    carregarListaSaida();
	    getListaSaidaFinanceiro();
	    saidaFinanceiro = new SaidaFinanceiro();
	    
		return PaginasUtil.SAIDA_FINANCEIRO;
	}
	
	public void carregarListaEntrada(){
		listaEntradaFinanceiro = consultaGeralService.findAllEntradaFinanceiro(dataInicio, dataFinal);
		getListaEntradaFinanceiro();
	}
	
	public void carregarListaSaida(){
		listaSaidaFinanceiro = consultaGeralService.findAllSaidaFinanceiro(null, null);
		getListaSaidaFinanceiro();
	}
	
	public void salvarArquivo() throws IOException{
		UploadedFile arq = file;
		if(arq != null){
			saidaFinanceiro.setArquivo(new Arquivo());
			saidaFinanceiro.getArquivo().setDescricao(arq.getFileName());
			saidaFinanceiro.getArquivo().setContentType(arq.getContentType());
			saidaFinanceiro.getArquivo().setTamanho(arq.getSize());
			saidaFinanceiro.getArquivo().setConteudo(arq.getContents());
		}
	}
	
	public void download() {  
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(saidaFinanceiro.getArquivo().getDescricao());  
        fileDownload = new DefaultStreamedContent(stream, saidaFinanceiro.getArquivo().getDescricao());  
    } 
	
	public SaidaFinanceiro getSaidaFinanceiro() {
		return saidaFinanceiro;
	}
	public void setSaidaFinanceiro(SaidaFinanceiro saidaFinanceiro) {
		this.saidaFinanceiro = saidaFinanceiro;
	}
	public List<SaidaFinanceiro> getListaSaidaFinanceiro() {
		totalSaida = 0;
		for (SaidaFinanceiro saida : listaSaidaFinanceiro) {
		  totalSaida += saida.getValor();
		}
		return listaSaidaFinanceiro;
	}
	public void setListaSaidaFinanceiro(List<SaidaFinanceiro> listaSaidaFinanceiro) {
		this.listaSaidaFinanceiro = listaSaidaFinanceiro;
	}
	public List<EntradaFinanceiro> getListaEntradaFinanceiro() {
		    totalValor = 0;
		    totalValorPago = 0;
		for (EntradaFinanceiro entrada : listaEntradaFinanceiro) {
			totalValor += entrada.getValorTotal();
			totalValorPago += entrada.getValorPago();
		}
		return listaEntradaFinanceiro;
	}
	public void setListaEntradaFinanceiro(List<EntradaFinanceiro> listaEntradaFinanceiro) {
		totalValor = 0;
		totalValorPago = 0;
		for (EntradaFinanceiro entrada : listaEntradaFinanceiro) {
			totalValor += entrada.getValorTotal();
			totalValorPago += entrada.getValorPago();
		}
		this.listaEntradaFinanceiro = listaEntradaFinanceiro;
	}
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public StreamedContent getFileDownload() {
		download();
		return fileDownload;
	}
	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}
	
	public void exibirMensagemSucesso(String operacao){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Registro "+operacao+" com Sucesso!"));
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getTotalValor() {
		return NumberFormat.getCurrencyInstance().format(totalValor);
	}
	public String getTotalValorPago() {
		return NumberFormat.getCurrencyInstance().format(totalValorPago);
	}
	public String getTotalPendencia() {
		totalPendencia = totalValor - totalValorPago;
		 return NumberFormat.getCurrencyInstance().format(totalPendencia);
	}
	public String getTotalSaida() {
		return NumberFormat.getCurrencyInstance().format(totalSaida);
	}

}
