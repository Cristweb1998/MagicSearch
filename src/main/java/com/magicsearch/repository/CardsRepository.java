package com.magicsearch.repository;

import com.magicsearch.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository per la gestione delle carte nel database.
 * Estendiamo JpaRepository che fornisce i metodi CRUD di base.
 * Spring Data JPA genera automaticamente le query SQL dai nomi dei metodi.
 */
@Repository
public interface CardsRepository extends JpaRepository<Card, String> {

    // Abbiamo una lista di tipo carta che cerca le carte  per il nome
	// senza considerare l'uso delle maiuscole o minuscole
    // es. "karn liberato" trova "Karn Liberato", "" ecc.
    List<Card> findByNameContainingIgnoreCase(String name);

    // lista che Cerca carte per rarità es. "common", "uncommon", "rare", "mythic"
    List<Card> findByRarity(String rarity);

    // lista che Cerca carte per codice set es. "dsk", "mkm"
    List<Card> findBySetCode(String setCode);

    // lista che Cerca carte per tipo di carta magic es. "Creature", "Instant", "Sorcery"
    List<Card> findByTypeLineContainingIgnoreCase(String typeLine);

    // Lista che cerca carte per colore es. "W", "U", "B", "R", "G"
    List<Card> findByColorsContaining(String color);

 // qui utilizziamo una Query personalizzata  — cerca carte con prezzo EUR sotto una soglia
 // es. cerca tutte le carte che costano meno di 5€ ecc
 @Query("SELECT c FROM Card c WHERE c.priceEur <= :maxPrice AND c.priceEur IS NOT NULL ORDER BY c.priceEur DESC")
 List<Card> findByMaxPriceEur(@Param("maxPrice") Double maxPrice);
 
}