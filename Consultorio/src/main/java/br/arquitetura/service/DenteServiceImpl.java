package br.arquitetura.service;

import org.springframework.stereotype.Service;

import br.arquitetura.dao.DenteDao;
import br.arquitetura.dominio.Dente;

@Service
public class DenteServiceImpl extends GenericServiceImpl<Dente> implements DenteService{
	 private final String SEQUENCE = "dente_id_seq";
	 private final String SEQUENCE_ARQUIVO = "arquivo_id_seq";

		@Override
		public void cadastrar(Dente obj) {
	        
			DenteDao dao = new DenteDao();
			
	        obj.getArquivo().setId(nextSequence(SEQUENCE_ARQUIVO));
	        dao.salvarArquivoDente(obj.getArquivo());
	        
	        obj.setId(nextSequence(SEQUENCE));
	        super.cadastrar(obj);
		}

		
}
