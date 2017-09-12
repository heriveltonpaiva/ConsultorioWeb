package br.arquitetura.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.CategoriaTratamento;
@Service
@Transactional
public class CategoriaTratamentoServiceImpl extends GenericServiceImpl<CategoriaTratamento> implements CategoriaTratamentoService{

    private final String SEQUENCE = "categoria_tratamento_id_seq";

	@Override
	public void cadastrar(CategoriaTratamento obj) {
        obj.setId(nextSequence(SEQUENCE));
		super.cadastrar(obj);
	}
	
}
