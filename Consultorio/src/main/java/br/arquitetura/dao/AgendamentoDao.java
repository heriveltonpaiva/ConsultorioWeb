package br.arquitetura.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.Agendamento;

@Repository
@Transactional
public class AgendamentoDao extends GenericDaoImpl{

	private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		public List<Agendamento> findAllAgendamentoByPeriodo(Date dataInicio, Date dataFinal){
			   
			   String hql =" From Agendamento a ";
			   
			if(dataInicio!=null && dataFinal != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				hql+= "WHERE a.dataAgendamento between '"+sdf.format(dataInicio)+"' and '"+sdf.format(dataFinal)+"'";	
			}
			return (List<Agendamento>) getSession().createQuery(hql).list();  
		}
	
}
