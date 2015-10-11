package br.arquitetura.dominio;

import java.util.Date;

/**
 * Classe auxiliar para entrada de R$
 * @author Herivelton
 *
 */
public class EntradaFinanceiro {

   
   private String nomePaciente;
   private int numeroConsulta;
   private Date dataConsulta;
   private double valorTotal;
   private int parcelamento;
   private double valorPago;
   private double saldoDevedor;
   
	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public int getNumeroConsulta() {
		return numeroConsulta;
	}

	public void setNumeroConsulta(int numeroConsulta) {
		this.numeroConsulta = numeroConsulta;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public int getParcelamento() {
		return parcelamento;
	}

	public void setParcelamento(int parcelamento) {
		this.parcelamento = parcelamento;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}  	
	
	public void setSaldoDevedor(double saldoDevedor) {
		this.saldoDevedor = saldoDevedor;
	}
	public double getSaldoDevedor(){
		saldoDevedor = (valorTotal - valorPago);
		return saldoDevedor;
	}
}
