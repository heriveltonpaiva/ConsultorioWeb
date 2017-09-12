package br.arquitetura.dominio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="expediente")
public class Expediente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	private int dia;
	@Column(name = "hora_inicio")
	private Date horaInicio;
	@Column(name = "hora_final")
	private Date horaFinal;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Date getHoraFinal() {
		return horaFinal;
	}
	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Expediente other = (Expediente) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public String getDiaExpediente(){		
		if(this.dia==1)
			return "Segunda-Feira";
		if(this.dia==2)
			return "Terça-Feira";
		if(this.dia==3)
			return "Quarta-Feira";
		if(this.dia==4)
			return "Quinta-Feira";
		if(this.dia==5)
			return "Sexta-Feira";
		if(this.dia==6)
			return "Sabádo";
		
		return null;
	}
	
	public String getHorarios(){
		
		String horaInicio = new SimpleDateFormat("HH:mm").format(this.horaInicio);
		String horaFinal = new SimpleDateFormat("HH:mm").format(this.horaFinal);

		return horaInicio+" ás "+horaFinal;
	}
	
	
	public String toString() {
		return getDiaExpediente() +": "+getHorarios();
	}
	
}
