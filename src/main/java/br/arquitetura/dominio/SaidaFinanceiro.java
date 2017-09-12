package br.arquitetura.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="saida_financeiro")
public class SaidaFinanceiro implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	
	private String descricao;
	
	@Column(name="data_saida")
	private Date dataSaida;
	
	@Column(name="data_cadastro")
	private Date dataCadastro;
	
	private double valor;
	
	@ManyToOne
	@JoinColumn(name="id_arquivo", referencedColumnName="id")
	private Arquivo arquivo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}
	
	
	
	
}
