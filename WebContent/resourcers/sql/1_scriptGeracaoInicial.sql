-- Criar Base de Dados
CREATE DATABASE "Consultorio"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Portuguese_Brazil.1252'
       LC_CTYPE = 'Portuguese_Brazil.1252'
       CONNECTION LIMIT = -1;

COMMENT ON DATABASE "Consultorio"
  IS 'Consultório Web - Sistema para Controle e Gerência Odontológica.';
  
  -- Cria Tabela Users para Spring Security
CREATE TABLE users
(
  id serial NOT NULL,
  username character varying NOT NULL,
  password character varying NOT NULL,
  authority character varying,
  CONSTRAINT id_users_pk PRIMARY KEY (id),
  CONSTRAINT username UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;
COMMENT ON TABLE users
  IS 'Usuários para o perfil atendente e dentista.';
 
 -- Cria Tabela Categoria do Tratamento
  CREATE TABLE categoria_tratamento
(
  id serial NOT NULL,
  descricao character varying,
  CONSTRAINT id_categoria_tratamento_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE categoria_tratamento
  OWNER TO postgres;
COMMENT ON TABLE categoria_tratamento
  IS 'Essa tabela trata das especialidades de tratamentos';
  
  -- Cria Tabela do Tratamento
  CREATE TABLE tratamento
(
  id serial NOT NULL,
  descricao character varying NOT NULL,
  id_categoria_tratamento integer,
  CONSTRAINT id_tratamento_pk PRIMARY KEY (id),
  CONSTRAINT categoria_tratamento_fk FOREIGN KEY (id_categoria_tratamento)
      REFERENCES categoria_tratamento (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tratamento
  OWNER TO postgres;

-- Criar Tabela Expediente
  CREATE TABLE expediente
(
  id serial NOT NULL,
  dia integer NOT NULL,
  hora_inicio time with time zone,
  hora_final time with time zone,
  CONSTRAINT id_expediente_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE expediente
  OWNER TO postgres;
COMMENT ON TABLE expediente
  IS 'Horário e dia em que o dentista atende.';
  

-- Criar Tabela Arquivo 
CREATE TABLE arquivo
(
  id serial NOT NULL,
  descricao character varying NOT NULL,
  content_type character varying NOT NULL,
  conteudo bytea NOT NULL,
  tamanho numeric,
  CONSTRAINT id_arquivo_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE arquivo
  OWNER TO postgres;
-- Criar Tabela Saída Financeiro 
CREATE TABLE saida_financeiro
(
  id serial NOT NULL,
  descricao character varying NOT NULL,
  valor numeric NOT NULL,
  data_saida date,
  data_cadastro date NOT NULL,
  id_arquivo integer,
  CONSTRAINT id_saida_financeiro PRIMARY KEY (id),
  CONSTRAINT arquivo_fk FOREIGN KEY (id_arquivo)
      REFERENCES arquivo (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE saida_financeiro
  OWNER TO postgres;
COMMENT ON TABLE saida_financeiro
  IS 'Tabela responsável por registrar as saídas da clínica.';
  
  
  
