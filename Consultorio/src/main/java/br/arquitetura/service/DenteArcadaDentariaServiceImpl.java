package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dao.DenteArcadaDentariaDao;
import br.arquitetura.dao.GenericDaoImpl;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

@Service
public class DenteArcadaDentariaServiceImpl extends GenericServiceImpl<DenteArcadaDentaria> implements DenteArcadaDentariaService {

	private final String SEQUENCE = "dente_arcada_dentaria_id_seq";
    private List<DenteArcadaDentaria> listaDentes = null;
    
	public void cadastrar(DenteArcadaDentaria obj) {
        obj.setId(nextSequence(SEQUENCE));
        //listaDentes.add(obj);
        super.cadastrar(obj);
	}
	
	@Transactional
	public List<DenteArcadaDentaria> findByPaciente(Pessoa paciente) {
		DenteArcadaDentariaDao dao = new DenteArcadaDentariaDao();
		return dao.findByPaciente(paciente);
	}

	public List<DenteArcadaDentaria> getListaDentes() {
		return listaDentes;
	}
	public void setListaDentes(List<DenteArcadaDentaria> listaDentes) {
		this.listaDentes = listaDentes;
	}

	public void salvarDentesPreAtendimento(List<DenteArcadaDentaria> dentes){
	    GenericDaoImpl genericDao = new GenericDaoImpl();
		for (DenteArcadaDentaria dente : dentes) {
			genericDao.cadastrar(dente);
		}
	}
	
}
