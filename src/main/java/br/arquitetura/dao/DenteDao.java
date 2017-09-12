package br.arquitetura.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Arquivo;

@Repository
@Transactional
public class DenteDao extends GenericDaoImpl{

	private static final long serialVersionUID = 1L;

	@Transactional		
	public void salvarArquivoDente(Arquivo arquivo) {
			try{
				getSession().save(arquivo);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
