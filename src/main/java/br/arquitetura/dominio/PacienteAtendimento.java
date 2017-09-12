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
@Table(name="paciente_atendimento")
public class PacienteAtendimento implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	
	@Column(name="nome_paciente")
	private String nomePaciente;
	
	private String observacao;
	
	@Column(name="ordem_chegada")
	private int ordemChegada;
	
	@Column(name="horario")
	private Date dataHorario;
	
	private boolean atendido;
	
	private boolean desistencia;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa", referencedColumnName="id")
	private Pessoa paciente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getOrdemChegada() {
		return ordemChegada;
	}

	public void setOrdemChegada(int ordemChegada) {
		this.ordemChegada = ordemChegada;
	}

	public Date getDataHorario() {
		return dataHorario;
	}

	public void setDataHorario(Date dataHorario) {
		this.dataHorario = dataHorario;
	}

	public boolean isAtendido() {
		return atendido;
	}

	public void setAtendido(boolean atendido) {
		this.atendido = atendido;
	}

	public boolean isDesistencia() {
		return desistencia;
	}

	public void setDesistencia(boolean desistencia) {
		this.desistencia = desistencia;
	}

	public Pessoa getPaciente() {
		return paciente;
	}

	public void setPaciente(Pessoa paciente) {
		this.paciente = paciente;
	}
	
	
	
	
}
