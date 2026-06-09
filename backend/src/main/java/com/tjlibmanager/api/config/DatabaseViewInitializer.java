package com.tjlibmanager.api.config;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class DatabaseViewInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseViewInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createViews() {
        try {
            ClassPathResource resource = new ClassPathResource("db/view_relatorio_livros_por_autor.sql");
            String ddl = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8).trim();
            jdbcTemplate.execute(ddl);
        } catch (Exception ex) {
            throw new IllegalStateException("Falha ao criar a view de relatório", ex);
        }
    }
}
