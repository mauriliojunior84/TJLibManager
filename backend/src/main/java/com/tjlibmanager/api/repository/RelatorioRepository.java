package com.tjlibmanager.api.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tjlibmanager.api.dto.RelatorioLinhaDTO;

@Repository
public class RelatorioRepository {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RelatorioLinhaDTO> buscarLivrosPorAutor() {
        String sql = "SELECT autor_codigo, autor_nome, livro_codigo, livro_titulo, "
            + "livro_editora, livro_edicao, livro_ano, livro_valor, livro_assuntos "
            + "FROM vw_relatorio_livros_por_autor";
        return jdbcTemplate.query(sql, linhaRowMapper());
    }

    private RowMapper<RelatorioLinhaDTO> linhaRowMapper() {
        return (rs, rowNum) -> {
            RelatorioLinhaDTO linha = new RelatorioLinhaDTO();
            linha.setAutorCodigo(rs.getInt("autor_codigo"));
            linha.setAutorNome(rs.getString("autor_nome"));
            linha.setLivroCodigo(rs.getInt("livro_codigo"));
            linha.setTitulo(rs.getString("livro_titulo"));
            linha.setEditora(rs.getString("livro_editora"));
            int edicao = rs.getInt("livro_edicao");
            linha.setEdicao(rs.wasNull() ? null : edicao);
            linha.setAnoPublicacao(rs.getString("livro_ano"));
            linha.setValor(rs.getBigDecimal("livro_valor"));
            linha.setAssuntos(rs.getString("livro_assuntos"));
            return linha;
        };
    }
}
