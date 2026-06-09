package com.tjlibmanager.api.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tjlibmanager.api.dto.LivroDTO;
import com.tjlibmanager.api.exception.ResourceNotFoundException;
import com.tjlibmanager.api.exception.ValidacaoException;
import com.tjlibmanager.api.fixture.LivroDTOTemplate;
import com.tjlibmanager.api.model.Livro;
import com.tjlibmanager.api.repository.AssuntoRepository;
import com.tjlibmanager.api.repository.AutorRepository;
import com.tjlibmanager.api.repository.LivroRepository;
import com.tjlibmanager.api.validation.LivroValidacaoChain;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private AssuntoRepository assuntoRepository;
    @Mock
    private AutorService autorService;
    @Mock
    private AssuntoService assuntoService;
    @Mock
    private LivroValidacaoChain validacaoChain;

    @InjectMocks
    private LivroService livroService;

    @BeforeAll
    static void carregarTemplates() {
        FixtureFactoryLoader.loadTemplates("com.tjlibmanager.api.fixture");
    }

    @Test
    void deveValidarPelaCadeiaAntesDeSalvarNoCreate() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        Livro salvo = new Livro();
        salvo.setCodl(1);
        salvo.setTitulo(dto.getTitulo());
        salvo.setValor(dto.getValor());
        when(livroRepository.save(any(Livro.class))).thenReturn(salvo);

        livroService.create(dto);

        verify(validacaoChain).validar(dto);
        verify(livroRepository).save(any(Livro.class));
    }

    @Test
    void naoDeveSalvarQuandoCadeiaReprova() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        doThrow(new ValidacaoException("invalido")).when(validacaoChain).validar(dto);

        assertThatThrownBy(() -> livroService.create(dto))
            .isInstanceOf(ValidacaoException.class);

        verify(livroRepository, never()).save(any(Livro.class));
    }

    @Test
    void deveLancarNotFoundAoAtualizarLivroInexistente() {
        LivroDTO dto = Fixture.from(LivroDTO.class).gimme(LivroDTOTemplate.VALIDO);
        when(livroRepository.findByIdWithRelations(99)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> livroService.update(99, dto))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(livroRepository, never()).save(any(Livro.class));
    }
}
