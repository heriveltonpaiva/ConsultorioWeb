package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.arquitetura.dao.ProcedimentoDao;
import br.arquitetura.dominio.Procedimento;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

@Service
public class ProcedimentoServiceImpl extends GenericServiceImpl<Procedimento> implements ProcedimentoService{

	private final String SEQUENCE = "procedimento_id_seq";

	public void cadastrar(Procedimento obj) {
        obj.setId(nextSequence(SEQUENCE));
        super.cadastrar(obj);
	}
	
	
	public List<Procedimento> findHistoricoByPacienteAndDente(Pessoa paciente, DenteArcadaDentaria dente) {
	    ProcedimentoDao dao = new ProcedimentoDao();
		return dao.findHistoricoByPacienteAndDente(paciente, dente);
	}
}

