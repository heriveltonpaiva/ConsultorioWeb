package br.arquitetura.service;

import org.springframework.stereotype.Service;

import br.arquitetura.dominio.Agendamento;

@Service
public class AgendamentoServiceImpl extends GenericServiceImpl<Agendamento> implements AgendamentoService{

	private final String SEQUENCE = "agendamento_id_seq";
	private final String SEQUENCE_NUMERO_AGENDAMENTO = "numero_agendamento";

	public void cadastrar(Agendamento obj) {
        obj.setId(nextSequence(SEQUENCE));
		super.cadastrar(obj);
	}

	public Integer gerarNumeroAgendamento() {
		return super.nextSequence(SEQUENCE_NUMERO_AGENDAMENTO);
	}
	
	
	
}
