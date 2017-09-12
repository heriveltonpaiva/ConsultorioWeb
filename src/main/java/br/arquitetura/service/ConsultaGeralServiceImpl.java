package br.arquitetura.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.arquitetura.dao.ConsultaGeralDao;
import br.arquitetura.dao.GenericDaoImpl;
import br.arquitetura.dominio.Arquivo;
import br.arquitetura.dominio.ConsultaGeral;
import br.arquitetura.dominio.EntradaFinanceiro;
import br.arquitetura.dominio.Pessoa;
import br.arquitetura.dominio.SaidaFinanceiro;

@Service
@Transactional
public class ConsultaGeralServiceImpl extends GenericServiceImpl<ConsultaGeral> implements ConsultaGeralService{

	private final String SEQUENCE = "consulta_geral_id_seq";
    private final String SEQUENCE_ARQUIVO = "arquivo_id_seq";
    private final String SEQUENCE_SAIDA = "saida_financeiro_id_seq";


	public void cadastrar(ConsultaGeral obj) {
        obj.setId(nextSequence(SEQUENCE));
        super.cadastrar(obj);
	}
	
	public List<EntradaFinanceiro> findAllEntradaFinanceiro(Date dataInicio, Date dataFinal){
		ConsultaGeralDao dao = new ConsultaGeralDao();
		return dao.findAllEntradaFinanceiro(dataInicio, dataFinal);
	}
	
	public List<SaidaFinanceiro> findAllSaidaFinanceiro(Date dataInicio, Date dataFinal){
		ConsultaGeralDao dao = new ConsultaGeralDao();
		return dao.findAllSaidaFinanceiro(dataInicio, dataFinal);
	}
	

	public void cadastrarSaidaFinanceiro(SaidaFinanceiro obj) {
		GenericDaoImpl dao = new GenericDaoImpl();

		if(obj.getArquivo()!=null){
	        obj.getArquivo().setId(nextSequence(SEQUENCE_ARQUIVO));
	        dao.getSession().save(obj.getArquivo());
		}else{
			obj.setArquivo(null);
		}
		obj.setId(nextSequence(SEQUENCE_SAIDA));
		try{
		dao.getSession().save(obj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
