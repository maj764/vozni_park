
CREATE TABLE IF NOT EXISTS public.user_settings (
  id serial PRIMARY KEY,
  uporabnik_id int NOT NULL REFERENCES public.uporabniki(id) ON DELETE CASCADE,
  key varchar(50) NOT NULL,
  value text NOT NULL,
  CONSTRAINT uq_user_settings UNIQUE (uporabnik_id, key)
);

CREATE OR REPLACE FUNCTION public.user_settings_init(p_uporabnik_id int)
RETURNS void AS
$$
BEGIN
  INSERT INTO public.user_settings(uporabnik_id, key, value) VALUES
    (p_uporabnik_id, 'bg_color', '#111827'),
    (p_uporabnik_id, 'text_color', '#E5E7EB'),
    (p_uporabnik_id, 'accent_color', '#22C55E')
  ON CONFLICT (uporabnik_id, key) DO NOTHING;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION public.user_settings_list(p_uporabnik_id int)
RETURNS TABLE(key varchar, value text) AS
$$
BEGIN
  PERFORM public.user_settings_init(p_uporabnik_id);

  RETURN QUERY
  SELECT s.key, s.value
  FROM public.user_settings s
  WHERE s.uporabnik_id = p_uporabnik_id
  ORDER BY s.key;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION public.user_settings_get(p_uporabnik_id int, p_key varchar)
RETURNS TABLE(key varchar, value text) AS
$$
BEGIN
  PERFORM public.user_settings_init(p_uporabnik_id);

  RETURN QUERY
  SELECT s.key, s.value
  FROM public.user_settings s
  WHERE s.uporabnik_id = p_uporabnik_id AND s.key = p_key;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION public.user_settings_set(p_uporabnik_id int, p_key varchar, p_value text)
RETURNS void AS
$$
BEGIN
  PERFORM public.user_settings_init(p_uporabnik_id);

  INSERT INTO public.user_settings(uporabnik_id, key, value)
  VALUES (p_uporabnik_id, p_key, p_value)
  ON CONFLICT (uporabnik_id, key)
  DO UPDATE SET value = EXCLUDED.value;
END;
$$ LANGUAGE plpgsql;
