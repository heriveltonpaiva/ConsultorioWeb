package br.arquitetura.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contato")
public class Contato implements Serializable{
   @Id
   private int id;
   private int telefone;
   private int celular;
   private String email;
   
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
   
public int getTelefone() {
	return telefone;
}

public void setTelefone(int telefone) {
	this.telefone = telefone;
}

public int getCelular() {
	return celular;
}

public void setCelular(int celular) {
	this.celular = celular;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
	
   
   
}
