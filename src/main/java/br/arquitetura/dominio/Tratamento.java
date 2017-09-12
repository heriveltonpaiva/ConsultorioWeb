package br.arquitetura.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="tratamento")
public class Tratamento implements Serializable{
	
	private static final long serialVersionUID = -4253604410734365894L;
	
	@NotNull
	@Id
	private int id;
	
	@NotNull(message="Campo descrição não pode ser vazio.")
	@Size(min=10, max=50, message="Campo descrição deve ter o tamanho entre 10 e 50.")
	private String descricao;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name="id_categoria_tratamento", referencedColumnName="id")
	private CategoriaTratamento categoriaTratamento;

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

	public CategoriaTratamento getCategoriaTratamento() {
		return categoriaTratamento;
	}

	public void setCategoriaTratamento(CategoriaTratamento categoriaTratamento) {
		this.categoriaTratamento = categoriaTratamento;
	}
	
	
	
}
