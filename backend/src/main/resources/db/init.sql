CREATE DATABASE IF NOT EXISTS tjlibmanager;
USE tjlibmanager;

CREATE TABLE IF NOT EXISTS Autor (
    CodAu INT NOT NULL AUTO_INCREMENT,
    Nome  VARCHAR(40) NOT NULL,
    PRIMARY KEY (CodAu)
);

CREATE TABLE IF NOT EXISTS Assunto (
    codAs     INT NOT NULL AUTO_INCREMENT,
    Descricao VARCHAR(20) NOT NULL,
    PRIMARY KEY (codAs)
);

CREATE TABLE IF NOT EXISTS Livro (
    Codl          INT NOT NULL AUTO_INCREMENT,
    Titulo        VARCHAR(40) NOT NULL,
    Editora       VARCHAR(40),
    Edicao        INT,
    AnoPublicacao VARCHAR(4),
    Valor         DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (Codl)
);

CREATE TABLE IF NOT EXISTS Livro_Autor (
    Livro_Codl    INT NOT NULL,
    Autor_CodAu   INT NOT NULL,
    PRIMARY KEY (Livro_Codl, Autor_CodAu),
    CONSTRAINT FK_LivroAutor_Livro  FOREIGN KEY (Livro_Codl)  REFERENCES Livro(Codl)  ON DELETE CASCADE,
    CONSTRAINT FK_LivroAutor_Autor  FOREIGN KEY (Autor_CodAu) REFERENCES Autor(CodAu) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Livro_Assunto (
    Livro_Codl    INT NOT NULL,
    Assunto_codAs INT NOT NULL,
    PRIMARY KEY (Livro_Codl, Assunto_codAs),
    CONSTRAINT FK_LivroAssunto_Livro    FOREIGN KEY (Livro_Codl)    REFERENCES Livro(Codl)    ON DELETE CASCADE,
    CONSTRAINT FK_LivroAssunto_Assunto  FOREIGN KEY (Assunto_codAs) REFERENCES Assunto(codAs) ON DELETE CASCADE
);

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
ORDER BY a.Nome, l.Titulo;
