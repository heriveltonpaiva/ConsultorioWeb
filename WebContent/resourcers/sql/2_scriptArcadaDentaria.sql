-- Criar Tabela Dente 
  CREATE TABLE dente
(
  id serial NOT NULL,
  numero integer NOT NULL,
  localizacao character varying NOT NULL, -- Indica sua localização, seja ela na parte inferior ou superior
  CONSTRAINT id_dente_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dente
  OWNER TO postgres;
COMMENT ON TABLE dente
  IS 'Essa tabela serve só como referência para a tabela DenteArcadaDentaria, pois cada paciente posuirá todos os dentes presente nessa tabela.';
COMMENT ON COLUMN dente.localizacao IS 'Indica sua localização, seja ela na parte inferior ou superior ';

-- Criar Tabela Arcada Dentária
CREATE TABLE arcada_dentaria
(
  id serial NOT NULL,
  tipo integer NOT NULL, -- Identifica se a acarda dentária e de 32 ou 24 dentes
  id_pessoa integer,
  CONSTRAINT id_acarda_pk PRIMARY KEY (id),
  CONSTRAINT pessoa_fk FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE arcada_dentaria
  OWNER TO postgres;
COMMENT ON TABLE arcada_dentaria
  IS 'Ao iniciar um procedimento, caso seja a primeira vez a arcada dentária do paciente será criada. ';
COMMENT ON COLUMN arcada_dentaria.tipo IS 'Identifica se a acarda dentária e de 32 ou 24 dentes';

-- Criar Tabela Dente Arcada Dentária

CREATE TABLE dente_arcada_dentaria
(
  id serial NOT NULL,
  situacao integer,
  em_tratamento boolean,
  id_arcada_dentaria integer,
  id_dente integer,
  CONSTRAINT id_arcada_dentaria_pk PRIMARY KEY (id),
  CONSTRAINT arcada_dentaria_fk FOREIGN KEY (id_arcada_dentaria)
      REFERENCES arcada_dentaria (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT dente_fk FOREIGN KEY (id_dente)
      REFERENCES dente (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dente_arcada_dentaria
  OWNER TO postgres;
COMMENT ON TABLE dente_arcada_dentaria
  IS 'Para cada paciente que teve um procedimento realizado, ele possui todos os dente na sua arcada dentária, pois os procedimentos nos dentes, acarretarão em modificações nessa entidade.';
