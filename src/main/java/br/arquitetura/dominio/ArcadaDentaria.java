package br.arquitetura.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="arcada_dentaria")
public class ArcadaDentaria implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	private int tipo;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa", referencedColumnName="id")
	private Pessoa paciente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPaciente() {
		return paciente;
	}

	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
