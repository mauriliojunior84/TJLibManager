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
