CREATE DATABASE db_sistema;

USE db_sistema;

CREATE TABLE tb_estado (
  codigo int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(45) DEFAULT NULL,
  sigla char(2) DEFAULT NULL,
  situacao char(1) DEFAULT 'A',
  PRIMARY KEY (codigo),
  UNIQUE KEY nome_UNIQUE (nome),
  UNIQUE KEY sigla_UNIQUE (sigla)
);

CREATE TABLE tb_cidade (
  codigo int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(45) DEFAULT NULL,
  estado int(11) DEFAULT NULL,
  situacao char(1) DEFAULT 'A',
  PRIMARY KEY (codigo),
  KEY fk_estado_idx (estado),
  CONSTRAINT fk_estado FOREIGN KEY (estado) REFERENCES tb_estado (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION
);






