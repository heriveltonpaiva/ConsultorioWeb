package br.arquitetura.service;

import org.springframework.stereotype.Service;

import br.arquitetura.dominio.Tratamento;

@Service
public class TratamentoServiceImpl extends GenericServiceImpl<Tratamento> implements TratamentoService {

	private final String SEQUENCE = "tratamento_id_seq";

	public void cadastrar(Tratamento obj) {
        obj.setId(nextSequence(SEQUENCE));
		super.cadastrar(obj);
	}
	
}
