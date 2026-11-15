INSERT INTO ddi (nro_ddi) VALUES 
(55), 
(1),  
(351); 
INSERT INTO ddd (nro_ddd) VALUES 
(11),  
(21),  
(45),  
(41);  

INSERT INTO tipo_logradouro (sigla_tipo_logradouro, nome_tipo_logradouro) VALUES 
('R', 'Rua'),
('AV', 'Avenida'),
('TRAV', 'Travessa'),
('AL', 'Alameda');

INSERT INTO uf (sigla_uf, nome_uf) VALUES 
('PR', 'Paraná'),
('SP', 'São Paulo'),
('RJ', 'Rio de Janeiro'),
('SC', 'Santa Catarina');

INSERT INTO bairro (nome_bairro) VALUES 
('Centro'),
('Vila Portes'),
('Jardim América'),
('Copacabana'),
('Batel');

INSERT INTO logradouro (nome_logradouro, sigla_tipo_logradouro) VALUES 
('Brasil', 'AV'),
('das Flores', 'R'),
('Juscelino Kubitschek', 'AV'),
('XV de Novembro', 'R'),
('Atlântica', 'AV');

INSERT INTO cidade (nome_cidade, sigla_uf) VALUES 
('Foz do Iguaçu', 'PR'),
('São Paulo', 'SP'),
('Rio de Janeiro', 'RJ'),
('Curitiba', 'PR'),
('Joinville', 'SC');

INSERT INTO endereco (cep, id_cidade, id_logradouro, id_bairro) VALUES 
('85890-000', 1, 1, 2),
('01000-000', 2, 2, 3),
('22000-000', 3, 5, 4), 
('80000-000', 4, 4, 1), 
('85890-111', 1, 3, 2), 
('89200-000', 5, 2, 5); 

INSERT INTO fornecedor (nome_fornecedor, cnpj, saldo_total, endereco_residencial) VALUES 
('Tech Solutions Ltda', '12.345.678/0001-90', 15000.50, ROW(123, 'Sala 4', 1)),
('Distribuidora ABC', '98.765.432/0001-10', 8500.00, ROW(456, 'Bloco B', 2)),
('Comércio de Alimentos Silva', '11.222.333/0001-44', 3200.75, ROW(789, 'Fundos', 3)),
('Serviços Gerais RJ', '55.666.777/0001-88', 12000.00, ROW(101, 'Apto 12', 4)),
('Importadora Sul', '33.444.555/0001-22', 50000.00, ROW(50, 'Galpão 1', 5)),
('Construtora Forte', '77.888.999/0001-55', 0.00, ROW(1, NULL, 6));

INSERT INTO email_fornecedor (email_fornecedor, id_fornecedor) VALUES 
('contato@techsolutions.com.br', 1),
('vendas@techsolutions.com.br', 1),
('comercial@distribuidoraabc.com', 2),
('silva.alimentos@gmail.com', 3),
('atendimento@servicosrj.com.br', 4),
('contato@importadorasul.com', 5);

INSERT INTO fone_fornecedor (nro_fone, nro_ddi, nro_ddd, id_fornecedor) VALUES 
('999991111', 55, 45, 1), 
('33332222', 55, 45, 1),  
('988887777', 55, 11, 2), 
('44445555', 55, 11, 2),  
('977776666', 55, 21, 4),
('966665555', 55, 41, 5), 
('123456789', 1, 11, 5);  

INSERT INTO motivo_fatura (descricao_motivo_fatura) VALUES 
('Compra de Insumos'),
('Prestação de Serviços'),
('Manutenção de Equipamentos'),
('Frete e Logística'),
('Consultoria Especializada');

INSERT INTO fatura (data_lancamento, data_vencimento, valor_fatura, pagamento_feito, id_fornecedor, id_motivo_fatura) VALUES 
('2023-10-01', '2023-10-15', 1500.00, true,  1, 2), 
('2023-10-05', '2023-11-05', 3200.50, false, 2, 1),
('2023-10-10', '2023-10-20', 850.00,  true,  3, 1), 
('2023-10-12', '2023-11-12', 4500.00, false, 4, 3), 
('2023-10-15', '2023-10-30', 1200.00, true,  5, 4), 
('2023-10-20', '2023-11-20', 7500.00, false, 1, 5),
('2023-10-25', '2023-11-25', 25000.00, true, 6, 2), 
('2023-11-01', '2023-12-01', 980.00,  false, 3, 2); 

INSERT INTO fatura (data_lancamento, data_vencimento, valor_fatura, pagamento_feito, id_fornecedor, id_motivo_fatura) VALUES
('2023-11-05', '2023-11-15', 1250.00, true,  1, 1),
('2023-11-10', '2023-12-10', 3400.50, false, 1, 2),
('2023-11-15', '2023-11-30', 890.00,  true,  1, 3),
('2023-11-20', '2023-12-20', 2150.75, false, 1, 4),
('2023-11-25', '2023-12-05', 5600.00, true,  1, 5),

('2023-11-02', '2023-11-12', 4500.00, true,  2, 2),
('2023-11-08', '2023-12-08', 1200.00, false, 2, 1),
('2023-11-14', '2023-11-24', 780.50,  true,  2, 5),
('2023-11-21', '2023-12-21', 9100.00, false, 2, 3),
('2023-11-28', '2023-12-28', 350.00,  true,  2, 4),

('2023-11-03', '2023-11-13', 450.00,  false, 3, 1),
('2023-11-09', '2023-12-09', 1100.00, true,  3, 2),
('2023-11-16', '2023-11-26', 230.50,  false, 3, 3),
('2023-11-22', '2023-12-22', 890.00,  true,  3, 4),
('2023-11-29', '2023-12-09', 320.00,  false, 3, 5),

('2023-11-01', '2023-11-15', 8900.00, true,  4, 3),
('2023-11-07', '2023-12-07', 1500.00, false, 4, 1),
('2023-11-13', '2023-11-23', 2400.00, true,  4, 2),
('2023-11-19', '2023-12-19', 750.50,  false, 4, 4),
('2023-11-27', '2023-12-27', 3100.00, true,  4, 5),

('2023-11-04', '2023-11-14', 12500.00, true,  5, 4),
('2023-11-11', '2023-12-11', 8200.00,  false, 5, 2),
('2023-11-18', '2023-11-28', 1450.00, true,  5, 1),
('2023-11-24', '2023-12-24', 6700.00, false, 5, 3),
('2023-11-30', '2023-12-10', 900.00,  true,  5, 5),

('2023-11-06', '2023-12-06', 45000.00, false, 6, 2),
('2023-11-12', '2023-11-22', 12000.00, true,  6, 1),
('2023-11-17', '2023-12-17', 8500.00,  false, 6, 3),
('2023-11-23', '2023-12-03', 3200.00,  true,  6, 4),
('2023-11-29', '2023-12-29', 15000.00, false, 6, 5);

UPDATE fornecedor SET saldo_total = 13051.25 WHERE id_fornecedor = 1;
UPDATE fornecedor SET saldo_total = 13500.50 WHERE id_fornecedor = 2;
UPDATE fornecedor SET saldo_total = 1980.50  WHERE id_fornecedor = 3;
UPDATE fornecedor SET saldo_total = 6750.50  WHERE id_fornecedor = 4;
UPDATE fornecedor SET saldo_total = 14900.00 WHERE id_fornecedor = 5;
UPDATE fornecedor SET saldo_total = 68500.00 WHERE id_fornecedor = 6;

select 
    f.nro_fatura,
    f.data_lancamento,
    f.data_vencimento,
    f.valor_fatura,
    f.pagamento_feito,
    forn.id_fornecedor,
    forn.nome_fornecedor,
    forn.cnpj,
    forn.saldo_total,
    ende.cep,
    bai.id_bairro,
    bai.nome_bairro,
    cid.id_cidade,
    cid.nome_cidade,
    u.sigla_uf,
    u.nome_uf,
    logr.id_logradouro,
    logr.nome_logradouro,  
    tplg.sigla_tipo_logradouro,
    tplg.nome_tipo_logradouro,
    (forn.endereco_residencial).nro_endereco,
    (forn.endereco_residencial).complemento,
    mot.id_motivo_fatura,
    mot.descricao_motivo_fatura as motivo,
    case when f.pagamento_feito = true then 'Pago' else 'Pendente' end as status_pagamento
from fatura f
join fornecedor forn ON f.id_fornecedor = forn.id_fornecedor
join endereco ende on (forn.endereco_residencial).id_endereco = ende.id_endereco
join motivo_fatura mot ON f.id_motivo_fatura = mot.id_motivo_fatura
join bairro bai on ende.id_bairro = bai.id_bairro
join cidade cid on ende.id_cidade = cid.id_cidade
join logradouro logr on ende.id_logradouro = logr.id_logradouro 
join uf u on cid.sigla_uf = u.sigla_uf
join tipo_logradouro tplg on tplg.sigla_tipo_logradouro = logr.sigla_tipo_logradouro
where f.id_fornecedor = 1;

select
	f.id_fornecedor,
	f.nome_fornecedor,
	ff.id_fone_fornecedor,
	ff.nro_ddi,
	ff.nro_ddd,
	ff.nro_fone,
	ef.id_email_fornecedor,
	ef.email_fornecedor
from fone_fornecedor ff
join fornecedor f on f.id_fornecedor = ff.id_fornecedor
join email_fornecedor ef on f.id_fornecedor = ef.id_fornecedor;

select
	f.id_fornecedor,
	f.nome_fornecedor,
	f.cnpj,
	f.saldo_total,
	(f.endereco_residencial).nro_endereco,
	(f.endereco_residencial).complemento,
	(f.endereco_residencial).id_endereco,
	e.id_bairro,
	e.id_cidade,
	e.id_logradouro,
	b.nome_bairro,
	l.nome_logradouro,
	l.sigla_tipo_logradouro,
	tl.nome_tipo_logradouro,
	c.nome_cidade,
	c.sigla_uf,
	u.nome_uf
from fornecedor f
join endereco e on e.id_endereco = (f.endereco_residencial).id_endereco
join bairro b on b.id_bairro = e.id_bairro 
join cidade c on c.id_cidade = e.id_cidade 
join logradouro l on l.id_logradouro = e.id_logradouro 
join tipo_logradouro tl on tl.sigla_tipo_logradouro = l.sigla_tipo_logradouro 
join uf u on u.sigla_uf = c.sigla_uf;

select
	ff.id_fone_fornecedor,
	ff.nro_ddi,
	ff.nro_ddd,
	ff.nro_fone
from fone_fornecedor ff
join fornecedor f on f.id_fornecedor = ff.id_fornecedor
where f.id_fornecedor = 1;

select
	e.id_email_fornecedor,
	e.email_fornecedor
from email_fornecedor e where e.id_fornecedor = 1;

CREATE USER admin_total WITH PASSWORD 'senha_admin';
CREATE USER usuario_consulta WITH PASSWORD 'senha_consulta';
CREATE USER usuario_insercao WITH PASSWORD 'senha_insert';

GRANT CONNECT ON DATABASE "ContasAPagar" TO admin_total, usuario_consulta, usuario_insercao;
GRANT USAGE ON SCHEMA public TO admin_total, usuario_consulta, usuario_insercao;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin_total;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin_total;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO usuario_consulta;

GRANT SELECT, INSERT, UPDATE ON ALL TABLES IN SCHEMA public TO usuario_insercao;

GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO usuario_insercao;

REVOKE DELETE ON ALL TABLES IN SCHEMA public FROM usuario_insercao;
REVOKE DELETE ON TABLE fatura FROM usuario_insercao;

SELECT pid, state, query, state_change 
FROM pg_stat_activity 
WHERE state = 'idle in transaction';

commit;
