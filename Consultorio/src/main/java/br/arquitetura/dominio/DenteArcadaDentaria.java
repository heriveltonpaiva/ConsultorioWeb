package br.arquitetura.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="dente_arcada_dentaria")
public class DenteArcadaDentaria {
	
	@Id
	private int id;
	private int situacao;
	
	@Column(name="em_tratamento")
	private boolean emTratamento;
	
	@ManyToOne
    @JoinColumn(name="id_dente", referencedColumnName="id")
	private Dente dente;
	
	@ManyToOne
    @JoinColumn(name="id_arcada_dentaria", referencedColumnName="id")
	private ArcadaDentaria arcadaDentaria;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public boolean isEmTratamento() {
		return emTratamento;
	}

	public void setEmTratamento(boolean emTratamento) {
		this.emTratamento = emTratamento;
	}

	public Dente getDente() {
		return dente;
	}

	public void setDente(Dente dente) {
		this.dente = dente;
	}

	public ArcadaDentaria getArcadaDentaria() {
		return arcadaDentaria;
	}

	public void setAcardaDentaria(ArcadaDentaria arcadaDentaria) {
		this.arcadaDentaria = arcadaDentaria;
	}
	
	@Override
	public String toString() {
		return String.valueOf(dente.getNumero());
	}
}
