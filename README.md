MagicSearch: Advanced MTG Data Engine & REST API

DESCRIZIONE PROGETTO
è un applicazione Back-End sviluppata con java 21 e Spring boot 3.5.
Il sistema implementa una pipeline ETL (Extract, Transform, Load) completa
per digitalizzare i dati delle carte di Magic: The Gathering dalle API ufficiali di Scryfall 
in database locale altamente ottimizzato.

PUNTI DI FORZA DEL PROGETTO
- Modern Java Stack: Sviluppato con Java 21, sfruttando le ultime ottimizzazioni della JVM e le funzionalità di Spring Boot 3.5.
- Reactive Data Sourcing: Utilizzo di Spring WebFlux (WebClient) per il consumo non bloccante di API esterne,
  con gestione intelligente del buffer (10MB) per gestire payload JSON massivi.
- Interactive API Docs: Integrazione nativa con SpringDoc OpenAPI (Swagger) per la documentazione e il testing interattivo degli endpoint.
- Unicode-Ready Database: Schema MySQL configurato specificamente in utf8mb4_unicode_ci per preservare l'integrità dei simboli speciali.

STACK TECNOLOGICO E DIPENDENZE
Modulo-Tecnologia-Utilizzo
Core,Java 21 / Spring Boot 3.5,Motore principale dell'applicazione.
Data, Spring Data JPA / Hibernate,Gestione della persistenza e mappatura ORM.
Networking ,WebFlux / WebClient,Chiamate HTTP asincrone verso Scryfall.
Database,MySQL 8.0,Persistenza dei dati con supporto Unicode completo.
Docs,SpringDoc OpenAPI,Generazione automatica dell'interfaccia Swagger UI.

API ENDPOINT E DOCUMENTAZIONE
Una volta avviata l'applicazione, la documentazione interattiva è disponibile qui:
http://localhost:8080/swagger-ui/index.html

endopint(GET)
Questi endpoint servono per leggere i dati dal database. Restituiscono oggetti JSON o liste di oggetti.

Documentazione-URLcompleto(esempi)

-Tutte le carte,http://localhost:8080/api/cards
-Singola carta per ID,http://localhost:8080/api/cards/(74b4862a-8772-4d7a-8f55-24285b02657d, ecc)
-Ricerca carte per nome,http://localhost:8080/api/cards/search?name=(Venerated rotpriest,Abhorrent oculus,ecc)
-Filtra carte per rarità,http://localhost:8080/api/cards/rarity/(mythic,rare,common,uncommon)
-Filtra carte per set,http://localhost:8080/api/cards/set/(khm,one,dsk,ecc)
-Filtra carte per tipo,http://localhost:8080/api/cards/type?typeLine=(Creature,Planswalker,Enchantment,Scorcery,ecc)
-Filtra carte per colore o combinazione di colori,http://localhost:8080/api/cards/color?color=(usare una singola lettera per cercare carte di un colore,piu lettere dopo la virgola per combinazione di colori R,B,W,U,G )
-Filtra carte per prezzo,http://localhost:8080/api/cards/price?maxPrice=(10.50,8.34.2.56.12.98,ecc)

Endpoint di Sistema e Manutenzione (POST)
Questi endpoint attivano logiche di business pesanti (importazione esterna o pulizia massiva del DB).

// IN FASE DI SVILUPPO DEL README//






















































