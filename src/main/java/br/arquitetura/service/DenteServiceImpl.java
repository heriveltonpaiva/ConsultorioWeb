package br.arquitetura.service;

import org.springframework.stereotype.Service;

import br.arquitetura.dominio.Dente;

@Service
public class DenteServiceImpl extends GenericServiceImpl<Dente> implements DenteService{
	 private final String SEQUENCE = "dente_id_seq";

		@Override
		public void cadastrar(Dente obj) {
	        obj.setId(nextSequence(SEQUENCE));
	        super.cadastrar(obj);
		}

		
}
