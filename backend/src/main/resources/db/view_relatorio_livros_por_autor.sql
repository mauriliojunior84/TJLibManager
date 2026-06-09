CREATE OR REPLACE VIEW vw_relatorio_livros_por_autor AS
SELECT
    a.CodAu           AS autor_codigo,
    a.Nome            AS autor_nome,
    l.Codl            AS livro_codigo,
    l.Titulo          AS livro_titulo,
    l.Editora         AS livro_editora,
    l.Edicao          AS livro_edicao,
    l.AnoPublicacao   AS livro_ano,
    l.Valor           AS livro_valor,
    COALESCE(GROUP_CONCAT(DISTINCT s.Descricao ORDER BY s.Descricao SEPARATOR ', '), '') AS livro_assuntos
FROM Autor a
JOIN Livro_Autor la   ON la.Autor_CodAu = a.CodAu
JOIN Livro l          ON l.Codl = la.Livro_Codl
LEFT JOIN Livro_Assunto lsa ON lsa.Livro_Codl = l.Codl
LEFT JOIN Assunto s         ON s.codAs = lsa.Assunto_codAs
GROUP BY a.CodAu, a.Nome, l.Codl, l.Titulo, l.Editora, l.Edicao, l.AnoPublicacao, l.Valor
ORDER BY a.Nome, l.Titulo
