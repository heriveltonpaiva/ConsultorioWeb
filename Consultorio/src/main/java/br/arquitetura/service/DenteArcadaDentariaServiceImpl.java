package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dao.DenteArcadaDentariaDao;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

@Service
public class DenteArcadaDentariaServiceImpl extends GenericServiceImpl<DenteArcadaDentaria> implements DenteArcadaDentariaService {

	private final String SEQUENCE = "dente_arcada_dentaria_id_seq";

	public void cadastrar(DenteArcadaDentaria obj) {
        obj.setId(nextSequence(SEQUENCE));
        super.cadastrar(obj);
	}
	
	@Transactional
	public List<DenteArcadaDentaria> findByPaciente(Pessoa paciente) {
		DenteArcadaDentariaDao dao = new DenteArcadaDentariaDao();
		return dao.findByPaciente(paciente);
	}
}
