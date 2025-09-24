package pt.antoniopmartinho.urlShortner.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.antoniopmartinho.urlShortner.model.LinkModel;
import pt.antoniopmartinho.urlShortner.repository.LinkRepository;
import pt.antoniopmartinho.urlShortner.repository.ClickMetricRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService { 

    private final LinkRepository linkRepository;
    private final ClickMetricRepository clickMetricRepository;

    public LinkService(LinkRepository linkRepository, ClickMetricRepository clickMetricRepository) {
        this.linkRepository = linkRepository;
        this.clickMetricRepository = clickMetricRepository;
    }

    // 1) Criar link novo
    @Transactional
    public Link shortenUrl(String originalUrl, OffsetDateTime expiresAt, UUID ownerId) {
        // TODO: gerar shortCode único
        // TODO: criar Link, preencher campos, gravar na DB
        return null;
    }

    // 2) Buscar original se não expirou
    @Transactional(readOnly = true)
    public Optional<String> getOriginalUrlIfNotExpired(String shortCode) {
        // TODO: procurar Link pelo shortCode
        // TODO: validar expiresAt
        return Optional.empty();
    }

    // 3) Incrementar clique e guardar métrica
    @Transactional
    public void incrementClickAndSaveMetric(String shortCode, String ip, String userAgent, String referer) {
        // TODO: procurar Link
        // TODO: incrementar contador
        // TODO: gravar ClickMetric
    }
}
