-- Criar Tabela Contato

CREATE TABLE contato
(
  id serial NOT NULL,
  telefone integer,
  celular integer,
  email character varying,
  CONSTRAINT id_contato_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE contato
  OWNER TO postgres;
  
-- Criar Tabela Endereço

  CREATE TABLE endereco
(
  id serial NOT NULL,
  rua character varying,
  numero integer,
  cidade character varying,
  estado character varying,
  CONSTRAINT id_endereco_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE endereco
  OWNER TO postgres;
  
-- Criar Tabela Pessoa
  CREATE TABLE pessoa
(
  id serial NOT NULL,
  nome character varying NOT NULL,
  nome_mae character varying,
  nome_pai character varying,
  nacionalidade character varying,
  cpf character varying,
  rg character varying,
  data_nascimento date,
  estado_civil character varying,
  sexo character varying,
  naturalidade character varying,
  ativo boolean,
  observacao character varying,
  tipo_pessoa integer NOT NULL,
  id_endereco integer,
  id_contato integer,
  id_users integer,
  id_arquivo integer,
  CONSTRAINT id_pessoa_pk PRIMARY KEY (id),
  CONSTRAINT arquivo_fk FOREIGN KEY (id_arquivo)
      REFERENCES arquivo (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT contato_fk FOREIGN KEY (id_contato)
      REFERENCES contato (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT endereco_fk FOREIGN KEY (id_endereco)
      REFERENCES endereco (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT users_fk FOREIGN KEY (id_users)
      REFERENCES users (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa
  OWNER TO postgres;
COMMENT ON TABLE pessoa
  IS 'Tabela central, que serve para cadastrar pessoas (Pacientes, Dentistas e Atendentes)';
  