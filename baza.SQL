/*
Created: 06/01/2026
Modified: 06/01/2026
Model: PostgreSQL 10
Database: PostgreSQL 10
*/


-- Create tables section -------------------------------------------------

-- Table kraji

CREATE TABLE "kraji"(
 "id" Serial NOT NULL,
 "ime" Character varying(200) NOT NULL,
 "posta" Character varying(50) NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Add keys for table kraji

ALTER TABLE "kraji" ADD CONSTRAINT "PK_kraji" PRIMARY KEY ("id")
;

-- Table uporabniki

CREATE TABLE "uporabniki"(
 "id" Serial NOT NULL,
 "ime" Character varying(50) NOT NULL,
 "priimek" Character varying(50) NOT NULL,
 "email" Character varying(150) NOT NULL,
 "password_hash" Character varying(200) NOT NULL,
 "kraj_id" Integer,
 "datum_ustvarjanja" Date NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table uporabniki

CREATE INDEX "IX_Relationship1" ON "uporabniki" ("kraj_id")
;

-- Add keys for table uporabniki

ALTER TABLE "uporabniki" ADD CONSTRAINT "PK_uporabniki" PRIMARY KEY ("id")
;

ALTER TABLE "uporabniki" ADD CONSTRAINT "email" UNIQUE ("email")
;

-- Table vozila

CREATE TABLE "vozila"(
 "id" Serial NOT NULL,
 "registrska" Character varying(50) NOT NULL,
 "letnik" Integer NOT NULL,
 "kw" Integer,
 "kilometri" Integer NOT NULL,
 "opis" Text,
 "odgovorni_uporabnik_id" Integer,
 "model" Character varying(200) NOT NULL,
 "znamka" Character varying(200) NOT NULL
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table vozila

CREATE INDEX "IX_Relationship4" ON "vozila" ("odgovorni_uporabnik_id")
;

-- Add keys for table vozila

ALTER TABLE "vozila" ADD CONSTRAINT "PK_vozila" PRIMARY KEY ("id")
;

ALTER TABLE "vozila" ADD CONSTRAINT "registrska" UNIQUE ("registrska")
;

-- Table servisi

CREATE TABLE "servisi"(
 "id" Serial NOT NULL,
 "datum" Date NOT NULL,
 "vrsta" Character varying(200) NOT NULL,
 "km" Integer NOT NULL,
 "opis" Text NOT NULL,
 "naslednji_servis" Date,
 "vozilo_id" Integer,
 "uporabnik_id" Integer
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table servisi

CREATE INDEX "IX_Relationship5" ON "servisi" ("vozilo_id")
;

CREATE INDEX "IX_Relationship6" ON "servisi" ("uporabnik_id")
;

-- Add keys for table servisi

ALTER TABLE "servisi" ADD CONSTRAINT "PK_servisi" PRIMARY KEY ("id")
;

-- Table stroski

CREATE TABLE "stroski"(
 "id" Serial NOT NULL,
 "datum" Date NOT NULL,
 "kategorija" Character varying(200) NOT NULL,
 "opis" Text,
 "znesek" Real NOT NULL,
 "vozilo_id" Integer,
 "servis_id" Integer,
 "uporabnik_id" Integer
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table stroski

CREATE INDEX "IX_Relationship7" ON "stroski" ("vozilo_id")
;

CREATE INDEX "IX_Relationship8" ON "stroski" ("servis_id")
;

CREATE INDEX "IX_Relationship9" ON "stroski" ("uporabnik_id")
;

-- Add keys for table stroski

ALTER TABLE "stroski" ADD CONSTRAINT "PK_stroski" PRIMARY KEY ("id")
;

-- Table logs

CREATE TABLE "logs"(
 "id" Serial NOT NULL,
 "tabela" Character varying(50) NOT NULL,
 "datum" Date NOT NULL,
 "akcija" Character varying(200),
 "uporabnik_id" Integer
)
WITH (
 autovacuum_enabled=true)
;

-- Create indexes for table logs

CREATE INDEX "IX_Relationship10" ON "logs" ("uporabnik_id")
;

-- Add keys for table logs

ALTER TABLE "logs" ADD CONSTRAINT "PK_logs" PRIMARY KEY ("id")
;
-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE "uporabniki" ADD CONSTRAINT "Relationship1" FOREIGN KEY ("kraj_id") REFERENCES "kraji" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "vozila" ADD CONSTRAINT "Relationship4" FOREIGN KEY ("odgovorni_uporabnik_id") REFERENCES "uporabniki" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "servisi" ADD CONSTRAINT "Relationship5" FOREIGN KEY ("vozilo_id") REFERENCES "vozila" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "servisi" ADD CONSTRAINT "Relationship6" FOREIGN KEY ("uporabnik_id") REFERENCES "uporabniki" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "stroski" ADD CONSTRAINT "Relationship7" FOREIGN KEY ("vozilo_id") REFERENCES "vozila" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "stroski" ADD CONSTRAINT "Relationship8" FOREIGN KEY ("servis_id") REFERENCES "servisi" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "stroski" ADD CONSTRAINT "Relationship9" FOREIGN KEY ("uporabnik_id") REFERENCES "uporabniki" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "logs" ADD CONSTRAINT "Relationship10" FOREIGN KEY ("uporabnik_id") REFERENCES "uporabniki" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION
;




