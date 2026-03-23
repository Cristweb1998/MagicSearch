package com.magicsearch.service;

import com.magicsearch.entity.Card;
import com.magicsearch.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servizio per pulire i nomi delle carte nel database.
 * Sostituisce spazi non-breaking, riduce doppi spazi e fa trim.
 */
@Service
public class CardNameCleanupService {

    @Autowired
    private CardsRepository cardRepository;

    public void cleanAllCardNames() {
        List<Card> cards = cardRepository.findAll();

        for (Card card : cards) {
            String name = card.getName();
            if (name != null) {
                String cleaned = name
                        .replace("\u00A0", " ")   // sostituisce spazi non-breaking
                        .replaceAll("\\s+", " ")  // riduce doppi spazi a uno
                        .trim();                   // rimuove spazi iniziali/finali

                if (!cleaned.equals(name)) {
                    card.setName(cleaned);
                }
            }
        }

        cardRepository.saveAll(cards);
        System.out.println("Pulizia nomi carte completata!");
    }
}