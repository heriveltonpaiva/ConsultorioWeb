package br.arquitetura.dominio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="agendamento")
public class Agendamento implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private int numero;
	@Column(name="data_agendamento")
	private Date dataAgendamento;
	
	@Column(name="data_anterior")
	private Date dataAnterior;
	
	private boolean confirmado;
	private boolean cancelado;
	
	@Column(name="sequencia_confirmado")
	private int sequenciaConfirmado;
	
	@Column(name="sequencia_cancelado")
	private int sequenciaCancelado;
	
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name="id_expediente", referencedColumnName="id")
	private Expediente expediente;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa", referencedColumnName="id")
	private Pessoa pessoa;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Date getDataAnterior() {
		return dataAnterior;
	}

	public void setDataAnterior(Date dataAnterior) {
		this.dataAnterior = dataAnterior;
	}

	public boolean isConfirmado() {
		return confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public int getSequenciaConfirmado() {
		return sequenciaConfirmado;
	}

	public void setSequenciaConfirmado(int sequenciaConfirmado) {
		this.sequenciaConfirmado = sequenciaConfirmado;
	}

	public int getSequenciaCancelado() {
		return sequenciaCancelado;
	}

	public void setSequenciaCancelado(int sequenciaCancelado) {
		this.sequenciaCancelado = sequenciaCancelado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getDataAgendamentoString(){
		String dataAgendamento = new SimpleDateFormat("dd/MM/yyyy").format(this.dataAgendamento);
		return dataAgendamento;
	}
	
	
}
