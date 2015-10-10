package br.arquitetura.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="consulta")
public class Consulta implements Serializable{

	private static final long serialVersionUID = 1899994452284253908L;
	@Id
	private int id;
	private int numero;
	private Double valor; 
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name="id_dente_arcada_dentaria", referencedColumnName="id")
	private DenteArcadaDentaria denteArcadaDentaria;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa", referencedColumnName="id")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name="id_tratamento", referencedColumnName="id")
	private Tratamento tratamento;
	
	@ManyToOne
	@JoinColumn(name="id_consulta_geral", referencedColumnName="id")
	private ConsultaGeral consultaGeral;

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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public DenteArcadaDentaria getDenteArcadaDentaria() {
		return denteArcadaDentaria;
	}
	public void setDenteArcadaDentaria(DenteArcadaDentaria denteArcadaDentaria) {
		this.denteArcadaDentaria = denteArcadaDentaria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Tratamento getTratamento() {
		return tratamento;
	}

	public void setTratamento(Tratamento tratamento) {
		this.tratamento = tratamento;
	}

	public ConsultaGeral getConsultaGeral() {
		return consultaGeral;
	}

	public void setConsultaGeral(ConsultaGeral consultaGeral) {
		this.consultaGeral = consultaGeral;
	}
	
}
