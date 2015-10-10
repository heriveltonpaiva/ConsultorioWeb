package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.Consulta;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public interface ConsultaService {

	public List<Consulta> findHistoricoByPacienteAndDente(Pessoa paciente, DenteArcadaDentaria dente);

}
