package br.arquitetura.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.arquitetura.dao.GenericDaoImpl;
import br.arquitetura.dao.PessoaDao;
import br.arquitetura.dominio.Medicacao;
import br.arquitetura.dominio.PacienteAtendimento;
import br.arquitetura.dominio.Pessoa;

@Service
public class PessoaServiceImpl extends GenericServiceImpl<Pessoa> implements PessoaService {


	 private final String SEQUENCE = "pessoa_id_seq";
	 private final String SEQUENCE_ARQUIVO = "arquivo_id_seq";
	 private final String SEQUENCE_ENDERECO = "endereco_id_seq";
	 private final String SEQUENCE_CONTATO = "contato_id_seq";
	 private final String SEQUENCE_PACIENTE_AVULSO = "paciente_atendimento_id_seq";

		public void cadastrar(Pessoa obj) {
	        
			PessoaDao dao = new PessoaDao();
			
			 dao.getSession().getTransaction().begin();
			
			 if(obj.getArquivo()!=null){
				if(obj.getArquivo().getDescricao()!=null){
			        obj.getArquivo().setId(nextSequence(SEQUENCE_ARQUIVO));
			        dao.salvarArquivoPessoa(obj.getArquivo());
				}else{
					obj.setArquivo(null);
				}
			}
			if(obj.getEndereco()!=null){
		        obj.getEndereco().setId(nextSequence(SEQUENCE_ENDERECO));
		        dao.salvarEnderecoPessoa(obj.getEndereco());
			}
			if(obj.getContato()!=null){
				if(obj.getContato().getCelular() >0 || obj.getContato().getTelefone() >0 || !obj.getContato().getEmail().equals("")){
			        obj.getContato().setId(nextSequence(SEQUENCE_CONTATO));;
			        dao.salvarContatoPessoa(obj.getContato());
				}else{
					obj.setContato(null);
				}
			}
	        obj.setId(nextSequence(SEQUENCE));
	        super.cadastrar(obj);
	        
	        dao.getSession().getTransaction().commit();
		}
		
		/**
		 * Salva o paciente na base de dados pré cadastrado, no passo de finalização da consulta.
		 * @param obj
		 */
		public void salvarPacientePreAtendimento(Pessoa obj){
			PessoaDao dao = new PessoaDao();
            dao.cadastrar(obj);
		}
		
		public List<Medicacao> findMedicacaoByPaciente(Pessoa paciente){
			PessoaDao dao = new PessoaDao();
			return dao.findMedicacaoByPaciente(paciente);
		}
		
		public void salvarPacienteAvulso(PacienteAtendimento paciente){
			GenericDaoImpl genericDao = new GenericDaoImpl();
			paciente.setId(nextSequence(SEQUENCE_PACIENTE_AVULSO));
			genericDao.cadastrar(paciente);
		}

}
