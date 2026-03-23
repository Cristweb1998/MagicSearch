package com.magicsearch.service;

import com.magicsearch.entity.Card;
import com.magicsearch.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service che gestisce l'importazione delle carte Magic dal sito Scryfall.
 * Converte i dati JSON in oggetti Card e li salva nel database.
 */
@Service
public class ScryfallImportService {

    @Autowired
    private CardsRepository cardRepository;

    // WebClient per chiamare Scryfall, buffer fino a 10MB
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.scryfall.com")
            .codecs(configurer -> configurer
                    .defaultCodecs()
                    .maxInMemorySize(10 * 1024 * 1024))
            .build();

    /**
     * Importa tutte le carte di un set specifico da Scryfall.
     * Gestisce automaticamente la paginazione e salva le carte nel DB.
     *
     * @param setCode codice del set es. "dsk", "mkm"
     */
    public void importBySet(String setCode) {
        String url = "https://api.scryfall.com/cards/search?q=set:" + setCode + "&unique=cards";
        boolean hasMore = true;

        while (hasMore) {
            try {
                Map<String, Object> response = webClient.get()
                        .uri(URI.create(url))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .block();

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> data = (List<Map<String, Object>>) response.get("data");

                List<Card> cards = data.stream()
                        .map(this::mapToCard)
                        .collect(Collectors.toList());

                cardRepository.saveAll(cards);

                hasMore = (Boolean) response.getOrDefault("has_more", false);
                if (hasMore) {
                    url = (String) response.get("next_page");
                    System.out.println("Prossima pagina: " + url);
                }

                // Rispetta limite massimo 10 richieste/sec di Scryfall
                try {
                    Thread.sleep(110);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            } catch (Exception e) {
                System.out.println("Errore durante l'import: " + e.getMessage());
                hasMore = false;
            }
        }
    }

    /**
     * Converte i dati JSON di Scryfall in un oggetto Card.
     * Pulizia del nome: spazi non-breaking, doppi spazi, trim.
     *
     * @param raw dati JSON della carta
     * @return oggetto Card pronto per il database
     */
    @SuppressWarnings("unchecked")
    private Card mapToCard(Map<String, Object> raw) {

        Map<String, String> imageUris = (Map<String, String>) raw.get("image_uris");
        Map<String, String> prices = (Map<String, String>) raw.get("prices");
        List<String> colors = (List<String>) raw.getOrDefault("colors", List.of());

        // Pulizia del nome della carta
        String rawName = (String) raw.get("name");
        String cleanName = rawName
                .replace("\u00A0", " ")   // sostituisce spazi non-breaking
                .replaceAll("\\s+", " ")  // riduce doppi spazi a uno
                .trim();                   // rimuove spazi iniziali/finali

        return new Card(
                (String) raw.get("id"),
                cleanName,
                (String) raw.get("mana_cost"),
                raw.get("cmc") != null ? ((Number) raw.get("cmc")).doubleValue() : null,
                (String) raw.get("type_line"),
                (String) raw.get("oracle_text"),
                (String) raw.get("power"),
                (String) raw.get("toughness"),
                (String) raw.get("loyalty"),
                (String) raw.get("rarity"),
                (String) raw.get("set"),
                (String) raw.get("set_name"),
                imageUris != null ? imageUris.get("normal") : null,
                String.join(",", colors),
                prices != null && prices.get("eur") != null ? Double.parseDouble(prices.get("eur")) : null,
                prices != null && prices.get("usd") != null ? Double.parseDouble(prices.get("usd")) : null,
                raw.get("released_at") != null ? LocalDate.parse((String) raw.get("released_at")) : null
        );
    }
}