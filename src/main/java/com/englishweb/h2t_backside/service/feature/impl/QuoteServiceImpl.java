package com.englishweb.h2t_backside.service.feature.impl;

import com.englishweb.h2t_backside.dto.feature.homepage.QuoteDTO;
import com.englishweb.h2t_backside.service.feature.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class QuoteServiceImpl implements QuoteService {

    private final Integer DEFAULT_LIMIT = 5;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${quote.api.url}")
    private String url;
    @Value("${quote.api.key}")
    private String apiKey;

    @Override
    public List<QuoteDTO> getRandomQuotes(Integer limit) {
        if (limit == null || limit <= 0) {
            limit = DEFAULT_LIMIT;
        }
        log.info("Fetching {} random quotes", limit);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        List<QuoteDTO> result = new LinkedList<>();

        try {
            for (int i = 0; i < limit; i++) {
                ResponseEntity<List<QuoteDTO>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<List<QuoteDTO>>() {}
                );

                List<QuoteDTO> quotes = response.getBody();
                if (quotes != null && !quotes.isEmpty()) {
                    QuoteDTO quote = quotes.get(0);
                    log.info("Fetched quote: {}", quote.getQuote());
                    result.add(quote);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching random quotes", e);
        }

        return result;
    }
}
