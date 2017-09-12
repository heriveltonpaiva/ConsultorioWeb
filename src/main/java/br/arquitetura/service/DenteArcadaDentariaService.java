package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public interface DenteArcadaDentariaService {

	public List<DenteArcadaDentaria> findByPaciente(Pessoa paciente);
	
	public List<DenteArcadaDentaria> getListaDentes();
	
	public void setListaDentes(List<DenteArcadaDentaria> listaDentes);
	
	public void salvarDentesPreAtendimento(List<DenteArcadaDentaria> dentes);
}
