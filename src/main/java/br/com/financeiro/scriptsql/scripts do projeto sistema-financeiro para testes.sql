select usuario.senha, pessoa.cpf AS "CPF da Pessoa", usuario.ativo
from usuario
inner join pessoa
on usuario.id_pessoa = pessoa.id
where usuario.ativo = false; 

SELECT usuario.senha, pessoa.cpf AS "CPF da Pessoa"
FROM usuario
INNER JOIN pessoa
ON usuario.id_pessoa = pessoa.id; 

select * from usuario;

select * from venda;

SELECT * FROM ITEMVENDA;

select * from cliente;

select * from produto;

SELECT venda.horario AS "Data e Hora da venda", venda.precototal AS "valor Total", pessoa.nome AS "Nome do cliente"
FROM venda
INNER JOIN cliente
ON venda.id_cliente = cliente.id
INNER JOIN pessoa
ON cliente.id_pessoa = pessoa.id
WHERE venda.id = 1;

SELECT itemvenda.id AS "ID DO ITEM VENDA", itemvenda.precoparcial AS "VALOR PARCIAL", itemvenda.quantidade AS "QUANTIDADE VENDIDA",
	produto.descricao AS "PRODUTO", produto.preco AS "VALOR UNIT DO PRODUTO",
	venda.id AS "ID DA VENDA", venda.horario AS "DATA E HORA DA VENDA"
FROM itemvenda
INNER JOIN produto
ON itemvenda.id_produto = produto.id
INNER JOIN venda
ON itemvenda.id_venda = venda.id
WHERE venda.id = 1;
	
 