package br.arquitetura.dao;


import org.hibernate.Query;

import br.arquitetura.dominio.ArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public class ArcadaDentariaDao extends GenericDaoImpl {

	private static final long serialVersionUID = 1L;

	public ArcadaDentaria findByPaciente(Pessoa paciente){
		
		String hql = "from ArcadaDentaria arc "+
		             "JOIN arc.paciente p WHERE p.id = :idPaciente";
		
		Query q = getSession().createQuery(hql);
		q.setParameter("idPaciente", paciente.getId());
		
		return (ArcadaDentaria) q.uniqueResult();
	}
	
}
