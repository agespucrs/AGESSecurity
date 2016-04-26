/***
* Scripts para criacao e insersao de dados
* Base Dados Security AGES
* Casssio Trindade, Rafael Companhoni, Lucka de Souza
* 04/2016
***/
USE security_e;

-- DROP TABLE TB_TIPO_USUARIO;
-- DROP TABLE TB_USUARIO;
-- DROP TABLE TB_STAKEHOLDERS;

-- Tabela Usuario
CREATE TABLE tb_usuario (
  ID_USUARIO int(11) NOT NULL AUTO_INCREMENT,
  USUARIO varchar(45) NOT NULL,
  SENHA varchar(45) NOT NULL,
  PRIMARY KEY (ID_USUARIO),
  UNIQUE KEY USUARIO_UNIQUE (USUARIO),
  -- PERFIL_ACESSO varchar(20) NOT NULL,
  -- STATUS_USUARIO varchar(20) NOT NULL,
  -- ID_TIPO_USUARIO int(11) NOT NULL,
  -- MATRICULA varchar(45) NOT NULL,
  -- NOME varchar(120) DEFAULT NULL,
  -- EMAIL varchar(120) DEFAULT NULL,
  -- DATA_INCLUSAO datetime DEFAULT NULL,
  -- PRIMARY KEY (ID_USUARIO,MATRICULA),
  -- UNIQUE KEY MATRICULA_UNIQUE (MATRICULA),
  -- CONSTRAINT U_USERNAME UNIQUE (USUARIO)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- Tabela Tipo Usuario
-- CREATE TABLE tb_tipo_usuario (
-- D_TIPO_USUARIO int(11) NOT NULL AUTO_INCREMENT,
-- NOME varchar(20) NOT NULL,
-- DESCRICAO varchar(120) DEFAULT NULL,
-- DATA_INCLUSAO datetime DEFAULT NULL,
-- PRIMARY KEY (ID_TIPO_USUARIO)
-- );

-- Testes

INSERT INTO tb_usuario VALUES
('ADMIN', 'ADMIN');
INSERT INTO tb_usuario VALUES
('João', 'joao123');
INSERT INTO tb_usuario VALUES
('Maria', '1234');

SELECT * FROM tb_usuario;

-- Inserts
--INSERT INTO tb_tipo_usuario VALUES
--('1', 'Arquiteto', 'Responsavel pela parte tecnica', '2015-10-01 00:00:00');
--INSERT INTO tb_tipo_usuario VALUES
--('2', 'Aluno', '', '2015-10-01 00:00:00');
--INSERT INTO tb_tipo_usuario VALUES
--('3', 'Professor', '', '2015-10-01 00:00:00');
--INSERT INTO tb_tipo_usuario VALUES
--('4', 'Secretaria', '', '2015-10-01 00:00:00');

--INSERT INTO tb_usuario
--(ID_USUARIO,USUARIO,SENHA,PERFIL_ACESSO,STATUS_USUARIO,ID_TIPO_USUARIO,MATRICULA,NOME,EMAIL,DATA_INCLUSAO)
--VALUES
--('10', 'admin', 'admin', 'ADMINISTRADOR', 'ATIVO', '1', '00000', 'Cassio Trindade', 'cassio.trindade@pucrs.br', '2015-10-01 00:00:00');

--select * from tb_usuario;

 
-- Tabela Stakeholders
--CREATE TABLE tb_stakeholders (
--  ID_STAKEHOLDER int(11) NOT NULL AUTO_INCREMENT,
--  NOME_STAKEHOLDER varchar(45) NOT NULL,
--  DATA_INCLUSAO datetime DEFAULT NULL, 
--  PRIMARY KEY (ID_STAKEHOLDER)
--) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

