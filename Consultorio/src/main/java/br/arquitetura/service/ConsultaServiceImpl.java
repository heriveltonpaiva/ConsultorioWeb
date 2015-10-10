package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.arquitetura.dao.ConsultaDao;
import br.arquitetura.dominio.Consulta;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

@Service
public class ConsultaServiceImpl extends GenericServiceImpl<Consulta> implements ConsultaService{

	private final String SEQUENCE = "consulta_id_seq";

	public void cadastrar(Consulta obj) {
        obj.setId(nextSequence(SEQUENCE));
        super.cadastrar(obj);
	}
	
	
	public List<Consulta> findHistoricoByPacienteAndDente(Pessoa paciente, DenteArcadaDentaria dente) {
	    ConsultaDao dao = new ConsultaDao();
		return dao.findHistoricoByPacienteAndDente(paciente, dente);
	}
}

