package br.arquitetura.service;

import java.util.List;

import br.arquitetura.dominio.Medicacao;
import br.arquitetura.dominio.PacienteAtendimento;
import br.arquitetura.dominio.Pessoa;
/**
 * 
 * @author Herivelton
 *
 */
public interface PessoaService {

	public void salvarPacientePreAtendimento(Pessoa obj);
	
	public List<Medicacao> findMedicacaoByPaciente(Pessoa paciente);
	
	public void salvarPacienteAtendimento(PacienteAtendimento paciente);
	
	public List<Pessoa> findByTipoPessoa(int tipo);
}
