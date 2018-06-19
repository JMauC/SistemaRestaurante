CREATE ROLE restaurante LOGIN
  ENCRYPTED PASSWORD 'md5365803233722b28b710adb138bd3bb14'
  SUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;


CREATE DATABASE restaurante
  WITH OWNER = restaurante
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'pt_BR.UTF-8'
       LC_CTYPE = 'pt_BR.UTF-8'
       CONNECTION LIMIT = -1;

-- Tabela de clientes
CREATE TABLE public.clientes(
  cpf VARCHAR(11) PRIMARY KEY,
  nome VARCHAR(50) NOT null,
  rg VARCHAR(20),
  data_nascimento DATE,
  filiacao_pai VARCHAR(50),
  filiacao_mae VARCHAR(50),
  naturalidade VARCHAR(20),
  estado_civil VARCHAR(12),
  sexo_masculino BOOLEAN NOT null,
  telefone VARCHAR(12),
  email VARCHAR(30),
  endereco VARCHAR(50),
  escolaridade VARCHAR(20),
  profissao VARCHAR(20),
);
ALTER TABLE public.clientes
  OWNER TO restaurante;

-- Tabela de cargos
CREATE TABLE public.cargos(
  codigo INTEGER PRIMARY KEY,
  nome VARCHAR(20) NOT null
);
ALTER TABLE public.cargos
  OWNER TO restaurante;

-- Tabela de funcionarios
CREATE TABLE public.funcionarios(
  cpf VARCHAR(11) PRIMARY KEY,
  nome VARCHAR(50) NOT null,
  rg VARCHAR(20),
  data_nascimento DATE,
  filiacao_pai VARCHAR(50),
  filiacao_mae VARCHAR(50),
  naturalidade VARCHAR(20),
  estado_civil VARCHAR(12),
  sexo_masculino BOOLEAN NOT null,
  telefone VARCHAR(12),
  email VARCHAR(30),
  endereco VARCHAR(50),
  escolaridade VARCHAR(20),
  ctps VARCHAR(20) NOT null,
  salario NUMERIC(10,2) NOT null DEFAULT 0.0,
  turno_diurno BOOLEAN NOT null DEFAULT true,
  senha VARCHAR(20) NOT null,
  cargo INTEGER REFERENCES public.cargos(codigo)
);
ALTER TABLE public.funcionarios
  OWNER TO restaurante;

-- Tabela de fornecedores
CREATE TABLE public.fornecedores(
  codigo SERIAL,
  cnpj VARCHAR(14) PRIMARY KEY,
  razao_social VARCHAR(30) NOT NULL,
  telefone VARCHAR(12),
  endereco VARCHAR(50),
  email VARCHAR(30)
);
ALTER TABLE public.fornecedores
  OWNER TO restaurante;

-- Tabela de mesas
CREATE TABLE public.mesas(
  codigo SERIAL PRIMARY KEY,
  andar INTEGER NOT null,
  ocupada BOOLEAN NOT null DEFAULT false,
  cpf_ocupante VARCHAR(11) DEFAULT null
);
ALTER TABLE public.mesas
  OWNER TO restaurante;

-- Tabela de ingredientes
CREATE TABLE public.ingredientes(
  codigo SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT null,
  qtd_estoque INTEGER NOT null DEFAULT 0
);
ALTER TABLE public.ingredientes
  OWNER TO restaurante;

-- Tabela de produtos
CREATE TABLE public.produtos(
  codigo SERIAL PRIMARY KEY,
  nome VARCHAR(50) NOT null,
  preco NUMERIC(10,2) NOT null DEFAULT 0.0,
  bebida BOOLEAN NOT null
);
ALTER TABLE public.produtos
  OWNER TO restaurante;

-- Tabela de pedidos
CREATE TABLE public.pedidos(
  codigo SERIAL PRIMARY KEY,
  cod_mesa INTEGER NOT null REFERENCES public.mesas(codigo),
  cpf_cliente VARCHAR(11),
  cpf_garcom VARCHAR(11) NOT null REFERENCES public.funcionarios(cpf),
  preco_total NUMERIC(10,2) NOT null DEFAULT 0.0,
  pedido_pronto BOOLEAN DEFAULT false,
  pedido_pago BOOLEAN DEFAULT false
);
ALTER TABLE public.pedidos
  OWNER TO restaurante;

-- Tabela que relaciona produtos e ingredientes
CREATE TABLE public.produto_ingrediente(
  cod_produto INTEGER REFERENCES public.produtos(codigo),
  cod_ingrediente INTEGER REFERENCES public.ingredientes(codigo),
  qtd_ingrediente INTEGER NOT null,
  PRIMARY KEY (cod_produto, cod_ingrediente)
);
ALTER TABLE public.produto_ingrediente
  OWNER TO restaurante;

-- Tabela que relaciona pedidos e produtos
CREATE TABLE public.pedido_produto(
  cod_pedido INTEGER REFERENCES public.pedidos(codigo),
  cod_produto INTEGER REFERENCES public.produtos(codigo),
  qtd_produto INTEGER NOT null,
  pronto BOOLEAN NOT null DEFAULT false,
  PRIMARY KEY (cod_pedido, cod_produto)
);
ALTER TABLE public.pedido_produto
  OWNER TO restaurante;

-- Tabela de registro de pagamentos de pedidos
  CREATE TABLE public.pagamentos(
  codigo SERIAL PRIMARY KEY,
  cod_pedido INTEGER NOT null REFERENCES public.pedidos(codigo),
  data TIMESTAMP NOT null DEFAULT now()::timestamp without time zone,
  valor NUMERIC(10,2) NOT null DEFAULT 0.0
);
ALTER TABLE public.pagamentos
OWNER TO restaurante;

-- Tabela de custos de RH
CREATE TABLE public.custos_rh(
  codigo SERIAL PRIMARY KEY,
  data DATE NOT null DEFAULT CURRENT_DATE,
  cpf_funcionario VARCHAR(11) REFERENCES public.funcionarios(cpf),
  salario NUMERIC(10,2) NOT null
);
ALTER TABLE public.custos_rh
  OWNER TO restaurante;

-- Tabela de custos de reposição de estoque
CREATE TABLE public.custos_estoque(
  codigo SERIAL PRIMARY KEY,
  cnpj_fornecedor VARCHAR(14) NOT null REFERENCES public.fornecedores(cnpj),
  data DATE NOT null DEFAULT CURRENT_DATE,
  cod_ingrediente INTEGER REFERENCES public.ingredientes(codigo),
  qtd_ingredientes INTEGER NOT null,
  preco_unitario NUMERIC(10,2) NOT null DEFAULT 0.0
);
ALTER TABLE public.custos_estoque
  OWNER TO restaurante;







-- DROP TABLE clientes, cargos, funcionarios, fornecedores, mesas, ingredientes, produtos, pedidos, produto_ingrediente, pedido_produto, pagamentos, custos_rh, custos_estoque;