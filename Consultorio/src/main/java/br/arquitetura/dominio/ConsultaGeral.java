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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agendamento == null) ? 0 : agendamento.hashCode());
		result = prime * result + ((dataConsulta == null) ? 0 : dataConsulta.hashCode());
		result = prime * result + id;
		result = prime * result + numeroProtocolo;
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + parcelas;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tempoAtendimento == null) ? 0 : tempoAtendimento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorPago);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorTotal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConsultaGeral other = (ConsultaGeral) obj;
		if (agendamento == null) {
			if (other.agendamento != null)
				return false;
		} else if (!agendamento.equals(other.agendamento))
			return false;
		if (dataConsulta == null) {
			if (other.dataConsulta != null)
				return false;
		} else if (!dataConsulta.equals(other.dataConsulta))
			return false;
		if (id != other.id)
			return false;
		if (numeroProtocolo != other.numeroProtocolo)
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (parcelas != other.parcelas)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tempoAtendimento == null) {
			if (other.tempoAtendimento != null)
				return false;
		} else if (!tempoAtendimento.equals(other.tempoAtendimento))
			return false;
		if (Double.doubleToLongBits(valorPago) != Double.doubleToLongBits(other.valorPago))
			return false;
		if (Double.doubleToLongBits(valorTotal) != Double.doubleToLongBits(other.valorTotal))
			return false;
		return true;
	}
    

    
    
}
