package com.magicsearch.controller;
import com.magicsearch.entity.Card;
import com.magicsearch.service.CardNameCleanupService;
import com.magicsearch.service.CardService;
import com.magicsearch.service.ScryfallImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST per la gestione delle carte Magic.
 * Riceve le richieste HTTP e le delega al CardService.
 * Risponde sempre in formato JSON.
 *
 * @RestController — indica che questa classe gestisce richieste HTTP REST
 * @RequestMapping — definisce l'URL base per tutti gli endpoint di questa classe
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {

    // Inietta dipendenza del Service — Spring lo istanzia automaticamente
    @Autowired
    private CardService cardService;
    
    @Autowired
    private CardNameCleanupService cleanupService; // Servizio per pulire i nomi

    

    @Autowired
    private ScryfallImportService scryfallImportService;

    // GET http://localhost:8080/api/cards
    // Ritorna tutte le carte presenti nel database
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // GET http://localhost:8080/api/cards/{id}
    // Ritorna una singola carta cercata per ID univoco Scryfall
    // ResponseEntity permette di gestire il caso in cui la carta non esiste
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable String id) {
        Optional<Card> card = cardService.getCardById(id);
        if (card.isPresent()) {
            return ResponseEntity.ok(card.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET http://localhost:8080/api/cards/search?name=dragon
    // Cerca carte per nome — ricerca parziale e case insensitive
    @GetMapping("/search")
    public List<Card> searchByName(@RequestParam String name) {
        return cardService.searchByName(name);
    }

    // GET http://localhost:8080/api/cards/rarity/{rarity}
    // Filtra carte per rarità es. "common", "uncommon", "rare", "mythic"
    @GetMapping("/rarity/{rarity}")
    public List<Card> getByRarity(@PathVariable String rarity) {
        return cardService.searchByRarity(rarity);
    }

    // GET http://localhost:8080/api/cards/set/{setCode}
    // Filtra carte per codice set es. "dsk", "mkm"
    @GetMapping("/set/{setCode}")
    public List<Card> getBySetCode(@PathVariable String setCode) {
        return cardService.searchBySetCode(setCode);
    }

    // GET http://localhost:8080/api/cards/type?typeLine=Creature
    // Filtra carte per tipo es. "Creature", "Instant", "Sorcery"
    @GetMapping("/type")
    public List<Card> getByTypeLine(@RequestParam String typeLine) {
        return cardService.searchByTypeLine(typeLine);
    }

    // GET http://localhost:8080/api/cards/color?color=W
    // Filtra carte per colore es. "W", "U", "B", "R", "G"
    @GetMapping("/color")
    public List<Card> getByColor(@RequestParam String color) {
        return cardService.searchByColor(color);
    }

    // GET http://localhost:8080/api/cards/price?maxPrice=5.0
    // Ritorna carte con prezzo EUR sotto la soglia indicata
    @GetMapping("/price")
    public List<Card> getByMaxPrice(@RequestParam Double maxPrice) {
        return cardService.searchByMaxPrice(maxPrice);
    }
    
 // POST http://localhost:8080/api/cards/import/{setCode}
    @PostMapping("/import/{setCode}")
    public String importSet(@PathVariable String setCode) {
    	scryfallImportService.importBySet(setCode);
        return "Import completato per il set: " + setCode;
    }
    
    
    /**
     * Pulizia dei nomi delle carte nel database.
     * Rimuove spazi non-breaking, doppi spazi e trim.
     * Dopo questa operazione le ricerche per nome completo funzionano correttamente.
     * URL: POST /api/cards/cleanup/names
     */
      @PostMapping("/cleanup/names")
       public String cleanupCardNames() {
        cleanupService.cleanAllCardNames();
        return "Pulizia nomi carte completata!";
       }
}