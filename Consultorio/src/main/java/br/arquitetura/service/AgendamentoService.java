package br.arquitetura.service;

import java.util.Date;
import java.util.List;

import br.arquitetura.dominio.Agendamento;

public interface AgendamentoService extends GenericService<Agendamento>{
	
	public List<Agendamento> findAllAgendamentoByPeriodo(Date dataInicio, Date dataFinal);

}
