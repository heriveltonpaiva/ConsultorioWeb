package br.arquitetura.service;

import br.arquitetura.dominio.ArcadaDentaria;

public class ArcadaDentariaServiceImpl extends GenericServiceImpl<ArcadaDentaria> implements ArcadaDentariaService {

	 private final String SEQUENCE = "arcada_dentaria_id_seq";

		public void cadastrar(ArcadaDentaria obj) {
	        obj.setId(nextSequence(SEQUENCE));
	        super.cadastrar(obj);
		}
	
}
