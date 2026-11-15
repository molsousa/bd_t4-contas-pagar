create type endereco_especifico as (
	nro_endereco int,
	complemento varchar(100),
	id_endereco int
);

create table ddd (
	nro_ddd int primary key
);

create table ddi (
	nro_ddi int primary key
);

create table tipo_logradouro (
	sigla_tipo_logradouro varchar(5) primary key,
	nome_tipo_logradouro varchar(32)
);

create table logradouro (
	id_logradouro serial primary key,
	nome_logradouro varchar(50),
	sigla_tipo_logradouro varchar(5),
	foreign key (sigla_tipo_logradouro) references tipo_logradouro (sigla_tipo_logradouro)
);

create table uf (
	sigla_uf varchar(4) primary key,
	nome_uf varchar(26)
);

create table cidade (
	id_cidade serial primary key, 
	nome_cidade varchar(100),
	sigla_uf varchar(4),
	foreign key (sigla_uf) references uf (sigla_uf)
);

create table bairro (
	id_bairro serial primary key,
	nome_bairro varchar(100)
);

create table endereco (
	id_endereco serial primary key, 
	cep varchar(12),
	id_cidade int not null,
	id_logradouro int not null, 
	id_bairro int not null,
	foreign key (id_cidade) references cidade (id_cidade),
	foreign key (id_logradouro) references logradouro (id_logradouro),
	foreign key (id_bairro) references bairro (id_bairro)
);

create table fornecedor (
	id_fornecedor serial primary key,
	nome_fornecedor varchar(100),
	cnpj varchar(26),
	saldo_total numeric(10,2),
	endereco_residencial endereco_especifico
);


create table email_fornecedor (
	id_email_fornecedor serial primary key,
	email_fornecedor varchar(50),
	id_fornecedor int not null,
	foreign key (id_fornecedor) references fornecedor (id_fornecedor)
);

create table fone_fornecedor (
	id_fone_fornecedor serial primary key,
	nro_fone varchar(11),
	nro_ddi int,
	nro_ddd int,
	id_fornecedor int not null,
	foreign key (nro_ddi) references ddi (nro_ddi),
	foreign key (nro_ddd) references ddd (nro_ddd),
	foreign key (id_fornecedor) references fornecedor (id_fornecedor)
);

create table motivo_fatura (
	id_motivo_fatura serial primary key,
	descricao_motivo_fatura varchar(100)
);

create table fatura (
	nro_fatura serial primary key,
	data_lancamento date,
	data_vencimento date, 
	valor_fatura numeric(10,2),
	pagamento_feito bool,
	id_fornecedor int,
	id_motivo_fatura int, 
	foreign key (id_fornecedor) references fornecedor (id_fornecedor),
	foreign key (id_motivo_fatura) references motivo_fatura (id_motivo_fatura)
);
