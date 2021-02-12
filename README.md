# ISA2021
Predmetni projekat iz predmeta "Internet softverske arhitekture" - E2 - 2020/2021
Stefana Petrović - RA-114/2015

### Napomena 1: Lozinke svih korisnika su "pasPas123".
### Napomena 2: Zbog datuma (formata), kao i animacija i optimizacije, preporučuje se korišćenje Google Chrome pretraživača.
### Napomena 3: Ukoliko želite da isprobate funkcionalnost slanja mejla, registrujte se sa vašim email nalogom, ili promenite u skripti (za pacijenta).

## Zahtevi
- MySQL 8.0
- Maven (korišćena je verzija 3.6.3) - dodati u Environment variables!
- Java (JDK 11.0.6) - dodati u Environment variables!
- Node.js (korišćena je verzija 10.15.0) - dodati u Environment variables!

Preporučuje se praćenje

## Pokretanje

Pokretanje ove dve aplikacije je isto kao i za većinu Spring Boot i Angular aplikacija, dakle:

- preko komandne linije, komandom `mvn spring-boot:run` unutar `/apoteka` foldera, i
- preko komandne linije, komandom `npm start` unutar `/apoteka-frontend` foldera (ukoliko se pokreće prvi put, potrebno je prvo pozvati komandu `npm install` i sačekati kraj njenog izvršenja).

Aplikacije se mogu pokrenuti i u razvojnim okruženjima, poput Eclipse, IntelliJ IDEA za Maven (Spring Boot) projekat, ili Angular razvojnog okruženja baziranog na Eclipse.

Serverska aplikacija trči na `localhost:8080`, dok klijentska na `localhost:4200`, a možete joj pristupiti iz internet pretraživača.
