/*ustvari strosek*/
CREATE OR REPLACE FUNCTION ustvari_strosek(s_datum date, s_kategorija varchar, s_opis text,
s_znesek real, v_id int, s_id int, u_id int)
RETURNS VOID AS
    $$
    BEGIN
        INSERT INTO stroski (datum, kategorija, opis, znesek, vozilo_id, servis_id, uporabnik_id) 
        VALUES (s_datum, s_kategorija, s_opis, s_znesek, v_id, s_id, u_id);
    END;
    $$
LANGUAGE 'plpgsql';

/*beri strosek*/
CREATE OR REPLACE FUNCTION dobi_strosek(s_id int)
RETURNS TABLE(
    datum date,
    kategorija varchar,
    opis text,
    znesek real,
    v_id int,
    ser_id int,
    u_id int
    )
AS
    $$
    BEGIN
        RETURN QUERY 
        SELECT datum, kategorija, opis, znesek, vozilo_id, servis_id, uporabnik_id
        FROM stroski
        WHERE id = s_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*vsi stroski vozila*/
CREATE OR REPLACE FUNCTION stroski_vozila(v_id int)
RETURNS TABLE(
    id int,
    datum date,
    kategorija varchar,
    opis text,
    znesek real,
    s_id int,
    u_id int
    )
AS
    $$
    BEGIN
       RETURN QUERY 
        SELECT id, datum, kategorija, opis, znesek, servis_id, uporabnik_id 
        FROM stroski
        WHERE vozilo_id = v_id
        ORDER BY datum;
    END;
    $$
LANGUAGE 'plpgsql';

/*posodobi strosek*/
CREATE OR REPLACE FUNCTION update_strosek (s_id int, s_datum date, s_kategorija varchar, s_opis text, 
s_znesek real, v_id int, ser_id int, u_id int)
RETURNS VOID AS
    $$
    BEGIN
       UPDATE stroski
        SET datum = s_datum,
            kategorija = s_kategorija,
            opis = s_opis,
            znesek = s_znesek,
            vozilo_id = v_id,
            servis_id = ser_id,
            uporabnik_id = u_id
        WHERE id = s_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*brisi strosek*/
CREATE OR REPLACE FUNCTION brisi_strosek(s_id int)
RETURNS VOID AS
    $$
    BEGIN
        DELETE FROM stroski
        WHERE id = s_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*logs - pregled logov*/
CREATE OR REPLACE FUNCTION load_logs()
RETURNS TABLE(
    id int,
    tabela varchar,
    datum date,
    akcija varchar,
    u_id int
    )
AS
    $$
    BEGIN
        RETURN query 
        SELECT id, tabela, datum, akcija, uporabnik_id
        FROM logs;
    END;
    $$
LANGUAGE 'plpgsql';