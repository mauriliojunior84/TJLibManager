package com.tjlibmanager.api.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.tjlibmanager.api.dto.RelatorioAutorDTO;
import com.tjlibmanager.api.dto.RelatorioItemDTO;
import com.tjlibmanager.api.dto.RelatorioLinhaDTO;
import com.tjlibmanager.api.exception.ReportGenerationException;
import com.tjlibmanager.api.repository.RelatorioRepository;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class RelatorioService {

    private static final Locale LOCALE_BR = new Locale("pt", "BR");

    private final RelatorioRepository relatorioRepository;
    private JasperReport relatorioCompilado;

    public RelatorioService(RelatorioRepository relatorioRepository) {
        this.relatorioRepository = relatorioRepository;
    }

    public List<RelatorioAutorDTO> livrosPorAutor() {
        List<RelatorioLinhaDTO> linhas = relatorioRepository.buscarLivrosPorAutor();
        Map<Integer, RelatorioAutorDTO> agrupado = new LinkedHashMap<>();

        for (RelatorioLinhaDTO linha : linhas) {
            RelatorioAutorDTO autor = agrupado.computeIfAbsent(linha.getAutorCodigo(), codigo -> {
                RelatorioAutorDTO novo = new RelatorioAutorDTO();
                novo.setAutorCodigo(linha.getAutorCodigo());
                novo.setAutorNome(linha.getAutorNome());
                return novo;
            });
            autor.getLivros().add(toItem(linha));
        }

        return new ArrayList<>(agrupado.values());
    }

    public byte[] livrosPorAutorPdf() {
        List<RelatorioLinhaDTO> linhas = relatorioRepository.buscarLivrosPorAutor();
        try {
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(linhas);
            Map<String, Object> parametros = new java.util.HashMap<>();
            parametros.put(net.sf.jasperreports.engine.JRParameter.REPORT_LOCALE, LOCALE_BR);
            JasperPrint print = JasperFillManager.fillReport(getRelatorioCompilado(), parametros, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception ex) {
            throw new ReportGenerationException("Falha ao gerar o relatório em PDF", ex);
        }
    }

    private synchronized JasperReport getRelatorioCompilado() throws Exception {
        if (relatorioCompilado == null) {
            ClassPathResource resource = new ClassPathResource("reports/livros_por_autor.jrxml");
            try (InputStream is = resource.getInputStream()) {
                relatorioCompilado = JasperCompileManager.compileReport(is);
            }
        }
        return relatorioCompilado;
    }

    private RelatorioItemDTO toItem(RelatorioLinhaDTO linha) {
        RelatorioItemDTO item = new RelatorioItemDTO();
        item.setLivroCodigo(linha.getLivroCodigo());
        item.setTitulo(linha.getTitulo());
        item.setEditora(linha.getEditora());
        item.setEdicao(linha.getEdicao());
        item.setAnoPublicacao(linha.getAnoPublicacao());
        item.setValor(linha.getValor());
        item.setAssuntos(linha.getAssuntos());
        return item;
    }
}
