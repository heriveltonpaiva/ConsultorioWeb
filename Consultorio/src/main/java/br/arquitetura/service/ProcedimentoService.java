package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.Procedimento;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public interface ProcedimentoService {

	public List<Procedimento> findHistoricoByPacienteAndDente(Pessoa paciente, DenteArcadaDentaria dente);

}
