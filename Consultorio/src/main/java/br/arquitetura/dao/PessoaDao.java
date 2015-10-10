package br.arquitetura.dao;

import java.util.List;

import org.hibernate.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.Contato;
import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Endereco;
import br.arquitetura.dominio.Medicacao;
import br.arquitetura.dominio.Pessoa;

@Repository
@Transactional
public class PessoaDao extends GenericDaoImpl{

	private static final long serialVersionUID = 1L;

	@Transactional		
	public void salvarArquivoPessoa(Arquivo arquivo) {
			try{
				getSession().save(arquivo);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	@Transactional		
	public void salvarEnderecoPessoa(Endereco endereco) {
			try{
				getSession().save(endereco);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	@Transactional		
	public void salvarContatoPessoa(Contato contato) {
			try{
				getSession().save(contato);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public List<Medicacao> findMedicacaoByPaciente(Pessoa paciente){
		
		String hql = "From Medicacao m where m.paciente.id = :idPaciente"; 
		
			Query q = getSession().createQuery(hql);
			q.setParameter("idPaciente", paciente.getId());		
			return (List<Medicacao>) q.list();
	}
	
}

