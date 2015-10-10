package br.arquitetura.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.DenteArcadaDentaria;
import br.arquitetura.dominio.Pessoa;

public class DenteArcadaDentariaDao extends GenericDaoImpl{

	private static final long serialVersionUID = 1L;

	@Transactional
	public List<DenteArcadaDentaria> findByPaciente(Pessoa paciente){
			
			String hql = "select da From DenteArcadaDentaria da "+
			             " JOIN da.arcadaDentaria arc "+
			             " JOIN arc.paciente p WHERE p.id = :idPaciente";
			
			Query q = getSession().createQuery(hql);
			q.setParameter("idPaciente", paciente.getId());
			
			return (List<DenteArcadaDentaria>) q.list();
	}
	

}
