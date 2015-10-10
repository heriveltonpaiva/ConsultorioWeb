package br.arquitetura.service;

import br.arquitetura.dominio.Agendamento;

public interface AgendamentoService extends GenericService<Agendamento>{
	
	public Integer gerarNumeroAgendamento();

}
