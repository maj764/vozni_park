
CREATE TABLE IF NOT EXISTS settings (
  id SERIAL PRIMARY KEY,
  uporabnik_id INT NOT NULL UNIQUE REFERENCES uporabniki(id) ON DELETE CASCADE,
  bg_color VARCHAR(20) NOT NULL DEFAULT '#1e1e1e',
  text_color VARCHAR(20) NOT NULL DEFAULT '#ffffff',
  accent_color VARCHAR(20) NOT NULL DEFAULT '#4f8cff'
);


CREATE OR REPLACE FUNCTION settings_get(u_id INT)
RETURNS TABLE(
  bg_color VARCHAR,
  text_color VARCHAR,
  accent_color VARCHAR
)
AS $$
BEGIN
  INSERT INTO settings (uporabnik_id)
  VALUES (u_id)
  ON CONFLICT (uporabnik_id) DO NOTHING;

  RETURN QUERY
  SELECT s.bg_color, s.text_color, s.accent_color
  FROM settings s
  WHERE s.uporabnik_id = u_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION settings_update(u_id INT, p_bg VARCHAR, p_text VARCHAR, p_accent VARCHAR)
RETURNS VOID AS $$
BEGIN
  INSERT INTO settings (uporabnik_id, bg_color, text_color, accent_color)
  VALUES (u_id, p_bg, p_text, p_accent)
  ON CONFLICT (uporabnik_id) DO UPDATE
  SET bg_color = EXCLUDED.bg_color,
      text_color = EXCLUDED.text_color,
      accent_color = EXCLUDED.accent_color;
END;
$$ LANGUAGE plpgsql;
