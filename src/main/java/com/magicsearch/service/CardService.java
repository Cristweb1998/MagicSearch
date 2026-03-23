package com.magicsearch.service;
import com.magicsearch.entity.Card;
import com.magicsearch.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service che gestisce la logica di business per le carte Magic.
 * Si collega tra il Controller e il Repository.
 * Il Controller chiama il Service, il Service chiama il Repository.
 */
@Service
public class CardService {

    // Inietta la dipendenza del Repository — Spring lo istanzia automaticamente
    @Autowired
    private CardsRepository cardsRepository;

    // Ritorna tutte le carte presenti nel database
    public List<Card> getAllCards() {
        return cardsRepository.findAll();
    }

    // Ritorna una singola carta cercata per ID univoco Scryfall
    // Optional perché la carta potrebbe non esistere nel database
    public Optional<Card> getCardById(String id) {
        return cardsRepository.findById(id);
    }

    // Ritorna una lista di carte cercate per nome
    // La ricerca è parziale e case insensitive
    public List<Card> searchByName(String name) {
        return cardsRepository.findByNameContainingIgnoreCase(name);
    }

    // Ritorna una lista di carte filtrate per rarità
    // es. "common", "uncommon", "rare", "mythic"
    public List<Card> searchByRarity(String rarity) {
        return cardsRepository.findByRarity(rarity);
    }

    // Ritorna una lista di carte filtrate per codice set
    // es. "dsk" = Duskmourn, "mkm" = Murders at Karlov Manor
    public List<Card> searchBySetCode(String setCode) {
        return cardsRepository.findBySetCode(setCode);
    }

    // Ritorna una lista di carte filtrate per tipo
    // es. "Creature", "Instant", "Sorcery", "Enchantment"
    public List<Card> searchByTypeLine(String typeLine) {
        return cardsRepository.findByTypeLineContainingIgnoreCase(typeLine);
    }

    // Ritorna una lista di carte filtrate per colore
    // es. "W"=Bianco, "U"=Blu, "B"=Nero, "R"=Rosso, "G"=Verde
    public List<Card> searchByColor(String color) {
        return cardsRepository.findByColorsContaining(color);
    }

    // Ritorna una lista di carte con prezzo EUR sotto la soglia indicata
    // es. maxPrice=5.0 ritorna tutte le carte che costano meno di 5€
    public List<Card> searchByMaxPrice(Double maxPrice) {
        return cardsRepository.findByMaxPriceEur(maxPrice);
    }

    // Salva una singola carta nel database
    // Usato dal servizio di import per salvare le carte scaricate da Scryfall
    public Card saveCard(Card card) {
        return cardsRepository.save(card);
    }

    // Salva una lista di carte nel database in una sola operazione
    // Usato dal servizio di import per salvare le carte in batch
    public List<Card> saveAllCards(List<Card> cards) {
        return cardsRepository.saveAll(cards);
    }
}