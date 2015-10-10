package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.arquitetura.dao.ConsultaGeralDao;
import br.arquitetura.dominio.ConsultaGeral;
import br.arquitetura.dominio.Pessoa;

@Service
public class ConsultaGeralServiceImpl extends GenericServiceImpl<ConsultaGeral> implements ConsultaGeralService{

	private final String SEQUENCE = "consulta_geral_id_seq";

	public void cadastrar(ConsultaGeral obj) {
        obj.setId(nextSequence(SEQUENCE));
        super.cadastrar(obj);
	}
	
}
