/*insert vozilo*/
CREATE OR REPLACE FUNCTION insert_vozilo(v_registrska varchar, v_letnik int,
v_kw int, v_km int, v_opis text, u_id int, v_model varchar, v_znamka varchar)
RETURNS VOID AS
    $$
    BEGIN
        INSERT INTO vozila(registrska, letnik, kw, kilometri, opis, odgovorni_uporabnik_id, model, znamka)
        VALUES (v_registrska, v_letnik, v_kw, v_km, v_opis, u_id, v_model, v_znamka);
    END;
    $$
LANGUAGE 'plpgsql';

/*pridobi vozilo*/
CREATE OR REPLACE FUNCTION dobi_vozilo(v_id int)
RETURNS TABLE(
    registrska varchar,
    letnik int,
    kw int,
    km int,
    opis text,
    uporabnik_id int,
    model varchar,
    znamka varchar
    )
AS
    $$
    BEGIN
        RETURN query
            SELECT registrska, letnik, kw, kilometri, opis, odgovorni_uporabnik_id, model, znamka
            FROM vozila
            WHERE id = v_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*seznam vozil*/
CREATE OR REPLACE FUNCTION vozila_list()
RETURNS TABLE (
  id int,
  registrska varchar,
  letnik int,
  kw int,
  kilometri int,
  opis text,
  odgovorni_uporabnik_id int,
  model varchar,
  znamka varchar
)
AS
    $$
    BEGIN
      RETURN QUERY 
      SELECT
        v.id, v.registrska, v.letnik, v.kw, v.kilometri, v.opis, v.odgovorni_uporabnik_id, v.model, v.znamka
      FROM vozila v;
    END;
    $$
LANGUAGE 'plpgsql';

/*uredi vozilo*/
CREATE OR REPLACE FUNCTION update_vozilo(v_id int, v_registrska varchar, v_letnik int, v_kw int,
                            v_km int, v_opis text, v_oup int, v_model varchar, v_znamka varchar)
RETURNS VOID AS
    $$
    BEGIN
        UPDATE vozila
        SET registrska = v_registrska,
            letnik = v_letnik,
            kw = v_kw,
            kilometri = v_km,
            opis = v_opis,
            odgovorni_uporabnik_id = v_oup,
            model = v_model,
            znamka = v_znamka
        WHERE id = v_id;
    END;
    $$
LANGUAGE 'plpgsql';

/*brisanje vozil*/
CREATE OR REPLACE FUNCTION brisi_vozilo(v_id int)
RETURNS VOID AS
    $$
    BEGIN
       DELETE FROM vozila
        WHERE id = v_id;
    END;
    $$
LANGUAGE 'plpgsql';