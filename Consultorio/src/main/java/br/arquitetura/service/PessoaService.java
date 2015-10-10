package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.Medicacao;
import br.arquitetura.dominio.Pessoa;
/**
 * 
 * @author Herivelton
 *
 */
public interface PessoaService {

	public List<Medicacao> findMedicacaoByPaciente(Pessoa paciente);
}
