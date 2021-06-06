# ISA2021
Predmetni projekat iz predmeta "Internet softverske arhitekture" - E2 - 2020/2021
Stefana Petrović - RA-114/2015

### Napomena 1: Lozinke svih korisnika su "pasPas123".
### Napomena 2: Zbog datuma (formata i selektora), kao i animacija i optimizacije, preporučuje se korišćenje Google Chrome (ili Chromium-based: Opera, Edge) pretraživača.
### Napomena 3: Ukoliko želite da isprobate funkcionalnost slanja mejla, registrujte se sa vašim email nalogom, ili promenite u skripti.
### Napomena 4: Verzija Angulara je sada 12.0.3, tako da se preporučuje ponavljanje postupka pokretanja (uključujući `npm install` komandu).
### Napomena 5: Dodeljivanje i resetovanje penala se vrši automatski, putem @Scheduled anotacije. Dodeljivanje se vrši svaki dan u 23:59, a resetovanje svakog prvog u mesecu u 00:00. Da biste testirali obe funkcionalnosti, potrebno je izmeniti `cron` parametar prosleđen anotaciji (primer: `cron = "0/15 * * * * ?"` znači da će se metoda izvršavati na svakih 15 sekundi, svakog minuta, svakog sata, svakog dana u mesecu, svakog meseca, svake godine). Za više informacija posetite sajt: [DZone - Scheduler](https://dzone.com/articles/running-on-time-with-springs-scheduled-tasks)

## Zahtevi
- MySQL 8.0.25
- Maven (korišćena je verzija 3.8.1) - dodati u Environment variables!
- Java (JDK 11.0.11) - dodati u Environment variables!
- Node.js (korišćena je verzija 14.17.0) - dodati u Environment variables!

Preporučuje se praćenje zvanične dokumentacije za instalaciju, podešavanje i pokretanje.

## Pokretanje

Pokretanje ove dve aplikacije je isto kao i za većinu Spring Boot i Angular aplikacija, dakle:

- preko komandne linije, komandom `mvn spring-boot:run` unutar `/apoteka` foldera, i
- preko komandne linije, komandom `npm start` unutar `/apoteka-frontend` foldera (ukoliko se pokreće prvi put, potrebno je prvo pozvati komandu `npm install` i sačekati kraj njenog izvršenja).

Aplikacije se mogu pokrenuti i u razvojnim okruženjima, poput Eclipse, IntelliJ IDEA za Maven (Spring Boot) projekat, ili Angular razvojnog okruženja baziranog na Eclipse.

Serverska aplikacija trči na `localhost:8080`, dok klijentska na `localhost:4200`, a možete joj pristupiti iz internet pretraživača.
