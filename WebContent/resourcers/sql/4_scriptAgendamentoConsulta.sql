-- Criar Tabela Agendamento
CREATE TABLE agendamento
(
  id serial NOT NULL,
  numero integer NOT NULL,
  data_agendamento date NOT NULL,
  data_anterior date,
  confirmado boolean,
  cancelado boolean,
  sequencia_confirmado integer,
  sequencia_cancelado integer,
  observacao character varying,
  id_expediente integer,
  id_pessoa integer,
  CONSTRAINT id_agendamento_pk PRIMARY KEY (id),
  CONSTRAINT expediente_fk FOREIGN KEY (id_expediente)
      REFERENCES expediente (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT pessoa_fk FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE agendamento
  OWNER TO postgres;
COMMENT ON TABLE agendamento
  IS 'Agendamentos e reagendamentos são registrados nessa tabela';
  
-- Criar Tabela Paciente Atendimento

CREATE TABLE paciente_atendimento
(
  id serial NOT NULL,
  nome_paciente character varying,
  observacao character varying,
  ordem_chegada integer NOT NULL,
  horario date,
  atendido boolean,
  desistencia boolean,
  id_pessoa integer,
  CONSTRAINT id_paciente_atendimento PRIMARY KEY (id),
  CONSTRAINT pessoa_fk FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE paciente_atendimento
  OWNER TO postgres;
COMMENT ON TABLE paciente_atendimento
  IS 'Esta tabela foi criada para a fila de espera de atendimento, quando paciente chega ao consultório será adicionado seu nome a lista de espera, caso o paciente não possua cadastro, ele será adicionado nessa tabela. Os pacientes que entram nessa condição, são aqueles que vem solicitar orçamentos, estão com um problema, serão atendidos, mas n será feito nenhum procedimento, e o tipo de paciente avulso.';
 
 -- Criar Tabela Consulta Geral
 CREATE TABLE consulta_geral
(
  id serial NOT NULL,
  num_protocolo serial NOT NULL,
  data_consulta date NOT NULL,
  tempo_atendimento time with time zone,
  observacao character varying,
  status character varying,
  valor_total numeric,
  valor_pago numeric,
  parcelas integer,
  id_agendamento integer,
  CONSTRAINT id_consulta_geral_pk PRIMARY KEY (id),
  CONSTRAINT agendamento_fk FOREIGN KEY (id_agendamento)
      REFERENCES agendamento (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE consulta_geral
  OWNER TO postgres;
COMMENT ON TABLE consulta_geral
  IS 'Tabela geral, responsável por salvar cada consulta realizada pelo dentista';
 
-- Criar Tabela Procedimento
  CREATE TABLE procedimento
(
  id serial NOT NULL,
  numero integer NOT NULL,
  valor numeric(8,0) NOT NULL,
  observacao character varying,
  id_pessoa integer,
  id_tratamento integer,
  id_consulta_geral integer,
  id_dente_arcada_dentaria integer,
  CONSTRAINT id_consulta_pk PRIMARY KEY (id),
  CONSTRAINT consulta_geral_fk FOREIGN KEY (id_consulta_geral)
      REFERENCES consulta_geral (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT dente_arcada_dentaria_fk FOREIGN KEY (id_dente_arcada_dentaria)
      REFERENCES dente_arcada_dentaria (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT pessoa_fk FOREIGN KEY (id_pessoa)
      REFERENCES pessoa (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT tratamento_fk FOREIGN KEY (id_tratamento)
      REFERENCES tratamento (id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE procedimento
  OWNER TO postgres;
COMMENT ON TABLE procedimento
  IS 'Cada consulta possui um ou mais procedimentos, que será as operações a ser realizada nos dentes do paciente.';