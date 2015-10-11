package br.arquitetura.service;

import java.util.Date;
import java.util.List;

import br.arquitetura.dominio.EntradaFinanceiro;
import br.arquitetura.dominio.SaidaFinanceiro;

public interface ConsultaGeralService {

	public List<EntradaFinanceiro> findAllEntradaFinanceiro(Date dataInicio, Date dataFinal);
	
	public List<SaidaFinanceiro> findAllSaidaFinanceiro(Date dataInicio, Date dataFinal);
	
	public void cadastrarSaidaFinanceiro(SaidaFinanceiro saida);

}
