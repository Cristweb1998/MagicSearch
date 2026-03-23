package com.magicsearch.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Entità che rappresenta una carta di Magic: The Gathering.
 * I dati vengono importati dall'API di Scryfall (https://scryfall.com).
 * Hibernate mappa automaticamente questa classe sulla tabella "cards" nel database MySQL.
 *
 * @Entity  — dice a Hibernate che questa classe è una tabella del database
 * @Table   — specifica il nome della tabella nel database
 * @Id      — indica la chiave primaria della tabella
 * @Column  — personalizza il nome e le proprietà della colonna nel database
 */
@Entity
@Table(name = "cards")
public class Card {

    // ID univoco della carta preso da Scryfall
    @Id
    @Column(name = "scryfall_id", length = 36)
    private String id;

    // Nome della carta
    @Column(nullable = false)
    private String name;

    // Costo di mana es. {2}{W}{U}
    @Column(name = "mana_cost", length = 50)
    private String manaCost;

    // Costo di mana convertito (numero totale di mana)
    @Column(name = "cmc")
    private Double cmc;

    // Tipo della carta es. "Creature — Dragon"
    @Column(name = "type_line")
    private String typeLine;

    // Testo delle abilità della carta
    @Column(columnDefinition = "TEXT")
    private String oracleText;

    // Forza della creatura es. "1,2,3,4"
    private String power;

    // Resistenza della creatura es. "1,2,3,4"
    private String toughness;

    // punti Fedeltà del planeswalker es. "4"
    private String loyalty;

    // Rarità: common, uncommon, rare, mythic
    @Column(length = 20)
    private String rarity;

    // Codice del set es. "dsk" = Duskmourn
    @Column(name = "set_code", length = 10)
    private String setCode;

    // Nome completo del set es. "Duskmourn: House of Horror"
    @Column(name = "set_name")
    private String setName;

    // URL dell'immagine della carta
    @Column(name = "image_uri", columnDefinition = "TEXT")
    private String imageUri;

    // Colore della carta es. "W,U,G,B,R" = (W)Bianco, (U)Blu, (G)Verde, (B)Nero, (R)Rosso
    // O combinazioni di colori per carta es. "W,G" = Bianco+Verde, "B,R" = Nero+Rosso
    @Column(name = "colors", length = 20)
    private String colors;

    // Prezzo in Euro
    @Column(name = "price_eur")
    private Double priceEur;

    // Prezzo in Dollari
    @Column(name = "price_usd")
    private Double priceUsd;

    // Data di uscita della carta
    @Column(name = "released_at")
    private LocalDate releasedAt;

    /**
     * Costruttore vuoto — obbligatorio per Hibernate.
     * Hibernate lo usa internamente per creare oggetti Card
     * quando legge i dati dal database.
     */
    public Card() {}

    // Costruttore parametrico — utilizzato per inizializzare gli attributi dell'oggetto Card
    public Card(String id, String name, String manaCost, Double cmc, String typeLine,
                String oracleText, String power, String toughness, String loyalty,
                String rarity, String setCode, String setName, String imageUri,
                String colors, Double priceEur, Double priceUsd, LocalDate releasedAt) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.cmc = cmc;
        this.typeLine = typeLine;
        this.oracleText = oracleText;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
        this.rarity = rarity;
        this.setCode = setCode;
        this.setName = setName;
        this.imageUri = imageUri;
        this.colors = colors;
        this.priceEur = priceEur;
        this.priceUsd = priceUsd;
        this.releasedAt = releasedAt;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManaCost() { return manaCost; }
    public void setManaCost(String manaCost) { this.manaCost = manaCost; }

    public Double getCmc() { return cmc; }
    public void setCmc(Double cmc) { this.cmc = cmc; }

    public String getTypeLine() { return typeLine; }
    public void setTypeLine(String typeLine) { this.typeLine = typeLine; }

    public String getOracleText() { return oracleText; }
    public void setOracleText(String oracleText) { this.oracleText = oracleText; }

    public String getPower() { return power; }
    public void setPower(String power) { this.power = power; }

    public String getToughness() { return toughness; }
    public void setToughness(String toughness) { this.toughness = toughness; }

    public String getLoyalty() { return loyalty; }
    public void setLoyalty(String loyalty) { this.loyalty = loyalty; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public String getSetCode() { return setCode; }
    public void setSetCode(String setCode) { this.setCode = setCode; }

    public String getSetName() { return setName; }
    public void setSetName(String setName) { this.setName = setName; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public String getColors() { return colors; }
    public void setColors(String colors) { this.colors = colors; }

    public Double getPriceEur() { return priceEur; }
    public void setPriceEur(Double priceEur) { this.priceEur = priceEur; }

    public Double getPriceUsd() { return priceUsd; }
    public void setPriceUsd(Double priceUsd) { this.priceUsd = priceUsd; }

    public LocalDate getReleasedAt() { return releasedAt; }
    public void setReleasedAt(LocalDate releasedAt) { this.releasedAt = releasedAt; }

    // Metodo toString ereditato dalla superclasse Object,
    // sovrascritto per restituire i valori degli attributi principali della carta
    @Override
    public String toString() {
        return "Card{id='" + id + "', name='" + name + "', rarity='" + rarity + "', set='" + setCode + "'}";
    }
}