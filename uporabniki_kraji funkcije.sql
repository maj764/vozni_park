/*Dobi en kraj*/
SELECT k.id, k.ime, k.posta
FROM public.kraji k
WHERE k.id = p_id;

/*Dobi list krajev*/
SELECT k.id, k.ime, k.posta
FROM public.kraji k
ORDER BY k.posta, k.ime;

/*registracija*/
CREATE OR REPLACE function registracija(u_ime varchar, u_priimek varchar, u_email varchar, pass varchar, kraj varchar, datum date)
RETURNS void AS
    $$
    DECLARE v_hash VARCHAR(32);
    BEGIN
          v_hash := extensions.crypt(pass, extensions.gen_salt('bf'));
        INSERT INTO uporabniki (ime, priimek, email, password_hash, kraj_id, datum_ustvarjanja)
        VALUES (u_ime, u_priimek, u_email, v_hash, (SELECT id FROM kraji WHERE LOWER(ime) = LOWER(kraj)), datum);
    END;
    $$
LANGUAGE 'plpgsql';

/*prijava*/
CREATE OR REPLACE FUNCTION prijava(u_email VARCHAR, u_password TEXT)
RETURNS TABLE(
  id INT,
  ime VARCHAR,
  priimek VARCHAR,
  email VARCHAR,
  kraj_id INT
)
AS
    $$
DECLARE
  pass_hash TEXT;
BEGIN
  SELECT u.password_hash
  INTO pass_hash
  FROM public.uporabniki u
  WHERE lower(u.email) = lower(u_email);

  IF extensions.crypt(u_password, pass_hash) <> pass_hash THEN
    raise exception 'INVALID_CREDENTIALS' using errcode = '28000';
  ELSE
      RETURN QUERY
          SELECT u.id, u.ime, u.priimek, u.email, u.kraj_id
          FROM uporabniki u
          WHERE lower(u.email) = lower(u_email);
  END IF;
END;
$$
LANGUAGE 'plpgsql';

/*brisanje uporabnika*/
CREATE OR REPLACE FUNCTION delete_uporabnik(u_email varchar)
RETURNS VOID AS
    $$
    BEGIN
        DELETE FROM uporabniki u
        WHERE LOWER(u_email) = u.email;
    END;
    $$
LANGUAGE 'plpgsql';

/*pridobi uporabnika*/
CREATE OR REPLACE FUNCTION dobi_uporabnika(u_id int)
RETURNS TABLE(
  ime VARCHAR,
  priimek VARCHAR,
  email VARCHAR,
  kraj_id INT
)
AS
$$
    BEGIN
        RETURN QUERY
          SELECT u.ime, u.priimek, u.email, u.kraj_id
          FROM uporabniki u
          WHERE u.id = u_id;
    END;
$$
LANGUAGE 'plpgsql';
