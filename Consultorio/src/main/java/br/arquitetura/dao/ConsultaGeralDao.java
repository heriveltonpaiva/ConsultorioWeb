package br.arquitetura.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dominio.EntradaFinanceiro;
import br.arquitetura.dominio.SaidaFinanceiro;

@Repository
@Transactional
public class ConsultaGeralDao extends GenericDaoImpl{
	
	private static final long serialVersionUID = 1L;

	
	@SuppressWarnings("unchecked")
	public List<EntradaFinanceiro> findAllEntradaFinanceiro(Date dataInicio, Date dataFinal){
		
		String hql = "select p.nome as nomePaciente, cg.numeroProtocolo as numeroConsulta, cg.dataConsulta as dataConsulta, "
					+ "	cg.valorTotal as valorTotal, cg.valorPago as valorPago, cg.parcelas as parcelamento "
					+ " From Procedimento c join c.pessoa p join c.consultaGeral cg ";
		if(dataInicio!=null && dataFinal != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			hql+= "WHERE cg.dataConsulta between '"+sdf.format(dataInicio)+"' and '"+sdf.format(dataFinal)+"'";	
		}
		return getSession().createQuery(hql).setResultTransformer(new AliasToBeanResultTransformer(EntradaFinanceiro.class)).list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SaidaFinanceiro> findAllSaidaFinanceiro(Date dataInicio, Date dataFinal){
		
		String hql = "from SaidaFinanceiro";
		Query q = getSession().createQuery(hql);
		
		if(dataInicio != null && dataFinal != null){
			q.setParameter("dataInicio", dataInicio);
			q.setParameter("dataFinal", dataFinal);
		}
		
		return (List<SaidaFinanceiro>) q.list();
	}
}
