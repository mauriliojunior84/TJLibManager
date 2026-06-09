package com.tjlibmanager.api.validation;

// DEMONSTRAÇÃO - Chain of Responsibility
//
// Este handler mostra a principal vantagem do padrão: adicionar uma nova regra
// de negócio criando UMA classe, sem alterar LivroService nem os demais handlers.
//
// Para ativar durante a apresentação:
//   1. Descomente o bloco da classe abaixo.
//   2. O Spring detecta o @Component e o injeta automaticamente na
//      LivroValidacaoChain, já ordenado por @Order(5), depois dos demais.
//   3. O metodo existsByTituloIgnoreCaseAndEditoraIgnoreCase ja existe em
//      LivroRepository, entao nenhum outro arquivo precisa ser tocado.
//
// Regra: nao permitir dois livros com o mesmo titulo na mesma editora.
//
// Observacao: esta versao valida o cenario de cadastro (create). Para o update,
// bastaria refinar a consulta para ignorar o proprio registro (uma variante
// ...AndCodlNot(dto.getCodl())), evidenciando como a regra evolui isoladamente.

// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
//
// import com.tjlibmanager.api.dto.LivroDTO;
// import com.tjlibmanager.api.exception.ValidacaoException;
// import com.tjlibmanager.api.repository.LivroRepository;
//
// @Component
// @Order(5)
// public class TituloUnicoPorEditoraHandler implements LivroValidationHandler {
//
//     private final LivroRepository livroRepository;
//
//     public TituloUnicoPorEditoraHandler(LivroRepository livroRepository) {
//         this.livroRepository = livroRepository;
//     }
//
//     @Override
//     public void validar(LivroDTO dto) {
//         if (dto.getTitulo() == null || dto.getEditora() == null) {
//             return;
//         }
//         if (livroRepository.existsByTituloIgnoreCaseAndEditoraIgnoreCase(dto.getTitulo(), dto.getEditora())) {
//             throw new ValidacaoException(
//                 "Ja existe um livro com este titulo para a editora informada.");
//         }
//     }
// }
