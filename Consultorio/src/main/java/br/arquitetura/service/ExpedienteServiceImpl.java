package br.arquitetura.service;

import org.springframework.stereotype.Service;

import br.arquitetura.dominio.Expediente;

@Service
public class ExpedienteServiceImpl extends GenericServiceImpl<Expediente> implements ExpedienteService {

	private final String SEQUENCE = "expediente_id_seq";

	public void cadastrar(Expediente obj) {
        obj.setId(nextSequence(SEQUENCE));
		super.cadastrar(obj);
	}
}
