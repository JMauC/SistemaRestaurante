-- Relaciona produtos, ingredientes e quantidade de ingredientes
SELECT prod.nome AS nome_produto, 
	ingr.nome AS nome_ingrediente, 
	prod.qtd_ingrediente 
FROM 
	(SELECT p.codigo, p.nome, pi.cod_produto, pi.cod_ingrediente, pi.qtd_ingrediente
		FROM produtos AS p 
		INNER JOIN produto_ingrediente AS pi
		ON p.codigo = pi.cod_produto) AS prod
INNER JOIN
	(SELECT i.codigo, i.nome, pi.cod_produto, pi.cod_ingrediente  
		FROM ingredientes AS i 
		INNER JOIN produto_ingrediente AS pi
		ON i.codigo = pi.cod_ingrediente) AS ingr
ON prod.codigo = ingr.cod_produto
AND ingr.codigo = prod.cod_ingrediente
ORDER BY prod.codigo, ingr.codigo;

-- Relaciona pedidos mais frequentes por cliente
SELECT pe_c.cpf, pe_c.nome, pp_pr.nome AS produto, SUM(pp_pr.qtd_produto)
FROM (SELECT c.cpf, c.nome, pe.codigo AS cod_pedido
	FROM pedidos AS pe
	INNER JOIN clientes AS c
	ON pe.cpf_cliente = c.cpf
	WHERE pe.pedido_pago = true) AS pe_c
INNER JOIN (SELECT pp.cod_pedido,pr.nome, pp.cod_produto, pp.qtd_produto
		FROM pedido_produto AS pp
		INNER JOIN produtos AS pr
		ON pp.cod_produto = pr.codigo) AS pp_pr
ON pe_c.cod_pedido = pp_pr.cod_pedido
GROUP BY pe_c.cpf, pe_c.nome, pp_pr.nome
ORDER BY pe_c.nome ASC, SUM(pp_pr.qtd_produto) DESC;

-- Pedidos prontos
SELECT cod_pedido FROM pedido_produto WHERE cod_pedido 
	NOT IN (SELECT cod_pedido FROM pedido_produto 
			WHERE pronto = false)
GROUP BY cod_pedido;

-- Pedidos prontos por garcom
SELECT p.codigo, p.cod_mesa
FROM pedidos AS p
INNER JOIN (SELECT cod_pedido FROM pedido_produto 
			WHERE cod_pedido 
			NOT IN (SELECT cod_pedido FROM pedido_produto 
					WHERE pronto = false)) AS pronto
ON p.codigo = pronto.cod_pedido
WHERE p.cpf_garcom = '45645645645' AND pedido_pago = false
GROUP BY codigo
ORDER BY codigo;

-- Total de pedidos atendidos por garcom
SELECT f.cpf, f.nome, COUNT(p.codigo) 
FROM funcionarios AS f
	INNER JOIN pedidos AS p
	ON f.cpf = p.cpf_garcom
GROUP BY f.cpf;

-- Pedidos pendentes cozinha
SELECT pp.cod_pedido, pr.nome, pp.qtd_produto
FROM produtos AS pr
INNER JOIN
	(SELECT cod_pedido, cod_produto, qtd_produto 
	FROM pedido_produto WHERE pronto = false) AS pp
ON pr.codigo = pp.cod_produto
WHERE bebida = false
ORDER BY pr.codigo;

-- Pedidos pendentes bebibas
SELECT pp.cod_pedido, pr.nome, pp.qtd_produto
FROM produtos AS pr
INNER JOIN
	(SELECT cod_pedido, cod_produto, qtd_produto 
	FROM pedido_produto WHERE pronto = false) AS pp
ON pr.codigo = pp.cod_produto
WHERE bebida = true
ORDER BY pr.codigo;