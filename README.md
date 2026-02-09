# vozni_park
Sistem za upravljanje voznega parka podjetja

Dokumentacija: https://scvsi-my.sharepoint.com/:w:/g/personal/maj_kovac_scv_si/IQCTEf3oVIY0Ro_Byx0YyHkEAXmAA65UYrzL8Y5Tq-t7czI?e=4D6vNi
# Avtopark – JavaFX + Spring Boot + PostgreSQL (Neon)

Projekt je aplikacija za upravljanje avtoparka:
- uporabniki (registracija, prijava)
- vozila
- servisi
- stroški
- settings

Backend je narejen v Spring Boot, frontend pa kot JavaFX.

Zahteve za zagon

- Java17+
- Gradle
- Postgresql baza na racunalniku ali online hosting
- IntelliJ IDEA (priporočeno)

BAZA
Baza je na zunanjem strežniku (Neon).
V sql editorju zaženi kodo iz vseh .sql ali .ddl datotek v baza branchu.

BACKEND
Prenesi datoteke iz Java brancha
V root backend projekta ustvari datoteko .env ki naj vsebuja:
```env
DB_URL=jdbc:postgresql://<HOST>/<DBNAME>?sslmode=require
DB_USER=<USERNAME>
DB_PASS=<PASSWORD>
```
V terminalu v mapi z backendom zaženi:
gradlew bootRun

Backend se bo zagnal na: http://localhost:8080

FRONTEND
Prenesi datoteke iz frontend brancha.
V intellij odpri projekt in app.main ali pa v gradle terminalu zaženi:
gradlew run


Zagnala se bo aplikacija na kateri se lahko registriraš ali prijaviš in upravljaš z vozili, servisi in stroški.
