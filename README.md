# TjLibManager

CRUD de livros, autores e assuntos, com relatório em PDF agrupado por autor.
API em Spring Boot (Java 8) + front em Angular 8, banco MySQL.

## Stack

- **Back-end:** Java 8, Spring Boot 2.7, Spring Data JPA, JasperReports
- **Front-end:** Angular 8, Bootstrap 4
- **Banco:** MySQL 8
- **Infra:** Docker / Docker Compose

## Modelo

Segue o modelo proposto (Livro, Autor, Assunto e as associativas N:N), com o
acréscimo do campo `Valor` no Livro. Um livro pode ter vários autores e assuntos.

## Subindo o projeto

Com Docker, na raiz:

```bash
docker-compose up -d --build
```

- Front: http://localhost:4200
- API: http://localhost:8080/api
- MySQL: localhost:3307 (`tjlib` / `tjlib123`, base `tjlibmanager`)

A porta do banco é 3307 no host pra não brigar com um MySQL local.

Para derrubar tudo (e zerar o banco):

```bash
docker-compose down -v
```

## Rodando local (sem Docker)

Banco via Docker e o resto na mão:

```bash
docker-compose up -d mysql

cd backend && mvn spring-boot:run

cd frontend && npm install && npm start
```

> No Node 17+ o Angular 8 precisa da flag `--openssl-legacy-provider`, que já
> está nos scripts npm.

## Relatório

O relatório de livros por autor sai de uma view no banco
(`vw_relatorio_livros_por_autor`) e é renderizado em PDF pelo JasperReports.
Como um livro pode ter mais de um autor, ele aparece em cada autor, com subtotal
por autor e total geral. Acessível pela tela "Relatório".

A view é criada pelo `init.sql` e também recriada no start da aplicação, então
funciona mesmo num banco já existente.

## Testes

```bash
cd backend && mvn test          # JUnit + Mockito

cd frontend && npm run test:ci  # Karma + Jasmine (headless)
```

## Organização

```
backend/    API Spring Boot (controller / service / repository / validation)
frontend/   SPA Angular
docker-compose.yml
```

Obs.: Validação do livro foi feita com Chain of Responsibility
(cada regra é um handler isolado) e tratamento de erros centralizado, devolvendo
status adequados (404, 400, 409).
