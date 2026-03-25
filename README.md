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
-Singola carta per ID,http://localhost:8080/api/cards/ cerchiamo la carta attraverso il suo  id (74b4862a-8772-4d7a-8f55-24285b02657d, ecc)
-Ricerca carte per nome,http://localhost:8080/api/cards/search?name= inseriamo il nome della carta che stiamo cercando (Venerated rotpriest,Abhorrent oculus,ecc)
-Filtra carte per rarità,http://localhost:8080/api/cards/rarity/ cerchiamo le rarita delle carte (mythic,rare,common,uncommon)
-Filtra carte per set,http://localhost:8080/api/cards/set/ cerchiamo un set di carte che vogliamo (khm,one,dsk,ecc)
-Filtra carte per tipo,http://localhost:8080/api/cards/type?typeLine= cerchiamo il tipo di carte che vogliamo (Creature,Planswalker,Enchantment,Scorcery,ecc)
-Filtra carte per colore o combinazione di colori Il sistema utilizza le iniziali standard del formato Magic: The Gathering. La ricerca viene effettuata tramite l'operatore LIKE %color% sulla stringa dei colori salvata nel database. Iniziali Supportate W (White) | U (Blue) | B (Black) | R (Red) | G (Green) esempio http://localhost:8080/api/cards/color?color=R questa URL con la R trova tute le carte che sono rosso mentre localhost:8080/api/cards/color?color=R,W trova tutte le carte multicolore che sono sia rosse che bianche.
-Filtra carte per prezzo,http://localhost:8080/api/cards/price?maxPrice= cerchiamo tutte le carte in base al prezzo mettiamo (10.50,8.34.2.56.12.98,ecc)

Endpoint di Sistema e Manutenzione (POST)
Questi endpoint attivano logiche di business pesanti (importazione esterna o pulizia massiva del DB).

Descrizione-URL Completo (Esempio)
Importazione Set,	http://localhost:8080/api/cards/import/ importazione del set (khm,dsk,ecc)
Pulizia Nomi DB,	http://localhost:8080/api/cards/cleanup/names pulizia del DB



Il progetto implementa una logica di sanificazione dati per correggere anomalie comuni nelle API esterne (come gli spazi non-breaking Unicode \u00A0). I nomi vengono normalizzati automaticamente durante l'importazione, garantendo ricerche esatte e database pulito.

 Installazione Rapida
Clona il repository.

Crea un database MySQL Magic_db con codifica utf8mb4_unicode_ci.

Configura le tue credenziali in src/main/resources/application.properties.

Esegui mvn spring-boot:run.

AutoreCristian Bruno.




















































