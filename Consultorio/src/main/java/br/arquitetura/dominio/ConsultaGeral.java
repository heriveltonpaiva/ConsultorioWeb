package br.arquitetura.dominio;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="consulta_geral")
public class ConsultaGeral implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5926273210516045991L;

	@Id
	private int id;
	
	@Column(name="num_protocolo")
	private int numeroProtocolo;
	
	@Column(name="data_consulta")
	private Date dataConsulta;
	
	@Column (name="tempo_atendimento")
	private Time tempoAtendimento;
	
	private String observacao;
	
	private String status;
	
	@Column(name="valor_total")
	private double valorTotal;
	
	@Column(name="valor_pago")
	private double valorPago;
	
	@Column(name="parcelas")
	private int parcelas;
	
    @ManyToOne
    @JoinColumn(name="id_agendamento", referencedColumnName="id")
	private Agendamento agendamento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumeroProtocolo() {
		return numeroProtocolo;
	}
	public void setNumeroProtocolo(int numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public Time getTempoAtendimento() {
		return tempoAtendimento;
	}

	public void setTempoAtendimento(Time tempoAtendimento) {
		this.tempoAtendimento = tempoAtendimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}
    public double getValorPago() {
		return valorPago;
	}
    public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
    public int getParcelas() {
		return parcelas;
	}
    public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}
    

}
