package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public interface DenteArcadaDentariaService {

	public List<DenteArcadaDentaria> findByPaciente(Pessoa paciente);
	
}
