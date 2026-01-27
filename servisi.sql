/*ustvari servis*/
CREATE OR REPLACE FUNCTION ustvari_servis(s_datum date, s_vrsta varchar, s_km int, s_opis text,
s_naslednji date, v_id int, u_id int, s_znesek real)
RETURNS VOID AS
    $$
    BEGIN
        INSERT INTO servisi (datum, vrsta, km, opis, naslednji_servis, vozilo_id, uporabnik_id)
        VALUES (s_datum, s_vrsta, s_km, s_opis, s_naslednji, v_id, u_id);
        INSERT INTO stroski (datum, kategorija, znesek, vozilo_id, servis_id, uporabnik_id)
        VALUES (s_datum, 'servis', s_znesek, v_id, 
                (SELECT id FROM servisi ORDER BY id DESC LIMIT 1), u_id);
    END;
    $$
LANGUAGE 'plpgsql';

/*dobi podatke o servisu*/
CREATE OR REPLACE FUNCTION dobi_servis(s_id int)
RETURNS TABLE(
    datum date,
    vrsta varchar,
    km int,
    opis text,
    naslednji_servis date,
    v_id int,
    u_id int
             )
AS
    $$
    BEGIN
       RETURN query
        SELECT datum, vrsta, km, opis, naslednji_servis, vozilo_id, uporabnik_id
        FROM servisi
        WHERE id = s_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*pridobi vse servise vozila*/
CREATE OR REPLACE FUNCTION servisi_vozila(v_id int)
RETURNS TABLE(
    id int,
    datum date,
    vrsta varchar,
    km int,
    opis text,
    naslednji_servis date,
    uporabnik_id int
    )
AS
    $$
    BEGIN
        RETURN query
        SELECT id, datum, vrsta, km, opis, naslednji_servis, uporabnik_id
        FROM servisi
        WHERE vozilo_id = v_id
        ORDER BY datum;
    END;
    $$
LANGUAGE 'plpgsql';

/*posodobi servis*/
CREATE OR REPLACE FUNCTION update_servis(s_id int,
    s_datum date,
    s_vrsta varchar,
    s_km int,
    s_opis text,
    s_naslednji_servis date,
    s_vozilo_id int,
    s_uporabnik_id int)
RETURNS VOID AS
    $$
    BEGIN
        UPDATE servisi
        SET datum = s_datum, 
            vrsta = s_vrsta,
            km = s_km,
            opis = s_opis,
            naslednji_servis = s_naslednji_servis,
            vozilo_id = s_vozilo_id,
            uporabnik_id = s_uporabnik_id
        WHERE id = s_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*izbrisi servis*/
CREATE OR REPLACE FUNCTION servis_delete(s_id int)
RETURNS VOID AS
    $$
    BEGIN
        DELETE FROM servisi
        WHERE id = s_id;
    END;
    $$

LANGUAGE 'plpgsql';
